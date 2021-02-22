package com.example.alarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class RingtonePlayingService extends Service {
    static Ringtone r;
    Vibrator vibrator;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //activating alarm sound
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(getBaseContext(), notification);

        //playing sound alarm
        r.play();

        //vibration
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(300000); // 5 minutes

        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy()
    {
        vibrator.cancel();
        r.stop();
    }
}