package com.example.messagesender


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.messagesender.R
import java.util.Base64

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val secretMessageInput = findViewById<EditText>(R.id.secretMessageInput)
        val sendButton = findViewById<Button>(R.id.sendButton)

        sendButton.setOnClickListener {
            val message = secretMessageInput.text.toString()
            val encryptedMessage = encryptMessage(message)

            val intent = Intent(this, DisplayMessageActivity::class.java)
            intent.putExtra("ENCRYPTED_MESSAGE", encryptedMessage)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encryptMessage(message: String): String {
        return Base64.getEncoder().encodeToString(message.toByteArray())
    }
}
