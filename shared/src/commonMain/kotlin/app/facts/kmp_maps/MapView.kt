package app.facts.kmp_maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

const val mapsApiKey = "AIzaSyBZQEY-F1dcI5mq9M8geeiRMoxFSAQkNmc" // api for test

val LocalMapDrawScope = staticCompositionLocalOf<MapDrawScope> {
    throw Error("No MapDrawScope provided")
}

fun rememberMapState(
    defaultCenter: Coordinates = Coordinates.ZERO,
    defaultZoom: Int = 1
) = MapState(
    defaultCenter,
    defaultZoom
)

@Composable
expect fun MapView(
    state: MapState = rememberMapState(),
    modifier: Modifier = Modifier,
    draw: @Composable ()->Unit
)