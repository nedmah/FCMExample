package com.example.testtaskfcm.data.remote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.testtaskfcm.R
import com.example.testtaskfcm.di.ServiceEntryPoint
import com.example.testtaskfcm.domain.repository.TokenRepository
import com.example.testtaskfcm.presentation.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject
import kotlin.random.Random

class FCMService : FirebaseMessagingService() {

    @Inject
    lateinit var repository: TokenRepository

    override fun onCreate() {
        super.onCreate()
        val entryPoint = EntryPointAccessors.fromApplication(applicationContext, ServiceEntryPoint::class.java)
        repository = entryPoint.getTokenRepository()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        repository.saveToken(token)
    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let { sendNotification(it) }
    }

    override fun handleIntent(intent: Intent?) {
        super.handleIntent(intent)
    }


    private fun sendNotification(message: RemoteMessage.Notification) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, FLAG_IMMUTABLE
        )

        val channelId = this.getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "channelName", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        manager.notify(Random.nextInt(), notificationBuilder.build())
    }
}
