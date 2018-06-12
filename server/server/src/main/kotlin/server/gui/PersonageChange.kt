package server.gui

import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class PersonageChange(gui : GUI) : JComponent(){
    val shortiesRadioButton = JRadioButton("Коротышка")
    val moonlighterRadioButton = JRadioButton("Лунатик")
    val readerRagioButton = JRadioButton("Читатель")
    val nameTextFiled = JTextField(50)
    val xPointSpinner = JSpinner()
    val yPointSpinner = JSpinner()
    val hightSpinner = JSpinner(SpinnerNumberModel(0, 0, 300, 1))
    val forceSpinner = JSpinner(SpinnerNumberModel(0, 0, 100, 1))
    val normalMoodRadioButton = JRadioButton("Нормальное")
    val happyMoodRadioButton = JRadioButton("Веселое")
    val sadMoodRadioButton = JRadioButton("Грустное")
    val furyMoodRadioButton = JRadioButton("Яростное")
    val addButton = JButton("Добавить")
    val changeButton = JButton("Изменить")
    val removeButton = JButton("Удалить")
    val personageTree = gui.tree

    init {
        layout = GridBagLayout()
        val c = GridBagConstraints()
        c.fill = GridBagConstraints.HORIZONTAL
        c.gridx = 0
        c.gridy = 0
        c.gridwidth = 3
        add(object : JLabel(){
            init {
                text = "Тип:"
                foreground = Color.darkGray
            }
        }, c)
        val typeButtonGroup = ButtonGroup()
        shortiesRadioButton.foreground = Color.darkGray
        moonlighterRadioButton.foreground = Color.darkGray
        readerRagioButton.foreground = Color.darkGray
        typeButtonGroup.add(shortiesRadioButton)
        typeButtonGroup.add(moonlighterRadioButton)
        typeButtonGroup.add(readerRagioButton)
        c.gridy = 1
        c.gridwidth = 1
        add(shortiesRadioButton, c)
        c.gridx = 1
        add(moonlighterRadioButton, c)
        c.gridx = 2
        add(readerRagioButton, c)

        c.gridx = 0
        c.gridy = 2
        add(object : JLabel(){
            init {
                text = "Имя:"
                foreground = Color.darkGray
            }
        }, c)

        c.gridy = 3
        c.gridwidth = 3
        add(nameTextFiled, c)

        c.gridy = 4
        c.gridwidth = 1
        add(object : JLabel(){
            init {
                text = "X:"
                foreground = Color.darkGray
            }
        }, c)

        c.gridx = 1
        add(object : JLabel(){
            init {
                text = "Y:"
                foreground = Color.darkGray
            }
        }, c)

        c.gridx = 0
        c.gridy = 5
        add(xPointSpinner, c)

        c.gridx = 1
        add(yPointSpinner, c)

        c.gridx = 0
        c.gridy = 6
        add(object : JLabel(){
            init {
                text = "Рост:"
                foreground = Color.darkGray
            }
        }, c)

        c.gridy = 7
        add(hightSpinner, c)

        c.gridy = 8
        add(object : JLabel(){
            init {
                text = "Сила:"
                foreground = Color.darkGray
            }
        }, c)

        c.gridy = 9
        add(forceSpinner, c)

        val moodButtonGroup = ButtonGroup()
        moodButtonGroup.add(happyMoodRadioButton)
        moodButtonGroup.add(normalMoodRadioButton)
        moodButtonGroup.add(sadMoodRadioButton)
        moodButtonGroup.add(furyMoodRadioButton)

        c.gridy = 10
        add(object : JLabel(){
            init {
                text = "Настроение:"
                foreground = Color.darkGray
            }
        }, c)

        c.gridy = 11
        add(happyMoodRadioButton, c)
        c.gridwidth = 2
        c.gridy = 12
        add(normalMoodRadioButton, c)
        c.gridwidth = 1
        c.gridy = 13
        add(sadMoodRadioButton, c)
        c.gridy = 14
        add(furyMoodRadioButton, c)

        c.gridy = 15
        c.gridx = 0
        add(addButton, c)
        c.gridx = 1
        add(changeButton, c)
        c.gridx = 2
        add(removeButton, c)
    }

}