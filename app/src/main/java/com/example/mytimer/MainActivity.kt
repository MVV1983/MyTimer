package com.example.mytimer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
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
            ).show();


        }
    }
}