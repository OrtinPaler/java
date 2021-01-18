package courseJava;

import java.util.ArrayList;     // подключаем класс ArrayList для работы с данными
import java.util.Arrays;        // подключаем класс Array для работы с данными
import java.util.Date;          // подкючаем класс для работы с датой
import java.util.LinkedList;    // подключаем класс LinkedList для работы с данными

public class Autotests {
    // метод для добавления случайного вагона в список вагонов
    private static void create_some_carriage(ArrayList<RailwayCarriage> some_carriages) {
        // создаем случайный вагон
        RailwayCarriage some_carriage = new RailwayCarriage();
        // присваиваем номер случайному вагону
        some_carriage.set_number_carriage(some_carriages.size() + 1);

        // присваиваем новому вагону случайный тип
        switch ((int)(1 + Math.random() * 4)) {
            case 1 -> some_carriage.set_type_car(CarriageType.undefined);
            case 2 -> some_carriage.set_type_car(CarriageType.platform_for_containers);
            case 3 -> some_carriage.set_type_car(CarriageType.tanks_for_liquids);
            case 4 -> some_carriage.set_type_car(CarriageType.for_bulk_cargo);
            case 5 -> some_carriage.set_type_car(CarriageType.car_platform);
        }

        // добавляем случайный вагон в список вагонов
        some_carriages.add(some_carriage);
    }

    // метод для создания случайного поезда
    private static Train create_some_train(String ID) {
        // создаем случайный поезд
        Train some_train = new Train(ID);

        // создаем список вагонов
        ArrayList<RailwayCarriage> new_rail_carriages = new ArrayList<>();
        // добавляем случайные вагоны в список вагонов
        for (int i = 0; i < 10; i++)
            create_some_carriage(new_rail_carriages);

        // добавляем список вагонов в случайный поезд
        some_train.set_carriages(new_rail_carriages);
        // возвращаем случайный поезд
        return some_train;
    }

    // метод для рассчета времени добавления в ArrayList и удаления из ArrayList
    private static void autotests_of_arrayList(int size, ArrayList<Point> addTotal_points,
                                                         ArrayList<Point> removeTotal_points,
                                                         ArrayList<Point> addMedian_points,
                                                         ArrayList<Point> removeMedian_points)
    {
        ArrayList<Train> arrayList = new ArrayList<>();

        long[] add_time = new long[size];
        long start, finish;

        for (int i = 1; i < (size + 1); i++) {
            String ID = Integer.toString(i);        // присваиваем каждому поезду свой ID
            start = System.nanoTime();              // начало отчета времени
            arrayList.add(create_some_train(ID));   // заполняем список поездами
            finish = System.nanoTime();             // конец отчета времени
            add_time[i - 1] = finish - start;       // считаем время, затраченное на создание поезда
            //System.out.println("ADD, ID = " + ID + ", " + add_time[i - 1]);
            //Logs.log_of_autotests("ADD, ID = " + ID + ", " + add_time[i - 1]);
        }

        System.out.println("addTotalCount = " + size
                + "\naddTotalTime = " + Arrays.stream(add_time).sum()
                + "\naddMedianTime = " + Arrays.stream(add_time).sum() / size);

        addTotal_points.add(new Point(Math.log10(size), Arrays.stream(add_time).sum()));
        addMedian_points.add(new Point(Math.log10(size), (double)Arrays.stream(add_time).sum() / size));

        Logs.log_of_autotests("addTotalCount = " + size
                + "\naddTotalTime = " + Arrays.stream(add_time).sum()
                + "\naddMedianTime = " + Arrays.stream(add_time).sum() / size);

        long[] remove_time = new long[size / 10];

        for (int i = 0; i < (size / 10); i++) {
            int some_id = (int)(0 + Math.random() * (size - i));           // выбираем случайный поезд для удаления
            String num_train = arrayList.get(some_id).get_number_train();  // узнаем номер этого поезда
            start = System.nanoTime();                                     // начало отчета времени
            arrayList.remove(some_id);                                     // удаляем из списка случайный элемент
            finish = System.nanoTime();                                    // конец отчета времени
            remove_time[i] = finish - start;                               // считаем время, затраченное на удаление поезда
            //System.out.println("REMOVE, ID = " + num_train + ", " + remove_time[i]);
            //Logs.log_of_autotests("REMOVE, ID = " + num_train + ", " + remove_time[i]);
        }

        System.out.println("removeTotalCount = " + (size / 10)
                + "\nremoveTotalTime = " + Arrays.stream(remove_time).sum()
                + "\nremoveMedianTime = " + Arrays.stream(remove_time).sum() / (size / 10));

        removeTotal_points.add(new Point(Math.log10(size), Arrays.stream(remove_time).sum()));
        removeMedian_points.add(new Point(Math.log10(size), Arrays.stream(remove_time).sum() / (double)(size / 10)));

        Logs.log_of_autotests("removeTotalCount = " + (size / 10)
                + "\nremoveTotalTime = " + Arrays.stream(remove_time).sum()
                + "\nremoveMedianTime = " + Arrays.stream(remove_time).sum() / (size / 10));
    }

    // метод для рассчета времени добавления в LinkedList и удаления из LinkedList
    private static void autotests_of_linkedList(int size, ArrayList<Point> addTotal_points,
                                                          ArrayList<Point> removeTotal_points,
                                                          ArrayList<Point> addMedian_points,
                                                          ArrayList<Point> removeMedian_points)
    {
        LinkedList<Train> arrayList = new LinkedList<>();

        long[] add_time = new long[size];
        long start, finish;

        for (int i = 1; i < (size + 1); i++) {
            String ID = Integer.toString(i);        // присваиваем каждому поезду свой ID
            start = System.nanoTime();              // начало отчета времени
            arrayList.add(create_some_train(ID));   // заполняем список поездами
            finish = System.nanoTime();             // конец отчета времени
            add_time[i - 1] = finish - start;       // считаем время, затраченное на создание поезда
            //System.out.println("ADD, ID = " + ID + ", " + add_time[i - 1]);
            //Logs.log_of_autotests("ADD, ID = " + ID + ", " + add_time[i - 1]);
        }

        System.out.println("addTotalCount = " + size
                + "\naddTotalTime = " + Arrays.stream(add_time).sum()
                + "\naddMedianTime = " + Arrays.stream(add_time).sum() / size);

        addTotal_points.add(new Point(Math.log10(size), Arrays.stream(add_time).sum()));
        addMedian_points.add(new Point(Math.log10(size), (double)Arrays.stream(add_time).sum() / size));

        Logs.log_of_autotests("addTotalCount = " + size
                + "\naddTotalTime = " + Arrays.stream(add_time).sum()
                + "\naddMedianTime = " + Arrays.stream(add_time).sum() / size);

        long[] remove_time = new long[size / 10];

        for (int i = 0; i < (size / 10); i++) {
            int some_id = (int)(Math.random() * (size - i));               // выбираем случайный поезд для удаления
            String num_train = arrayList.get(some_id).get_number_train();  // узнаем номер этого поезда
            start = System.nanoTime();                                     // начало отчета времени
            arrayList.remove(some_id);                                     // удаляем из списка случайный элемент
            finish = System.nanoTime();                                    // конец отчета времени
            remove_time[i] = finish - start;                               // считаем время, затраченное на удаление поезда
            //System.out.println("REMOVE, ID = " + num_train + ", " + remove_time[i]);
            //Logs.log_of_autotests("REMOVE, ID = " + num_train + ", " + remove_time[i]);
        }

        System.out.println("removeTotalCount = " + (size / 10)
                + "\nremoveTotalTime = " + Arrays.stream(remove_time).sum()
                + "\nremoveMedianTime = " + Arrays.stream(remove_time).sum() / (size / 10));

        removeTotal_points.add(new Point(Math.log10(size), Arrays.stream(remove_time).sum()));
        removeMedian_points.add(new Point(Math.log10(size), Arrays.stream(remove_time).sum() / (double)(size / 10)));

        Logs.log_of_autotests("removeTotalCount = " + (size / 10)
                + "\nremoveTotalTime = " + Arrays.stream(remove_time).sum()
                + "\nremoveMedianTime = " + Arrays.stream(remove_time).sum() / (size / 10));
    }

    // метод для проведения автотестов
    public static void autotests() {
        MethodsForMain.clear_terminal();
        System.out.println("\n" + new Date() + ": @@@@@@@@@@ Запуск автотестов @@@@@@@@@@");
        Logs.log_of_autotests("\n" + new Date() + ": @@@@@@@@@@ Запуск автотестов @@@@@@@@@@");

        System.out.println("ArrayList");
        Logs.log_of_autotests("ArrayList");

        ArrayList<Point> arrayList_addTotal_points = new ArrayList<>();
        ArrayList<Point> arrayList_removeTotal_points = new ArrayList<>();
        ArrayList<Point> arrayList_addMedian_points = new ArrayList<>();
        ArrayList<Point> arrayList_removeMedian_points = new ArrayList<>();

        autotests_of_arrayList(10, arrayList_addTotal_points, arrayList_removeTotal_points,
                                        arrayList_addMedian_points, arrayList_removeMedian_points);
        autotests_of_arrayList(100, arrayList_addTotal_points, arrayList_removeTotal_points,
                arrayList_addMedian_points, arrayList_removeMedian_points);
        autotests_of_arrayList(1000, arrayList_addTotal_points, arrayList_removeTotal_points,
                arrayList_addMedian_points, arrayList_removeMedian_points);
        autotests_of_arrayList(10000, arrayList_addTotal_points, arrayList_removeTotal_points,
                arrayList_addMedian_points, arrayList_removeMedian_points);
        autotests_of_arrayList(100000, arrayList_addTotal_points, arrayList_removeTotal_points,
                arrayList_addMedian_points, arrayList_removeMedian_points);

        System.out.println("\nLinkedList");
        Logs.log_of_autotests("LinkedList");

        ArrayList<Point> linkedList_addTotal_points = new ArrayList<>();
        ArrayList<Point> linkedList_removeTotal_points = new ArrayList<>();
        ArrayList<Point> linkedList_addMedian_points = new ArrayList<>();
        ArrayList<Point> linkedList_removeMedian_points = new ArrayList<>();

        autotests_of_linkedList(10, linkedList_addTotal_points, linkedList_removeTotal_points,
                                         linkedList_addMedian_points, linkedList_removeMedian_points);
        autotests_of_linkedList(100, linkedList_addTotal_points, linkedList_removeTotal_points,
                linkedList_addMedian_points, linkedList_removeMedian_points);
        autotests_of_linkedList(1000, linkedList_addTotal_points, linkedList_removeTotal_points,
                linkedList_addMedian_points, linkedList_removeMedian_points);
        autotests_of_linkedList(10000, linkedList_addTotal_points, linkedList_removeTotal_points,
                linkedList_addMedian_points, linkedList_removeMedian_points);
        autotests_of_linkedList(100000, linkedList_addTotal_points, linkedList_removeTotal_points,
                linkedList_addMedian_points, linkedList_removeMedian_points);

        Schedule.create_schedule(arrayList_addTotal_points,
                                arrayList_removeTotal_points,
                                arrayList_addMedian_points,
                                arrayList_removeMedian_points,
                                linkedList_addTotal_points,
                                linkedList_removeTotal_points,
                                linkedList_addMedian_points,
                                linkedList_removeMedian_points);

        System.out.println(new Date() + ": @@@@@@@@@@ Конец автотестов @@@@@@@@@@\n");
        Logs.log_of_autotests(new Date() + ": @@@@@@@@@@ Конец автотестов @@@@@@@@@@\n");
        MethodsForMain.pause_terminal();
    }
}
