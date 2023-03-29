package ua.vitolex.cypher.presentation.screens

import android.app.Application
import android.util.Base64
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : ViewModel() {

    private val _alias = mutableStateOf("secret")
    val alias: State<String> = _alias


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
            return Base64.encodeToString(cipher.doFinal
                (strToEncrypt.toByteArray(charset("UTF-8"))), Base64.DEFAULT)
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
            return String(cipher.doFinal(Base64.
            decode(strToDecrypt, Base64.DEFAULT)))
        } catch (e: Exception) {
            println("Error while decrypting: $e")
        }
        return null
    }


    fun main(args: Array<String>) {
        // key
        val secretKey = "secretkey"
        // secret text
        val originalString = "knowledgefactory.net"
        // Encryption
        val encryptedString = encrypt(originalString, secretKey)
        // Decryption
        val decryptedString = decrypt(encryptedString, secretKey)
        // Printing originalString,encryptedString,decryptedString
        println("Original String:$originalString")
        println("Encrypted value:$encryptedString")
        println("Decrypted value:$decryptedString")
    }

    fun encryptMessage(messageToEncrypt:String,secretKey:String ):String {
        val encryptedString = encrypt(messageToEncrypt, secretKey)
        return "$encryptedString"
    }
    fun decryptMessage(messageToDecrypt: String, secretKey:String):String {
        val decryptedString = decrypt(messageToDecrypt, secretKey)
        return "$decryptedString"
    }

}