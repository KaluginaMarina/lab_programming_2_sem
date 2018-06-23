package manage

import model.Personage
import orm.Repository

import java.util.concurrent.ConcurrentLinkedDeque

object Command {
    private var jTreeUpdate : (ConcurrentLinkedDeque<Personage>) -> Unit = {}

    var heroes = ConcurrentLinkedDeque<Personage>()

    fun collectionCreater(){
        val repository = Repository("jdbc:postgresql://localhost:5432/db", "marina", "1234")
        heroes = repository.selectAll()
        jTreeUpdate(heroes)
    }

    /**
     * Метод, записывающий текущее состояние коллекции в файл fileNameClose
     */
    fun collectionSave() {
        val repository = Repository("jdbc:postgresql://localhost:5432/db", "marina", "1234")
        repository.deleteAll<Personage>()
        heroes.forEach { repository.insert(it) }
        jTreeUpdate(heroes)
    }

    /**
     * add {element} Метод для добавления элемента в коллекцию в интерактивном режиме
     */
    fun add(pers: Personage){
        heroes.add(pers)
        val repository = Repository("jdbc:postgresql://localhost:5432/db", "marina", "1234")
        repository.insert(pers)
        jTreeUpdate(heroes)
    }

    /**
     * load - перечитать коллекцию из файла
     */
    fun load(){
        val repository = Repository("jdbc:postgresql://localhost:5432/db", "marina", "1234")
        heroes = repository.selectAll()
        jTreeUpdate(heroes)
    }

    fun remove(pers: Personage?){
        if (pers != null)
            heroes.removeIf { it.type == pers.type &&
                    it.name == pers. name &&
                    it.x == pers.x &&
                    it.y == pers.y &&
                    it.force == pers.force &&
                    it.height == pers.height &&
                    it.mood == pers.mood
            }
        collectionSave()
        jTreeUpdate(heroes)
    }

    fun setJTreeUpdate(update : (ConcurrentLinkedDeque<Personage>) -> Unit){
        jTreeUpdate = update
    }
}
