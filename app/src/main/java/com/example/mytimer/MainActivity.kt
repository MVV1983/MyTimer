package com.example.mytimer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "Test notif"
    private val notificationId = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeHour = findViewById<TextView>(R.id.hourView)
        val timeMinute = findViewById<TextView>(R.id.minuteView)
        val timeSecond = findViewById<TextView>(R.id.secondView)
        val timerAlarmMinute = findViewById<EditText>(R.id.editTimerMinute)
        val timerAlarmHour = findViewById<EditText>(R.id.editTimerHour)


        object : Thread() {
            override fun run() {
                val res = Calendar.getInstance()

                val hour = res.get(Calendar.HOUR_OF_DAY)
                val minute = res.get(Calendar.MINUTE)
                val second = res.get(Calendar.SECOND)

                Handler(Looper.getMainLooper()).post {
                    timeHour.text = "$hour:"
                    timeMinute.text = "$minute:"
                    timeSecond.text = "$second"
                }
                Handler(Looper.getMainLooper()).postDelayed(this, 1000)
                checkTimerAlarm(timerAlarmHour, timerAlarmMinute)
            }
        }.start()

        createNotificationChannel()

    }

    fun checkTimerAlarm(timerAlarmHour: EditText, timerAlarmMinute: EditText) {


        val hourNow = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
        val minNow = Calendar.getInstance().get(Calendar.MINUTE).toString()

        val checkH = timerAlarmHour.text.toString()
        val checkM = timerAlarmMinute.text.toString()

        if (hourNow == checkH && minNow == checkM) {

            Toast.makeText(
                this, "Din-dong :  Wake up ",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Title notification"
            val descriptionText = "Notif dicsript"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel =
                NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotif() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle("Уведомление будильника")
            .setContentText("Wake up")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }

    }

    override fun onStop() {
        super.onStop()
        sendNotif()

    }

}