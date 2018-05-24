package com.example.luiseduardomb.listadetarefas.model

sealed class Action

var counter = 0L
data class AdicionarTarefa(val text: String, val id: Long = counter++) : Action()
data class MudarTarefa(val id: Long) : Action()
data class SetVisibility(val visibility: Visibility) : Action()
data class RemoverTarefa(val id: Long) : Action()

sealed class Visibility{
    class Todas: Visibility()
    class Ativas: Visibility()
    class Finalizadas: Visibility()
}