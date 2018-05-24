package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.MessageListAdapter;
import id.smartin.org.homecaretimedic.adapter.MessageListAdapterFst;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ChatID;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ChatUserFst;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ThreadIDProperty;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class ListOfChatActivity extends AppCompatActivity {
    public static final String TAG = "[ListOfChatActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.reyclerview_message_list)
    RecyclerView mMessageRecycler;
    @BindView(R.id.edittext_chatbox)
    EditText edittext_chatbox;
    @BindView(R.id.button_chatbox_send)
    Button btnSend;

    private MessageListAdapterFst mMessageAdapter;
    private List<ChatID> chatMessageList = new ArrayList<ChatID>() {
        @Override
        public int indexOf(Object o) {

            if (o instanceof ChatID) {
                for (int i = 0; i < this.size(); i++) {
                    ChatID c = (ChatID) o;
                    Log.i(TAG, "this one is called or not" + this.get(i).getChatTimeStamp() + " " + c.getChatTimeStamp());
                    if (c.getChatTimeStamp().equals(this.get(i).getChatTimeStamp())) {
                        return i;
                    }
                }
            }
            return -1;
        }
    };

    public static ChatUserFst buddy, appuser;
    private Date lastMsgDate;
    private String chatNode;
    private ValueEventListener detectNewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_admin);
        ButterKnife.bind(this);
        createTitleBar();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        buddy = (ChatUserFst) getIntent().getSerializableExtra("selected_admin");
        appuser = (ChatUserFst) getIntent().getSerializableExtra("selected_app_user");

        if (buddy.getUserType().equals(Constants.CHAT_ROLE_ADMIN)) {
            chatNode = buddy.getId() + "-" + user.getUid();
        } else {
            chatNode = user.getUid() + "-" + buddy.getId();
        }

        mMessageAdapter = new MessageListAdapterFst(this, this, chatMessageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMessageRecycler.setLayoutManager(linearLayoutManager);

        mMessageRecycler.setAdapter(mMessageAdapter);

        setFonts();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage(){
        if (edittext_chatbox.length() == 0)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edittext_chatbox.getWindowToken(), 0);

        String s = edittext_chatbox.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final ChatID conversation = new ChatID();
            conversation.setChatMessage(edittext_chatbox.getText().toString());
            conversation.setChatTimeStamp(new Date().getTime());
            conversation.setSender(user.getUid());
            conversation.setStatus(ChatID.STATUS_SENT);
            insertMessage(conversation, chatNode);
        }
        mMessageAdapter.notifyDataSetChanged();
        edittext_chatbox.setText(null);
    }

    private void checkIfThreadNodeMetaExist(final String node){

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_MESSAGE_THREAD_META).orderByChild("threadId").equalTo(node);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    createThreadNodeMeta(node);
                } else {
                    updateStatusMeta(node, true);
                    loadMessages(node);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createThreadNodeMeta(String node){
        ThreadIDProperty threadIDProperty = new ThreadIDProperty();
        if (buddy.getUserType().equals(Constants.CHAT_ROLE_ADMIN)) {
            threadIDProperty.setAdminChat(buddy);
            threadIDProperty.setCreatedAt(new Date().getTime());
            appuser.setOnline(true);
            threadIDProperty.setAppUser(appuser);
            threadIDProperty.setCreatedByUserId(appuser.getId());
            threadIDProperty.setThreadId(node);
            threadIDProperty.setThreadType("private");
        } else {
            threadIDProperty.setAdminChat(appuser);
            threadIDProperty.setCreatedAt(new Date().getTime());
            appuser.setOnline(true);
            threadIDProperty.setAppUser(buddy);
            threadIDProperty.setCreatedByUserId(buddy.getId());
            threadIDProperty.setThreadId(node);
            threadIDProperty.setThreadType("private");
        }
        FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_MESSAGE_THREAD_META).child(node)
                .setValue(threadIDProperty).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                }
            }
        });
    }

    private void updateStatusMeta(String node, Boolean online){
        if (Constants.CHAT_ROLE_ME.equals(Constants.CHAT_ROLE_USER)) {
            FirebaseDatabase.getInstance()
                    .getReference(Constants.CHAT_NODE_MESSAGE_THREAD_META)
                    .child(node)
                    .child("appUser")
                    .child("online")
                    .setValue(online)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    }
                }
            });
        } else {
            FirebaseDatabase.getInstance()
                    .getReference(Constants.CHAT_NODE_MESSAGE_THREAD_META)
                    .child(node)
                    .child("adminChat")
                    .child("online")
                    .setValue(online)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    }
                }
            });
        }
    }

    private void loadMessages(final String node){
        chatMessageList.clear();
        mMessageAdapter.notifyDataSetChanged();
        detectNewMessage =
                FirebaseDatabase.getInstance()
                        .getReference(Constants.CHAT_NODE_MESSAGE_THREAD)
                        .child(node)
                        .limitToLast(20)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    chatMessageList.clear();
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        ChatID conversation = ds.getValue(ChatID.class);
                                        Log.i(TAG, "ADA DATA MASUK");
                                        if (conversation.getSender().contentEquals(user.getUid()) || conversation.getSender().contentEquals(buddy.getId())) {
                                            chatMessageList.add(conversation);
                                            Date dateConv = new Date(conversation.getChatTimeStamp());
                                            if (lastMsgDate == null || lastMsgDate.before(dateConv))
                                                lastMsgDate = dateConv;
                                            mMessageAdapter.notifyDataSetChanged();
                                            if (conversation.getSender().equals(buddy.getId())) {
                                                updateMessageStatus(node, ds.getKey());
                                            }
                                        }
                                    }
                                    mMessageRecycler.scrollToPosition(chatMessageList.size() - 1);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
    }

    private void updateMessageStatus(String node, String key) {
        FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_MESSAGE_THREAD)
                .child(node)
                .child(key)
                .child("status")
                .setValue(ChatID.STATUS_READ_BY_USER)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            //error
                            Log.i(TAG, "ERROR ");
                            //Toast.makeText(SignUpActivity.this, "Error " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i(TAG, "SIGNING OK");
                        }
                    }
                });
    }

    private void insertMessage(final ChatID conversation, final String node) {
        chatMessageList.add(conversation);
        mMessageAdapter.notifyDataSetChanged();

        final String key = FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_MESSAGE_THREAD).child(node)
                .push().getKey();

        FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_MESSAGE_THREAD).child(node).child(key)
                .setValue(conversation)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        final int isExist = chatMessageList.indexOf(conversation);
                        Log.i(TAG, "INDEX SENT MESSAGE " + isExist);
                        if (task.isSuccessful()) {
                            if (isExist != -1)
                                chatMessageList.get(isExist).setStatus(ChatID.STATUS_RECEIVED_BY_USER);
                        } else {
                            if (isExist != -1)
                                chatMessageList.get(isExist).setStatus(ChatID.STATUS_FAILED);
                        }
                        if (isExist != -1)
                            FirebaseDatabase
                                    .getInstance()
                                    .getReference(Constants.CHAT_NODE_MESSAGE_THREAD).child(node)
                                    .child(key).setValue(chatMessageList.get(isExist))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            mMessageAdapter.notifyDataSetChanged();
                                        }
                                    });
                    }
                });
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this, "fonts/BalooBhaina-Regular.ttf", R.color.theme_black);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }

    private void pushActiveThread(ChatUserFst user, String node){

    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(edittext_chatbox);
        arrayList.add(btnSend);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }

}
