package ua.vitolex.to_dolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.vitolex.cypher.domain.model.Message

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("SELECT * FROM message WHERE id = :id")
    suspend fun getMessageById(id: Int): Message?

    @Query("SELECT * FROM message")
    fun getMessages(): Flow<List<Message>>

}