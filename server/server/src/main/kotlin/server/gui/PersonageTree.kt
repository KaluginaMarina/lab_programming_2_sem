package server.gui

import model.Personage
import java.util.concurrent.ConcurrentLinkedDeque
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

class PersonageTree(heroes : ConcurrentLinkedDeque<Personage>) : JTree(treeModel1) {

    companion object {
        val shorties = DefaultMutableTreeNode("Коротышки")
        val moonlighters = DefaultMutableTreeNode("Лунатик")
        val readers = DefaultMutableTreeNode("Читатели")
        val personages = DefaultMutableTreeNode("Персонажи").apply {
            add(shorties)
            add(moonlighters)
            add(readers)}
        val treeModel1 = DefaultTreeModel(personages, true)
    }
    init {
        heroes.forEach{
            when (it.type) {
                "Коротышка" -> shorties.add(DefaultMutableTreeNode(it, false))
                "Лунатик" -> moonlighters.add(DefaultMutableTreeNode(it, false))
                "Читатель" -> readers.add(DefaultMutableTreeNode(it, false))
                "Персонаж" -> readers.add(DefaultMutableTreeNode(it, false))
            }
        }
    }
}