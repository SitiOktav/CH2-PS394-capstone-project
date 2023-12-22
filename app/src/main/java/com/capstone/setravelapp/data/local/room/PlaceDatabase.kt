package com.capstone.setravelapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.setravelapp.data.remote.response.PlaceResponseItem

@Database(entities = [PlaceResponseItem::class], version = 1, exportSchema = false)
abstract class PlaceDatabase : RoomDatabase() {


    companion object {
        @Volatile
        private var instance: PlaceDatabase? = null

        fun getInstance(context: Context): PlaceDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    PlaceDatabase::class.java, "setravelDatabase.db"
                ).build()
            }
    }
}
