package courseJava;

import java.io.*;               // подключаем все файлы проекта
import java.util.ArrayList;     // подключаем класс для работы со списками
import java.util.Scanner;       // подключаем класс для обработки ввода

// перечисление для прав пользователей
enum UserRights {
    regular,
    root
}

public class Authentication implements Serializable {
    private final String login;       // логин пользователя
    private final String password;    // пароль пользователя
    private final UserRights rights;  // права пользователя
    private final String firstname;   // имя пользователя
    private final String lastname;    // фамилия пользователя

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

    // свойство для вывода логина пользователя
    public String get_login() { return this.login; }

    // метод для регистрации в базе данных
    public static void registration(ArrayList<Authentication> base) throws IOException {
        String login;       // логин пользователя
        String password;    // пароль пользователя
        String firstname;   // имя пользователя
        String lastname;    // фамилия пользователя

        while (true) {
            MethodsForMain.clear_terminal();
            System.out.print("@Регистрация. Этап 1.\n\n");
            Scanner in_0 = new Scanner(System.in);
            System.out.print("Придумайте логин: ");
            login = in_0.next();

            // если логин не уникальный, то запрашиваем его еще раз
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
            if (!unique) continue;
            break;
        }
        while (true) {
            MethodsForMain.clear_terminal();
            System.out.print("@Регистрация. Этап 2.\n\n");
            Console terminal = System.console();
            password = new String(terminal.readPassword("Придумайте пароль: "));

            // если пароли не совпадают, то запрашиваем его еще раз
            if (!new String(terminal.readPassword("Подтвердите пароль: ")).equalsIgnoreCase(password)) {
                MethodsForMain.clear_terminal();
                System.out.print("ОШИБКА!\nПароли не совпадают, попробуйте снова...\n\n");
                MethodsForMain.pause_terminal();
                continue;
            }
            break;
        }
        // запрашиваем имя пользователя
        MethodsForMain.clear_terminal();
        System.out.print("@Регистрация. Этап 3.\n\n");
        Scanner in_1 = new Scanner(System.in);
        System.out.print("Введите имя: ");
        firstname = in_1.next();

        // запрашиваем фамилию пользователя
        MethodsForMain.clear_terminal();
        System.out.print("@Регистрация. Этап 4.\n\n");
        Scanner in_2 = new Scanner(System.in);
        System.out.print("Введите фамилию: ");
        lastname = in_2.next();

        // добавляем пользователя в базу данных
        base.add(new Authentication(login, password, UserRights.regular, firstname, lastname));
        // сохраняем обновленную базу данных
        saving_user(base);
        Logs.log("ДОБАВЛЕН НОВЫЙ ПОЛЬЗОВАТЕЛЬ: " + login);
    }

    // метод для авторизации в приложении
    public static Authentication authorization(ArrayList<Authentication> base) throws IOException {
        // берем данные из файла конфигурации
        Config config = new Config();
        Config.load_configuration(config);

        while (true) {
            // запрашиваем логин
            MethodsForMain.clear_terminal();
            Scanner in_0 = new Scanner(System.in);
            System.out.print("Добро пожаловать в @TrainLogistics\n\nЛогин: ");
            String login = in_0.next();

            // запрашиваем пароль
            // (класс Console присваивает переменной terminal null при запуске программы из IDE)
            Console terminal = System.console();
            String password = new String(terminal.readPassword("Пароль: "));

            // поиск пользователя в базе данных
            for (Authentication user : base)
                if (login.equalsIgnoreCase(user.login) && password.equalsIgnoreCase(user.password))
                    return user;

            // если польщователь не найден, то запрашиваем данные снова
            MethodsForMain.clear_terminal();
            System.out.print("ОШИБКА!\nВы ввели неверный логин или пароль, попробуйте снова...\n\n");
            Logs.log("ОШИБКА (метод authorization) - ПОПЫТКА ВВОДА НЕВЕРНОГО ЛОГИНА ИЛИ ПАРОЛЯ...");
            MethodsForMain.pause_terminal();
        }
    }

    // метод для сохранения пользователя в базу данных
    public static void saving_user(ArrayList<Authentication> new_users) throws IOException {
        // берем данные из файла конфигурации
        Config config = new Config();
        Config.load_configuration(config);

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("D:/" + config.data_users))) {
            // записываем новые данные в файл
            oos.writeObject(new_users);

            /*MethodsForMain.clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о новом пользователе успешно сохранены...\n\n");
            MethodsForMain.pause_terminal();*/
            Logs.log("ДАННЫЕ О НОВОМ ПОЛЬЗОВАТЕЛЕ СОХРАНЕНЫ");
        }
        catch (Exception ex) {
            MethodsForMain.clear_terminal();
            System.out.print("ОШИБКА!\nДанные о новом пользователе не сохранены...\n\n");
            Logs.log("ОШИБКА (метод saving_user) - ДАННЫЕ О НОВОМ ПОЛЬЗОВАТЕЛЕ НЕ СОХРАНЕНЫ...");
            MethodsForMain.pause_terminal();
        }
    }

    // метод для загрузки пользователей из базы данных
    public static void loading_user(ArrayList<Authentication> users) throws IOException {
        // берем данные из файла конфигурации
        Config config = new Config();
        Config.load_configuration(config);

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("D:/" + config.data_users))) {
            // читаем из файла
            ArrayList<?> new_objects = (ArrayList<?>)ois.readObject();
            ArrayList<Authentication> new_users = new ArrayList<>();
            assert new_objects != null;
            for(Object element : new_objects)
                new_users.add((Authentication)element);
            users.addAll(new_users);

            /*MethodsForMain.clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о пользователях успешно загружены...\n\n");
            MethodsForMain.pause_terminal();*/
            Logs.log("ДАННЫЕ О ПОЛЬЗОВАТЕЛЯХ ЗАГРУЖЕНЫ");
        }
        catch (Exception ex) {
            MethodsForMain.clear_terminal();
            System.out.print("ОШИБКА!\nДанные о пользователях не загружены...\n\n");
            Logs.log("ОШИБКА (метод loading_user) - ДАННЫЕ О ПОЛЬЗОВАТЕЛЯХ НЕ ЗАГРУЖЕНЫ...");
            MethodsForMain.pause_terminal();
        }
    }
}
