package app.facts.kmp_maps

import org.jxmapviewer.JXMapViewer
import org.jxmapviewer.input.PanMouseInputListener
import java.awt.event.MouseEvent
import java.awt.geom.Point2D
import javax.swing.SwingUtilities

class MapListener(
    private val mapView: JXMapViewer,
    private val scope: MapDrawScope
): PanMouseInputListener(mapView){
    var elements: List<MapElement> = emptyList()
    var maxDragRadius: Double = 10.0

    private var center: Point2D? = null
    private var prev: Point2D? = null
    private var id: String? = null

    override fun mouseClicked(e: MouseEvent) {
        val p = e.point
        if (!SwingUtilities.isLeftMouseButton(e)) return
        val c = p.toCoordinates(mapView)

        val element = elements
            .associateWith{element-> element.distanceTo(c) { it.toPosition(mapView) } }
            .filter{(element,distance)-> distance in 0.0..maxDragRadius && element.events.onClick!=null}
            .minByOrNull{(element, distance)-> distance - element.zIndex}
            ?.key
            ?: return

        if(element.events.onClick!!(scope, element.id)){
            e.consume()
        }
    }

    override fun mousePressed(e: MouseEvent) {
        if(!SwingUtilities.isLeftMouseButton(e)) return super.mousePressed(e)
        if(id!=null) return // only "return" coz dont want to move the map, only the marker

        val p = e.point
        val c = p.toCoordinates(mapView)

        val element = elements
            .filterIsInstance<MapElement.Marker>()
            .associateWith{element-> element.distanceTo(c) { it.toPosition(mapView) } }
            .filter{(element,distance)-> distance in 0.0..maxDragRadius && element.events.onClick!=null}
            .minByOrNull{(element, distance)-> distance - element.zIndex}
            ?.key
            ?: return super.mousePressed(e)

        center = mapView.center
        prev = p
        e.consume()
        id = element.id
    }

    override fun mouseDragged(e: MouseEvent) {
        if(!SwingUtilities.isLeftMouseButton(e)) return super.mouseDragged(e)
        if(id==null) return super.mouseDragged(e)
        val p = e.point as? Point2D ?: return
        val pPrev = prev ?: return super.mouseDragged(e)

        val element = elements
            .filterIsInstance<MapElement.Marker>()
            .find{element-> element.id==id}
//        alert(id.toString())
        if(element==null || element.events.onDrag==null) return super.mouseDragged(e)

        mapView.apply{
            val maxHeight = (tileFactory.getMapSize(zoom).getHeight() * tileFactory.getTileSize(zoom))
            val maxWidth = (tileFactory.getMapSize(zoom).getWidth() * tileFactory.getTileSize(zoom))

            val deltaX = pPrev.x - p.x
            val deltaY = pPrev.y - p.y
            val pE = element.position.toPosition(this)
            val x = (pE.x - deltaX).coerceIn(0.0..maxWidth)
            val y = (pE.y - deltaY).coerceIn(0.0..maxHeight)
            val c = Point2D.Double(x, y).toCoordinates(this)

            if(element.events.onDrag(scope, element.id, c)){
                repaint()
                e.consume()
                prev = p
            }else{
                resetDrag()
                return super.mouseDragged(e)
            }
        }
    }
    fun resetDrag(){
        prev = null
        center = null
        id = null
    }
    override fun mouseReleased(e: MouseEvent?) {
        if (!SwingUtilities.isLeftMouseButton(e) || id==null) return

        e?.consume()
        mapView.repaint()

        resetDrag()
    }
}