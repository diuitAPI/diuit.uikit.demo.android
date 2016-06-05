package diuit.duolc.com.myapplication;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.duolc.DiuitPushBroadcastService;

import org.json.JSONObject;

/**
 * Created by duolC on 3/17/16.
 */
public class MyDiuitPushBroadcastService extends DiuitPushBroadcastService
{
    @Override
    public void showNotification(String title, String message, JSONObject payload)
    {
        NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_diu_add_member)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify( 1002, mBuilder.build());
    }
}
