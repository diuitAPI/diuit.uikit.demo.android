package diuit.duolc.com.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.duolc.DiuitChat;
import com.duolc.DiuitMessagingAPI;
import com.duolc.DiuitMessagingAPICallback;
import com.duolc.DiuitUser;
import com.duolc.diuitapi.messageui.setting.DiuitParticipantSettingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by doulc on 5/20/16.
 */
public class DirectChatSettingActivity extends Activity {

    private DiuitParticipantSettingView settingView;
    private DiuitChat currentChat;
    private DiuitUser diuitUser;
    private Switch blockSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeView();
        this.currentChat = Utils.selectedChat;

        /**
         * Step1. initialize DiuitUser
         **/
        this.initializeDiuitUser();

        /**
         * Step2. Bind DiuitUser with DiuitParticipantSettingView
         **/
        this.settingView.bindUser(this.diuitUser).load();

        /**
         * Step3. Check Block Status
         **/
        boolean isBlockedByMe = DiuitMessagingAPI.getCurrentUser().getBlockList().contains(this.diuitUser.getSerial());
        this.blockSwitch.setChecked(isBlockedByMe);


        /**
         * Step4. Implement Block event
         **/
        blockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(Build.VERSION.SDK_INT >= 16) {
                    int thumbColor = isChecked ? R.color.diu_block_switch_check_thumb : R.color.diu_block_switch_uncheck_thumb;
                    blockSwitch.getThumbDrawable().setColorFilter(ContextCompat.getColor(DirectChatSettingActivity.this, thumbColor), PorterDuff.Mode.MULTIPLY);
                    int trackColor = isChecked ? R.color.diu_block_switch_check_track: R.color.diu_block_switch_uncheck_track;
                    blockSwitch.getTrackDrawable().setColorFilter( ContextCompat.getColor(DirectChatSettingActivity.this, trackColor), PorterDuff.Mode.MULTIPLY);
                }

                ArrayList<String> blockList = DiuitMessagingAPI.getCurrentUser().getBlockList();
                if(isChecked) {
                    if( !blockList.contains(diuitUser.getSerial()) )
                        block();
                } else {
                    if( blockList.contains(diuitUser.getSerial()) )
                        unblock();
                }
            }
        });
    }

    private void initializeView() {
        this.setContentView(R.layout.activity_direct_chat_setting);
        this.settingView = (DiuitParticipantSettingView) this.findViewById(R.id.diuitSettingView);
        this.blockSwitch = this.settingView.getBlockSwitch();
    }

    private void initializeDiuitUser() {
        try
        {
            JSONObject userObj = new JSONObject();
            userObj.put("serial", currentChat.getMemberSerials().get(0) );
            JSONObject metaObj = new JSONObject();
            metaObj.put("name", currentChat.getMemberSerials().get(0) );
            userObj.put("meta", metaObj);
            this.diuitUser = new DiuitUser(userObj);
        } catch (JSONException e) { e.printStackTrace();}
    }

    private void block() {
        final ProgressDialog progress = ProgressDialog.show(this, "Blocking", "", true, false);
        DiuitMessagingAPI.block(diuitUser.getSerial(), new DiuitMessagingAPICallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                DirectChatSettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        DiuitMessagingAPI.getCurrentUser().getBlockList().add(diuitUser.getSerial());
                    }
                });
            }

            @Override
            public void onFailure(final int code, final JSONObject result) {
                DirectChatSettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showErrorToast(DirectChatSettingActivity.this, code, result);
                        progress.dismiss();
                    }
                });
            }
        });
    }

    private void unblock() {
        final ProgressDialog progress = ProgressDialog.show(this, "Unblocking", "", true, false);
        DiuitMessagingAPI.unblock(diuitUser.getSerial(), new DiuitMessagingAPICallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                DirectChatSettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        DiuitMessagingAPI.getCurrentUser().getBlockList().remove(diuitUser.getSerial());
                    }
                });
            }

            @Override
            public void onFailure(final int code, final JSONObject result) {
                DirectChatSettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showErrorToast(DirectChatSettingActivity.this, code, result);
                        progress.dismiss();
                    }
                });
            }
        });

    }
}
