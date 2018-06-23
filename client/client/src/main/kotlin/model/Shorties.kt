package model

import java.time.LocalDateTime

class Shorties(name: String, x: Double, y: Double, height: Int) : Personage("Коротышка", name, x, y, 3, height, 5, Mood.NORMAL, LocalDateTime.now(), 0) {

    /**
     * Сравнение коротышек.
     * @param s - второй коротышка
     * @return true - коротышки похожи, false - разные коротышки
     */
    fun compare(s: Shorties): Boolean {
        if (equals(s)) {
            println(this.type + " похож на " + this.name + ".")
            return true
        } else {
            println(this.type + " не похож на " + this.name + ".")
            return false
        }
    }
}
