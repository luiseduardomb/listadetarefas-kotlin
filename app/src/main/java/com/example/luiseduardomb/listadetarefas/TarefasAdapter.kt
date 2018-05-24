package com.example.luiseduardomb.listadetarefas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.luiseduardomb.listadetarefas.model.Tarefas
import kotlinx.android.synthetic.main.check_tarefa.view.*

class TarefasAdapter (context: Context, val tarefas: List<Tarefas>):
        ArrayAdapter<Tarefas>(context, 0, tarefas){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View{
        val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.check_tarefa, parent, false)

        view.textView.text = tarefas[position].text
        view.checkBox.isChecked = tarefas[position].status

        return view
    }

    override fun getItemId(position: Int): Long = tarefas[position].id
}