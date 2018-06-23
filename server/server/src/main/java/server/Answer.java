package server;

import manage.*;
import client.util.CommandType;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Answer extends Thread {
    private client.util.Command command;
    private Socket client;

    public Answer(client.util.Command command, Socket client) {
        this.command = command;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(out);

            if (command.commandType ==  CommandType.LOAD) {
                Command.INSTANCE.load();
                oos.writeObject(Command.INSTANCE.getHeroes());
            } else if (command.commandType == CommandType.ADD) {
                Command.INSTANCE.add(command.personage);
                oos.writeObject(Command.INSTANCE.getHeroes());
            } else if (command.commandType == CommandType.PRINT) {
                oos.writeObject(Command.INSTANCE.getHeroes());
            } else if (command.commandType == CommandType.QUIT){
                oos.writeObject(Command.INSTANCE.getHeroes());
                oos.flush();
                out.flush();
                return;
            } else if (command.commandType == CommandType.HELP){
                oos.flush();
                out.flush();
            }
            else {
                oos.writeObject(Command.INSTANCE.getHeroes());
                System.out.println("Команда не найдена.\nhelp / ?: открыть справку");
            }
            oos.flush();
            out.flush();
        } catch (NoSuchElementException e) {
            System.out.println("Где-то ошибка");
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}