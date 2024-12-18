package app.facts.kmp_maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import app.facts.kmp_maps.MapElement.Events

@Composable
actual fun Marker(
    position: Coordinates,
    title: String,
    onClick: (MapDrawScope.(String)-> Boolean)?,
    onDrag: (MapDrawScope.(String, Coordinates)-> Boolean)?,
    id: String
){
    val drawScope = LocalMapDrawScope.current

    TODO("implement Marker for map")
}