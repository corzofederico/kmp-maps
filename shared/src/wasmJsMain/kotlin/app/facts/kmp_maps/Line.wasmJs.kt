package app.facts.kmp_maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.jvm.JvmName
import kotlin.uuid.ExperimentalUuidApi

@OptIn(markerClass = [ExperimentalUuidApi::class])
@Composable
actual fun Line(
    coordinates: List<Coordinates>,
    color: Color,
    width: Dp,
    onClick: (MapDrawScope.(String) -> Boolean)?,
    id: String,
) {
    TODO("Not yet implemented")
}

@OptIn(markerClass = [ExperimentalUuidApi::class])
@Composable
actual fun Line(
    markers: List<String>,
    color: Color,
    width: Dp,
    onClick: (MapDrawScope.(String) -> Boolean)?,
    id: String,
) {
    TODO("Not yet implemented")
}