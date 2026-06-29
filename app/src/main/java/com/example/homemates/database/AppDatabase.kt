package com.example.homemates.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homemates.model.Imovel

// MUDANÇA AQUI: version = 2
@Database(entities = [Imovel::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imovelDao(): ImovelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "homemates_database"
                )
                    .fallbackToDestructiveMigration() // MUDANÇA AQUI: Evita que o app feche ao mudar as colunas
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}