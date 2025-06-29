package com.github.gabrieltizziani.gerenciador_tarefas

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.gabrieltizziani.gerenciador_tarefas.data.AppDatabase
import com.github.gabrieltizziani.gerenciador_tarefas.data.Tarefa
import com.google.android.material.textfield.TextInputEditText

class CadastroActivity : AppCompatActivity() {

    private lateinit var editTitulo: TextInputEditText
    private lateinit var editDescricao: TextInputEditText
    private lateinit var btnSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        editTitulo = findViewById(R.id.editTitulo)
        editDescricao = findViewById(R.id.editDescricao)
        btnSalvar = findViewById(R.id.btnSalvar)

        btnSalvar.setOnClickListener {
            salvarTarefa()
        }
    }

    private fun salvarTarefa() {
        val titulo = editTitulo.text.toString().trim()
        val descricao = editDescricao.text.toString().trim()

        if (titulo.isEmpty()) {
            editTitulo.error = "Título obrigatório"
            editTitulo.requestFocus()
            return
        }

        val novaTarefa = Tarefa(titulo = titulo, descricao = descricao)
        val db = AppDatabase.getDatabase(this)
        db.tarefaDao().inserir(novaTarefa)

        esconderTeclado()
        Toast.makeText(this, "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun esconderTeclado() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}
