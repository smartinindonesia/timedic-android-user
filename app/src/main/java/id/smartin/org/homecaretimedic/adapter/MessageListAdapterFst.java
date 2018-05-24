package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.AdminListActivity;
import id.smartin.org.homecaretimedic.ChatAdminActivity;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.chatcompmodel.ChatID;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

public class MessageListAdapterFst extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = ChatID.MESSAGE_SENT;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = ChatID.MESSAGE_RECEIVED;

    private Context mContext;
    private Activity activity;
    private List<ChatID> mMessageList;
    private FirebaseAuth mAuth;

    public MessageListAdapterFst(Context context, Activity activity, List<ChatID> messageList) {
        this.mContext = context;
        this.activity = activity;
        this.mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        ChatID message = mMessageList.get(position);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (message.getSender().equals(user.getUid())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_message_out, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_message_in, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatID message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_message_body)
        TextView messageText;
        @BindView(R.id.text_message_time)
        TextView timeText;
        @BindView(R.id.thickMark)
        ImageView thickMark;

        SentMessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ViewFaceUtility.applyFont(messageText, activity, "fonts/Dosis-Medium.otf");
            ViewFaceUtility.applyFont(timeText, activity, "fonts/Dosis-Medium.otf");
        }

        void bind(ChatID message) {
            messageText.setText(message.getChatMessage());
            timeText.setText(ConverterUtility.getTimeOnly(message.getChatTimeStamp()));

            int defaultIcon = R.drawable.ic_single_thick;
            if (message.getStatus() == ChatID.STATUS_SENT){
                defaultIcon = R.drawable.ic_single_thick;
            } else if (message.getStatus() == ChatID.STATUS_RECEIVED_BY_USER){
                defaultIcon = R.drawable.ic_double_thick;
            } else if (message.getStatus() == ChatID.STATUS_READ_BY_USER) {
                defaultIcon = R.drawable.ic_single_thick_read;
            } else if (message.getStatus() == ChatID.STATUS_FAILED) {
                defaultIcon = R.drawable.ic_failure;
            }
            thickMark.setImageDrawable(ContextCompat.getDrawable(mContext, defaultIcon));
        }
    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_message_time)
        TextView timeText;
        @BindView(R.id.text_message_name)
        TextView nameText;
        @BindView(R.id.text_message_body)
        TextView messageText;
        @BindView(R.id.image_message_profile)
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ViewFaceUtility.applyFont(messageText, activity, "fonts/Dosis-Medium.otf");
            ViewFaceUtility.applyFont(timeText, activity, "fonts/Dosis-Medium.otf");
            ViewFaceUtility.applyFont(nameText, activity, "fonts/Dosis-Medium.otf");
        }

        void bind(ChatID message) {
            messageText.setText(message.getChatMessage());
            // Format the stored timestamp into a readable String using method.
            timeText.setText(ConverterUtility.getTimeOnly(message.getChatTimeStamp()));
            if (ChatAdminActivity.buddy != null)
                nameText.setText(ChatAdminActivity.buddy.getNickname());
            else
                nameText.setText("Admin");
            // Insert the profile image from the URL into the ImageView.
            //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }
}