package diuit.duolc.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.duolc.DiuitChat;
import com.duolc.DiuitMessage;

import org.json.JSONObject;

/**
 * Created by duolC on 5/20/16.
 */
public class Utils {

    public static final String DEVICE_TOKEN = "PUT_YOUR_DEVICE_SESSION_TOKEN";
    public static final String ACTION_LEAVING_CHAT = "LEAVING_CHAT";
    public static final int FETCHING_MESSAGES_COUNT_FOR_EACH = 10;
    public static final String GCM_PROJECT_ID = "PUT_YOUR_GCM_PROJECT_ID";

    public static DiuitChat selectedChat;
    public static DiuitMessage selectedMessage;

    public static void showErrorToast(final Context ctx, final int code, final JSONObject result) {
        ((Activity)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                Toast.makeText( ctx, "Error code : " + code + "\n" + result.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
