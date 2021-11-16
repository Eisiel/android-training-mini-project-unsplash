package com.example.miniproject.service

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
 class AESEncrypts @Inject constructor(){
    var myInitializationVector: ByteArray? = null
    var mySecretKey: SecretKey? = null
    @Provides
    @Singleton
    fun encryptData(strToEncrypt: String): ByteArray {


        val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(256)

        val key = keygen.generateKey()
        mySecretKey = key

        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val cipherText = cipher.doFinal(plainText)
        myInitializationVector = cipher.iv

        return cipherText
    }

}