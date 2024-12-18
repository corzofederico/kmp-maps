package app.facts.kmp_maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import app.facts.kmp_maps.MapElement.Events
import kotlin.uuid.ExperimentalUuidApi

@Composable
actual fun Marker(
    position: Coordinates,
    title: String,
    onClick: (MapDrawScope.(id: String) -> Boolean)?,
    onDrag: (MapDrawScope.(id: String, position: Coordinates) -> Boolean)?,
    id: String,
){
    val drawScope = LocalMapDrawScope.current

    val element = remember(id, position, title, onClick, onDrag){
        MapElement.Marker(
            id,
            position,
            title,
            1,
            Events(onClick, onDrag)
        )
    }
    LaunchedEffect(drawScope, element){
        drawScope.apply{
            +element
        }
    }
}