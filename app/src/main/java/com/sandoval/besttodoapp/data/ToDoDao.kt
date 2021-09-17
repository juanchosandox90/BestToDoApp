package com.sandoval.besttodoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sandoval.besttodoapp.data.models.ToDoData
import com.sandoval.besttodoapp.utils.databaseTable

//In this class we declare the queries that we will need for our app.
//For best practices, the Dao class should be declared as an interface
// The DAO allows to insert, read, update and delete. Using the annotations of room
// @Insert, @Read, @Update, @Delete. For custom queries just use @Query

@Dao
interface ToDoDao {

    //Query all data
    //We will query all the data but for this we will use a LiveData object to
    //control the states and the lifecycle of this data.
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    //Insert new data - suspend function to be used in a coroutine in the future
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

    //Update data from list fragment item.
    @Update
    suspend fun updateData(toDoData: ToDoData)

    //Delete single item inside the update fragment
    @Delete
    suspend fun deleteSingleItem(toDoData: ToDoData)

    //Delete all Data items from the list fragment
    @Query("DELETE FROM todo_table")
    suspend fun deleteAllItems()

    //Search Data item from the Database
    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
    fun searchToDoDatabase(searchQuery: String): LiveData<List<ToDoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<ToDoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'M%' THEN 1 WHEN priority LIKE 'H%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByMediumPriority(): LiveData<List<ToDoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<ToDoData>>

}