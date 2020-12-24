package yj.p.challenge12;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import yj.p.challenge12.MainActivity;

public class MyService extends Service {

    public MyService(){
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent == null) {
            return Service.START_STICKY;
        } else {
            String data = intent.getStringExtra("data");
            Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|
                    Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent2.putExtra("data2", data);
            startActivity(intent2);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
