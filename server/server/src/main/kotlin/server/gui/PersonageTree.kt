package server.gui

import model.Personage
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.concurrent.ConcurrentLinkedDeque
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

class PersonageTree(heroes : ConcurrentLinkedDeque<Personage>) : JTree(getTreeModel()) {

    companion object {
        fun getTreeModel() : DefaultTreeModel{
            val shorties = DefaultMutableTreeNode("Коротышки")
            val moonlighters = DefaultMutableTreeNode("Лунатики")
            val readers = DefaultMutableTreeNode("Читатели")
            val personages = DefaultMutableTreeNode("Персонажи").apply {
                add(shorties)
                add(moonlighters)
                add(readers)}
            return DefaultTreeModel(personages, true)
        }

    }
    init {
        refresh()
        addMouseListener(object : MouseAdapter(){
            override fun mousePressed(e: MouseEvent) {
                if (e.clickCount != 2) return
                val selPath = getPathForLocation(e.x, e.y)
                if (selPath != null) {
                    val selected = (selPath.getLastPathComponent() as DefaultMutableTreeNode).userObject
                    when (selected) {
                        is String -> {
                            println(selected)
                        }
                        is Personage -> {
                            println(selected)
                        }
                    }
                }
            }
        })
    }

    fun refresh(){
        val root = (model.root as DefaultMutableTreeNode).children().toList()
        (root[0] as DefaultMutableTreeNode).removeAllChildren()
        (root[1] as DefaultMutableTreeNode).removeAllChildren()
        (root[2] as DefaultMutableTreeNode).removeAllChildren()
        manage.Command.heroes.forEach{
            when (it.type) {
                "Коротышка" -> (root[0] as DefaultMutableTreeNode).add(DefaultMutableTreeNode(it, false))
                "Лунатик" -> (root[1] as DefaultMutableTreeNode).add(DefaultMutableTreeNode(it, false))
                "Читатель" -> (root[2] as DefaultMutableTreeNode).add(DefaultMutableTreeNode(it, false))
                "Персонаж" -> (model.root as DefaultMutableTreeNode).add(DefaultMutableTreeNode(it, false))
            }
        }
        (model as DefaultTreeModel).reload()
    }


}