package app.facts.kmp_maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier

@Composable
actual fun MapView(
    state: MapState,
    modifier: Modifier,
    draw: @Composable ()->Unit
) {
    val cScope = rememberCoroutineScope()
    val drawScope = remember(state){ MapDrawScope(state, cScope) }

    val center by state.center.collectAsState()
    val zoom by state.zoom.collectAsState()
    val elements by state.elements.collectAsState()

    CompositionLocalProvider(LocalMapDrawScope provides drawScope){
        draw()
    }

    TODO("implement MapView")
}
