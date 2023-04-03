package ua.vitolex.cypher.presentation.screens

import android.app.Application
import android.util.Base64
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ua.vitolex.cypher.domain.model.Message
import ua.vitolex.to_dolist.data.MessageRepository
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appContext: Application,
    private val repository: MessageRepository,
) : ViewModel() {
    val messages: Flow<List<Message>> = repository.getMessages()
    fun createMessage(encryptMessage: String, decryptMessage: String, image: String? = null) {
        val message = Message(encryptMessage = encryptMessage, decryptMessage = decryptMessage)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMessage(message)
        }
    }

    fun deleteMessage(message: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMessage(message)
        }
    }
//    private val _alias = mutableStateOf("secret")
//    val alias: State<String> = _alias

//    private val _inputValue = mutableStateOf("")
//    val inputValue: State<String> = _inputValue
//    fun enteredText (text:String) = run { _inputValue.value = text }

//    private val _outputValue = mutableStateOf("")
//    val outputValue: State<String> = _outputValue
//    fun getOutputValue (outputValue:String) = run { _outputValue.value = outputValue }

//    private val _alias = mutableStateOf("")
//    val alias: State<String> = _alias
//    fun enteredKey (key:String) = run { _alias.value = key }

//    private val _saveCheck = mutableStateOf(true)
//    val saveCheck: State<Boolean> = _saveCheck

    private var secretKey: SecretKeySpec? = null
    private lateinit var key: ByteArray

    // set Key
    fun setKey(myKey: String) {
        var sha: MessageDigest? = null
        try {
            key = myKey.toByteArray(charset("UTF-8"))
            sha = MessageDigest.getInstance("SHA-1")
            key = sha.digest(key)
            key = Arrays.copyOf(key, 16)
            secretKey = SecretKeySpec(key, "AES")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    // method to encrypt the secret text using key
    fun encrypt(strToEncrypt: String, secret: String): String? {
        try {
            setKey(secret)
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            return Base64.encodeToString(
                cipher.doFinal
                    (strToEncrypt.toByteArray(charset("UTF-8"))), Base64.DEFAULT
            )
        } catch (e: Exception) {

            println("Error while encrypting: $e")
        }
        return null
    }

    // method to encrypt the secret text using key
    fun decrypt(strToDecrypt: String?, secret: String): String? {
        try {
            setKey(secret)
            val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            return String(
                cipher.doFinal(
                    Base64.decode(strToDecrypt, Base64.DEFAULT)
                )
            )
        } catch (e: Exception) {
            println("Error while decrypting: $e")
        }
        return null
    }

    fun encryptMessage(messageToEncrypt: String, secretKey: String): String {
        val encryptedString = encrypt(messageToEncrypt, secretKey)
        return "$encryptedString"
    }

    fun decryptMessage(messageToDecrypt: String, secretKey: String): String {
        val decryptedString = decrypt(messageToDecrypt, secretKey)
        return "$decryptedString"
    }

}