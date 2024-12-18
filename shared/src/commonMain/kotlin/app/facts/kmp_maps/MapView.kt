package app.facts.kmp_maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalMapDrawScope = staticCompositionLocalOf<MapDrawScope> {
    throw Error("No MapDrawScope provided")
}

fun rememberMapState(
    key: String,
    defaultCenter: Coordinates = Coordinates.ZERO,
    defaultZoom: Int = 1
) = MapState(
    key,
    defaultCenter,
    defaultZoom
)

@Composable
expect fun MapView(
    state: MapState,
    modifier: Modifier = Modifier,
    draw: @Composable ()->Unit
)