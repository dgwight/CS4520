package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

/**
 * Created by DylanWight on 4/17/16.
 */

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

//http://stackoverflow.com/questions/4459058/alarm-manager-example
public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        sendMessage(context);
    }

    public void setAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Intent intent = new Intent(context, Alarm.class);

        PendingIntent alarm1 = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(alarm1);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5000, AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarm1);

        /*
        PendingIntent alarm1 = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(alarm1);
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarm1);

        PendingIntent alarm2 = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(alarm2);
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarm2);

        PendingIntent alarm3 = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(alarm3);
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarm3);
        */
    }

    private void sendMessage(Context context) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_stat_cloud)
                        .setContentTitle("Food Grouper")
        .setContentText("Time to log your meal");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(
                context, AddFoodActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }
}