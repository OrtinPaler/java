/*
 * Программа переводит сантиметры и метры в футы и дюймы.
 */

package com.courseJava;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String my_num; // вводимое значение
        int end_num; // индекс конца числа в строке
        float number; // вводимое число

        System.out.println("Hello, my dear friend.\n" +
                "This program converts centimeters and meters to feet and inches.\n");

        Scanner in = new Scanner(System.in); // создаем переменную "in" для считывания строки

        for (; ;) {
            System.out.println("Enter (For example, 10 cm or 10 m):");
            my_num = in.nextLine(); // считываем строку и записываем в переменную "my_num"

            // если строка заканчивается на "cm" или "m", то выходит из цикла
            if (my_num.endsWith(" cm") || my_num.endsWith(" m")) {
                break;
            }

            // иначе программа попросит ввести значения заново
            System.out.println("It is not clear what units of measurement. Try again.");
        }

        for (int i = 0; i < my_num.length(); i++) {

            // метод charAt() возвращает символ, расположенный по указанному индексу строки
            if (my_num.charAt(i) == ' ') {
                end_num = i; // конец числа

                // метод parseInt() преобразует строку в число
                number = Float.parseFloat(my_num.substring(0, end_num));

                // если строка не оканчивается на "cm", то умножаем число на 100 (переводим из м в см)
                if (!my_num.endsWith("cm")) {
                    number *= 100;
                }

                // "number / 2.54" - переводим в дюймы, "number / 30.48" - переводим в футы
                System.out.printf("\nAnswer: %f inches\t%f feet\nThank you!", number / 2.54, number / 30.48);
            }
        }
    }
}
