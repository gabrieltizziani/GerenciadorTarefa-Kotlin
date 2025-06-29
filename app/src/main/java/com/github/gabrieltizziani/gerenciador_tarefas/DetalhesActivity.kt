package com.github.gabrieltizziani.gerenciador_tarefas

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.gabrieltizziani.gerenciador_tarefas.data.AppDatabase
import com.github.gabrieltizziani.gerenciador_tarefas.data.Tarefa
import com.google.android.material.textfield.TextInputEditText

class DetalhesActivity : AppCompatActivity() {

    private lateinit var editTitulo: TextInputEditText
    private lateinit var editDescricao: TextInputEditText
    private lateinit var checkboxConcluida: CheckBox
    private lateinit var btnSalvar: Button
    private lateinit var btnExcluir: Button

    private var tarefa: Tarefa? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        // Bind dos elementos
        editTitulo = findViewById(R.id.editTitulo)
        editDescricao = findViewById(R.id.editDescricao)
        checkboxConcluida = findViewById(R.id.checkboxConcluida)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnExcluir = findViewById(R.id.btnExcluir)

        val idTarefa = intent.getLongExtra("idTarefa", -1L)
        if (idTarefa == -1L) {
            Toast.makeText(this, "Tarefa inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val dao = AppDatabase.getDatabase(this).tarefaDao()
        tarefa = dao.buscarPorId(idTarefa)

        if (tarefa == null) {
            Toast.makeText(this, "Tarefa não encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Preenche os campos com os dados
        editTitulo.setText(tarefa!!.titulo)
        editDescricao.setText(tarefa!!.descricao)
        checkboxConcluida.isChecked = tarefa!!.concluida

        // Botão Salvar
        btnSalvar.setOnClickListener {
            val novoTitulo = editTitulo.text.toString().trim()
            val novaDescricao = editDescricao.text.toString().trim()

            if (novoTitulo.isEmpty()) {
                editTitulo.error = "Título obrigatório"
                return@setOnClickListener
            }

            tarefa!!.titulo = novoTitulo
            tarefa!!.descricao = novaDescricao
            tarefa!!.concluida = checkboxConcluida.isChecked

            dao.atualizar(tarefa!!)
            Toast.makeText(this, "Tarefa atualizada com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Botão Excluir
        btnExcluir.setOnClickListener {
            dao.deletar(tarefa!!)
            Toast.makeText(this, "Tarefa excluída com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}
