package courseJava;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// права пользователей
enum UserRights {
    regular,
    root
}

public class Authentication implements Serializable {
    private final String login;       // логин
    private final String password;    // пароль
    private final UserRights rights;  // права пользователя
    private final String firstname;         // имя пользователя
    private final String lastname;          // фамилия пользователя

    // конструктор с параметрами login (логин), password (пароль), rights (права пользователя), firstname (имя) и
    // lastname (фамилия)
    Authentication(String login, String password, UserRights rights, String firstname, String lastname) {
        this.login = login;
        this.password = password;
        this.rights = rights;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // свойство для вывода прав пользователя
    public UserRights get_rights() { return this.rights; }

    // свойство для вывода имени пользователя
    public String get_firstname() { return this.firstname; }

    // свойство для вывода фамилии пользователя
    public String get_lastname() { return this.lastname; }

    public String get_login() { return this.login; }

    // метод для регистрации
    public static void registration(ArrayList<Authentication> base, boolean resolution_logs) {
        String login;
        String password;
        String firstname;
        String lastname;

        while (true) {
            MethodsForMain.clear_terminal();
            System.out.print("@Регистрация. Этап 1.\n\n");
            Scanner in_0 = new Scanner(System.in);
            System.out.print("Придумайте логин: ");
            login = in_0.next();

            boolean unique = true;
            for (Authentication user : base) {
                if (login.equalsIgnoreCase(user.login)) {
                    MethodsForMain.clear_terminal();
                    System.out.print("ОШИБКА!\nЛогин " + login + " уже занят, попробуйте снова...\n\n");
                    MethodsForMain.pause_terminal();
                    unique = false;
                    break;
                }
            }

            if (!unique)
                continue;
            break;
        }

        while (true) {
            MethodsForMain.clear_terminal();
            System.out.print("@Регистрация. Этап 2.\n\n");
            Console terminal = System.console();
            password = new String(terminal.readPassword("Придумайте пароль: "));

            if (!new String(terminal.readPassword("Подтвердите пароль: ")).equalsIgnoreCase(password)) {
                MethodsForMain.clear_terminal();
                System.out.print("ОШИБКА!\nПароли не совпадают, попробуйте снова...\n\n");
                MethodsForMain.pause_terminal();
                continue;
            }
            break;
        }

        MethodsForMain.clear_terminal();
        System.out.print("@Регистрация. Этап 3.\n\n");
        Scanner in_1 = new Scanner(System.in);
        System.out.print("Введите имя: ");
        firstname = in_1.next();

        MethodsForMain.clear_terminal();
        System.out.print("@Регистрация. Этап 4.\n\n");
        Scanner in_2 = new Scanner(System.in);
        System.out.print("Введите фамилию: ");
        lastname = in_2.next();

        base.add(new Authentication(login, password, UserRights.regular, firstname, lastname));
        saving_user(base, resolution_logs);
        Logs.log("ДОБАВЛЕН НОВЫЙ ПОЛЬЗОВАТЕЛЬ: " + login, resolution_logs);
    }

    // метод авторизации
    public static Authentication authorization(ArrayList<Authentication> base, boolean resolution_logs) {
        while (true) {
            MethodsForMain.clear_terminal();
            Scanner in_0 = new Scanner(System.in);
            System.out.print("Добро пожаловать в @Train\n\nЛогин: ");
            String login = in_0.next();

            Console terminal = System.console();
            String password = new String(terminal.readPassword("Пароль: "));

            for (Authentication user : base)
                if (login.equalsIgnoreCase(user.login) && password.equalsIgnoreCase(user.password))
                    return user;

            MethodsForMain.clear_terminal();
            System.out.print("ОШИБКА!\nВы ввели неверный логин или пароль, попробуйте снова...\n\n");
            Logs.log("ОШИБКА (метод authorization) - ПОПЫТКА ВВОДА НЕВЕРНОГО ЛОГИНА ИЛИ ПАРОЛЯ...", resolution_logs);
            MethodsForMain.pause_terminal();
        }
    }

    // метод для сохранения в базу данных пользователя
    public static void saving_user(ArrayList<Authentication> new_users, boolean resolution_logs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("D:/5_semester/java/javaCourse/data/users.bin"))) {

            oos.writeObject(new_users);

            /*MethodsForMain.clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о новом пользователе успешно сохранены...\n\n");
            MethodsForMain.pause_terminal();*/
            Logs.log("ДАННЫЕ О НОВОМ ПОЛЬЗОВАТЕЛЕ СОХРАНЕНЫ", resolution_logs);
        }
        catch (Exception ex) {
            MethodsForMain.clear_terminal();
            System.out.print("ОШИБКА!\nДанные о новом пользователе не сохранены...\n\n");
            Logs.log("ОШИБКА (метод saving_user) - ДАННЫЕ О НОВОМ ПОЛЬЗОВАТЕЛЕ НЕ СОХРАНЕНЫ...", resolution_logs);
            MethodsForMain.pause_terminal();
        }
    }

    // метод для загрузки базы данных пользователей
    public static void loading_user(ArrayList<Authentication> users, boolean resolution_logs) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("D:/5_semester/java/javaCourse/data/users.bin"))) {

            ArrayList<?> new_objects = (ArrayList<?>)ois.readObject();
            ArrayList<Authentication> new_users = new ArrayList<>();
            assert new_objects != null;
            for(Object element : new_objects)
                new_users.add((Authentication)element);

            users.addAll(new_users);

            /*MethodsForMain.clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о пользователях успешно загружены...\n\n");
            MethodsForMain.pause_terminal();*/
            Logs.log("ДАННЫЕ О ПОЛЬЗОВАТЕЛЯХ ЗАГРУЖЕНЫ", resolution_logs);
        }
        catch (Exception ex) {
            MethodsForMain.clear_terminal();
            System.out.print("ОШИБКА!\nДанные о пользователях не загружены...\n\n");
            Logs.log("ОШИБКА (метод loading_user) - ДАННЫЕ О ПОЛЬЗОВАТЕЛЯХ НЕ ЗАГРУЖЕНЫ...", resolution_logs);
            MethodsForMain.pause_terminal();
        }
    }
}
