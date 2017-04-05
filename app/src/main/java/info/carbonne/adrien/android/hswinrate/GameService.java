package info.carbonne.adrien.android.hswinrate;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class GameService extends Service {
    public GameService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Data data = Data.read(this);
            Deck deck = data.Decks.get(data.CurrentDeck);
            showOrRefreshForegroundNotification(deck);
        } else if (intent.getAction().equals(Constants.ACTION.DONOTHING_ACTION)) {
            // Do nothing
        } else if (intent.getAction().equals(Constants.ACTION.WON_ACTION)) {
            Data data = Data.read(this);
            Deck deck = data.Decks.get(data.CurrentDeck);
            deck.Won++;
            data.write(this);
            showOrRefreshForegroundNotification(deck);
        } else if (intent.getAction().equals(Constants.ACTION.LOST_ACTION)) {
            Data data = Data.read(this);
            Deck deck = data.Decks.get(data.CurrentDeck);
            deck.Lost++;
            data.write(this);
            showOrRefreshForegroundNotification(deck);
        } else if (intent.getAction().equals(Constants.ACTION.EXIT_ACTION)) {
            stopForeground(true);
            stopSelf();
        } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
            stopForeground(true);
            stopSelf();
        }

        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    private void showOrRefreshForegroundNotification(Deck deck) {
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

        //Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);

        Double winrate = deck.getWinrate();
        String winratetext = "No stats yet";
        if (winrate != null) {
            winratetext = Double.toString(winrate) + "% (" + deck.Won + "-" + deck.Lost + ")";
        }
        String title = getText(R.string.notification_title) + ": " + deck.Name + " " + winratetext;

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setTicker(title)
                .setContentText(getText(R.string.notification_message))
                .setSmallIcon(R.drawable.notification_icon)
                //.setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_MAX)
                .addAction(R.drawable.notification_won, "Won", wonPendingIntent)
                .addAction(R.drawable.notification_lost, "Lost", lostPendingIntent)
                .addAction(R.drawable.notification_exit, "Exit", exitPendingIntent).build();
        startForeground(Constants.NOTIFICATION_ID.ONGOING_NOTIFICATION_ID, notification);
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
