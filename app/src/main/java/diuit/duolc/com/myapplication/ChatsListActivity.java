package diuit.duolc.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.duolc.DiuitChat;
import com.duolc.DiuitMessagingAPI;
import com.duolc.DiuitMessagingAPICallback;
import com.duolc.diuitapi.messageui.adapter.DiuitChatsRecyclerViewAdapter;
import com.duolc.diuitapi.messageui.page.DiuitChatsRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ChatsListActivity extends Activity
{
    private ArrayList<DiuitChat> chats;
    private DiuitChatsRecyclerView chatsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.initializeView();

        /**
         * Step1. Login with authToken
         **/
        DiuitMessagingAPI.loginWithAuthToken( Utils.DEVICE_TOKEN , new DiuitMessagingAPICallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {

                /**
                 * Step2. List all chats
                 **/
                DiuitMessagingAPI.listChats(new DiuitMessagingAPICallback<ArrayList<DiuitChat>>() {
                    @Override
                    public void onSuccess(final ArrayList<DiuitChat> chats) {
                        /**
                         * Step3. Bind chatsRecyclerView with all chats in main thread
                         **/
                        ChatsListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ChatsListActivity.this.chats = chats;
                                chatsRecyclerView.bindChats(ChatsListActivity.this.chats, new DiuitChatsRecyclerViewAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(DiuitChat diuitChat) {
                                        Utils.selectedChat = diuitChat;
                                        ChatsListActivity.this.startActivity(new Intent(ChatsListActivity.this, MessagesListActivity.class));
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, JSONObject result) {
                        Utils.showErrorToast(ChatsListActivity.this, code, result);
                    }
                });
            }

            @Override
            public void onFailure(int code, JSONObject result) {
                Utils.showErrorToast(ChatsListActivity.this, code, result);
            }
        });
    }

    @Override
    protected void onDestroy() {
        /**
         * Step4. Before destroying this activity, discconect the session
         **/
        DiuitMessagingAPI.disConnect();
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getAction().equals(Utils.ACTION_LEAVING_CHAT) ) { /**{@link GroupChatSettingActivity}**/
            int chatId = intent.getIntExtra("chatId", -1);
            if(chatId > 0) {
                Iterator<DiuitChat> chatIterator = ChatsListActivity.this.chats.iterator();
                while(chatIterator.hasNext()) {
                    if(chatIterator.next().getId() == chatId) {
                        chatIterator.remove();
                        chatsRecyclerView.getChatsAdapter().notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }

    private void initializeView() {
        this.setContentView(R.layout.activity_chats_list);
        this.chatsRecyclerView = (DiuitChatsRecyclerView) this.findViewById(R.id.diuitChatsView);
    }
}
