package courseJava;

import java.io.Serializable;
import java.util.ArrayList;

public class Stock implements Serializable {
    private int quantity;                           // кол-во товара на складе
    private final ArrayList<Production> products;   // массив продукции

    // конструктор с параметром quantity (вместимость)
    Stock(int quantity) {
        this.quantity = quantity;
        this.products = new ArrayList<>();
    }

    // свойство для установки вместимсоти
    public void set_quantity(int quantity) { this.quantity = quantity; }

    // свойство для установки продукта
    public void set_product(Production product) { this.products.add(product); }

    // свойство для вывода массива продукции, хранимого на складе
    public ArrayList<Production> get_products() { return this.products; }

    // свойство для вывода вместимости склада
    public int get_quantity() { return this.quantity; }

    // метод для вывода информации о складе
    public void stock_info() {
        if (this.products.size() == 0) {
            System.out.print("Склад пуст...\n\n");
        }
        else {
            System.out.println("@Склад"
                    + "\nВместимость склада: " + this.quantity
                    + "\nКол-во продукции на складе: " + this.products.size());

            for (int i = 0; i < this.products.size(); i++)
                if (this.products.get(i) != null) {
                    System.out.print("\n  Товар №" + (i + 1));
                    products.get(i).production_info();
                }
            System.out.print("\n");
        }
    }
}
