package com.sandoval.besttodoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sandoval.besttodoapp.data.models.ToDoData
import com.sandoval.besttodoapp.utils.databaseName

//For best practices all DBs in Android, should be declared as Abstract classes.
//The DB class has to extends from RoomDataBase to access the advantages of less code with
//the help of the annotations. The DB will implement the interface of the DAO. As this DB Class
//is abstract, means that the DAO access has to be done with an abstract function.
@Database(entities = [ToDoData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            //If our instance of DB already exists then we will return the instance

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            //If not, we want to have only 1 instance our DB. So we make the instance in the
            //synchronized block. I prefer to use synchronize just to avoid the creation of multiple
            //instances of the DB with multiple threads.

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    databaseName
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}