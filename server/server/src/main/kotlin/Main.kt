import authorization.AutorizationGUI
import manage.Command
import server.manage.Manage
import server.Server

import java.net.ServerSocket
import java.util.concurrent.Executors


var executeIt = Executors.newFixedThreadPool(2)

fun main(args: Array<String>) {

    val manage = Manage()
    val manager = Thread(manage)
    manager.start()

    Runtime.getRuntime().addShutdownHook(Thread { Command.collectionSave() })

    val repository = orm.Repository("jdbc:postgresql://localhost:5432/db", "marina", "1234")
    //repository.createTable<Personage>()

    Command.collectionCreater()
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