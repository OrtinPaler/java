package client;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // вариант работы без базы данных
        new Client();

        // вариант работы с базой данных
        //Entrance.create_entrance();

        // проверка работы базы данных
        /*ArrayList<String> users = new ArrayList<>();
        users.add("Тигран");
        Entrance.saving_user(users);*/

        /*ArrayList<String> users = new ArrayList<>();
        Entrance.loading_user(users);
        for (String user : users)
            System.out.print(user);*/
    }
}
