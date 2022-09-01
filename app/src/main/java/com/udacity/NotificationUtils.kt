package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

private val NOTIFICATION_ID=0
private val REQUEST_CODE=0
private val FLAGS=0


fun NotificationManager.sendNotification(
    messageBody: String,
    applicationContext: Context,
    fileName: String?,
    status: String
) {
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    contentIntent.putExtra(FILE_NAME,fileName)
    contentIntent.putExtra(STATUS,status)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
//    to add big style to notification
    val downloadImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.download
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(downloadImage)
        .bigLargeIcon(null)



// notification builder
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )
//      set   title ,text and icon to builder
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext
            .getString(R.string.notification_title))
        .setContentText(messageBody)
//      add style to builder
        .setStyle(bigPicStyle)
        .setLargeIcon(downloadImage)
//      set intent
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
//       Add Action when click at notification
        .addAction(
            R.drawable.download,
            applicationContext.getString(R.string.details),
            contentPendingIntent
        )


    //you need at this app one notification at a time so u must use the same notificationId
    notify(NOTIFICATION_ID, builder.build())
}

/**
 * Cancels all notifications.
 *
 */

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
