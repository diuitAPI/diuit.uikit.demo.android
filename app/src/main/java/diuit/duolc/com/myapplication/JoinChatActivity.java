package diuit.duolc.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duolc.DiuitChat;
import com.duolc.DiuitMessagingAPI;
import com.duolc.DiuitMessagingAPICallback;

import org.json.JSONObject;

/**
 * Created by duolC on 5/23/16.
 */
public class JoinChatActivity extends Activity {

    private EditText inputChatIdEditText;
    private Button joinBtn;

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
                setJoinEvent();
            }

            @Override
            public void onFailure(int code, JSONObject result) {
                Utils.showErrorToast(JoinChatActivity.this, code, result);
            }
        });
    }

    @Override
    protected void onDestroy() {
        DiuitMessagingAPI.disConnect(); /* Before destroying this activity, discconect the session */
        super.onDestroy();
    }

    private void initializeView() {
        this.setContentView(R.layout.activity_join);
        this.inputChatIdEditText = (EditText) this.findViewById(R.id.inputChatEdit);
        this.joinBtn = (Button) this.findViewById(R.id.joinBtn);
    }

    private void setJoinEvent() {
        this.joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int chatId = Integer.parseInt( inputChatIdEditText.getText().toString() );
                DiuitMessagingAPI.joinChat(chatId, new DiuitMessagingAPICallback<DiuitChat>() {
                    @Override
                    public void onSuccess(DiuitChat result) {
                        JoinChatActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(JoinChatActivity.this, "Join! chatId : " + chatId, Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, JSONObject result) {
                        Utils.showErrorToast(JoinChatActivity.this, code, result);
                    }
                });
            }
        });
    }
}
