package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.hotspot2.pps.Credential;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.utilitymodel.ChatUser;
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

    public ChatUserAdapter (Context context, Activity activity, List<ChatUser> uList){
        this.context = context;
        this.activity = activity;
        this.uList = uList;
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

        ChatUser c = getItem(pos);
        TextView lbl = (TextView) v.findViewById(R.id.textView);
        lbl.setText(c.getNickname());
        TextView statusOnline = (TextView) v.findViewById(R.id.statusOnline);
        statusOnline.setText(c.isOnline() ? "Online": "Offline");
        ImageView srcOnline = (ImageView) v.findViewById(R.id.onlineIndicator);
        srcOnline.setImageDrawable(c.isOnline() ? ContextCompat.getDrawable(context, R.drawable.ic_online): ContextCompat.getDrawable(context, R.drawable.ic_offline));
        ViewFaceUtility.applyFont(statusOnline,activity,"fonts/Dosis-Light.otf");
        ViewFaceUtility.applyFont(lbl,activity,"fonts/Dosis-Bold.otf");
        return v;
    }


}