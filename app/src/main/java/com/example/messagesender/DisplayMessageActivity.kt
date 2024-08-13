package com.example.messagesender

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.messagesender.R
import java.util.Base64

class DisplayMessageActivity : AppCompatActivity() {

    companion object {
        private const val CHANNEL_ID = "secret_message_channel"
    }

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        val goBackButton: Button = findViewById(R.id.goBackButton)
        val encryptedMessage = intent.getStringExtra("ENCRYPTED_MESSAGE")
        val decryptedMessage = decryptMessage(encryptedMessage ?: "")

        val messageTextView = findViewById<TextView>(R.id.messageTextView)
        messageTextView.text = decryptedMessage

        createNotificationChannel()
        showNotification(decryptedMessage)

        goBackButton.setOnClickListener{
            val intent  = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun decryptMessage(encryptedMessage: String): String {
        return String(Base64.getDecoder().decode(encryptedMessage))
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Secret Message Channel"
            val descriptionText = "Channel for secret message notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(message: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Decrypted Message")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(1000, 1000))
            .setLights(0xFFFF0000.toInt(), 3000, 3000)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}
