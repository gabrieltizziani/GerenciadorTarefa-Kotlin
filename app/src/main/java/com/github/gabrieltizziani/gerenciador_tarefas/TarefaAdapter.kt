package com.github.gabrieltizziani.gerenciador_tarefas

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.gabrieltizziani.gerenciador_tarefas.data.Tarefa

class TarefaAdapter(
    private val context: Context,
    private var tarefas: List<Tarefa>
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    inner class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.itemTitulo)
        val descricao: TextView = itemView.findViewById(R.id.itemDescricao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = tarefas[position]
        holder.titulo.text = tarefa.titulo
        holder.descricao.text = tarefa.descricao

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetalhesActivity::class.java)
            intent.putExtra("idTarefa", tarefa.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = tarefas.size

    fun atualizarLista(novasTarefas: List<Tarefa>) {
        this.tarefas = novasTarefas
        notifyDataSetChanged()
    }
}
