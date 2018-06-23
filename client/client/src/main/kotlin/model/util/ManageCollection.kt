package client.util

import model.Personage

import java.util.concurrent.ConcurrentLinkedDeque

class ManageCollection() {
    fun maxPower(heroes: ConcurrentLinkedDeque<Personage>): Int {
        val c = ConcurrentLinkedDeque<Personage>()
        c.addAll(heroes)
        var res = 0
        while (!c.isEmpty()) {
            val tmp = c.removeFirst().force
            if (tmp > res) {
                res = tmp
            }
        }
        return res
    }

    fun maxHeight(heroes: ConcurrentLinkedDeque<Personage>): Int {
        val c = ConcurrentLinkedDeque<Personage>()
        c.addAll(heroes)
        var res = 0
        while (!c.isEmpty()) {
            val tmp = c.removeFirst().height
            if (tmp > res) {
                res = tmp
            }
        }
        return res
    }

    fun maxX(heroes: ConcurrentLinkedDeque<Personage>): Double {
        val c = ConcurrentLinkedDeque<Personage>()
        c.addAll(heroes)
        if (c.isEmpty()) {
            return 0.0
        }
        var res = java.lang.Double.MIN_VALUE
        while (!c.isEmpty()) {
            val tmp = c.removeFirst().x
            if (tmp > res) {
                res = tmp
            }
        }
        return res
    }

    fun minX(heroes: ConcurrentLinkedDeque<Personage>): Double {
        val c = ConcurrentLinkedDeque<Personage>()
        c.addAll(heroes)
        if (c.isEmpty()) {
            return 0.0
        }
        var res = java.lang.Double.MAX_VALUE
        while (!c.isEmpty()) {
            val tmp = c.removeFirst().x
            if (tmp < res) {
                res = tmp
            }
        }
        return res
    }

    fun maxY(heroes: ConcurrentLinkedDeque<Personage>): Double {
        val c = ConcurrentLinkedDeque<Personage>()
        c.addAll(heroes)
        if (c.isEmpty()) {
            return 0.0
        }
        var res = java.lang.Double.MIN_VALUE
        while (!c.isEmpty()) {
            val tmp = c.removeFirst().y
            if (tmp > res) {
                res = tmp
            }
        }
        return res
    }

    fun minY(heroes: ConcurrentLinkedDeque<Personage>): Double {
        val c = ConcurrentLinkedDeque<Personage>()
        c.addAll(heroes)
        if (c.isEmpty()) {
            return 0.0
        }
        var res = java.lang.Double.MAX_VALUE
        while (!c.isEmpty()) {
            val tmp = c.removeFirst().y
            if (tmp < res) {
                res = tmp
            }
        }
        return res
    }

    fun minHeight(heroes: ConcurrentLinkedDeque<Personage>): Double {
        val c = ConcurrentLinkedDeque<Personage>()
        c.addAll(heroes)
        if (c.isEmpty()) {
            return 0.0
        }
        var res = java.lang.Double.MAX_VALUE
        while (!c.isEmpty()) {
            val tmp = c.removeFirst().height.toDouble()
            if (tmp < res) {
                res = tmp
            }
        }
        return res
    }
}
