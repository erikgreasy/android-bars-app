package com.example.zadanie.helpers

import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class PasswordHelper {
    companion object {
        private val salt =  "tajny_salt".toByteArray()

        fun hash(password: String): ByteArray {
            val spec = PBEKeySpec(password.toCharArray(), salt, 1000, 256)
            try {
                val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                return skf.generateSecret(spec).encoded
            } catch (e: NoSuchAlgorithmException) {
                throw AssertionError("Hashing password failed. Error: " + e.message, e)
            } catch (e: InvalidKeySpecException) {
                throw AssertionError("Hashing password failed: Error: " + e.message, e)
            } finally {
                spec.clearPassword()
            }
        }
    }
}