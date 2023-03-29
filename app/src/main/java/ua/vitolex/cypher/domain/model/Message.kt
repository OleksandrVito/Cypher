package ua.vitolex.cypher.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
data class Message(
    val encryptMessage: String,
    val decryptMessage: String?,
    val dateUpdate: String = getDateCreated(),
    @PrimaryKey val id: Int? = null,
)

fun getDateCreated(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"))
}

fun Message.getDay(): String {
    if (this.dateUpdate.isEmpty()) return ""
    val formatter = java.time.format.DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")
    return java.time.LocalDateTime.parse(this.dateUpdate, formatter).toLocalDate()
        .format(java.time.format.DateTimeFormatter.ofPattern("yyy-MM-dd"))
}
