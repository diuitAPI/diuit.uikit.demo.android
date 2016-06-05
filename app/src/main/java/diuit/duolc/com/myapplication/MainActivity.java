package diuit.duolc.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by duolC on 5/23/16.
 */
public class MainActivity extends Activity {

    private Button goToChatsListActivityBtn;
    private Button goToJoinChatActivityBtn;
    private Button goToRegisterPushServiceActivityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeView();
        this.setGoingToChatsListActivity();
        this.setGoingToJoinChatActivity();
        this.setGoingToRegisterPushServiceActivity();
    }

    private void initializeView() {
        this.setContentView(R.layout.activity_main);
        this.goToChatsListActivityBtn = (Button) this.findViewById(R.id.goToChatsListActivity);
        this.goToJoinChatActivityBtn = (Button) this.findViewById(R.id.goToJoinChatActivity);
        this.goToRegisterPushServiceActivityBtn = (Button) this.findViewById(R.id.goToRegisterPushServiceActivityBtn);
    }

    private void setGoingToChatsListActivity() {
        this.goToChatsListActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ChatsListActivity.class));
            }
        });
    }

    private void setGoingToJoinChatActivity() {
        this.goToJoinChatActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, JoinChatActivity.class));
            }
        });
    }

    private void setGoingToRegisterPushServiceActivity() {
        this.goToRegisterPushServiceActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, RegisteringPushServiceActivity.class));
            }
        });
    }
}
