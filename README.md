# DemoBlaze TAF

A **Test Automation Framework (TAF)** for the [DemoBlaze](https://www.demoblaze.com/) e-commerce demo website. It is built with **Java, Selenium WebDriver, TestNG, and Rest Assured**, with **Allure** reports, logging, screenshots, and video recording.

> 👨‍💻 **Designed and built from scratch by [Ahmed Hussien Aboomar](https://github.com/ahmedhussienaboomar-tech)** – including the framework architecture, utilities, reporting setup, and test scripts.

---

## Features

- **UI automation** with Selenium WebDriver 4 (with DevTools support)
- **API testing** with Rest Assured
- **TestNG** for test execution, grouping, and assertions
- **Allure Reports** with environment info, steps, and attachments
- **Logging** with Log4j 2
- **Data-driven testing** with Excel (Apache POI) and JSON (JsonPath / json-simple)
- **Video recording** of test runs
- **HTML parsing** with Jsoup
- File handling utilities with Apache Commons IO
- Clean build output: all generated files go to `test-outputs/`

<!-- TODO: Add or remove features to match what your framework really does (e.g. Page Object Model, Fluent API, Retry Analyzer, Listeners, Parallel execution, Cross-browser) -->

---

## Tech Stack

| Category | Tool | Version |
|---|---|---|
| Language | Java | 17 |
| Build tool | Maven | 3.9+ |
| UI automation | Selenium (+ DevTools v155) | 4.51.0 |
| Test runner | TestNG | 7.12.0 |
| API testing | Rest Assured | 5.5.1 |
| Reporting | Allure (TestNG) | 3.0.0 |
| AOP for Allure | AspectJ Weaver | 1.9.24 |
| Logging | Log4j 2 | 2.26.1 |
| Excel data | Apache POI | 5.5.1 |
| JSON data | JsonPath / json-simple | 3.0.0 / 1.1.1 |
| Video recording | video-recorder-testng + JAVE | 2.0 / 4.2.0 |
| HTML parsing | Jsoup | 1.23.2 |
| File utilities | Commons IO | 2.22.0 |

---

## Project Structure

```
DemoBlazeTAF/
├── src/                 # Framework code and test scripts
├── test-outputs/        # Generated output (build files, reports, logs, ...)
├── pom.xml              # Maven configuration and dependencies
├── effective-pom        # Resolved Maven POM
├── error-log.txt        # Error log
└── .gitignore
```

<!-- TODO: Expand the src/ section with your real packages, for example:
src/
├── main/java/
│   ├── drivers/      # Browser/driver factory
│   ├── pages/        # Page classes
│   ├── utils/        # Helpers (Excel, JSON, logs, screenshots, waits)
│   └── listeners/    # TestNG listeners
└── test/java/
    └── tests/        # Test classes
-->

---

## Prerequisites

- **JDK 17** or higher
- **Apache Maven 3.9+**
- **Google Chrome** (or the browser configured in the framework)
- **Allure Commandline** (to open the report locally) – [install guide](https://allurereport.org/docs/install/)
- Git

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/ahmedhussienaboomar-tech/DemoBlazeTAF.git
cd DemoBlazeTAF
```

### 2. Install dependencies

```bash
mvn clean install -DskipTests
```

### 3. Run the tests

```bash
mvn clean test
```

<!-- TODO: If you use a TestNG suite file, add: mvn clean test -DsuiteXmlFile=testng.xml -->

---

## Reports

This framework uses **Allure** for reporting. The AspectJ agent is already configured in the Surefire plugin in `pom.xml`, so Allure results are collected automatically during the run.

After the run, generate and open the report:

```bash
allure serve <path-to-allure-results>
```

<!-- TODO: Replace <path-to-allure-results> with your real folder (usually under test-outputs/) -->

TestNG's default reports are also generated inside `test-outputs/target/`.

---

## Application Under Test

[DemoBlaze](https://www.demoblaze.com/) is a public demo store used for practicing test automation. It includes login/sign-up, product browsing, a shopping cart, and an order checkout.

<!-- TODO: List the scenarios you automated, for example: -->

- [ ] Sign up and login
- [ ] Browse categories and product details
- [ ] Add to cart / delete from cart
- [ ] Place an order
- [ ] API tests

---

## About the Author

This framework was **designed and built by me, Ahmed Hussien Aboomar**, as part of my journey in software testing . I worked on every part of it: the framework structure, the utilities, the reporting, logging and video setup, and the test scripts.

- GitHub: [@ahmedhussienaboomar-tech](https://github.com/ahmedhussienaboomar-tech)
- Related project: [Depi_project](https://github.com/ahmedhussienaboomar-tech/Depi_project)
<!-- TODO: Add your LinkedIn link -->

---

## License

<!-- TODO: Add a license (e.g. MIT) or remove this section -->
This project is for educational purposes.
