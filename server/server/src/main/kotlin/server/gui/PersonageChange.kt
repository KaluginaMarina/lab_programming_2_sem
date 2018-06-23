package server.gui

import manage.Command
import model.*
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.time.LocalDateTime
import javax.swing.*

class PersonageChange(gui : GUI) : JComponent(){
    val shortiesRadioButton = JRadioButton("Коротышка")
    val moonlighterRadioButton = JRadioButton("Лунатик")
    val readerRagioButton = JRadioButton("Читатель")
    val nameTextFiled = JTextField(50)
    val xPointSpinner = JSpinner(SpinnerNumberModel(0.0 as Double, -1000.0, 1000.0, 1.0))
    val yPointSpinner = JSpinner(SpinnerNumberModel(0.0 as Double, -1000.0, 1000.0, 1.0))
    val hightSpinner = JSpinner(SpinnerNumberModel(0, 0, 300, 1))
    val forceSpinner = JSpinner(SpinnerNumberModel(0, 0, 100, 1))
    val normalMoodRadioButton = JRadioButton("Нормальное")
    val happyMoodRadioButton = JRadioButton("Веселое")
    val sadMoodRadioButton = JRadioButton("Грустное")
    val furyMoodRadioButton = JRadioButton("Яростное")
    val addButton = JButton("Добавить")
    val changeButton = JButton("Изменить")
    val removeButton = JButton("Удалить")

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

        removeButton.addActionListener{
            dropThisPerson()
        }

        addButton.addActionListener{
            addThisPerson()
        }

        changeButton.addActionListener{
            changePersonage(gui.tree.selectedPersonage)
        }
    }

    fun dropThisPerson(){
        if(!check()) return
        Command.remove(createPers())
        removeColor()
    }

    fun addThisPerson(){
        if (!check()) return
        Command.add(createPers())
        removeColor()
    }

    fun changePersonage(pers : Personage?){
        if(!check()) return
        Command.remove(pers)
        addThisPerson()
        removeColor()
    }

    fun moodValue() : Mood {
        if (happyMoodRadioButton.isSelected)
            return Mood.HAPPY
        if (normalMoodRadioButton.isSelected)
            return Mood.NORMAL
        if (sadMoodRadioButton.isSelected)
            return Mood.SAD
        if (furyMoodRadioButton.isSelected)
            return Mood.FURY
        return Mood.NORMAL
    }

    fun check() : Boolean{
        var res = true
        if (!(shortiesRadioButton.isSelected || moonlighterRadioButton.isSelected || readerRagioButton.isSelected)){
            shortiesRadioButton.background = Color(255, 156, 140)
            moonlighterRadioButton.background = Color(255, 156, 140)
            readerRagioButton.background = Color(255, 156, 140)
            res = false
        }
        if (nameTextFiled.text.equals("")){
            nameTextFiled.background = Color(255, 156, 140)
            res = false
        }
        if (xPointSpinner.value == null){
            xPointSpinner.background = Color(255, 156, 140)
            res = false
        }
        if (yPointSpinner.value == null){
            yPointSpinner.background = Color(255, 156, 140)
            res = false
        }
        if (hightSpinner.value == null){
            hightSpinner.value = Color(255, 156, 140)
            res = false
        }
        if (!(happyMoodRadioButton.isSelected || normalMoodRadioButton.isSelected || sadMoodRadioButton.isSelected || furyMoodRadioButton.isSelected)){
            happyMoodRadioButton.background = Color(255, 156, 140)
            normalMoodRadioButton.background =  Color(255, 156, 140)
            sadMoodRadioButton.background = Color(255, 156, 140)
            furyMoodRadioButton.background = Color(255, 156, 140)
            res = false
        }

        return res
    }

    fun removeColor() {
        shortiesRadioButton.background = null
        shortiesRadioButton.isSelected = false
        moonlighterRadioButton.background = null
        moonlighterRadioButton.isSelected = false
        readerRagioButton.background = null
        readerRagioButton.isSelected = false
        nameTextFiled.background = Color.white
        nameTextFiled.text = ""
        xPointSpinner.background = null
        xPointSpinner.value = 0
        yPointSpinner.background = null
        yPointSpinner.value = 0
        hightSpinner.background = null
        hightSpinner.value = 0
        happyMoodRadioButton.background = null
        happyMoodRadioButton.isSelected = false
        normalMoodRadioButton.background =  null
        normalMoodRadioButton.isSelected = false
        sadMoodRadioButton.background = null
        sadMoodRadioButton.isSelected = false
        furyMoodRadioButton.background = null
        furyMoodRadioButton.isSelected = false
    }

    fun createPers() : Personage {
        val x = xPointSpinner.model.value as Double
        val y = yPointSpinner.model.value as Double
        val h = hightSpinner.value as Int
        if (shortiesRadioButton.isSelected) {
            val shorties = Shorties(nameTextFiled.text, x, y, h)
            shorties.force = forceSpinner.value as Int
            shorties.mood = moodValue()
            return shorties
        }
        if (moonlighterRadioButton.isSelected){
            val moonlighter = Moonlighter(nameTextFiled.text, x, y, h)
            moonlighter.force = forceSpinner.value as Int
            moonlighter.mood = moodValue()
            return moonlighter
        }
        if (readerRagioButton.isSelected){
            val reader = Reader(nameTextFiled.text)
            reader.x = x
            reader.y = y
            reader.height = h
            reader.force = forceSpinner.value as Int
            reader.mood = moodValue()
            return reader
        }
        return Personage("NoName", "Noname", 1.0, 1.0, 1, 1, 1, Mood.NORMAL, LocalDateTime.now())
    }
}