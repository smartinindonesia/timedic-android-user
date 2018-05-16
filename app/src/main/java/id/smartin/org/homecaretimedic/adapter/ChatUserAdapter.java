package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.hotspot2.pps.Credential;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatMessage;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatUser;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatUserStats;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

/**
 * Created by Hafid on 09/05/2018.
 */

public class ChatUserAdapter extends BaseAdapter {

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */

    private List<ChatUser> uList;
    private Context context;
    private Activity activity;
    private FirebaseUser firebaseUser;

    public ChatUserAdapter(Context context, Activity activity, List<ChatUser> uList) {
        this.context = context;
        this.activity = activity;
        this.uList = uList;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public int getCount() {
        return uList.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public ChatUser getItem(int arg0) {
        return uList.get(arg0);
    }

    public ChatUser getChatUser(int arg0){
        ChatUser a = new ChatUser();
        //a.setOnline(uList.get(arg0).isOnline());
        //a.setRoom(uList.get(arg0).getRoom());
        a.setUserType(uList.get(arg0).getUserType());
        a.setNickname(uList.get(arg0).getNickname());
        a.setId(uList.get(arg0).getId());
        //a.setEmail(uList.get(arg0).getEmail());
        //a.setConnectedWithId(uList.get(arg0).getConnectedWithId());
        //a.setProfileUrl(uList.get(arg0).getProfileUrl());
        //a.setUsername(uList.get(arg0).getUsername());
        return a;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int pos, View v, ViewGroup arg2) {
        if (v == null)
            v = LayoutInflater.from(arg2.getContext()).inflate(R.layout.item_admin_chat, null);

        final ChatUserStats c = (ChatUserStats) getItem(pos);
        TextView lbl = (TextView) v.findViewById(R.id.textView);
        lbl.setText(c.getNickname());
        String adminUuid, patientUuid, referenceChild;
        if (c.getUserType().equals(Constants.CHAT_ROLE_ADMIN)) {
            adminUuid = c.getId();
            patientUuid = firebaseUser.getUid();
        } else {
            adminUuid = firebaseUser.getUid();
            patientUuid = c.getId();
        }
        referenceChild = adminUuid + "-" + patientUuid;
        //setListener(pos, referenceChild);
        TextView messageCounter = (TextView) v.findViewById(R.id.counterMessages);
        messageCounter.setText(c.getNewMessagesCount().toString());
        if (c.getNewMessagesCount() > 0) {
            messageCounter.setVisibility(View.VISIBLE);
        } else {
            messageCounter.setVisibility(View.GONE);
        }
        TextView statusOnline = (TextView) v.findViewById(R.id.statusOnline);
        statusOnline.setText(c.isOnline() ? "Online" : "Offline");
        ImageView srcOnline = (ImageView) v.findViewById(R.id.onlineIndicator);
        srcOnline.setImageDrawable(c.isOnline() ? ContextCompat.getDrawable(context, R.drawable.ic_online) : ContextCompat.getDrawable(context, R.drawable.ic_offline));
        ViewFaceUtility.applyFont(statusOnline, activity, "fonts/Dosis-Light.otf");
        ViewFaceUtility.applyFont(messageCounter, activity, "fonts/Dosis-Light.otf");
        ViewFaceUtility.applyFont(lbl, activity, "fonts/Dosis-Bold.otf");
        return v;
    }

    private void updatePosition() {
        Collections.sort(uList, new Comparator<ChatUser>() {
            @Override
            public int compare(ChatUser o1, ChatUser o2) {
                return ((ChatUserStats) o2).getNewMessagesCount().compareTo(((ChatUserStats) o1).getNewMessagesCount());
            }
        });
        notifyDataSetChanged();
    }

    private void destroyListener(final ChatUserStats c) {
        c.fdb.removeEventListener(c.eventListener);
    }

    public void setListener(int pos, String referenceChild) {
        final ChatUserStats c = (ChatUserStats) getItem(pos);
        c.eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                c.setNewMessagesCount(0);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        ChatMessage conversation = ds.getValue(ChatMessage.class);
                        if (conversation.getReceiver().contentEquals(firebaseUser.getUid()) && conversation.getStatus() != ChatMessage.STATUS_READ_BY_USER) {
                            c.setNewMessagesCount(c.getNewMessagesCount() + 1);
                        }
                    }
                    updatePosition();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        c.fdb = FirebaseDatabase.getInstance()
                .getReference(Constants.CHAT_SINGLE_REFERENCE)
                .child(referenceChild);
        c.fdb.limitToLast(20).addValueEventListener(c.eventListener);
    }

    public void destroyAllListener() {
        for (int i = 0; i < uList.size(); i++) {
            destroyListener((ChatUserStats) uList.get(i));
        }
    }

}