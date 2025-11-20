package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new FileInputStream("src/test/resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getToken() {
        return prop.getProperty("github.token");
    }

    public static String getOwner() {
        return prop.getProperty("github.owner");
    }

    public static String getRepo() {
        return prop.getProperty("github.repo");
    }

    public static String getGitHubBaseUri() {
        return prop.getProperty("github.baseuri");
    }
}
