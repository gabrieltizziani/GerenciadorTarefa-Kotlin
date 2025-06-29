package com.github.gabrieltizziani.gerenciador_tarefas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.gabrieltizziani.gerenciador_tarefas.data.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerTarefas: RecyclerView
    private lateinit var adapter: TarefaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerTarefas = findViewById(R.id.recyclerTarefas)
        adapter = TarefaAdapter(this, emptyList())
        recyclerTarefas.layoutManager = LinearLayoutManager(this)
        recyclerTarefas.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fabAdicionar)
        fab.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.getDatabase(this)
        val tarefas = db.tarefaDao().buscarTodos()
        adapter.atualizarLista(tarefas)
    }
}
