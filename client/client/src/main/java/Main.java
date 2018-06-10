import client.Client;
import client.gui.ClientGUI;
import model.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedDeque;


public class Main {
    public static void main(String[] args) {
        ConcurrentLinkedDeque<Personage> heroes = new ConcurrentLinkedDeque<>();

        Client client = new Client(heroes);
        Thread clientThread = new Thread(client);
        clientThread.start();

        ClientGUI gui = new ClientGUI(client, 100, 100, 1200, 600);
        client.gui = gui;
    }
}