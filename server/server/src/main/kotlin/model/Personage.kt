package model

import orm.*
import orm.Enum
import java.io.Serializable
import java.time.LocalDateTime


@Table(name = "Personages")
open class Personage(
        var type: String = "Персонаж",
        var name: String = "NoName",
        var x: Double = 0.0,
        var y: Double = 0.0,
        var force: Int,
        var height: Int,
        var skillSwear: Int = 0,
        @Enum var mood: Mood,
        var dateCreate: LocalDateTime) : Comparable<Personage>, Serializable {

    override fun equals(s: Any?): Boolean {
        if (s == null) {
            return false
        }
        if (this === s) {
            return true
        }
        if (javaClass != s.javaClass) {
            return false
        } else {
            val tmp = s as Personage?
            return tmp!!.name == name && force == tmp.force && mood == tmp.mood && tmp.type == type
        }
    }

    override fun hashCode(): Int {
        val power = 239
        var hash = 0
        for (i in 0 until name.length) {
            hash = hash * power + name[i].toInt()
        }

        for (i in 0 until type.length) {
            hash = hash * power + type[i].toInt()
        }


        if (mood == Mood.NORMAL) {
            hash *= 2
        } else if (mood == Mood.FURY) {
            hash *= 3
        } else if (mood == Mood.HAPPY) {
            hash *= 4
        } else if (mood == Mood.SAD) {
            hash *= 5
        }

        hash *= (force.toDouble() * x * y * height.toDouble()).toInt()
        return hash
    }

    override fun toString(): String {
        return "$type $name"
    }

    /**
     * Кто-то что-то почувствовал
     */
    fun feel() {
        print("$name почувствовал, что ")
    }

    /**
     * Метод, сравнивает 2-х персонажей
     * @param p - персонаж с которым сравниваем
     * @return 0 - объекты равны, > 0 => (this > p), < 0 => (this < p)
     */
    override fun compareTo(p: Personage): Int {
        return if (this.toString().compareTo(p.toString()) == 0) {
            this.height - p.height
        } else {
            this.toString().compareTo(p.toString())
        }
    }

    fun setMood(s: String): Boolean {
        when (s) {
            "Mood.NORMAL" -> {
                run { }
                run {
                    this.mood = Mood.NORMAL
                }
            }
            "NORMAL" -> {
                this.mood = Mood.NORMAL
            }
            "Mood.FURY" -> {
                run { }
                run {
                    this.mood = Mood.FURY
                }
            }
            "FURY" -> {
                this.mood = Mood.FURY
            }
            "Mood.HAPPY" -> {
                run { }
                run {
                    this.mood = Mood.HAPPY
                }
            }
            "HAPPY" -> {
                this.mood = Mood.HAPPY
            }
            "Mood.SAD" -> {
                run { }
                run {
                    this.mood = Mood.SAD
                }
            }
            "SAD" -> {
                this.mood = Mood.SAD
            }
            else -> {
                return false
            }
        }
        return true
    }
}
