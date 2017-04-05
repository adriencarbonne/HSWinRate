package info.carbonne.adrien.android.hswinrate;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class GameService extends Service {
    public GameService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Intent notificationIntent = new Intent(this, GameService.class);
            notificationIntent.setAction(Constants.ACTION.DONOTHING_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            Intent wonIntent = new Intent(this, GameService.class);
            wonIntent.setAction(Constants.ACTION.WON_ACTION);
            PendingIntent wonPendingIntent = PendingIntent.getService(this, 0, wonIntent, 0);

            Intent lostIntent = new Intent(this, GameService.class);
            lostIntent.setAction(Constants.ACTION.LOST_ACTION);
            PendingIntent lostPendingIntent = PendingIntent.getService(this, 0, lostIntent, 0);

            Intent exitIntent = new Intent(this, GameService.class);
            exitIntent.setAction(Constants.ACTION.EXIT_ACTION);
            PendingIntent exitPendingIntent = PendingIntent.getService(this, 0, exitIntent, 0);

            //Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.truiton_short);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle(getText(R.string.notification_title))
                    .setContentText(getText(R.string.notification_message))
                    .setSmallIcon(R.drawable.notification_icon)
                    .setTicker(getText(R.string.notification_title))
                    //.setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .addAction(R.drawable.notification_won, "Won", wonPendingIntent)
                    .addAction(R.drawable.notification_lost, "Lost", lostPendingIntent)
                    .addAction(R.drawable.notification_exit, "Exit", exitPendingIntent).build();
            startForeground(Constants.NOTIFICATION_ID.ONGOING_NOTIFICATION_ID, notification);

            Toast.makeText(this, "GameService starting", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.DONOTHING_ACTION)) {
            Toast.makeText(this, "Do nothing", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.WON_ACTION)) {
            Toast.makeText(this, "Clicked WON", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.LOST_ACTION)) {
            Toast.makeText(this, "Clicked LOST", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.EXIT_ACTION)) {
            Toast.makeText(this, "Clicked EXIT", Toast.LENGTH_SHORT).show();
            stopForeground(true);
            stopSelf();
        } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Toast.makeText(this, "Received Stop Foreground Intent", Toast.LENGTH_SHORT).show();
            stopForeground(true);
            stopSelf();
        }

        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null.
        return null;
    }

    @Override
    public void onCreate() {
        // Called by the system when the service is first created.
        super.onCreate();

//        Intent notificationIntent = new Intent(this, GameService.class);
//        PendingIntent defaultPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        PendingIntent winPendingIntent = PendingIntent.getService(this, 0, notificationIntent, 0);
//        Notification.Action actionWin = new Notification.Action.Builder(R.drawable.notification_icon, "Win", winPendingIntent).build();
//        Notification notification = new Notification.Builder(this)
//                .setContentTitle(getText(R.string.notification_title))
//                .setContentText(getText(R.string.notification_message))
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentIntent(defaultPendingIntent)
//                .addAction(actionWin)
//                .build();
//        startForeground(ONGOING_NOTIFICATION_ID, notification);
    }

    @Override
    public void onDestroy() {
        // Called by the system to notify a Service that it is no longer used and is being removed.
        super.onDestroy();
    }
}
