package com.example.luiseduardomb.listadetarefas

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.core.util.Function
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.luiseduardomb.listadetarefas.model.*
import com.example.luiseduardomb.listadetarefas.store.Renderer
import com.example.luiseduardomb.listadetarefas.store.StoreTarefa
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.selector

class MainActivity : AppCompatActivity(), Renderer<ModeloTarefas> {
    private lateinit var store: StoreTarefa

    override fun render(model: LiveData<ModeloTarefas>){
        model.observe( this, Observer { newState ->
            listView.adapter = TarefasAdapter(this, newState?.tarefas ?: listOf())
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        store = ViewModelProviders.of(this).get(StoreTarefa::class.java)
        store.subscribe(this, mapStateToProps)

        addButton.setOnClickListener {
            store.dispatch( AdicionarTarefa(editText1.text.toString()))
            editText1.text = null
        }
        fab.setOnClickListener{ openDialog()}

        listView.adapter = TarefasAdapter( this, listOf())
        listView.setOnItemClickListener({ _, _, _, id ->
            store.dispatch( MudarTarefa(id))
        })

        listView.setOnItemLongClickListener { _, _, _, id ->
            store.dispatch(RemoverTarefa(id))
            true
        }
    }
    private fun openDialog(){
        val options = resources.getStringArray(R.array.filter_options).asList()
        selector(getString(R.string.filter_title), options, { _, i->
            val visible = when(i){
                1-> Visibility.Ativas()
                2-> Visibility.Finalizadas()
                else-> Visibility.Todas()
            }
            store.dispatch(SetVisibility(visible))
        })
    }
    private val mapStateToProps = Function<ModeloTarefas, ModeloTarefas>{
        val keep: (Tarefas) -> Boolean = when(it.visibility){

            is Visibility.Todas -> {_ -> true}
            is Visibility.Ativas -> {t: Tarefas -> !t.status}
            is Visibility.Finalizadas -> {t: Tarefas -> t.status}
        }

        return@Function it.copy(tarefas = it.tarefas.filter { keep(it) })
    }
}
