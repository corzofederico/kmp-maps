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

    TODO("implement Line for Map")
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

    TODO("implement Line for Map")
}