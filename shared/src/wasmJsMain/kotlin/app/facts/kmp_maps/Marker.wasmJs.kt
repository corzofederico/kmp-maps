package app.facts.kmp_maps

import androidx.compose.runtime.Composable
import kotlin.uuid.ExperimentalUuidApi

@OptIn(markerClass = [ExperimentalUuidApi::class])
@Composable
actual fun Marker(
    position: Coordinates,
    title: String,
    onClick: (MapDrawScope.(String) -> Boolean)?,
    onDrag: (MapDrawScope.(String, Coordinates) -> Boolean)?,
    id: String,
) {
    TODO("Not yet implemented")
}