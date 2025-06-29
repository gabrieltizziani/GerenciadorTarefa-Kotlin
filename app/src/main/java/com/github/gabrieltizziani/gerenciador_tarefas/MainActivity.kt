package com.github.gabrieltizziani.gerenciador_tarefas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.gabrieltizziani.gerenciador_tarefas.data.AppDatabase
import com.github.gabrieltizziani.gerenciador_tarefas.data.Tarefa
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerTarefas: RecyclerView
    private lateinit var adapter: TarefaAdapter
    private lateinit var spinnerFiltro: Spinner
    private lateinit var textResumo: TextView

    private val dao by lazy { AppDatabase.getDatabase(this).tarefaDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerFiltro = findViewById(R.id.spinnerFiltro)
        textResumo = findViewById(R.id.textResumo)
        recyclerTarefas = findViewById(R.id.recyclerTarefas)

        adapter = TarefaAdapter(this, emptyList())
        recyclerTarefas.layoutManager = LinearLayoutManager(this)
        recyclerTarefas.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fabAdicionar)
        fab.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        spinnerFiltro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                carregarTarefas()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onResume() {
        super.onResume()
        carregarTarefas()
    }

    private fun carregarTarefas() {
        val filtroSelecionado = spinnerFiltro.selectedItemPosition
        val tarefas: List<Tarefa> = when (filtroSelecionado) {
            1 -> dao.buscarPorStatus(true)  // Concluídas
            2 -> dao.buscarPorStatus(false) // Pendentes
            else -> dao.buscarTodos()       // Todas
        }

        adapter.atualizarLista(tarefas)

        // Atualiza resumo
        val total = dao.buscarTodos().size
        val concluidas = dao.buscarPorStatus(true).size
        val pendentes = dao.buscarPorStatus(false).size
        textResumo.text = "Total: $total | Concluídas: $concluidas | Pendentes: $pendentes"
    }
}
