package com.example.luiseduardomb.listadetarefas.store

import android.arch.lifecycle.LiveData

interface Renderer<T> {
    fun render(model: LiveData<T>)
}