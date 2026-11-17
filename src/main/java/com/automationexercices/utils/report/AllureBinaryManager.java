
package com.automationexercices.utils.report;

import com.automationexercices.utils.OSUtils;
import com.automationexercices.utils.TerminalUtils;
import com.automationexercices.utils.logs.LogsManager;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// /////////////////////////////////////////////////////// DOWNLOAD AND EXTRACT ALLURE FROM GITHUB RELEASES
// الهدف منه إن المشروع يجهّز أداة Allure تلقائيًا بدل ما كل شخص في الفريق يضطر يثبتها يدويًا.
public class AllureBinaryManager {

/*
الكود بيحاول يعمل الخطوات دي:
1. يحدد إصدار Allure من صفحة الإصدارات.
2. يحدد مسار التنزيل داخل مجلد .m2 في مجلد المستخدم.
3. ينزّل ملف ZIP لو مش موجود.
4. يفك ضغط الملف.
5. يوفر مسار تشغيل الأداة من خلال getExecutable().
*/

    public static void downloadAndExtract() {
        try {
            // Check if a valid local Allure installation already exists
            String localVersion = getLocalVersion();

            if (localVersion != null) {
                LogsManager.info("Allure version " + localVersion
                        + " already exists locally. Skipping download.");
                return;
            }

            // Resolve the latest version only when no valid local installation exists
            String version = LazyHolder.resolveVersion();

            Path extractionDir = Paths.get(
                    AllureConstants.EXTRACTION_DIR.toString(),
                    "allure-" + version
            );

            // Remove incomplete installation if it exists
            if (Files.exists(extractionDir)) {
                try (var paths = Files.walk(extractionDir)) {
                    paths.sorted(Comparator.reverseOrder())
                            .forEach(path -> {
                                try {
                                    Files.deleteIfExists(path);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            });
                }
            }

            if (!OSUtils.getCurrentOS().equals(OSUtils.OS.WINDOWS)) {
                TerminalUtils.executeTerminalCommand(
                        "chmod", "u+x", AllureConstants.USER_DIR.toString()
                );
            }

            Path zipPath = LazyHolder.downloadZip(version);
            LazyHolder.extractZip(zipPath);

            LogsManager.info("Allure version " + version
                    + " downloaded and extracted to: " + extractionDir);

            // Give execute permissions to the binary if not on Windows
            if (!OSUtils.getCurrentOS().equals(OSUtils.OS.WINDOWS)) {
                TerminalUtils.executeTerminalCommand(
                        "chmod", "u+x", getExecutable().toString()
                );
            }

            // Clean up the zip file after extraction
            Files.deleteIfExists(zipPath);

        } catch (Exception e) {
            LogsManager.error("Error downloading or extracting binaries", e.getMessage());
        }
    }

    // دي بترجع المسار المتوقع لملف تشغيل Allure، مع مراعاة إن Windows بيستخدم ملفًا بامتداد .bat.
    public static Path getExecutable() {

        String version = getVersion();

        Path binaryPath = Paths.get(
                AllureConstants.EXTRACTION_DIR.toString(),
                "allure-" + version,
                "bin",
                "allure"
        );

        return OSUtils.getCurrentOS() == OSUtils.OS.WINDOWS
                ? binaryPath.resolveSibling(binaryPath.getFileName() + ".bat")
                : binaryPath;
    }

    // Check local installation first, then resolve the latest version online
    private static String getVersion() {

        String localVersion = getLocalVersion();

        if (localVersion != null) {
            return localVersion;
        }

        return LazyHolder.resolveVersion();
    }

    // Get an existing local Allure version only if its executable is available
    private static String getLocalVersion() {

        try {
            if (!Files.exists(AllureConstants.EXTRACTION_DIR)) {
                return null;
            }

            try (var paths = Files.list(AllureConstants.EXTRACTION_DIR)) {

                return paths
                        .filter(Files::isDirectory)
                        .filter(path -> path.getFileName().toString().startsWith("allure-"))
                        .map(path -> path.getFileName().toString().substring("allure-".length()))
                        .filter(version -> {
                            Path executable = Paths.get(
                                    AllureConstants.EXTRACTION_DIR.toString(),
                                    "allure-" + version,
                                    "bin",
                                    "allure"
                            );

                            if (OSUtils.getCurrentOS() == OSUtils.OS.WINDOWS) {
                                executable = executable.resolveSibling("allure.bat");
                            }

                            return Files.exists(executable);
                        })
                        .findFirst()
                        .orElse(null);
            }

        } catch (Exception e) {
            LogsManager.error("Error checking local Allure installation", e.getMessage());
            return null;
        }
    }

    public static class LazyHolder {

        // get the latest version of allure from github releases page
        private static String resolveVersion() {
            try {
                String url = Jsoup.connect(
                                "https://github.com/allure-framework/allure2/releases/latest"
                        )
                        .followRedirects(true)
                        .execute()
                        .url()
                        .toString();

                return url.split("/tag/")[1];

            } catch (Exception e) {
                LogsManager.error("Failed to resolve Allure version: " + e.getMessage());
                throw new RuntimeException("Failed to resolve Allure version", e);
            }
        }


        //download zip file from github releases

        static Path downloadZip(String version) {
            try {
                // https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.34.1/allure-commandline-2.34.1.zip
                String url = AllureConstants.ALLURE_ZIP_BASE_URL
                        + version
                        + "/allure-commandline-" + version + ".zip";

                // C:\Users\hussi\.m2\repository\allure
                Path zipFile = Paths.get(
                        AllureConstants.EXTRACTION_DIR.toString(),
                        "allure-" + version + ".zip"
                );

                if (!Files.exists(zipFile)) {
                    Files.createDirectories(AllureConstants.EXTRACTION_DIR);

                    try (BufferedInputStream in = new BufferedInputStream(
                            new URI(url).toURL().openStream()
                    );
                         OutputStream out = Files.newOutputStream(zipFile)) {

                        in.transferTo(out);

                    } catch (Exception e) {
                        Files.deleteIfExists(zipFile);
                        LogsManager.error("Invalid URL for Allure download: ", e.getMessage());
                        throw e;
                    }
                }

                return zipFile;

            } catch (Exception e) {
                LogsManager.error("Error downloading Allure zip file", e.getMessage());
                throw new RuntimeException("Failed to download Allure ZIP file", e);
            }
        }

        //extract zip file to extraction directory
        static void extractZip(Path zipPath) {
            try (ZipInputStream zipInputStream =
                         new ZipInputStream(Files.newInputStream(zipPath))) {

                ZipEntry entry;

                while ((entry = zipInputStream.getNextEntry()) != null) {

                    Path filePath = Paths.get(
                            AllureConstants.EXTRACTION_DIR.toString(),
                            entry.getName()
                    ).normalize();

                    // Prevent Zip Slip vulnerability
                    if (!filePath.startsWith(AllureConstants.EXTRACTION_DIR.normalize())) {
                        throw new RuntimeException("Invalid ZIP entry: " + entry.getName());
                    }

                    if (entry.isDirectory()) {
                        Files.createDirectories(filePath);
                    } else {
                        Files.createDirectories(filePath.getParent());
                        Files.copy(zipInputStream, filePath);
                    }

                    zipInputStream.closeEntry();
                }

            } catch (Exception e) {
                LogsManager.error("Error extracting Allure zip file", e.getMessage());
                throw new RuntimeException("Failed to extract Allure ZIP file", e);
            }
        }
    }
}
