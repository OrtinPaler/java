package courseJava;
import courseJava.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    int a = ;
    // метод для создания списка продуктов
    private static ArrayList<Production> create_my_products_list() {

        ArrayList<Production> my_products = new ArrayList<Production>();

        return my_products;
    }

    // метод для добавления в список продуктов
    private static void fill_my_products_list(ArrayList<Production> my_products) {

        String number_st;                                   // номер типа
        int number = 0;

        Production new_product = new Production();

        Scanner in_0 = new Scanner(System.in);
        System.out.print("Введите название продукта: ");
        new_product.set_name_product(in_0.nextLine());

        Scanner in_1 = new Scanner(System.in);
        System.out.print("\nВыберите тип продукта:" +
                "\n1. Undefined (Неопределенный)" +
                "\n2. Containers (Контейнеры)" +
                "\n3. Liquids (Жидкости)" +
                "\n4. Bulk Cargo (Сыпучие вещества)" +
                "\n5. Cars (Машины)");

        while (true) {
            System.out.print("\nОтвет: ");
            number_st = in_1.next();

            // обрабатываем исключения
            try {
                number = Integer.parseInt(number_st);
                if (number < 1 || number > 5) {
                    System.out.print("Введите число от 1 до 5...");
                    continue;
                }
                break;
            } catch (NumberFormatException ex) {
                System.out.print("Введите число...");
            }
        }

        switch (number) {
            case 1: {
                new_product.set_type_production(ProductionType.undefined);
                break;
            }

            case 2: {
                new_product.set_type_production(ProductionType.containers);
                break;
            }

            case 3: {
                new_product.set_type_production(ProductionType.liquids);
                break;
            }

            case 4: {
                new_product.set_type_production(ProductionType.bulk_cargo);
                break;
            }

            case 5: {
                new_product.set_type_production(ProductionType.cars);
                break;
            }
        }

        my_products.add(new_product);
    }

    // метод для создания склада
    private static Stock create_my_stock(int len) {

        Stock my_stock = new Stock(len);

        return my_stock;
    }

    // метод для заполнения склада продуктами
    private static void fill_my_stock(Stock my_stock, ArrayList<Production> my_products) {

        int s;

        if (my_stock.get_quantity() <= my_products.size()) {
            s = my_stock.get_quantity();
            System.out.println("Часть продуктов не попадет на склад!\n");
        }
        else {
            s = my_products.size();
        }

        for (int i = 0; i < s; i++) {
            my_stock.set_product(my_products.get(i), i);
            my_products.remove(i);
        }
    }

    // метод для создания списка вагонов
    private static ArrayList<RailwayCarriage> create_my_carriages_list() {

        ArrayList<RailwayCarriage> my_carriages = new ArrayList<RailwayCarriage>();

        return my_carriages;
    }

    // метод для добавления в список вагонов
    private static void fill_my_carriages_list(ArrayList<RailwayCarriage> my_carriages) {

        String number_st;                                   // номер типа
        int number = 0;

        RailwayCarriage new_carriage = new RailwayCarriage();

        Scanner in_0 = new Scanner(System.in);
        System.out.print("\nВыберите тип продукта:" +
                "\n1. Undefined (Неопределенный)" +
                "\n2. Platform for containers (Платформа для контейнеров)" +
                "\n3. Tanks for liquids (Цистерны для жидкостей)" +
                "\n4. For bulk cargo (Вагон для сыпучих веществ)" +
                "\n5. Car platform (Платформа для машин)");

        while (true) {
            System.out.print("\nОтвет: ");
            number_st = in_0.next();

            // обрабатываем исключения
            try {
                number = Integer.parseInt(number_st);
                if (number < 1 || number > 5) {
                    System.out.print("Введите число от 1 до 5...");
                    continue;
                }
                break;
            } catch (NumberFormatException ex) {
                System.out.print("Введите число...");
            }
        }

        switch (number) {
            case 1: {
                new_carriage.set_type_car(CarriageType.undefined);
                break;
            }

            case 2: {
                new_carriage.set_type_car(CarriageType.platform_for_containers);
                break;
            }

            case 3: {
                new_carriage.set_type_car(CarriageType.tanks_for_liquids);
                break;
            }

            case 4: {
                new_carriage.set_type_car(CarriageType.for_bulk_cargo);
                break;
            }

            case 5: {
                new_carriage.set_type_car(CarriageType.car_platform);
                break;
            }
        }

        my_carriages.add(new_carriage);
    }

    // метод для создания вагонов
    private static RailwayCarriage[] ccreate_my_carriages() {

        String number_st;                                   // кол-во вагонов
        int number = 0;
        Scanner in_0 = new Scanner(System.in);

        while (true) {
            System.out.print("\nВведите кол-во продуктов: ");
            number_st = in_0.next();
            System.out.print("\n");

            // обрабатываем исключения
            try {
                number = Integer.parseInt(number_st);
                if (number < 1) {
                    System.out.print("Введите целое число больше нуля...");
                    continue;
                }
                break;
            }
            catch (NumberFormatException ex) {
                System.out.print("Введите целое число...");
            }
        }

        RailwayCarriage[] my_carriages = new RailwayCarriage[number]; // создаем массив вагонов

        for (int i = 0; i < my_carriages.length; i++) {
            my_carriages[i] = new RailwayCarriage();

            Scanner in_1 = new Scanner(System.in);
            System.out.print("\nВыберите тип продукта:" +
                    "\n1. Undefined (Неопределенный)" +
                    "\n2. Platform for containers (Платформа для контейнеров)" +
                    "\n3. Tanks for liquids (Цистерны для жидкостей)" +
                    "\n4. For bulk cargo (Вагон для сыпучих веществ)" +
                    "\n5. Car platform (Платформа для машин)");

            while (true) {
                System.out.print("\nОтвет: ");
                number_st = in_1.next();

                // обрабатываем исключения
                try {
                    number = Integer.parseInt(number_st);
                    if (number < 1 || number > 5) {
                        System.out.print("Введите число от 1 до 5...");
                        continue;
                    }
                    break;
                } catch (NumberFormatException ex) {
                    System.out.print("Введите число...");
                }
            }

            switch (number) {
                case 1: {
                    my_carriages[i].set_type_car(CarriageType.undefined);
                    break;
                }

                case 2: {
                    my_carriages[i].set_type_car(CarriageType.platform_for_containers);
                    break;
                }

                case 3: {
                    my_carriages[i].set_type_car(CarriageType.tanks_for_liquids);
                    break;
                }

                case 4: {
                    my_carriages[i].set_type_car(CarriageType.for_bulk_cargo);
                    break;
                }

                case 5: {
                    my_carriages[i].set_type_car(CarriageType.car_platform);
                    break;
                }
            }
        }

        return my_carriages;
    }

    // метод для заполнения вагонов продуктами
   /* private  static Stock fill_my_carriages(RailwayCarriage[] my_carriages, Stock my_stock) {

        Stock new_stock = new Stock(my_stock.get_quantity() - my_carriages.length);
        ArrayList<RailwayCarriage> new_carriages = new ArrayList<RailwayCarriage>();

        for (int i = 0; i < my_carriages.length; i++) {
            for (int j = 0; j < my_stock.get_quantity(); j++) {
                if (my_carriages[i].get_type_car() == my_stock.get_product(j).get_required_type_car()) {
                    my_carriages[i].set_product(my_stock.get_product(j));
                    new_carriages.add(my_carriages[i]);
                    break;
                }
            }
        }
    }
*/
    private static Train create_my_train(RailwayCarriage[] my_carriages) {
        Train my_train = new Train("AAA001", my_carriages.length);
        my_train.set_carriages(my_carriages);

        return my_train;
    }

    public static void main(String[] args) {

        ArrayList<Production> my_products = create_my_products_list();
        Stock my_stock = create_my_stock(1);
        fill_my_stock(my_stock, my_products);

        my_stock.stock_info();

        for (Production product : my_products) {
            product.production_info();
        }

/*
        Production[] my_products = create_my_products();
        Stock my_stock = create_my_stock(my_products);
        my_stock.stock_info();
        RailwayCarriage[] my_carriages = create_my_carriages(my_stock);
        Train my_train = create_my_train(my_carriages);
        my_train.train_info();
        my_stock.stock_info();

 */
    }
}