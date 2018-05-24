package com.example.luiseduardomb.listadetarefas.store

import android.arch.core.util.Function
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.luiseduardomb.listadetarefas.model.*


class StoreTarefa : Store <ModeloTarefas>, ViewModel(){
    private val state: MutableLiveData <ModeloTarefas> = MutableLiveData()
    private val initState = ModeloTarefas(listOf(), Visibility.Todas())


    override fun dispatch(action: Action) {
        state.value = reduce(state.value, action)
            }
    private fun reduce(state: ModeloTarefas?, action: Action): ModeloTarefas{
        val newState = state ?: initState
        return when(action){

            is AdicionarTarefa -> newState.copy(
                    tarefas = newState.tarefas.toMutableList().apply{
                        add(Tarefas(action.text, action.id))
                    }
            )
            is MudarTarefa -> newState.copy(
                    tarefas = newState.tarefas.map{
                        if (it.id == action.id){
                            it.copy(status = !it.status)
                        } else it
                    } as MutableList<Tarefas>
            )
            is SetVisibility -> newState.copy(
                    visibility = action.visibility
            )
            is RemoverTarefa -> newState.copy(
                    tarefas = newState.tarefas.filter{
                        it.id != action.id
                    } as MutableList<Tarefas>
            )
        }
    }

    override fun subscribe(renderer: Renderer<ModeloTarefas>, func: Function<ModeloTarefas, ModeloTarefas>) {
        renderer.render(Transformations.map(state, func))
    }


}