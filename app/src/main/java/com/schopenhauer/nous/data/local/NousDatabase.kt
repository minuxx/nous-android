package com.schopenhauer.nous.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.schopenhauer.nous.data.local.dao.JournalDao
import com.schopenhauer.nous.data.local.dao.TaskDao
import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.data.local.model.TaskEntity

@Database(entities = [TaskEntity::class, JournalEntity::class], version = 1, exportSchema = false)
abstract class NousDatabase : RoomDatabase() {
	abstract fun taskDao(): TaskDao
	abstract fun journalDao(): JournalDao

	companion object {
		fun buildDatabase(context: Context): NousDatabase {
			return Room.databaseBuilder(context, NousDatabase::class.java, DATABASE_NAME)
				.build()
		}
	}
}