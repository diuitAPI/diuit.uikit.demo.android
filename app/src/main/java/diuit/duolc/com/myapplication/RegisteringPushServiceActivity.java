package diuit.duolc.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.duolc.DiuitMessagingAPI;
import com.duolc.DiuitMessagingAPICallback;

import org.json.JSONObject;

/**
 * Created by duolC on 5/23/16.
 */
public class RegisteringPushServiceActivity extends Activity {

    private Button registerBtn;
    private TextView noteText;


    /**
     * 1. Remember register DiuitPushBroadcastService service in manifest, and you will receive notifications by default
     * <code>
     *     <service android:name="com.duolc.DiuitPushBroadcastService" android:exported="false" \>
     *          <intent-filter>
     *              <action android:name="com.google.android.c2dm.intent.RECEIVE" />
     *          </intent-filter>
     *      </service>
     * </code>
     *
     * 2. Calling enablePushNotification() to subscribe a chat room
     *
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeView();

        /**
         * Step1. Login with authToken
         **/
        DiuitMessagingAPI.loginWithAuthToken( Utils.DEVICE_TOKEN , new DiuitMessagingAPICallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                /**
                 * Step2. Register your GCM token
                 **/
                setRegisterButton();
            }

            @Override
            public void onFailure(int code, JSONObject result) {
                Utils.showErrorToast(RegisteringPushServiceActivity.this, code, result);
            }
        });
    }

    private void setRegisterButton() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                registerBtn.setAlpha(1.0f);
                registerBtn.setEnabled(true);
            }
        });
        this.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiuitMessagingAPI.registerPushNotificationService(RegisteringPushServiceActivity.this, Utils.GCM_PROJECT_ID);
                noteText.setText("Now go back to UI Kit to send a text and you will receive a notification  in a chat which you subscribe if setup GCM token successfully.");
            }
        });
    }

    @Override
    protected void onDestroy() {
        DiuitMessagingAPI.disConnect(); /* Before destroying this activity, discconect the session */
        super.onDestroy();
    }

    private void initializeView() {
        this.setContentView(R.layout.activity_registering_push_service);
        this.registerBtn = (Button) this.findViewById(R.id.registerBtn);
        this.noteText = (TextView) this.findViewById(R.id.noteText);
    }
}
