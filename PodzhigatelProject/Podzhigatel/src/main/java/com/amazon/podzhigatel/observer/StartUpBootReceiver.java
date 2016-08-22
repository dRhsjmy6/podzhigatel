package com.amazon.podzhigatel.observer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.amazon.podzhigatel.tools.AppStarter;
import com.amazon.podzhigatel.tools.SettingsProvider;

/**
 * Receiver for Boot-Complete Broadcast
 */
public class StartUpBootReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            Log.d(StartUpBootReceiver.class.getName(), "Received BOOT_COMPLETED intent.");

            // Get settings provider
            SettingsProvider settingsProvider = SettingsProvider.getInstance(context);

            // Check if background observer is active
            if(settingsProvider.getBackgroundObserverEnabled())
            {
                // Start foreground service
                Intent startIntent = new Intent(context, ForeGroundService.class);
                startIntent.setAction(ForeGroundService.FOREGROUNDSERVICE_START);
                context.startService(startIntent);

                // Start startup-activity
                String startPackage = settingsProvider.getStartupPackage();
                Log.d(StartUpBootReceiver.class.getName(), "Startup start package is: " + startPackage);
                if(startPackage != null && !startPackage.equals(""))
                {
                    AppStarter.startAppByPackageName(context, startPackage, true, true, true);
                }
            }
        }
    }
}
