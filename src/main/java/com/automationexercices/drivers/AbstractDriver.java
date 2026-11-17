package com.automationexercices.drivers;

import com.automationexercices.utils.dataReader.PropertyReader;
import org.openqa.selenium.WebDriver;
/*
* ليه استخدمت abstract class؟
لأن كل Browser له طريقة إنشاء وإعدادات ممكن تختلف عن غيره، لكن في نفس الوقت كل الـ Factories بتشترك في فكرة أساسية: إنشاء WebDriver.

فبدل ما تكرر تعريف العقد ده بشكل منفصل، عملت Class أساسية بتقول لكل Factory:
لازم توفري Implementation للميثود createDriver().

الميثود دي اسمها abstract method؛ لأنها متعرفة من غير Body.
* */

public abstract class AbstractDriver {
    protected final String remoteHost = PropertyReader.getProperty("remoteHost");
    protected final String remotePort = PropertyReader.getProperty("remotePort");

    /*
    * protected بتسمح باستخدام المتغير داخل الكلاس نفسه، وداخل الكلاسات الوارثة منه، مع مراعاة قواعد الوصول الخاصة بالـ packages.

ده مناسب هنا لأن ChromeFactory وEdgeFactory وغيرهم ممكن يحتاجوا عنوان Selenium Grid والمنفذ.

أما final فمعناها إن مرجع المتغير بيتحدد مرة واحدة، ومش بيتعاد إسناده بعد كده.
*/
    public abstract WebDriver createDriver();
    /// كل Factory ملموسة، زي ChromeFactory، لازم تعمل Override للميثود دي علشان تقدر تتعامل مع إنشاء المتصفح بالطريقة المناسبة.

}

// abstact class not having any implementation of createDriver method, it is just a blueprint for the concrete driver classes to implement their own version of createDriver method.
// اللي بيورثها هو اللي بيعملها implementation.