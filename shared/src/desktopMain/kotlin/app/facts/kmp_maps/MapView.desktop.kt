package app.facts.kmp_maps

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jxmapviewer.JXMapViewer
import org.jxmapviewer.OSMTileFactoryInfo
import org.jxmapviewer.VirtualEarthTileFactoryInfo
import org.jxmapviewer.google.GoogleMapsTileFactoryInfo
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor
import org.jxmapviewer.painter.CompoundPainter
import org.jxmapviewer.painter.Painter
import org.jxmapviewer.viewer.DefaultTileFactory
import org.jxmapviewer.viewer.DefaultWaypoint
import org.jxmapviewer.viewer.GeoPosition
import org.jxmapviewer.viewer.WaypointPainter
import java.awt.geom.Point2D

// APP -> JVM
//fun Position.toPoint() = Point2D.Double(x,y)
fun Coordinates.toGeoPosition() = GeoPosition(doubleArrayOf(lat,lng))
// JVM -> APP
fun Point2D.toPosition() = Position(x, y)
fun Point2D.toCoordinates(mapView: JXMapViewer) = mapView.convertPointToGeoPosition(this).toCoordinates()
fun GeoPosition.toCoordinates() = Coordinates(latitude, longitude)
// normalize
fun Coordinates.toPosition(mapView: JXMapViewer) = mapView.convertGeoPositionToPoint(toGeoPosition()).toPosition()

val Color.asMapColor: java.awt.Color get() = java.awt.Color(toArgb())

@Composable
fun rememberMapListener(
    mapView: JXMapViewer,
    elements: List<MapElement>,
    dragRadius: Dp = 5.dp
): MapListener {
    val scope = LocalMapDrawScope.current

    val listener = remember{
        MapListener(mapView, scope)
    }
    fun updateListener(){
        mapView.apply{
            removeMouseListener(listener)
            removeMouseMotionListener(listener)
            addMouseListener(listener)
            addMouseMotionListener(listener)
        }
    }

    val density = LocalDensity.current
    LaunchedEffect(density, dragRadius){
        listener.maxDragRadius = with(density){ dragRadius.toPx() }.toDouble()
        updateListener()
    }

    LaunchedEffect(elements){
        listener.elements = elements
        updateListener()
    }

    return listener
}


//val osmInfo = OSMTileFactoryInfo()
//val veInfo = VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP)
fun googleInfo(key: String) = GoogleMapsTileFactoryInfo("maps-test","https://maps.googleapis.com/maps/api/staticmap", key)
fun googleFactory(key: String) = DefaultTileFactory(googleInfo(key))
/*val factories = listOf(
    DefaultTileFactory(googleInfo),
    DefaultTileFactory(osmInfo),
    DefaultTileFactory(veInfo),
)*/

fun MapElement.painter(): Painter<JXMapViewer>{
    return when(this){
        is MapElement.Line -> LinePainter(this)
        is MapElement.Marker -> WaypointPainter<DefaultWaypoint>().apply{
            waypoints = listOf(DefaultWaypoint(position.toGeoPosition())).toSet()
        }
    }
}
@Composable
actual fun MapView(
    state: MapState,
    modifier: Modifier,
    draw: @Composable ()->Unit
) {
    val mapView = remember(state){
        JXMapViewer().apply{
            // add events
            addPropertyChangeListener { pE ->
                when(pE.propertyName){
                    "center","centerPosition" -> state.center.value = centerPosition.toCoordinates()
                    "zoom" -> state.zoom.value = this.zoom
                }
            }
            // Add interactions
            addMouseWheelListener(ZoomMouseWheelListenerCursor(this))
        }
    }
    val cScope = rememberCoroutineScope()
    val drawScope = remember(state){ MapDrawScope(state, cScope) }

    val key by state.key.collectAsState()
    val center by state.center.collectAsState()
    val zoom by state.zoom.collectAsState()
    val elements by state.elements.collectAsState()

    LaunchedEffect(mapView, elements){
        mapView.apply{
            overlayPainter = CompoundPainter<JXMapViewer>(
                elements
                    .filter{ it.visible }
                    .sortedBy { e -> e.zIndex }
                    .map{ element -> element.painter() }
            )
            repaint()
        }
    }
    LaunchedEffect(mapView, zoom, center, key){
        mapView.apply{
            setTileFactory(googleFactory(key))
            setZoom(zoom)
            setCenterPosition(center.toGeoPosition())
            repaint()
        }
    }

    CompositionLocalProvider(LocalMapDrawScope provides drawScope){
        rememberMapListener(mapView, elements)
        draw()
    }
    SwingPanel(
        colorScheme.background,
        {mapView},
        modifier
    )
}
