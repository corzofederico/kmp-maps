package app.facts.kmp_maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
expect fun Marker(
    position: Coordinates,
    title: String,
    onClick: (MapDrawScope.(id: String)-> Boolean)?=null,
    onDrag: (MapDrawScope.(id: String, position: Coordinates)-> Boolean) ?= null,
    id: String = rememberSaveable { Uuid.random().toHexString() },
)

val onMarkerDrag: MapDrawScope.(String, Coordinates) -> Boolean
    get() = {id,position->
        move(id, position)
        true
    }