package server.gui

import model.Personage
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.concurrent.ConcurrentLinkedDeque
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

class PersonageTree(heroes : ConcurrentLinkedDeque<Personage>, guiPersonageChange: PersonageChange) : JTree(getTreeModel()) {

    var selectedPersonage: Personage?

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
        selectedPersonage = null
        addMouseListener(object : MouseAdapter(){
            override fun mousePressed(e: MouseEvent) {
                if (e.clickCount != 2) return
                val selPath = getPathForLocation(e.x, e.y)
                if (selPath != null) {
                    val selected = (selPath.getLastPathComponent() as DefaultMutableTreeNode).userObject
                    when (selected) {
                        is Personage -> {
                            guiPersonageChange.apply {
                                when (selected.type) {
                                    "Коротышка" -> shortiesRadioButton.isSelected = true
                                    "Лунатик" -> moonlighterRadioButton.isSelected = true
                                    "Читатель" -> readerRagioButton.isSelected = true
                                }
                                nameTextFiled.text = selected.name
                                xPointSpinner.value = selected.x
                                yPointSpinner.value = selected.y
                                hightSpinner.value = selected.height
                                forceSpinner.value = selected.force
                                when (selected.mood.name) {
                                    "HAPPY" -> happyMoodRadioButton.isSelected = true
                                    "NORMAL" -> normalMoodRadioButton.isSelected = true
                                    "SAD" -> sadMoodRadioButton.isSelected = true
                                    "FURY" -> furyMoodRadioButton.isSelected = true
                                }
                            }
                            selectedPersonage = selected
                        }
                        is String -> {
                            selectedPersonage = null
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