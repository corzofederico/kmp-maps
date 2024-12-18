package app.facts.kmp_maps

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MapDrawScope(
    val state: MapState,
    private val scope: CoroutineScope
){
    operator fun contains(id: String) = state.elements.value.any{it.id==id}
    fun visible(id: String) = state.elements.value.any{it.id==id && it.visible}
    fun exist(element: MapElement) = contains(element.id)

    fun move(id: String, p: Coordinates){
        val elements = state.elements.value
        val index = elements.indexOfFirst{it.id==id}
        if(index !in elements.indices) return
        val element = elements[index] as? MapElement.Marker ?: return
        +element.move(p)
    }
    fun setVisibility(id: String, visible: Boolean){
        val elements = state.elements.value
        val index = elements.indexOfFirst{it.id==id}
        if(index !in elements.indices) return
        val element = elements[index] as? MapElement.Marker ?: return
        +element.copyVisibility(visible)
    }
    fun <T: MapElement> T.move(p: Coordinates) = move(id, p)

    fun <T: MapElement> T.set():T{
        state.updateElement(this)
        return this
    }
    fun set(e: MapElement)=state.updateElement(e)
    operator fun <T: MapElement> T.unaryPlus():T = set()
    operator fun String.unaryPlus() = setVisibility(this, true)

    operator fun <T: MapElement> T.unaryMinus() = state.removeElement(id)
    operator fun String.unaryMinus() = setVisibility(this, false)

    fun async(work: suspend () -> Unit){
        scope.launch{
            work()
        }
    }
}