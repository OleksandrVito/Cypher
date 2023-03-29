package ua.vitolex.to_dolist.data

import kotlinx.coroutines.flow.Flow
import ua.vitolex.cypher.domain.model.Message

class MessageRepositoryImpl(
    private val dao:MessageDao
):MessageRepository {
    override suspend fun insertMessage(message: Message) {
      dao.insertMessage(message)
    }

    override suspend fun deleteMessage(message: Message) {
       dao.deleteMessage(message)
    }

    override suspend fun getMessageById(id: Int): Message? {
    return  dao.getMessageById(id)
    }

    override fun getMessages(): Flow<List<Message>> {
    return dao.getMessages()
    }

}