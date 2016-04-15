package co.edu.udea.compumovil.lab3gr9.pomodoro.l3g9_pomodoro;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private TextView timestampText;
    private Intent intent;
    private ServicePomodoro mServicePomodoro;
    private boolean mServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timestampText = (TextView) findViewById(R.id.textView_timestamp);
        timestampText.setText(00 + ":" + 00 + ":" + 00 + ":" + 000);

        intent = new Intent(MainActivity.this,
                ServicePomodoro.class);
        Log.d(TAG,"Creo intent de pomodoro");
    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnPrintTimestamp:
                Log.d(TAG, "PrintTimestamp button");
                //startService(intent);
                if (mServiceBound)
                    timestampText.setText(mServicePomodoro.getTimestamp());

                break;

            case R.id.btnBoundService:
                Log.d(TAG, "BoundService button");
                startService(intent);
                Intent frank = new Intent(this,PomodoroService.class);
                startService(frank);
                bindService(intent, mServiceConnection, this.BIND_AUTO_CREATE);
                break;

            case R.id.btnUnboundService:
                Log.d(TAG, "UnboundService button");
                if (mServiceBound) {
                    unbindService(mServiceConnection);
                    mServiceBound = false;
                }
                stopService(intent);
                break;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServicePomodoro.MyBinder myBinder = (ServicePomodoro.MyBinder) service;
            Log.d(TAG,"Conectando el servicio");
            mServicePomodoro = myBinder.getService();
            mServiceBound = true;
        }
    };

}
