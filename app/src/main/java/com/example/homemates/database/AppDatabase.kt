package com.example.homemates.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homemates.model.Imovel

// Aqui dizemos quais tabelas existem e a versão do banco
@Database(entities = [Imovel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imovelDao(): ImovelDao

    // O companion object garante que abriremos apenas UMA conexão com o banco
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "homemates_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}