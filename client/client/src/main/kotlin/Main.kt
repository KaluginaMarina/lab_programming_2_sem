import client.Client
import client.gui.ClientGUI
import model.*

import java.util.Locale
import java.util.concurrent.ConcurrentLinkedDeque


fun main(args: Array<String>) {
    val heroes = ConcurrentLinkedDeque<Personage>()

    val client = Client(heroes)
    val clientThread = Thread(client)
    clientThread.start()

    val gui = ClientGUI(client, 100, 100, 1200, 620, Locale.getDefault())
    client.gui = gui
}