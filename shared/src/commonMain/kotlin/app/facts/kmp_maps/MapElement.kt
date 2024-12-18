package app.facts.kmp_maps

import androidx.compose.ui.graphics.Color
import kotlin.math.abs
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
sealed class MapElement(
    val id: String,
    val zIndex: Int,
    val events: Events = Events(),
    val visible: Boolean = true
){
    abstract fun distanceTo(p: Coordinates, normalize: (Coordinates)-> Position = {it}): Double

    class Marker(
        id: String = Uuid.random().toHexString(),
        val position: Coordinates = Coordinates.ZERO,
        val title: String = "",
        zIndex: Int = 1,
        events: Events = Events(),
        visible: Boolean = true
    ):MapElement(id, zIndex, events, visible){
        fun move(p: Coordinates) = Marker(id, p, title, zIndex, events, visible)
        fun copyVisibility(visible: Boolean) = Marker(id, position, title, zIndex, events, visible)
        override fun distanceTo(p: Coordinates, normalize: (Coordinates)-> Position) = normalize(position).distance(normalize(p))
    }

    class Line(
        id: String = Uuid.random().toHexString(),
        val positions: List<Coordinates> = emptyList(),
        val color: Color = Color.Black,
        val width: Float = 10f,
        zIndex: Int = 0,
        events: Events = Events(),
    ):MapElement(id, zIndex, events){
        override fun distanceTo(p: Coordinates, normalize: (Coordinates)-> Position):Double{
            val p0 = normalize(p)
            val p1 = normalize(positions.first())
            val p2 = normalize(positions.last())
            return abs((p2.y - p1.y) * p0.x - (p2.x - p1.x) * p0.y + p2.x * p1.y - p2.y * p1.x) / p1.distance(p2)
        }
    }
    data class Events(
        val onClick: (MapDrawScope.(String) -> Boolean)? = null,
        val onDrag: (MapDrawScope.(String,Coordinates) -> Boolean)? = null,
    )
}