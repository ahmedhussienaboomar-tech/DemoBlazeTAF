package com.automationexercices.drivers;
/// الخلاصة: Browser مش هو اللي بيفتح المتصفح؛ هو اللي بيحدد أي Factory مسؤولة عن إنشائه.
public enum Browser {
    CHROME{
        @Override
        public AbstractDriver getDriverFactory() {
            return new ChromeFactory();
        }
    },
    EDGE{
        @Override
        public AbstractDriver getDriverFactory() {
            return new EdgeFactory();
        }
    },
    FIREFOX{
        @Override
        public AbstractDriver getDriverFactory() {
            return new FirefoxFactory();
        }
    },
    SAFARI{;
        @Override
        public AbstractDriver getDriverFactory() {
            return new SafariFactory();
        }
    };

    public abstract AbstractDriver getDriverFactory();
}
