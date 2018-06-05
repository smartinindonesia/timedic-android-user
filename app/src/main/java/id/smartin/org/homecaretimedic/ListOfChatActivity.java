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
import android.util.EventLog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.MessageListAdapter;
import id.smartin.org.homecaretimedic.adapter.MessageListAdapterFst;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ChatID;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ChatUserFst;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ChatUserRef;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ConnectedWith;
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

    private Query queryNodeMeta;
    private ValueEventListener nodeMetaListener;

    private DatabaseReference detectNewMessages;
    private ValueEventListener detectNewMessageListener;

    private DatabaseReference metaDatabase;
    private ValueEventListener metaListener;

    private boolean recentlyOpen = false;

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
    private ThreadIDProperty threadIDProperty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_admin);
        ButterKnife.bind(this);
        createTitleBar();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (Constants.CHAT_ROLE_ME.equals(Constants.CHAT_ROLE_USER)) {
            buddy = (ChatUserFst) getIntent().getSerializableExtra("selected_admin");
            appuser = (ChatUserFst) getIntent().getSerializableExtra("selected_app_user");
        } else {
            appuser = (ChatUserFst) getIntent().getSerializableExtra("selected_admin");
            buddy = (ChatUserFst) getIntent().getSerializableExtra("selected_app_user");
        }

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
        checkIfThreadNodeMetaExist(chatNode);
        listenToMeta(chatNode);
    }

    @Override
    protected void onResume() {
        recentlyOpen = true;
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateStatusMeta(chatNode, false);
        if (nodeMetaListener != null) {
            queryNodeMeta.removeEventListener(nodeMetaListener);
        }
        if (detectNewMessageListener != null) {
            detectNewMessages.removeEventListener(detectNewMessageListener);
        }
        if (metaListener != null) {
            metaDatabase.removeEventListener(metaListener);
        }
    }

    private void sendMessage() {
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

    private void checkIfThreadNodeMetaExist(final String node) {
        queryNodeMeta = FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_MESSAGE_THREAD_META).orderByChild("threadId").equalTo(node);
        nodeMetaListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    createThreadNodeMeta(node);
                } else {
                    updateStatusMeta(node, true);
                    loadMessages(node);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        queryNodeMeta.addValueEventListener(nodeMetaListener);
    }

    private void createThreadNodeMeta(String node) {
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
                .setValue(threadIDProperty)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            pushActiveThread(appuser, chatNode);
                            pushActiveThread(buddy, chatNode);
                        }
                    }
                });
    }

    private void updateStatusMeta(String node, Boolean online) {
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
                                Log.i(TAG, "updated status user");
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
                                Log.i(TAG, "updated status admin");
                            }
                        }
                    });
        }
    }

    private void loadMessages(final String node) {
        chatMessageList.clear();
        mMessageAdapter.notifyDataSetChanged();
        detectNewMessageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatMessageList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ChatID conversation = ds.getValue(ChatID.class);
                    Log.i(TAG, "ADA DATA MASUK");
                    if (conversation.getSender().equals(appuser.getId()) || conversation.getSender().equals(buddy.getId())) {
                        chatMessageList.add(conversation);
                        Date dateConv = new Date(conversation.getChatTimeStamp());
                        if (lastMsgDate == null || lastMsgDate.before(dateConv))
                            lastMsgDate = dateConv;
                        mMessageAdapter.notifyDataSetChanged();
                        if (conversation.getSender().equals(buddy.getId())) {
                            updateMessageStatus(node, ds.getKey());
                        }
                    }
                    mMessageRecycler.scrollToPosition(chatMessageList.size() - 1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        detectNewMessages = FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_MESSAGE_THREAD)
                .child(node);
        detectNewMessages.limitToLast(Constants.CHAT_LIMIT_NUMBER)
                .addValueEventListener(detectNewMessageListener);
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
                                            updateMeta(node, chatMessageList.get(isExist));
                                        }
                                    });
                    }
                });

    }

    private void updateMeta(String node, ChatID lastMessages) {
        if (threadIDProperty != null) {
            if (threadIDProperty.getAppUser().getOnline() == null)
                threadIDProperty.getAppUser().setOnline(false);
            if (threadIDProperty.getUnseenMessages() == null)
                threadIDProperty.setUnseenMessages(0);
            if (threadIDProperty.getAdminChat().getOnline() == null)
                threadIDProperty.getAdminChat().setOnline(false);
            updateSenderAndReceiverUid(node, appuser.getId(), buddy.getId());
            updateLastMessages(node, lastMessages);
            if (Constants.CHAT_ROLE_ME.equals(Constants.CHAT_ROLE_USER)) {
                if (threadIDProperty.getAdminChat().getOnline()) {
                    updateUnseendMessages(node, 0);
                } else {
                    updateUnseendMessages(node, threadIDProperty.getUnseenMessages() + 1);
                }
            }
            if (Constants.CHAT_ROLE_ME.equals(Constants.CHAT_ROLE_ADMIN)) {
                if (threadIDProperty.getAppUser().getOnline()) {
                    updateUnseendMessages(node, 0);
                } else {
                    updateUnseendMessages(node, threadIDProperty.getUnseenMessages() + 1);
                }
            }
        }
    }

    private void updateLastMessages(String node, ChatID messages) {
        DatabaseReference metaDatabaseUpdate = FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_MESSAGE_THREAD_META)
                .child(node)
                .child("lastChatId");
        metaDatabaseUpdate.setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "unseenMessagesChanged");
                }
            }
        });
    }

    private void updateSenderAndReceiverUid(String node, String senderUid, String receiverUid) {
        DatabaseReference mainReference = FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_MESSAGE_THREAD_META)
                .child(node);
        DatabaseReference senderRef = mainReference.child("senderUid");
        DatabaseReference receiverRef = mainReference.child("receiverUid");
        senderRef.setValue(senderUid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "senderUidChanged");
                }
            }
        });
        receiverRef.setValue(receiverUid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "receiverUidChanged");
                }
            }
        });
    }

    private void updateUnseendMessages(String node, Integer numOfUnseen) {
        DatabaseReference metaDatabaseUpdate = FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_MESSAGE_THREAD_META)
                .child(node)
                .child("unseenMessages");
        metaDatabaseUpdate.setValue(numOfUnseen).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "unseenMessagesChanged");
                }
            }
        });
    }

    private void listenToMeta(final String node) {
        metaDatabase = FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_MESSAGE_THREAD_META)
                .child(node);
        metaListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    threadIDProperty = dataSnapshot.getValue(ThreadIDProperty.class);
                    if (recentlyOpen) {
                        if (threadIDProperty.getSenderUid() != null) {
                            if (threadIDProperty.getSenderUid().equals(buddy.getId())) {
                                updateUnseendMessages(node, 0);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        metaDatabase.addValueEventListener(metaListener);
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

    private void pushActiveThread(final ChatUserFst udata, final String node) {
        final String key = FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_USER_REF).child(udata.getId()).child("connectedWithChat")
                .push().getKey();
        ConnectedWith conn = new ConnectedWith();
        conn.setKey(key);
        conn.setThreadId(node);
        FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_NODE_USER_REF).child(udata.getId()).child("connectedWithChat").child(key)
                .setValue(conn).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    pushActiveThread(udata, node);
                } else {
                    Log.i(TAG, "successfull node insertion");
                }
            }
        });
    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(edittext_chatbox);
        arrayList.add(btnSend);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }

}
