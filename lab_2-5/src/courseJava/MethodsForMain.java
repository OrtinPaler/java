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

    // метод для добавления нового продукта в список продуктов
    public static void fill_my_products_list(ArrayList<Production> my_products) throws IOException {
        // создаем новый продукт
        Production new_product = new Production();

        // считываем название нового продукта
        Scanner in_0 = new Scanner(System.in);
        System.out.print("\nВведите название товара: ");
        new_product.set_name_product(in_0.nextLine());

        // считываем тип нового продукта
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
                    Logs.log("ОШИБКА (метод fill_my_products_list) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН [1; 5]...");
                    pause_terminal();
                    continue;
                }
                break;
            }
            catch (NumberFormatException ex) {
                clear_terminal();
                System.out.print("ОШИБКА!\nВведите число...\n\n");
                Logs.log("ОШИБКА (метод fill_my_products_list) - ПОПЫТКА ВВОДА СТРОКИ ВМЕСТО ЧИСЛА...");
                pause_terminal();
            }
        }
        // в зависимости от того, что ввели присваиваем новому продукту тип
        switch (number) {
            case 1 -> new_product.set_type_production(ProductionType.undefined);
            case 2 -> new_product.set_type_production(ProductionType.containers);
            case 3 -> new_product.set_type_production(ProductionType.liquids);
            case 4 -> new_product.set_type_production(ProductionType.bulk_cargo);
            case 5 -> new_product.set_type_production(ProductionType.cars);
        }

        // добавляем новый продукт в список продуктов
        my_products.add(new_product);
        Logs.log("В СПИСОК ДОБАВЛЕН НОВЫЙ ПРОДУКТ " + new_product.get_name_product());
    }

    // метод для заполнения склада продуктами из списка продуктов
    public static void fill_my_stock(Stock my_stock, ArrayList<Production> my_products) throws IOException{
        // если кол-во продуктов на складе меньше, чем вместительность склада
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
                        Logs.log("ОШИБКА (метод fill_my_stock) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН (0; +oo)...");
                        pause_terminal();
                        clear_terminal();
                        continue;
                    }
                    else if (number == 0) {
                        clear_terminal();
                        System.out.print("ОШИБКА!"
                                + "\nКоличество товаров не может быть равно 0. Поробуйте снова...\n\n");
                        Logs.log("ОШИБКА (метод fill_my_stock) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН (0; +oo)...");
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
                    Logs.log("ОШИБКА (метод fill_my_stock) - ПОПЫТКА ВВОДА СТРОКИ ВМЕСТО ЧИСЛА...");
                    pause_terminal();
                    clear_terminal();
                }
            }
            // если кол-во новый продуктов больше того, что может уместить склад
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
                + "СКЛАД");
                pause_terminal();
            }

            // вывод список продуктов
            for (int i = 0; i < number; i++) {
                clear_terminal();
                System.out.print("Товар №" + (i + 1));
                // инициализация списка продуктов
                fill_my_products_list(my_products);
                clear_terminal();
            }

            // заполняем склад новыми продуктами
            for (Production my_product : my_products) {
                my_stock.set_product(my_product);
                Logs.log("НА СКЛАД ПОСТУПИЛ ПРОДУКТ " + my_product.get_name_product());
            }

            // чистим список продуктов
            my_products.clear();

            // если кол-во продуктов на складе равно вместительности склада
            if (my_stock.get_products().size() == my_stock.get_quantity()) {
                clear_terminal();
                System.out.print("ВНИМАНИЕ! СКЛАД ЗАПОЛНЕН!"
                        + "\nРешение: избавьтесь от части товаров со склада.\n\n");
                Logs.log("ПРЕДУПРЕЖДЕНИЕ (метод fill_my_stock) - СКЛАД ЗАПОЛНЕН ПОСЛЕ ДОБАВЛЕНИЯ ПРОДУКТОВ");
                pause_terminal();
            }
        }
        else {
            System.out.print("ВНИМАНИЕ! СКЛАД ЗАПОЛНЕН!\nРешение: избавьтесь от части товаров со склада.\n\n");
            Logs.log("ПРЕДУПРЕЖДЕНИЕ (метод fill_my_stock) - СКЛАД ЗАПОЛНЕН, НЕВОЗМОЖНО ДОБАВИТЬ НОВЫЕ ПРОДУКТЫ");
            pause_terminal();
        }
    }

    // метод для добавления нового вагона в список вагонов
    public static void fill_my_carriages_list(ArrayList<RailwayCarriage> my_carriages) throws IOException{
        // создаем новый вагон
        RailwayCarriage new_carriage = new RailwayCarriage();
        // присваиваем номер новому вагону
        new_carriage.set_number_carriage(my_carriages.size() + 1);

        // считываем тип нового вагона
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
                    Logs.log("ОШИБКА (метод fill_my_carriages_list) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН [1; 5]...");
                    pause_terminal();
                    continue;
                }
                break;
            }
            catch (NumberFormatException ex) {
                clear_terminal();
                System.out.print("ОШИБКА!\nВведите число...\n\n");
                Logs.log("ОШИБКА (метод fill_my_carriages_list) - ПОПЫТКА ВВОДА СТРОКИ ВМЕСТО ЧИСЛА...");
                pause_terminal();
            }
        }
        // в зависимости от того, что ввели присваиваем новому вагону тип
        switch (number) {
            case 1 -> new_carriage.set_type_car(CarriageType.undefined);
            case 2 -> new_carriage.set_type_car(CarriageType.platform_for_containers);
            case 3 -> new_carriage.set_type_car(CarriageType.tanks_for_liquids);
            case 4 -> new_carriage.set_type_car(CarriageType.for_bulk_cargo);
            case 5 -> new_carriage.set_type_car(CarriageType.car_platform);
        }
        // добавляем новый вагон в список вагонов
        my_carriages.add(new_carriage);
    }

    // метод для добавления новых вагонов к новому поезду
    public static void fill_my_trains_list(ArrayList<Train> my_trains) throws IOException {
        // создаем новый поезд
        Train new_train = new Train();
        Logs.log("СОЗДАН ПОЕЗД №" + new_train.get_number_train());
        // создаем новый список вагонов
        ArrayList<RailwayCarriage> new_rail_carriages = new ArrayList<>();

        // считываем кол-во вагонов в новом поезде
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
                    Logs.log("ОШИБКА (метод fill_my_trains_list) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН (0; +oo)...");
                    pause_terminal();
                    clear_terminal();
                    continue;
                }
                else if (number == 0) {
                    clear_terminal();
                    System.out.print("ОШИБКА!"
                            + "\nКоличество вагонов не может быть равно 0. Поробуйте снова...\n\n");
                    Logs.log("ОШИБКА (метод fill_my_trains_list) - ПОПЫТКА ВВОДА ЧИСЛА, НЕВХОДЯЩЕГО В ДИАПАЗОН (0; +oo)...");
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
                Logs.log("ОШИБКА (метод fill_my_trains_list) - ПОПЫТКА ВВОДА СТРОКИ ВМЕСТО ЧИСЛА...");
                pause_terminal();
                clear_terminal();
            }
        }

        // добавляем новые вагоны в список новых вагонов
        for (int i = 0; i < number; i++) {
            fill_my_carriages_list(new_rail_carriages);
            Logs.log("К ПОЕЗДУ №" + new_train.get_number_train() + " ДОБАВЛЕН ВАГОН №" + (i + 1) + ": "
                    + new_rail_carriages.get(i).get_type_carriage());
        }

        // добавляем список новых вагонов в поезд
        new_train.set_carriages(new_rail_carriages);
        // добавляем новый поезд в список поездов
        my_trains.add(new_train);
    }

    // метод для заполнения поезда продуктами
    public static void fill_my_train_product(ArrayList<Train> my_trains, Stock my_stock) throws IOException {
        // если склад пустой
        if (my_stock.get_products().size() == 0) {
            System.out.print("ВНИМАНИЕ!\nСклад пуст...\n\n");
            pause_terminal();
        }
        else {
            int answer_product;
            int find = 0;
            // считываем номер продукта, который хотим погрузить на поезд
            while (true) {
                my_stock.stock_info();
                Scanner in_0 = new Scanner(System.in);
                System.out.print("Введите номер товара, который хотите погрузить в поезд: ");

                try {
                    answer_product = Integer.parseInt(in_0.next());
                    if (answer_product < 0 || answer_product > my_stock.get_products().size()) {
                        clear_terminal();
                        System.out.print("ОШИБКА!\nВведите номер товара, который есть складе...\n\n");
                        Logs.log("ОШИБКА (метод fill_my_train_product) - ПОПЫТКА ВВОДА НЕКОРРЕКТНОГО НОМЕРА ТОВАРА...");
                        pause_terminal();
                        continue;
                    }
                    // загружаем выбранный продукт в поезд
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
                                        + " В ВАГОН №" + carriage.get_number_carriage());
                                pause_terminal();
                                my_stock.get_products().remove(answer_product - 1);
                                break;
                            }
                        }
                        if (find != 0)
                            break;
                    }
                    // если не нашли свободного места в поезде
                    if (find == 0) {
                        clear_terminal();
                        System.out.print("ВНИМАНИЕ!\nВыбранный вами товар не на чем везти...\n\n");
                        Logs.log("ПРЕДУПРЕЖДЕНИЕ (метод fill_my_train_product) - НЕТ ПОДХОДЯЩЕГО ВАГОНА ДЛЯ ПЕРЕВОЗКИ"
                                + " ТОВАРА " + my_stock.get_products().get(answer_product - 1).get_name_product());
                        pause_terminal();
                    }
                    break;
                }
                catch (NumberFormatException ex) {
                    clear_terminal();
                    System.out.print("ОШИБКА!\nВведите число...\n\n");
                    Logs.log("ОШИБКА (метод fill_my_train_product) - ПОПЫТКА ВВОДА СТРОКИ ВМЕСТО ЧИСЛА...");
                    pause_terminal();
                }
            }
        }
    }

    // метод для отправления поезда
    public static void train_ready(ArrayList<Train> my_trains) throws IOException {
        // находим полные поезда
        for (int i = 0; i < my_trains.size(); i++) {
            boolean full = true;
            for (RailwayCarriage carriage : my_trains.get(i).get_carriages()) {
                if (carriage.get_full()) {
                    full = false;
                    break;
                }
            }
            // если нашли, то отправяляем поезд
            if (full) {
                clear_terminal();
                int way = (int)(1 + Math.random() * 68);
                System.out.print("ВНИМАНИЕ!\nПоезд №" + my_trains.get(i).get_number_train()
                        + " отправляется с " + way + "-го пути.\n\n");
                Logs.log("ПОЕЗД №" + my_trains.get(i).get_number_train() + " ОТПРАВЛЯЕТСЯ С " + way + "-ГО ПУТИ");
                Logs.log("ПОЕЗД №" + my_trains.get(i).get_number_train() + " УДАЛЕН ИЗ ЖУРНАЛА");
                my_trains.remove(i);
                pause_terminal();
            }
        }
    }

    // метод для сохранения данных о складе
    public static void saving_stock(Stock my_stock) throws IOException {
        // берем данные из файла конфигурации
        Config config = new Config();
        Config.load_configuration(config);

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("D:/" + config.data_stock))) {
            // записываем новые данные в файл
            oos.writeObject(my_stock);

            clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о складе успешно сохранены...\n\n");
            Logs.log("ДАННЫЕ О СКЛАДЕ УСПЕШНО СОХРАНЕНЫ");
            pause_terminal();
        }
        catch (Exception ex) {
            clear_terminal();
            System.out.print("ОШИБКА!\nДанные о складе не сохранены...\n\n");
            Logs.log("ОШИБКА (метод saving_stock) - ДАННЫЕ О СКЛАДЕ НЕ СОХРАНЕНЫ...");
            pause_terminal();
        }
    }

    // метод для сохранения данных о поездах
    public static void saving_trains(ArrayList<Train> my_trains) throws IOException {
        // берем данные из файла конфигурации
        Config config = new Config();
        Config.load_configuration(config);

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("D:/" + config.data_trains))) {
            // записываем новые данные в файл
            oos.writeObject(my_trains);

            clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о поездах успешно сохранены...\n\n");
            Logs.log("ДАННЫЕ О ПОЕЗДАХ УСПЕШНО СОХРАНЕНЫ");
            pause_terminal();
        }
        catch (Exception ex) {
            clear_terminal();
            System.out.print("ОШИБКА!\nДанные о поездах не сохранены...\n\n");
            Logs.log("ОШИБКА (метод saving_trains) - ДАННЫЕ О ПОЕЗДАХ НЕ СОХРАНЕНЫ...");
            pause_terminal();
        }
    }

    // метод для загрузки данных о складе
    public static void loading_stock(Stock my_stock) throws IOException {
        // берем данные из файла конфигурации
        Config config = new Config();
        Config.load_configuration(config);

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("D:/" + config.data_stock))) {
            // читаем из файла
            Stock new_objects = (Stock)ois.readObject();
            my_stock.set_quantity(new_objects.get_quantity());
            for (Production product : new_objects.get_products())
                my_stock.set_product(product);

            clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о складе успешно загружены...\n\n");
            Logs.log("ДАННЫЕ О СКЛАДЕ УСПЕШНО ЗАГРУЖЕНЫ");
            pause_terminal();
        }
        catch (Exception ex) {
            clear_terminal();
            System.out.print("ОШИБКА!\nДанные о складе не загружены...\n\n");
            Logs.log("ОШИБКА (метод loading_stock) - ДАННЫЕ О СКЛАДЕ НЕ ЗАГРУЖЕНЫ...");
            pause_terminal();
        }
    }

    // метод для загрузки данных о поездах
    public static void loading_trains(ArrayList<Train> my_trains) throws IOException {
        // берем данные из файла конфигурации
        Config config = new Config();
        Config.load_configuration(config);

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("D:/" + config.data_trains))) {
            // читаем из файла
            ArrayList<?> new_objects = (ArrayList<?>)ois.readObject();
            ArrayList<Train> new_trains = new ArrayList<>();
            assert new_objects != null;
            for(Object element : new_objects)
                new_trains.add((Train)element);
            my_trains.addAll(new_trains);

            clear_terminal();
            System.out.print("ВНИМАНИЕ!\nДанные о поездах успешно загружены...\n\n");
            Logs.log("ДАННЫЕ О ПОЕЗДАХ УСПЕШНО ЗАГРУЖЕНЫ");
            pause_terminal();
        }
        catch (Exception ex) {
            clear_terminal();
            System.out.print("ОШИБКА!\nДанные о поездах не загружены...\n\n");
            Logs.log("ОШИБКА (метод loading_trains) - ДАННЫЕ О ПОЕЗДАХ НЕ ЗАГРУЖЕНЫ...");
            pause_terminal();
        }
    }

    // метод для вывода информация о списке поездов
    public static void trains_info(ArrayList<Train> my_trains) {
        // если список поездов пуст
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
                                        ArrayList<Train> my_trains, Authentication user) throws IOException {
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
                fill_my_stock(my_stock, my_products);
            }
            else if (answer.equalsIgnoreCase("1")) {
                clear_terminal();
                my_stock.stock_info();
                pause_terminal();
            }
            else if (answer.equalsIgnoreCase("2")) {
                clear_terminal();
                fill_my_trains_list(my_trains);
            }
            else if (answer.equalsIgnoreCase("3")) {
                clear_terminal();
                trains_info(my_trains);
                pause_terminal();
            }
            else if(answer.equalsIgnoreCase("4")) {
                clear_terminal();
                fill_my_train_product(my_trains, my_stock);
                train_ready(my_trains);
            }
            else if (answer.equalsIgnoreCase("5")) {
                clear_terminal();
                saving_stock(my_stock);
                saving_trains(my_trains);
            }
            else if (answer.equalsIgnoreCase("6")) {
                clear_terminal();
                loading_stock(my_stock);
                loading_trains(my_trains);
            }
            else if (answer.equalsIgnoreCase("exit")) {
                Logs.log("@@@@@@@@@@ ВЫХОД ИЗ ПРИЛОЖЕНИЯ @@@@@@@@@@\n");
                break;
            }
        }
    }

    // метод для запуска программы для администратора
    private static void root_program(ArrayList<Authentication> base, Authentication user) throws IOException {
        while(true) {
            // берем данные из файла конфигурации
            Config config = new Config();
            Config.load_configuration(config);

            clear_terminal();
            Scanner in_2 = new Scanner(System.in);

            String res_on_off;
            if (config.resolution)
                res_on_off = "(вкл) -";
            else
                res_on_off = "(выкл) ";

            System.out.print("Добро пожаловать, " + user.get_firstname() + " " + user.get_lastname()
                    + "\n\nМЕНЮ:"
                    + "\nДобавить пользователя ------------------------------------------------------------- 0"
                    + "\nСписок пользователей -------------------------------------------------------------- 1"
                    + "\nОтладка " + res_on_off + "-------------------------------------------------------------------- 2"
                    + "\nАвтотесты ------------------------------------------------------------------------- 3"
                    + "\nВыход -------------------------------------------------------------------------- exit"
                    + "\n\nОтвет: ");

            String answer = in_2.nextLine();

            if (answer.equalsIgnoreCase("0"))
                Authentication.registration(base);
            else if (answer.equalsIgnoreCase("1")) {
                clear_terminal();
                System.out.println("Всего пользователей: " + base.size() + "\n");
                for (Authentication see : base) {
                    System.out.println("Логин: " + see.get_login() + "\nПрава: " + see.get_rights() + "\n");
                }
                pause_terminal();
            }
            else if (answer.equalsIgnoreCase("2")) {
                if (config.resolution)
                    Logs.log("@@@@@@@@@@ ОТКЛЮЧЕНИЕ ЗАПИСИ В ЛОГ ПОЛЬЗОВАТЕЛЕМ " + user.get_login() + " @@@@@@@@@@");

                Config.store_configuration(!config.resolution, config.autotest, config.data_stock,
                        config.data_trains, config.data_users);
                Config.load_configuration(config);

                if (config.resolution)
                    Logs.log("@@@@@@@@@@ ВКЛЮЧЕНИЕ ЗАПИСИ В ЛОГ ПОЛЬЗОВАТЕЛЕМ " + user.get_login() + " @@@@@@@@@@");
            }
            else if (answer.equalsIgnoreCase("3")) {
                Autotests.autotests();
            }
            else if (answer.equalsIgnoreCase("exit")) {
                Logs.log("@@@@@@@@@@ ВЫХОД ИЗ ПРИЛОЖЕНИЯ @@@@@@@@@@\n");
                break;
            }
        }
    }

    // метод для вывода меню в терминал
    public static void menu() throws IOException {
        // берем данные из файла конфигурации
        Config config = new Config();
        Config.load_configuration(config);

        Logs.log("@@@@@@@@@@ ВХОД В ПРИЛОЖЕНИЕ @@@@@@@@@@");

        // загружаем базу данных пользователей
        ArrayList<Authentication> base = new ArrayList<>();
        Authentication.loading_user(base);

        ArrayList<Production> my_products = new ArrayList<>();  // создаем лист продуктов
        Stock my_stock = new Stock(5);                           // создаем склад
        ArrayList<Train> my_trains = new ArrayList<>();         // создаем лист поездов

        // узнаем реального пользователя
        Authentication user = Authentication.authorization(base);

        // запускаем программу в зависимости от прав доступа
        if (user.get_rights() == UserRights.regular)
            regular_program(my_products, my_stock, my_trains, user);
        else
            root_program(base, user);
    }
}
