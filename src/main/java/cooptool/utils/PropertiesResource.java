package cooptool.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesResource {

    private static Properties getProperties(String url) {

        Properties properties = new Properties();

        try (InputStream inputStream = PropertiesResource.class.getClassLoader().getResourceAsStream(url)) {

            if (inputStream == null) {
                throw new NullPointerException("No properties file specify");
            }

            properties.load(new InputStreamReader(inputStream));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static Properties getDatabaseProperties() {
        return getProperties("config/database.properties");
    }

    public static Properties getMailProperties() {
        return getProperties("config/mail.properties");
    }
}
