package app.facts.kmp_maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.jvm.JvmName
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
@JvmName("LineByCoordinates")
expect fun Line(
    coordinates: List<Coordinates>,
    color: Color = Color.Black,
    width: Dp = 3.dp,
    onClick: (MapDrawScope.(String)-> Boolean)?=null,
    id: String = rememberSaveable{ Uuid.random().toHexString() },
)

@OptIn(ExperimentalUuidApi::class)
@Composable
@JvmName("LineByMarkers")
expect fun Line(
    markers: List<String>,
    color: Color = Color.Black,
    width: Dp = 3.dp,
    onClick: (MapDrawScope.(String)-> Boolean)? = null,
    id: String = rememberSaveable{ Uuid.random().toHexString() },
)