package app.facts.kmp_maps

import org.jxmapviewer.JXMapViewer
import org.jxmapviewer.painter.Painter
import java.awt.BasicStroke
import java.awt.Graphics2D
import kotlin.math.roundToInt

class LinePainter(
    val line: MapElement.Line
): Painter<JXMapViewer> {
    override fun paint(
        g: Graphics2D,
        mapView: JXMapViewer,
        width: Int,
        height: Int,
    ) {
        // Guarda el estado original del gráfico
        val g2d = g.create() as? Graphics2D?:return

        // Configura el color y el grosor de la línea
        g2d.color = line.color.asMapColor
        g2d.stroke = BasicStroke(line.width)

        // Convierte las posiciones geográficas a puntos en la pantalla
        line.positions.mapNotNull{
            mapView.convertGeoPositionToPoint(it.toGeoPosition())
        }.zipWithNext { a, b ->
            g2d.drawLine(a.x.roundToInt(), a.y.roundToInt(), b.x.roundToInt(), b.y.roundToInt())
        }

        // Libera recursos gráficos
        g2d.dispose()
    }
}