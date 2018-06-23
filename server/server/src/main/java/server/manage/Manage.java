package server.manage;

import manage.Command;

import java.util.Scanner;

import static java.lang.System.exit;

public class Manage implements Runnable{
    @Override
    public void run() {
        System.out.println("");
        Scanner input = new Scanner(System.in);
        while (true) {
            String command = input.nextLine();
            if (command.equals("exit")){
                exit(0);
            } else if (command.equals("help")){
                System.out.println("Доступные команды: print, info, load, help, exit");
            } else if(command.equals("print")) {
                System.out.println(Command.INSTANCE.getHeroes());
            } else if (command.equals("load")){
                Command.INSTANCE.load();
                System.out.println(Command.INSTANCE.getHeroes());
            } else {
                System.out.println("Неверная команда.");
            }
        }
    }
}
