package model

import java.time.LocalDateTime

class Moonlighter(name: String, x: Double, y: Double, height: Int) : Personage("Лунатик", name, x, y, 7, height, 10, Mood.NORMAL, LocalDateTime.now(), 0)
