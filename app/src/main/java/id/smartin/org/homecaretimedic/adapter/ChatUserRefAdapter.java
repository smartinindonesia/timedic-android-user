package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
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
import java.util.List;

import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ChatUserFst;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ChatUserRef;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatMessage;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatUser;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatUserStats;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class ChatUserRefAdapter extends BaseAdapter {

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */

    private List<ChatUserFst> uList;
    private Context context;
    private Activity activity;
    private FirebaseUser firebaseUser;

    public ChatUserRefAdapter(Context context, Activity activity, List<ChatUserFst> uList) {
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
    public ChatUserFst getItem(int arg0) {
        return uList.get(arg0);
    }

    public ChatUserFst getChatUser(int arg0){
        ChatUserFst a = new ChatUserFst();
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

        final ChatUserFst c = (ChatUserFst) getItem(pos);
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
        TextView statusOnline = (TextView) v.findViewById(R.id.statusOnline);
        statusOnline.setText(c.getOnline() ? "Online" : "Offline");
        ImageView srcOnline = (ImageView) v.findViewById(R.id.onlineIndicator);
        srcOnline.setImageDrawable(c.getOnline() ? ContextCompat.getDrawable(context, R.drawable.ic_online) : ContextCompat.getDrawable(context, R.drawable.ic_offline));
        ViewFaceUtility.applyFont(statusOnline, activity, "fonts/Dosis-Light.otf");
        ViewFaceUtility.applyFont(messageCounter, activity, "fonts/Dosis-Light.otf");
        ViewFaceUtility.applyFont(lbl, activity, "fonts/Dosis-Bold.otf");
        return v;
    }

}