package courseJava;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logs {

    public static void log(String log, boolean resolution) {
        if (resolution) {
            try(FileWriter writer = new FileWriter("D:/5_semester/java/javaCourse/data/logs.txt", true)) {
                writer.write(new Date() + ": " + log + "\n");
                writer.flush();
            }
            catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
