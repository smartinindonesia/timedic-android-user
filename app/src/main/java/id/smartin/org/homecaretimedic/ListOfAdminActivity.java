package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.ChatUserAdapter;
import id.smartin.org.homecaretimedic.adapter.ChatUserRefAdapter;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ChatUserFst;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ChatUserRef;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ConnectedWith;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ThreadIDProperty;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatUser;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatUserStats;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class ListOfAdminActivity extends AppCompatActivity {
    public static final String TAG = "[ListOfAdminActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.listAdmin)
    ListView listAdmin;
    /**
     * The Chat list.
     */
    private ArrayList<ChatUserFst> uList = new ArrayList<ChatUserFst>();

    /**
     * The user.
     */
    public static ChatUserRef user;

    private ChatUserRefAdapter chatUserAdapter;
    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_admin);
        ButterKnife.bind(this);
        createTitleBar();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        chatUserAdapter = new ChatUserRefAdapter(getApplicationContext(), ListOfAdminActivity.this, uList);
        listAdmin.setAdapter(chatUserAdapter);
        listAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0,
                                    View arg1, int pos, long arg3) {
                Intent intent = new Intent(ListOfAdminActivity.this,
                        ListOfChatActivity.class);
                if (user != null) {
                    if (Constants.CHAT_ROLE_ME.equals(Constants.CHAT_ROLE_USER)) {
                        intent.putExtra("selected_admin", chatUserAdapter.getChatUser(pos));
                        intent.putExtra("selected_app_user", user.getParent());
                    } else {
                        intent.putExtra("selected_admin", user.getParent());
                        intent.putExtra("selected_app_user", chatUserAdapter.getChatUser(pos));
                    }
                    startActivity(intent);
                } else {
                    Snackbar.make(mainLayout, "Profil pengguna tidak ditemukan. Muat ulang halaman ini!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        setFonts();
    }

    private void updateUserStatus(boolean online) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database = FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_NODE_USER_REF);
        database.child(fuser.getUid()).child("online").setValue(online).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    private void updateUserRole(String usertype) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_NODE_USER_REF);
        database.child(fuser.getUid()).child("userType").setValue(usertype).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    //error
                    Log.i(TAG, "UserType not Changed ");
                    //Toast.makeText(SignUpActivity.this, "Error " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "UserType changed");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //chatUserAdapter.destroyAllListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isExistOnFirebaseDB();
    }

    private void loadUserList() {
        final ProgressDialog dia = ProgressDialog.show(this, "", "Loading");
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        if (Constants.CHAT_ROLE_ME.equals(Constants.CHAT_ROLE_USER)) {
            // Pull the users list once no sync required.
            database.child(Constants.CHAT_NODE_USER_REF)
                    .orderByChild("userType")
                    .equalTo(Constants.CHAT_ROLE_ADMIN)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            dia.dismiss();
                            long size = dataSnapshot.getChildrenCount();
                            if (size == 0) {
                                Toast.makeText(ListOfAdminActivity.this,
                                        "Admin tidak ditemukan",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                            uList.clear();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                ChatUserRef user = ds.getValue(ChatUserRef.class);
                                if (!user.getId().contentEquals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                    uList.add(user);
                            }
                            chatUserAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            dia.dismiss();
                        }
                    });
        } else {
            database.child(Constants.CHAT_NODE_USER_REF)
                    .child(fuser.getUid())
                    .child("connectedWithChat")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            dia.dismiss();
                            //long size = dataSnapshot.getChildrenCount();
                            if (!dataSnapshot.exists()) {
                                Snackbar.make(mainLayout,
                                        "User tidak ditemukan",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                uList.clear();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    ConnectedWith connectedThread = ds.getValue(ConnectedWith.class);
                                    getThreadForAdmin(connectedThread.getThreadId());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            dia.dismiss();
                        }
                    });

        }
    }

    private void getThreadForAdmin(String connedtedThread) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child(Constants.CHAT_NODE_MESSAGE_THREAD_META)
                .child(connedtedThread)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            ThreadIDProperty connectedThread = dataSnapshot.getValue(ThreadIDProperty.class);
                            int index = uList.indexOf(connectedThread.getAppUser());
                            if (index == -1) {
                                uList.add(connectedThread.getAppUser());
                            } else {
                                uList.set(index, connectedThread.getAppUser());
                            }
                            chatUserAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        updateUserStatus(false);
        super.onDestroy();
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this, "fonts/BalooBhaina-Regular.ttf", R.color.theme_black);
        ActionBar mActionbar = getSupportActionBar();
        String title;
        if (Constants.CHAT_ROLE_ME.equals(Constants.CHAT_ROLE_USER)) {
            title = "Daftar Admin";
        } else {
            title = "Daftar User";
        }
        mActionbar.setTitle(title);
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Regular.otf");
    }

    private void isExistOnFirebaseDB() {
        final ProgressDialog dia = ProgressDialog.show(this, "", "Load profil pengguna");
        DatabaseReference mUsersDBref = FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_NODE_USER_REF);
        DatabaseReference userNameRef = mUsersDBref.child(fuser.getUid());
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dia.dismiss();
                if (!dataSnapshot.exists()) {
                    Log.i(TAG, "Doesnt exist " + user.getNickname());
                } else {
                    user = dataSnapshot.getValue(ChatUserRef.class);
                    loadUserList();
                    updateUserStatus(true);
                    updateUserRole(Constants.CHAT_ROLE_ME);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dia.dismiss();
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }
}
