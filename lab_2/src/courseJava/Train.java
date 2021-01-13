package courseJava;
import courseJava.RailwayCarriage;

public class Train {
    final int Z = 10;
    private String number_train;            // номер поезда
    private int number_carriage;            // кол-во вагонов
    private RailwayCarriage[] carriages;    // массив вагонов

    // конструктор с параматрами number_train (номер поезда) и number_carriage (кол-во вагонов)
    Train(String number_train, int number_carriage) {

        this.number_train = number_train;
        this.number_carriage = number_carriage;
        this.carriages = new RailwayCarriage[this.number_carriage];
    }

    // свойство для установки вагонов в поезд
    public void set_carriages(RailwayCarriage[] carriages) {

        this.carriages = carriages;
    }

    // метод для вывода информации о поезде
    public void train_info() {

        System.out.println("\n@ИНФОРМАЦИЯ ПО ПОЕЗДУ\n\nНомер поезда: " + this.number_train
                + "\nКол-во вагонов: " + (this.number_carriage - 1) + "\n");

        for (int i = 0; i < this.carriages.length; i++) {
            if (this.carriages[i].get_product() != null) {
                System.out.println("Номера вагона: " + this.carriages[i].get_num_car()
                        + "\nТип вагона: " + this.carriages[i].get_type_car()
                        + "\nСодержимое: " + this.carriages[i].get_product().get_name_product() + "\n");
            }
        }
    }
}
