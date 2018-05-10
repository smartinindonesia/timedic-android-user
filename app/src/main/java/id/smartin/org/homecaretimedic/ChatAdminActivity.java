package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.MessageListAdapter;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatMessage;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatUser;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class ChatAdminActivity extends AppCompatActivity {
    public static final String TAG = "[ChatAdminActivity]";

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

    private MessageListAdapter mMessageAdapter;
    private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>() {
        @Override
        public int indexOf(Object o) {

            if (o instanceof ChatMessage) {
                for (int i = 0; i < this.size(); i++) {
                    ChatMessage c = (ChatMessage) o;
                    Log.i(TAG, "this one is called or not" + this.get(i).getMessageTime() + " " + c.getMessageTime());
                    if (c.getMessageTime().equals(this.get(i).getMessageTime())) {
                        return i;
                    }
                }
            }
            return -1;
        }
    };
    ;
    public static ChatUser buddy;
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

        buddy = (ChatUser) getIntent().getSerializableExtra("selected_admin");

        if (buddy.getUserType().equals("chat_admin")) {
            chatNode = buddy.getId() + "-" + user.getUid();
        } else {
            chatNode = user.getUid() + "-" + buddy.getId();
        }

        mMessageAdapter = new MessageListAdapter(this, this, chatMessageList);
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

    private void sendMessage() {
        if (edittext_chatbox.length() == 0)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edittext_chatbox.getWindowToken(), 0);

        String s = edittext_chatbox.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final ChatMessage conversation = new ChatMessage();
            conversation.setMessageText(edittext_chatbox.getText().toString());
            conversation.setMessageTime(new Date().getTime());
            conversation.setReceiver(buddy.getId());
            conversation.setSender(AdminListActivity.user.getId());
            conversation.setStatus(ChatMessage.STATUS_SENT);
            insertMessage(conversation, chatNode);
        }
        mMessageAdapter.notifyDataSetChanged();
        edittext_chatbox.setText(null);
    }

    /*
    private void checkIfNodeExist() {
        final ProgressDialog dia = ProgressDialog.show(this, null, "Loading");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_SINGLE_REFERENCE);
        DatabaseReference userNameRef = ref.child(chatNode);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dia.dismiss();
                if (!dataSnapshot.exists()) {
                    chatNode = chatNode_1;
                } else {
                    chatNode = chatNode_2;
                }
                loadConversationList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }
    */

    private void isExistOnBuddyList() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String me = user.getUid();
        FirebaseDatabase.getInstance().getReference().child("Users").child(buddy.getId()).child("connectedWithId").child(me)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            addMe(me);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void addMe(final String me) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        List<String> flist = buddy.getConnectedWithId();
        if (flist == null){
            flist = new ArrayList<>();
        }
        flist.add(me);
        FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .child(buddy.getId())
                .child("connectedWithId").setValue(flist)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "Sccs");
                        } else {
                            Log.i(TAG, "Failed");
                        }
                    }
                });
    }


    private void insertMessage(final ChatMessage conversation, final String node) {
        chatMessageList.add(conversation);
        mMessageAdapter.notifyDataSetChanged();

        final String key = FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_SINGLE_REFERENCE).child(node)
                .push().getKey();

        FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_SINGLE_REFERENCE).child(node).child(key)
                .setValue(conversation)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        final int isExist = chatMessageList.indexOf(conversation);
                        Log.i(TAG, "INDEX SENT MESSAGE " + isExist);
                        if (task.isSuccessful()) {
                            if (isExist != -1)
                                chatMessageList.get(isExist).setStatus(ChatMessage.STATUS_RECEIVED_BY_USER);
                        } else {
                            if (isExist != -1)
                                chatMessageList.get(isExist).setStatus(ChatMessage.STATUS_FAILED);
                        }
                        if (isExist != -1)
                            FirebaseDatabase
                                    .getInstance()
                                    .getReference(Constants.CHAT_SINGLE_REFERENCE).child(node)
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

    private void loadConversationList() {
        chatMessageList.clear();
        mMessageAdapter.notifyDataSetChanged();
        detectNewMessage =
                FirebaseDatabase.getInstance()
                        .getReference(Constants.CHAT_SINGLE_REFERENCE)
                        .child(chatNode)
                        .limitToLast(20)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    chatMessageList.clear();
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        ChatMessage conversation = ds.getValue(ChatMessage.class);
                                        Log.i(TAG, "ADA DATA MASUK");
                                        if (conversation.getReceiver().contentEquals(user.getUid()) || conversation.getSender().contentEquals(user.getUid())) {
                                            chatMessageList.add(conversation);
                                            Date dateConv = new Date(conversation.getMessageTime());
                                            if (lastMsgDate == null || lastMsgDate.before(dateConv))
                                                lastMsgDate = dateConv;
                                            mMessageAdapter.notifyDataSetChanged();
                                            if (conversation.getSender().equals(buddy.getId())) {
                                                updateMessageStatus(ds.getKey());
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

    private void updateMessageStatus(String key) {
        FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_SINGLE_REFERENCE)
                .child(chatNode)
                .child(key)
                .child("status")
                .setValue(ChatMessage.STATUS_READ_BY_USER)
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

    @Override
    protected void onResume() {
        super.onResume();
        //checkIfNodeExist();
        loadConversationList();
        isExistOnBuddyList();
    }

    @Override
    public void onBackPressed() {
        this.finish();
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
        FirebaseDatabase.getInstance().getReference(Constants.CHAT_SINGLE_REFERENCE).child(chatNode).removeEventListener(detectNewMessage);
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

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(edittext_chatbox);
        arrayList.add(btnSend);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }
}
