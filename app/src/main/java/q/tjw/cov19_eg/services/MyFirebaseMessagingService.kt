package q.tjw.cov19_eg.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import q.tjw.cov19_eg.R
import q.tjw.cov19_eg.map.di.app.CO19Application.Companion.context
import java.util.*


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val message = remoteMessage.notification!!.body
        val title = remoteMessage.notification!!.title

        showNotification(message, title)
    }

    private fun showNotification(message: String?, title: String?) {
        val remoteViews = RemoteViews(context!!.packageName, R.layout.custom_notification)

// Open Notification View Class on Notification Click
        // Open Notification View Class on Notification Click
        val intent = Intent()
        val pIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationManager =
            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context) // Set Icon
            .setSmallIcon(R.drawable.notification_icon_2) // Dismiss Notification
            .setAutoCancel(true) // Set PendingIntent into Notification
            .setContentIntent(pIntent) // Set RemoteViews into Notification
            .setContent(remoteViews)
            .setCustomBigContentView(remoteViews)
            .setSound(uri)
            .setVibrate(longArrayOf(500, 500, 500, 500, 500))
            .setLights(Color.RED, 3000, 3000)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "cov_19"
            val channel = NotificationChannel(
                channelId,
                context!!.resources.getString(R.string.channel_title),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }

        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.message, message)

        val notificationId: Int = Random().nextInt(100)

        val notification: Notification = builder.build()
        notificationManager.notify(notificationId, notification)
    }
}