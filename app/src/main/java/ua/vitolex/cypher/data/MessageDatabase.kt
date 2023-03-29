package ua.vitolex.to_dolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.vitolex.cypher.domain.model.Message

@Database(
    entities = [Message::class],
    version = 1
)
abstract class MessageDatabase : RoomDatabase() {
    abstract val dao: MessageDao
}