package com.github.gabrieltizziani.gerenciador_tarefas.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarefas")
data class Tarefa(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var  titulo: String,
    var  descricao: String,
    var  concluida: Boolean = false
)
