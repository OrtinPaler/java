package courseJava;

import java.io.*;
import java.util.Properties;

public class Config {
    boolean resolution;
    boolean autotest;

    public static void load_configuration(Config config) throws IOException {
        File file = new File("D:/5_semester/java/javaCourse/data/configuration.properties");
        Properties properties = new Properties();
        properties.load(new FileReader(file));

        String resolution_string = properties.getProperty("writeLog");
        config.resolution = resolution_string.equalsIgnoreCase("true");

        String autotest_string = properties.getProperty("autoTest");
        config.autotest = autotest_string.equalsIgnoreCase("true");
    }

    public static void store_configuration(boolean writeLog, boolean autoTest) throws IOException {
        File file = new File("D:/5_semester/java/javaCourse/data/configuration.properties");
        Properties properties = new Properties();
        properties.setProperty("writeLog", Boolean.toString(writeLog));
        properties.setProperty("autoTest", Boolean.toString(autoTest));
        properties.store(new FileOutputStream(file), null);
    }
}
