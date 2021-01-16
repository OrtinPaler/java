package courseJava;

import java.io.*;               // подключаем все файлы проекта
import java.util.Properties;    // подключаем класс для работы с файлами конфигурации

public class Config {
    boolean resolution; // запись в лог
    boolean autotest;   // автотесты
    String data_stock;  // путь к файлу склада
    String data_trains; // путь к файлу поездов
    String data_users;  // путь к файлу базы данных пользователей

    // метод для загрузки данных из файла конфигурации
    public static void load_configuration(Config config) throws IOException {
        // чтение настроек из файла конфигурации
        File file = new File("D:/5_semester/java/javaCourse/data/configuration.properties");
        Properties properties = new Properties();
        properties.load(new FileReader(file));

        // считывание настроек
        String resolution_string = properties.getProperty("writeLog");
        config.resolution = resolution_string.equalsIgnoreCase("true");

        String autotest_string = properties.getProperty("autoTest");
        config.autotest = autotest_string.equalsIgnoreCase("true");

        config.data_stock = properties.getProperty("dataStock");
        config.data_trains = properties.getProperty("dataTrains");
        config.data_users = properties.getProperty("dataUsers");
    }

    // метод для сохранения новых данных в файл конфигурации
    public static void store_configuration(boolean writeLog, boolean autoTest, String dataStock, String dataTrains,
                                           String dataUsers) throws IOException {
        // записываем настройки в файл конфигурации
        File file = new File("D:/5_semester/java/javaCourse/data/configuration.properties");
        Properties properties = new Properties();

        // запись новых значений
        properties.setProperty("writeLog", Boolean.toString(writeLog));
        properties.setProperty("autoTest", Boolean.toString(autoTest));
        properties.setProperty("dataStock", dataStock);
        properties.setProperty("dataTrains", dataTrains);
        properties.setProperty("dataUsers", dataUsers);

        properties.store(new FileOutputStream(file), null);
    }
}
