package com.schopenhauer.nous.data.local.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.schopenhauer.nous.data.local.DATABASE_NAME
import com.schopenhauer.nous.data.local.dao.TaskDao
import com.schopenhauer.nous.data.local.model.JournalEntity
import com.schopenhauer.nous.data.local.model.TaskEntity

@Database(entities = [TaskEntity::class, JournalEntity::class], version = 1, exportSchema = false)
abstract class NousLocalDataSource : RoomDatabase() {
	abstract fun taskDao(): TaskDao

	companion object {
		fun buildDatabase(context: Context): NousLocalDataSource {
			return Room.databaseBuilder(context, NousLocalDataSource::class.java, DATABASE_NAME)
				.build()
		}
	}
}