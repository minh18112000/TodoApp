package com.example.todoapp

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.database.TodoDatabase
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.viewmodel.TodoViewModel
import com.example.todoapp.viewmodel.TodoViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use view binding in activities
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val todoRepository = TodoRepository(TodoDatabase(this))
        val todoViewModelFactory = TodoViewModelFactory(application, todoRepository)

        todoViewModel = ViewModelProvider(
            this,
            todoViewModelFactory
        ).get(TodoViewModel::class.java)

    }

    fun sendNotification(contentTitle: String, contentText: String) {
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val notification = NotificationCompat.Builder(this, "CHANNEL_1")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setLargeIcon(bitmap)
            .build()

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(getNotificationId(), notification)
    }

    private fun getNotificationId(): Int {
        val date = Date().time
        return date.toInt()
    }

//    fun sendNotification(time: Long) {
//        val intent = Intent(this, ReminderBroadcast::class.java)
//
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
//
//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
//        alarmManager.setRepeating(
//            AlarmManager.RTC,
//            time,
//            AlarmManager.INTERVAL_DAY,
//            pendingIntent
//        )
//    }

//    fun createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = getString(R.string.channel_name)
//            val descriptionText = getString(R.string.channel_description)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }

}