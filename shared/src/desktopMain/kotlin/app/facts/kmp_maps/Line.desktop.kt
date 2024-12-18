package app.facts.kmp_maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import app.facts.kmp_maps.MapElement.Events

@JvmName("LineByCoordinates")
@Composable
actual fun Line(
    coordinates: List<Coordinates>,
    color: Color,
    width: Dp,
    onClick: (MapDrawScope.(String) -> Boolean)?,
    id: String
) {
    val drawScope = LocalMapDrawScope.current
    val density = LocalDensity.current

    val element = remember(id, coordinates, color, width, onClick){
        MapElement.Line(
            id,
            coordinates,
            color,
            with(density) { width.toPx() },
            0,
            Events(onClick)
        )
    }
    LaunchedEffect(drawScope, element){
        drawScope.apply{
            +element
        }
    }
}

@JvmName("LineByMarkers")
@Composable
actual fun Line(
    markers: List<String>,
    color: Color,
    width: Dp,
    onClick: (MapDrawScope.(String)-> Boolean)?,
    id: String
) {
    val drawScope = LocalMapDrawScope.current
    val density = LocalDensity.current

    val elements by drawScope.state.elements.collectAsState()

    val element = remember(id, markers, color, width, onClick, elements){
        MapElement.Line(
            id,
            elements.mapNotNull { element -> if (element.id in markers && element is MapElement.Marker) element.position else null },
            color,
            with(density) { width.toPx() },
            0,
            Events(onClick)
        )
    }
    LaunchedEffect(Unit, drawScope, element){
        drawScope.set(element)
    }
}