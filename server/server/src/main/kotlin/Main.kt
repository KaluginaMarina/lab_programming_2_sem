import authorization.AutorizationGUI
import manage.Command
import model.Mood
import model.Personage
import model.Shorties
import server.manage.Manage
import server.Server

import java.net.ServerSocket
import java.util.concurrent.Executors

import orm.*
import java.time.LocalDateTime


var executeIt = Executors.newFixedThreadPool(2)

fun main(args: Array<String>) {

    val manage = Manage()
    val manager = Thread(manage)
    manager.start()

    Command.collectionCreater()

    Runtime.getRuntime().addShutdownHook(Thread { Command.collectionSave() })

    val repository = Repository("jdbc:postgresql://localhost:5432/db", "marina", "1234")

    // repository.insert(Shorties("Миго", 10.0, 20.0, 12))
    //repository.createTable<Personage>()
    //Command.heroes.forEach { repository.insert(it) }
    //print(repository.selectAll<Personage>())

    var gui = AutorizationGUI()

    println("Сервер начал работу.")
    try {
        ServerSocket(27525).use { server ->
            while (!server.isClosed) {
                val client = server.accept()
                executeIt.execute(Server(client))
                print("Connection accepted.")
            }
            executeIt.shutdown()

        }
    } catch (e: Exception) {
        println("exp e")
    }
}