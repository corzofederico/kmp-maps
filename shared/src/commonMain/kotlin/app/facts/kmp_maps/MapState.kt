package app.facts.kmp_maps

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MapState(
    defaultCenter: Coordinates = Coordinates.ZERO,
    defaultZoom: Int = 1
){
    val center = MutableStateFlow<Coordinates>(defaultCenter)
    val zoom = MutableStateFlow<Int>(defaultZoom)

    private val _elements = MutableStateFlow<List<MapElement>>(emptyList())
    val elements = _elements.asStateFlow()
    fun updateElement(element: MapElement){
        _elements.update{
            it.toMutableList().apply{
                val index = indexOfFirst{it.id==element.id}
                if(index in indices) set(index, element) else add(element)
            }
        }
    }
    fun removeElement(id: String){
        _elements.update{
            it.toMutableList().apply{
                removeAll{it.id==id}
            }
        }
    }
}