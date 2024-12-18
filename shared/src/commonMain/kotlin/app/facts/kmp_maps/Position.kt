package app.facts.kmp_maps

import kotlin.math.sqrt

open class Position(
    val x: Double,
    val y: Double
){
    operator fun plus(r: Double) = Position(x + r, y + r)
    operator fun plus(p: Position) = Position(x + p.x, y + p.y)
    operator fun minus(p: Position) = Position(x - p.x, y - p.y)

    fun distance(other: Position):Double{
        val dx = x - other.x
        val dy = y - other.y
        return sqrt((dx * dx) + (dy * dy))
    }
}

data class Coordinates(
    val lat: Double,
    val lng: Double
):Position(lng, lat){
    operator fun plus(c: Coordinates) = Coordinates(lat + c.lat, lng + c.lng)
    companion object{
        val ZERO = Coordinates(.0, .0)
    }
}