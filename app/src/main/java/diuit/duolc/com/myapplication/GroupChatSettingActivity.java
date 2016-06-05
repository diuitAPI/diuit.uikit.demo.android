package diuit.duolc.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.duolc.DiuitChat;
import com.duolc.DiuitMessagingAPI;
import com.duolc.DiuitMessagingAPICallback;
import com.duolc.DiuitUser;
import com.duolc.diuitapi.messageui.setting.DiuitGroupChatSettingView;
import com.duolc.diuitapi.messageui.user.DiuitAddMemberView;
import com.duolc.diuitapi.messageui.user.DiuitMemberView;

import org.json.JSONObject;

/**
 * Created by doulc on 5/20/16.
 */
public class GroupChatSettingActivity extends Activity
{

    private DiuitGroupChatSettingView settingView;
    private DiuitChat currentChat;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.initializeView();
        this.currentChat = Utils.selectedChat;

        /**
         * Step1. Bind DiuitChat with DiuitGroupChatSettingView
         **/
        this.settingView.bindChat(this.currentChat).load();

        /**
         *  Step2. Implement Leaving groups
         **/
        this.setLeaveEvent();

        /**
         * Step3. Implement Kick User
         **/
        this.setMemberKickEvent();

        /**
         * Step4. Implement Add User Into this chat
         **/
        this.setAddMemberEvent();

    }

    private void setLeaveEvent() {
        this.settingView.getLeaveBtn().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentChat.leaveChat(new DiuitMessagingAPICallback<DiuitChat>()
                {
                    @Override
                    public void onSuccess(DiuitChat result) {
                        /**
                         * if success, go back to {@link ChatsListActivity}
                         **/
                        GroupChatSettingActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                leaveChat();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, JSONObject result) {
                        Utils.showErrorToast(GroupChatSettingActivity.this, code, result);
                    }
                });
            }
        });
    }

    private void setMemberKickEvent() {
        LinearLayout memberLayout = this.settingView.getMemberLinearLayout();
        final int count = memberLayout.getChildCount();
        for(int i = 0 ; i < count ; i++ ) {
            final View view =  memberLayout.getChildAt(i);
            if( view instanceof DiuitMemberView) {
                ((DiuitMemberView)view).getActionIcon().setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        final DiuitUser user = ((DiuitMemberView)view).getDiuitUser();

                        if( user.getSerial().equals(DiuitMessagingAPI.getCurrentUser().getSerial()) ) { /*kick self*/
                            GroupChatSettingActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GroupChatSettingActivity.this, "Kick yourself", Toast.LENGTH_SHORT).show();
                                    leaveChat();
                                }
                            });
                        } else { /*kick others*/
                            currentChat.kick(user.getSerial(), new DiuitMessagingAPICallback<DiuitChat>() { /*kick user*/
                                @Override
                                public void onSuccess(DiuitChat result) {
                                    GroupChatSettingActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GroupChatSettingActivity.this, "Kick " + user.getSerial(), Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(int code, JSONObject result) {
                                    Utils.showErrorToast(GroupChatSettingActivity.this, code, result);
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    private void setAddMemberEvent() {
        LinearLayout memberLayout = this.settingView.getMemberLinearLayout();
        DiuitAddMemberView memberView = (DiuitAddMemberView) memberLayout.getChildAt(0);
        memberView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 5/23/16  
            }
        });
    }

    private void leaveChat() {
        Intent resultIntent = new Intent(GroupChatSettingActivity.this, ChatsListActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        resultIntent.setAction(Utils.ACTION_LEAVING_CHAT);
        resultIntent.putExtra("chatId", currentChat.getId());
        startActivity(resultIntent);
        finish();
    }

    private void initializeView() {
        this.setContentView(R.layout.activity_group_chat_setting);
        this.settingView = (DiuitGroupChatSettingView) this.findViewById(R.id.diuitSettingView);
    }
}
