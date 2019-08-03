package com.example.customviewhandson;

public class Configurator {

    private static volatile Configurator sharedInstance = null;
    public String baseUrl = "https://developer.myntra.com";

    private Configurator() {

    }

    public static Configurator getSharedInstance() {
        if (sharedInstance == null) {
            synchronized (Configurator.class) {
                if (sharedInstance == null) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return new Configurator();
                }
            }
        }
        return sharedInstance;
    }

    public static Configurator getNullInstance () {
        return null;
    }
}
