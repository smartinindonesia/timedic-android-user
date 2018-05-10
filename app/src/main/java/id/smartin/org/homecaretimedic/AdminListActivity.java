package id.smartin.org.homecaretimedic;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.adapter.ChatUserAdapter;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatMessage;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatUser;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class AdminListActivity extends AppCompatActivity {
    public static final String TAG = "[AdminListActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.listAdmin)
    ListView listAdmin;
    /**
     * The Chat list.
     */
    private ArrayList<ChatUser> uList;

    /**
     * The user.
     */
    public static ChatUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);
        ButterKnife.bind(this);
        createTitleBar();

        updateUserStatus(true);
        updateUserRole(Constants.CHAT_ROLE_ME);
        setFonts();
    }

    private void updateUserStatus(boolean online) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference().child("Users");
        database.child(user.getId()).child("online").setValue(online).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference().child("Users");
        database.child(user.getId()).child("userType").setValue(usertype).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    protected void onResume() {
        super.onResume();
        loadUserList();
        getUserChatProfile();
    }

    private void loadUserList() {
        final ProgressDialog dia = ProgressDialog.show(this, null, "Loading");
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        // Pull the users list once no sync required.
        database.child("Users")
                .orderByChild("userType")
                .equalTo(Constants.CHAT_ROLE_BUDDY)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dia.dismiss();
                        long size = dataSnapshot.getChildrenCount();
                        if (size == 0) {
                            Toast.makeText(AdminListActivity.this,
                                    "Admin tidak ditemukan",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        uList = new ArrayList<ChatUser>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            ChatUser user = ds.getValue(ChatUser.class);
                            if (!user.getId().contentEquals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                uList.add(user);
                        }
                        listAdmin.setAdapter(new ChatUserAdapter(getApplicationContext(), AdminListActivity.this, uList));
                        listAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0,
                                                    View arg1, int pos, long arg3) {
                                Intent intent = new Intent(AdminListActivity.this,
                                        ChatAdminActivity.class);
                                intent.putExtra("selected_admin", uList.get(pos));
                                startActivity(intent);
                            }
                        });
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
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }

    private void getUserChatProfile() {
        FirebaseUser userfb = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .equalTo(userfb.getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Log.i(TAG, "incoming snapshot data");
                        AdminListActivity.user = ds.getValue(ChatUser.class);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
}
