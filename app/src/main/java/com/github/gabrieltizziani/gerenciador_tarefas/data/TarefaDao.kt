package com.github.gabrieltizziani.gerenciador_tarefas.data


import androidx.room.*

@Dao
interface TarefaDao {
    @Query("SELECT * FROM tarefas")
    fun listarTodas(): List<Tarefa>

    @Insert
    fun inserir(tarefa: Tarefa)

    @Update
    fun atualizar(tarefa: Tarefa)

    @Delete
    fun deletar(tarefa: Tarefa)

    @Query("SELECT * FROM tarefas WHERE id = :id")
    fun buscarPorId(id: Long): Tarefa?

    @Query("SELECT * FROM tarefas ORDER BY id DESC")
    fun buscarTodos(): List<Tarefa>

}
