package com.example.luiseduardomb.listadetarefas.store

import android.arch.core.util.Function
import com.example.luiseduardomb.listadetarefas.model.Action


interface Store<T> {
    fun dispatch(action: Action)
    fun subscribe(renderer: Renderer<T>, func: Function<T, T> = Function { it })
}