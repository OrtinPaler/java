package courseJava;

import java.io.FileWriter;      // подлючаем класс для записи в текстовый файл
import java.io.IOException;     // подключаем класс для обработки исключений
import java.util.Date;          // подкючаем класс для работы с датой

public class Logs {
    // метод для записи в лог
    public static void log(String log) throws IOException {
        // берем данные из файла конфигурации
        Config config = new Config();
        Config.load_configuration(config);

        if (config.resolution) {
            try(FileWriter writer = new FileWriter("D:/5_semester/java/javaCourse/data/logs.txt", true)) {
                writer.write(new Date() + ": " + log + "\n");
                writer.flush(); // финализирует выходное состояние, очищая все буферы вывода
            }
            catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
