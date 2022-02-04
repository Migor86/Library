package com.migor;

import java.util.Scanner;

public class Application
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        BookStorage bookStorage = new BookStorageImpl();
        UserStorage userStorage = new UserStorageImpl();

        CommandManager manager = new CommandManager(bookStorage, userStorage, scanner);
        manager.start();
    }


}
