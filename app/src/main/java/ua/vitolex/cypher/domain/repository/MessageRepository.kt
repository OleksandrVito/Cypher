package ua.vitolex.to_dolist.data

import kotlinx.coroutines.flow.Flow
import ua.vitolex.cypher.domain.model.Message

interface MessageRepository {
    suspend fun insertMessage(message: Message)

    suspend fun deleteMessage(message: Message)

    suspend fun getMessageById(id: Int): Message?

    fun getMessages(): Flow<List<Message>>
}