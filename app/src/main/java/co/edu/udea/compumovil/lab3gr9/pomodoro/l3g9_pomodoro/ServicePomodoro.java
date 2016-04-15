package co.edu.udea.compumovil.lab3gr9.pomodoro.l3g9_pomodoro;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

/**
 * Created by julian.vasquezg on 15/04/16.
 */
public class ServicePomodoro extends Service {
    private static String LOG_TAG = "PomodoroService";
    private IBinder mBinder = new MyBinder();
    private Chronometer mChronometer;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOG_TAG, "in onCreate");

    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind");

        mChronometer = new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
        return mBinder;
    }
}
public class MyBinder extends Binder {
    ServicePomodoro getService() {
        return ServicePomodoro.this;
    }
}
