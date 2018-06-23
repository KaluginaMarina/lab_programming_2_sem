package model


import java.time.LocalDateTime

class Reader(name: String) : Personage("Читатель", name, 0.0, 0.0, 0, 158, 0, Mood.NORMAL, LocalDateTime.now(), 0)
