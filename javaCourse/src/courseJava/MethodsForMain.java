package courseJava;

import java.io.*;           // подключаем все классы
import java.util.ArrayList; // подключаем класс ArrayList для работы с данными
import java.util.Scanner;   // подключаем класс Scanner для считывания ввода

public class MethodsForMain {
    // метод для "отчистки" терминала
    public static void clear_terminal() { for (int i = 0; i < 50; ++i) System.out.println(); }

    // метод для приостановки терминала
    public static void pause_terminal() {
        System.out.println("Нажмите клавишу Enter, чтобы продолжить...");
        new java.util.Scanner(System.in).nextLine();
    }

    // метод для добавления в список продуктов
    public static void fill_my_products_list(ArrayList<Production> my_products, boolean resolution_logs) {
        Production new_product = new Production();

        Scanner in_0 = new Scanner(System.in);
        System.out.print("\nВведите название товара: ");
        new_product.set_name_product(in_0.nextLine());

        Scanner in_1 = new Scanner(System.in);
        int number;
        while (true) {
            clear_terminal();
            System.out.print("Введите название товара: " + new_product.get_name_product());
            System.out.print("\n\nВыберите тип товара:" +
                    "\n1. Undefined (Неопределенный)" +
                    "\n2. Containers (Контейнеры)" +
                    "\n3. Liquids (Жидкости)" +
                    "\n4. Bulk Cargo (Сыпучие вещества)" +
                    "\n5. Cars (Машины)" +
                    "\nОтвет: ");

            // обрабатываем исключения
            try {
                number = Integer.parseInt(in_1.next());
                if (number < 1 || number > 5) {
                    clear_terminal();
                    System.out.print("ОШИБКА!\nВведите число от 1 до 5...\n\n");
                    Logs.log("ОШИБКА (метод fill_my_products_list) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН [1; 5]...", resolution_logs);
                    pause_terminal();
                    continue;
                }
                break;
            }
            catch (NumberFormatException ex) {
                clear_terminal();
                System.out.print("ОШИБКА!\nВведите число...\n\n");
                Logs.log("ОШИБКА (метод fill_my_products_list) - ПОПЫТКА ВВОДА СТРОКИ ВМЕСТО ЧИСЛА...", resolution_logs);
                pause_terminal();
            }
        }

        switch (number) {
            case 1 -> new_product.set_type_production(ProductionType.undefined);
            case 2 -> new_product.set_type_production(ProductionType.containers);
            case 3 -> new_product.set_type_production(ProductionType.liquids);
            case 4 -> new_product.set_type_production(ProductionType.bulk_cargo);
            case 5 -> new_product.set_type_production(ProductionType.cars);
        }

        my_products.add(new_product);
        Logs.log("В СПИСОК ДОБАВЛЕН НОВЫЙ ПРОДУКТ " + new_product.get_name_product(), resolution_logs);
    }

    // метод для заполнения склада продуктами
    public static void fill_my_stock(Stock my_stock, ArrayList<Production> my_products, boolean resolution_logs) {
        if (my_stock.get_products().size() < my_stock.get_quantity()) {
            Scanner in_0 = new Scanner(System.in);
            int number;
            while (true) {
                System.out.print("Введите количество товаров, которое хотите добавить на склад: ");
                try {
                    number = Integer.parseInt(in_0.next());
                    if (number < 0) {
                        clear_terminal();
                        System.out.print("ОШИБКА!"
                                + "\nКоличество товаров не может быть отрицательным числом. Поробуйте снова...\n\n");
                        Logs.log("ОШИБКА (метод fill_my_stock) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН (0; +oo)...", resolution_logs);
                        pause_terminal();
                        clear_terminal();
                        continue;
                    }
                    else if (number == 0) {
                        clear_terminal();
                        System.out.print("ОШИБКА!"
                                + "\nКоличество товаров не может быть равно 0. Поробуйте снова...\n\n");
                        Logs.log("ОШИБКА (метод fill_my_stock) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН (0; +oo)...", resolution_logs);
                        pause_terminal();
                        clear_terminal();
                        continue;
                    }
                    break;
                }
                catch (NumberFormatException ex) {
                    clear_terminal();
                    System.out.print("ОШИБКА!"
                            + "\nКоличество товаров можно вводить только с помощью цифр. Поробуйте снова...\n\n");
                    Logs.log("ОШИБКА (метод fill_my_stock) - ПОПЫТКА ВВОДА СТРОКИ ВМЕСТО ЧИСЛА...", resolution_logs);
                    pause_terminal();
                    clear_terminal();
                }
            }

            if (number > (my_stock.get_quantity() - my_stock.get_products().size())) {
                clear_terminal();
                System.out.print("ВНИМАНИЕ!"
                        + "\nСклад рассчитан максимально на " + my_stock.get_quantity() + " товаров."
                        + "\nВместе с тем, что вы хотите добавить получится "
                        + (number + my_stock.get_products().size()) + " товаров."
                        + "\nВы можете добавить " + (my_stock.get_quantity() - my_stock.get_products().size())
                        + " товаров.\n\n");
                number = my_stock.get_quantity() - my_stock.get_products().size();
                Logs.log("ПРЕДУПРЕЖДЕНИЕ (метод fill_my_stock) - ПОПЫТКА ДОБАВИТЬ БОЛЬШЕ ТОВАРОВ, ЧЕМ МОЖЕТ УМЕСТИТЬ"
                + "СКЛАД", resolution_logs);
                pause_terminal();
            }

            for (int i = 0; i < number; i++) {
                clear_terminal();
                System.out.print("Товар №" + (i + 1));
                // инициализация списка продуктов
                fill_my_products_list(my_products, resolution_logs);
                clear_terminal();
            }

            // заполняем склад
            for (Production my_product : my_products) {
                my_stock.set_product(my_product);
                Logs.log("НА СКЛАД ПОСТУПИЛ ПРОДУКТ " + my_product.get_name_product(), resolution_logs);
            }

            my_products.clear();

            if (my_stock.get_products().size() == my_stock.get_quantity()) {
                clear_terminal();
                System.out.print("ВНИМАНИЕ! СКЛАД ЗАПОЛНЕН!"
                        + "\nРешение: избавьтесь от части товаров со склада.\n\n");
                Logs.log("ПРЕДУПРЕЖДЕНИЕ (метод fill_my_stock) - СКЛАД ЗАПОЛНЕН ПОСЛЕ ДОБАВЛЕНИЯ ПРОДУКТОВ", resolution_logs);
                pause_terminal();
            }
        }
        else {
            System.out.print("ВНИМАНИЕ! СКЛАД ЗАПОЛНЕН!\nРешение: избавьтесь от части товаров со склада.\n\n");
            Logs.log("ПРЕДУПРЕЖДЕНИЕ (метод fill_my_stock) - СКЛАД ЗАПОЛНЕН, НЕВОЗМОЖНО ДОБАВИТЬ НОВЫЕ ПРОДУКТЫ", resolution_logs);
            pause_terminal();
        }
    }

    // метод для добавления в список вагонов
    public static void fill_my_carriages_list(ArrayList<RailwayCarriage> my_carriages, boolean resolution_logs) {
        RailwayCarriage new_carriage = new RailwayCarriage();
        new_carriage.set_number_carriage(my_carriages.size() + 1);

        Scanner in_0 = new Scanner(System.in);
        int number;
        while (true) {
            clear_terminal();
            System.out.print("Добавление вагона №" + new_carriage.get_number_carriage() +
                    "\nВыберите тип товара:" +
                    "\n1. Undefined (Неопределенный)" +
                    "\n2. Platform for containers (Платформа для контейнеров)" +
                    "\n3. Tanks for liquids (Цистерны для жидкостей)" +
                    "\n4. For bulk cargo (Вагон для сыпучих веществ)" +
                    "\n5. Car platform (Платформа для машин)" +
                    "\nОтвет: ");

            // обрабатываем исключения
            try {
                number = Integer.parseInt(in_0.next());
                if (number < 1 || number > 5) {
                    clear_terminal();
                    System.out.print("ОШИБКА!\nВведите число от 1 до 5...\n\n");
                    Logs.log("ОШИБКА (метод fill_my_carriages_list) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН [1; 5]...", resolution_logs);
                    pause_terminal();
                    continue;
                }
                break;
            }
            catch (NumberFormatException ex) {
                clear_terminal();
                System.out.print("ОШИБКА!\nВведите число...\n\n");
                Logs.log("ОШИБКА (метод fill_my_carriages_list) - ПОПЫТКА ВВОДА СТРОКИ ВМЕСТО ЧИСЛА...", resolution_logs);
                pause_terminal();
            }
        }

        switch (number) {
            case 1 -> new_carriage.set_type_car(CarriageType.undefined);
            case 2 -> new_carriage.set_type_car(CarriageType.platform_for_containers);
            case 3 -> new_carriage.set_type_car(CarriageType.tanks_for_liquids);
            case 4 -> new_carriage.set_type_car(CarriageType.for_bulk_cargo);
            case 5 -> new_carriage.set_type_car(CarriageType.car_platform);
        }

        my_carriages.add(new_carriage);
    }

    // метод для добавления в список поездов
    public static void fill_my_trains_list(ArrayList<Train> my_trains, boolean resolution_logs) {
        Train new_train = new Train();
        Logs.log("СОЗДАН ПОЕЗД №" + new_train.get_number_train(), resolution_logs);
        ArrayList<RailwayCarriage> new_rail_carriages = new ArrayList<>();

        Scanner in_0 = new Scanner(System.in);
        int number;
        while (true) {
            System.out.print("Введите количество вагонов, которое хотите добавить к поезду: ");
            try {
                number = Integer.parseInt(in_0.next());
                if (number < 0) {
                    clear_terminal();
                    System.out.print("ОШИБКА!"
                            + "\nКоличество вагонов не может быть отрицательным числом. Поробуйте снова...\n\n");
                    Logs.log("ОШИБКА (метод fill_my_trains_list) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН (0; +oo)...", resolution_logs);
                    pause_terminal();
                    clear_terminal();
                    continue;
                }
                else if (number == 0) {
                    clear_terminal();
                    System.out.print("ОШИБКА!"
                            + "\nКоличество вагонов не может быть равно 0. Поробуйте снова...\n\n");
                    Logs.log("ОШИБКА (метод fill_my_trains_list) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН (0; +oo)...", resolution_logs);
                    pause_terminal();
                    clear_terminal();
                    continue;
                }
                break;
            }
            catch (NumberFormatException ex) {
                clear_terminal();
                System.out.print("ОШИБКА!"
                        + "\nКоличество вагонов можно вводить только с помощью цифр. Поробуйте снова...\n\n");
                Logs.log("ОШИБКА (метод fill_my_trains_list) - ПОПЫТКА ВВОДА СТРОКИ ВМЕСТО ЧИСЛА...", resolution_logs);
                pause_terminal();
                clear_terminal();
            }
        }

        for (int i = 0; i < number; i++) {
            fill_my_carriages_list(new_rail_carriages, resolution_logs);
            Logs.log("К ПОЕЗДУ №" + new_train.get_number_train() + " ДОБАВЛЕН ВАГОН №" + (i + 1) + ": "
                    + new_rail_carriages.get(i).get_type_carriage(), resolution_logs);
        }

        new_train.set_carriages(new_rail_carriages);
        my_trains.add(new_train);
    }

    // метод для заполнения поезда продукцией
    public static void fill_my_train_product(ArrayList<Train> my_trains, Stock my_stock, boolean resolution_logs) {
        if (my_stock.get_products().size() == 0) {
            System.out.print("ВНИМАНИЕ!\nСклад пуст...\n\n");
            pause_terminal();
        }
        else {
            int answer_product;
            int find = 0;
            while (true) {
                my_stock.stock_info();
                Scanner in_0 = new Scanner(System.in);
                System.out.print("Введите номер товара, который хотите погрузить в поезд: ");

                try {
                    answer_product = Integer.parseInt(in_0.next());
                    if (answer_product < 0 || answer_product > my_stock.get_products().size()) {
                        clear_terminal();
                        System.out.print("ОШИБКА!\nВведите номер товара, который есть складе...\n\n");
                        Logs.log("ОШИБКА (метод fill_my_train_product) - ПОПЫТКА ВВОДА НЕКОРРЕКТНОГО НОМЕРА ТОВАРА...", resolution_logs);
                        pause_terminal();
                        continue;
                    }

                    for (Train train : my_trains) {
                        for (RailwayCarriage carriage : train.get_carriages()) {
                            if (my_stock.get_products().get(answer_product - 1).get_required_type()
                                    == carriage.get_type_carriage()  && carriage.get_full()) {
                                find++;
                                carriage.set_product(my_stock.get_products().get(answer_product - 1));
                                carriage.set_full(true);
                                clear_terminal();
                                System.out.print("Товар был успешно погружен: поезд №" + train.get_number_train() +
                                        ", вагон №" + carriage.get_number_carriage() + "\n\n");
                                Logs.log("ТОВАР " + my_stock.get_products().get(answer_product - 1).get_name_product()
                                        + " БЫЛ УСПЕШНО ПОГРУЖЕН НА ПОЕЗД №" + train.get_number_train()
                                        + " В ВАГОН №" + carriage.get_number_carriage(), resolution_logs);
                                pause_terminal();
                                my_stock.get_products().remove(answer_product - 1);
                                break;
                            }
                        }
                        if (find != 0)
                            break;
                    }

                    if (find == 0) {
                        clear_terminal();
                        System.out.print("ВНИМАНИЕ!\nВыбранный вами товар не на чем везти...\n\n");
                        Logs.log("ПРЕДУПРЕЖДЕНИЕ (метод fill_my_train_product) - НЕТ ПОДХОДЯЩЕГО ВАГОНА ДЛЯ ПЕРЕВОЗКИ"
                                + " ТОВАРА " + my_stock.get_products().get(answer_product - 1).get_name_product(), resolution_logs);
                        pause_terminal();
                    }
                    break;
                }
                catch (NumberFormatException ex) {
                    clear_terminal();
                    System.out.print("ОШИБКА!\nВведите число...\n\n");
                    Logs.log("ОШИБКА (метод fill_my_train_product) - ПОПЫТКА ВВОДА СТРОКИ ВМЕСТО ЧИСЛА...", resolution_logs);
                    pause_terminal();
                }
            }
        }
    }

    // метод для отправления поезда
    public static void train_ready(ArrayList<Train> my_trains, boolean resolution_logs) {
        for (int i = 0; i < my_trains.size(); i++) {
            boolean full = true;
            for (RailwayCarriage carriage : my_trains.get(i).get_carriages()) {
                if (carriage.get_full()) {
                    full = false;
                    break;
                }
            }
            if (full) {
                clear_terminal();
                int way = (int)(1 + Math.random() * 68);
                System.out.print("ВНИМАНИЕ!\nПоезд №" + my_trains.get(i).get_number_train()
                        + " отправляется с " + way + "-го пути.\n\n");
                Logs.log("ПОЕЗД №" + my_trains.get(i).get_number_train() + " ОТПРАВЛЯЕТСЯ С " + way + "-ГО ПУТИ", resolution_logs);
                Logs.log("ПОЕЗД №" + my_trains.get(i).get_number_train() + " УДАЛЕН ИЗ ЖУРНАЛА", resolution_logs);
                my_trains.remove(i);
                pause_terminal();
            }
        }
    }

    // метод для сохранения данных о складе
    public static void saving_stock(Stock my_stock, boolean resolution_logs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("D:/5_semester/java/javaCourse/data/stock.bin"))) {

            oos.writeObject(my_stock);

            clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о складе успешно сохранены...\n\n");
            Logs.log("ДАННЫЕ О СКЛАДЕ УСПЕШНО СОХРАНЕНЫ", resolution_logs);
            pause_terminal();
        }
        catch (Exception ex) {
            clear_terminal();
            System.out.print("ОШИБКА!\nДанные о складе не сохранены...\n\n");
            Logs.log("ОШИБКА (метод saving_stock) - ДАННЫЕ О СКЛАДЕ НЕ СОХРАНЕНЫ...", resolution_logs);
            pause_terminal();
        }
    }

    // метод для сохранения данных о поездах
    public static void saving_trains(ArrayList<Train> my_trains, boolean resolution_logs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("D:/5_semester/java/javaCourse/data/trains.bin"))) {

            oos.writeObject(my_trains);

            clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о поездах успешно сохранены...\n\n");
            Logs.log("ДАННЫЕ О ПОЕЗДАХ УСПЕШНО СОХРАНЕНЫ", resolution_logs);
            pause_terminal();
        }
        catch (Exception ex) {
            clear_terminal();
            System.out.print("ОШИБКА!\nДанные о поездах не сохранены...\n\n");
            Logs.log("ОШИБКА (метод saving_trains) - ДАННЫЕ О ПОЕЗДАХ НЕ СОХРАНЕНЫ...", resolution_logs);
            pause_terminal();
        }
    }

    // метод для загрузки данных о складе
    public static void loading_stock(Stock my_stock, boolean resolution_logs) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("D:/5_semester/java/javaCourse/data/stock.bin"))) {

            Stock new_objects = (Stock)ois.readObject();
            my_stock.set_quantity(new_objects.get_quantity());
            for (Production product : new_objects.get_products())
                my_stock.set_product(product);

            clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о складе успешно загружены...\n\n");
            Logs.log("ДАННЫЕ О СКЛАДЕ УСПЕШНО ЗАГРУЖЕНЫ", resolution_logs);
            pause_terminal();
        }
        catch (Exception ex) {
            clear_terminal();
            System.out.print("ОШИБКА!\nДанные о складе не загружены...\n\n");
            Logs.log("ОШИБКА (метод loading_stock) - ДАННЫЕ О СКЛАДЕ НЕ ЗАГРУЖЕНЫ...", resolution_logs);
            pause_terminal();
        }
    }

    // метод для загрузки данных о поездах
    public static void loading_trains(ArrayList<Train> my_trains, boolean resolution_logs) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("D:/5_semester/java/javaCourse/data/trains.bin"))) {

            ArrayList<?> new_objects = (ArrayList<?>)ois.readObject();
            ArrayList<Train> new_trains = new ArrayList<>();
            assert new_objects != null;
            for(Object element : new_objects)
                new_trains.add((Train)element);

            my_trains.addAll(new_trains);

            clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о поездах успешно загружены...\n\n");
            Logs.log("ДАННЫЕ О ПОЕЗДАХ УСПЕШНО ЗАГРУЖЕНЫ", resolution_logs);
            pause_terminal();
        }
        catch (Exception ex) {
            clear_terminal();
            System.out.print("ОШИБКА!\nДанные о поездах не загружены...\n\n");
            Logs.log("ОШИБКА (метод loading_trains) - ДАННЫЕ О ПОЕЗДАХ НЕ ЗАГРУЖЕНЫ...", resolution_logs);
            pause_terminal();
        }
    }

    // метод для вывода информация о списке поездов
    public static void trains_info(ArrayList<Train> my_trains) {
        if (my_trains.size() == 0) {
            System.out.print("Список поездов пуст...\n\n");
        }
        else {
            System.out.print("@Список поездов\n");
            for (Train my_train : my_trains) {
                System.out.print("  Поезд №" + my_train.get_number_train());
                my_train.train_info();
            }
        }
    }

    // метод для запуска программы для обычного пользователя
    private static void regular_program(ArrayList<Production> my_products, Stock my_stock,
                                        ArrayList<Train> my_trains, Authentication user, boolean resolution_logs) {
        while(true) {
            clear_terminal();
            Scanner in_2 = new Scanner(System.in);
            System.out.print("Добро пожаловать, " + user.get_firstname() + " " + user.get_lastname()
                    + "\n\nМЕНЮ:"
                    + "\nДобавить товар на склад ----------------------------------------------------------- 0"
                    + "\nПосмотреть информацию о складе ---------------------------------------------------- 1"
                    + "\nДобавить поезд -------------------------------------------------------------------- 2"
                    + "\nПосмотреть информацию о поездах --------------------------------------------------- 3"
                    + "\nПогрузить товар ------------------------------------------------------------------- 4"
                    + "\nСохранить ------------------------------------------------------------------------- 5"
                    + "\nЗагрузить ------------------------------------------------------------------------- 6"
                    + "\nВыход -------------------------------------------------------------------------- exit"
                    + "\n\nОтвет: ");
            String answer = in_2.nextLine();

            if (answer.equalsIgnoreCase("0")) {
                clear_terminal();
                fill_my_stock(my_stock, my_products, resolution_logs);
            }
            else if (answer.equalsIgnoreCase("1")) {
                clear_terminal();
                my_stock.stock_info();
                pause_terminal();
            }
            else if (answer.equalsIgnoreCase("2")) {
                clear_terminal();
                fill_my_trains_list(my_trains, resolution_logs);
            }
            else if (answer.equalsIgnoreCase("3")) {
                clear_terminal();
                trains_info(my_trains);
                pause_terminal();
            }
            else if(answer.equalsIgnoreCase("4")) {
                clear_terminal();
                fill_my_train_product(my_trains, my_stock, resolution_logs);
                train_ready(my_trains, resolution_logs);
            }
            else if (answer.equalsIgnoreCase("5")) {
                clear_terminal();
                saving_stock(my_stock, resolution_logs);
                saving_trains(my_trains, resolution_logs);
            }
            else if (answer.equalsIgnoreCase("6")) {
                clear_terminal();
                loading_stock(my_stock, resolution_logs);
                loading_trains(my_trains, resolution_logs);
            }
            else if (answer.equalsIgnoreCase("exit")) {
                Logs.log("@@@@@@@@@@ ВЫХОД ИЗ ПРИЛОЖЕНИЯ @@@@@@@@@@\n", resolution_logs);
                break;
            }
        }
    }

    // метод для запуска программы для администратора
    private static void root_program(ArrayList<Authentication> base, Authentication user,
                                     boolean resolution_logs, boolean autotest) throws IOException {
        while(true) {
            clear_terminal();
            Scanner in_2 = new Scanner(System.in);
            if (resolution_logs)
                System.out.print("Добро пожаловать, " + user.get_firstname() + " " + user.get_lastname()
                    + "\n\nМЕНЮ:"
                    + "\nДобавить пользователя ------------------------------------------------------------- 0"
                    + "\nОтладка (вкл) --------------------------------------------------------------------- 1"
                    + "\nАвтотесты ------------------------------------------------------------------------- 2"
                    + "\nВыход -------------------------------------------------------------------------- exit"
                    + "\n\nОтвет: ");
            else
                System.out.print("Добро пожаловать, " + user.get_firstname() + " " + user.get_lastname()
                        + "\n\nМЕНЮ:"
                        + "\nДобавить пользователя ------------------------------------------------------------- 0"
                        + "\nОтладка (выкл) -------------------------------------------------------------------- 1"
                        + "\nАвтотесты ------------------------------------------------------------------------- 2"
                        + "\nВыход -------------------------------------------------------------------------- exit"
                        + "\n\nОтвет: ");
            String answer = in_2.nextLine();

            if (answer.equalsIgnoreCase("0"))
                Authentication.registration(base, resolution_logs);
            else if (answer.equalsIgnoreCase("1")) {
                if (resolution_logs)
                    Logs.log("@@@@@@@@@@ ОТКЛЮЧЕНИЕ ЗАПИСИ В ЛОГ ПОЛЬЗОВАТЕЛЕМ " + user.get_login() + " @@@@@@@@@@",
                            true);

                Config.store_configuration(!resolution_logs, autotest);
                resolution_logs = !resolution_logs;

                if (resolution_logs)
                    Logs.log("@@@@@@@@@@ ВКЛЮЧЕНИЕ ЗАПИСИ В ЛОГ ПОЛЬЗОВАТЕЛЕМ " + user.get_login() + " @@@@@@@@@@",
                            true);
            }
            else if (answer.equalsIgnoreCase("exit")) {
                Logs.log("@@@@@@@@@@ ВЫХОД ИЗ ПРИЛОЖЕНИЯ @@@@@@@@@@\n", resolution_logs);
                break;
            }
        }
    }

    // метод для вывода меню в терминал
    public static void menu() throws IOException {
        Config config = new Config();
        Config.load_configuration(config);   // считываем данные с конфигурационного файла

        /*ArrayList<Authentication> base = new ArrayList<>();
        base.add(new Authentication("ortin", "Misha", UserRights.root, "Mikhail", "Misharin"));
        Authentication.saving_user(base, false);*/

        Logs.log("@@@@@@@@@@ ВХОД В ПРИЛОЖЕНИЕ @@@@@@@@@@", config.resolution);

        ArrayList<Authentication> base = new ArrayList<>();
        Authentication.loading_user(base, config.resolution);  // загружаем базу пользователей

        ArrayList<Production> my_products = new ArrayList<>();  // создаем лист продуктов
        Stock my_stock = new Stock(5);                           // создаем склад
        ArrayList<Train> my_trains = new ArrayList<>();         // создаем лист поездов

        Authentication user = Authentication.authorization(base, config.resolution);   // узнаем реального пользователя

        // запускаем программу в зависимости от прав доступа
        if (user.get_rights() == UserRights.regular)
            regular_program(my_products, my_stock, my_trains, user, config.resolution);
        else
            root_program(base, user, config.resolution, config.autotest);
    }
}
