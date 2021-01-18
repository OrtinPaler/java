/*
 * Базовые команды для запуска приложения в терминале:
 * javac -encoding "UTF-8" -d build src/courseJava/*.java
 * java courseJava/Main
 */
package courseJava;

import java.io.IOException; // подключаем класс для обработки исключений

public class Main {
    public static void main(String[] args) throws IOException {
        // запуск главного метода
        MethodsForMain.menu();

        /*ArrayList<Authentication> base = new ArrayList<>();
        base.add(new Authentication("ortin", "Misha", UserRights.root, "Mikhail", "Misharin"));
        Authentication.saving_user(base);*/
    }
}
