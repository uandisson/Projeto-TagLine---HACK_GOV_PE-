package org.osmdroid.config;

public class Configuration {
    private static IConfigurationProvider ref;

    public Configuration() {
    }

    public static synchronized IConfigurationProvider getInstance() {
        IConfigurationProvider iConfigurationProvider;
        IConfigurationProvider iConfigurationProvider2;
        synchronized (Configuration.class) {
            if (ref == null) {
                new DefaultConfigurationProvider();
                ref = iConfigurationProvider2;
            }
            iConfigurationProvider = ref;
        }
        return iConfigurationProvider;
    }

    public static void setConfigurationProvider(IConfigurationProvider instance) {
        ref = instance;
    }
}
