package id.pptik.org.homecaretimedic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.org.homecaretimedic.R;
import id.pptik.org.homecaretimedic.model.Assestment;

/**
 * Created by Hafid on 11/2/2017.
 */

public class AssestmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "[AssestmentAdapter]";

    private List<Assestment> assestmentList;
    private Context context;

    public AssestmentAdapter(Context context, List<Assestment> assestmentList) {
        this.assestmentList = assestmentList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 1:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.assestment_item_text, parent, false);
                return new AssestmentAdapter.MyViewHolder(itemView);
            case 2:
            itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.assestment_item_option, parent, false);
                return new AssestmentAdapter.MyViewHolderOption(itemView);
            case 3:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.assestment_item_file, parent, false);
                return new AssestmentAdapter.MyViewHolderFile(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Assestment assestment = assestmentList.get(position);
        switch (holder.getItemViewType()) {
            case 1:
                MyViewHolder viewHolderText = (MyViewHolder) holder;
                viewHolderText.assessment.setText(assestment.getQuestions());
                break;
            case 2:
                MyViewHolderOption viewHolderOption = (MyViewHolderOption) holder;
                viewHolderOption.assessment.setText(assestment.getQuestions());
                for (int i = 0; i < assestment.getOptionAssestments().size(); i++) {
                    RadioButton rdbtn = new RadioButton(context);
                    rdbtn.setId(assestment.getOptionAssestments().get(i).getId());
                    rdbtn.setText(assestment.getOptionAssestments().get(i).getOption());
                    viewHolderOption.option.addView(rdbtn);
                }
                break;
            case 3:
                MyViewHolderFile viewHolderFile = (MyViewHolderFile) holder;
                viewHolderFile.assessment.setText(assestment.getQuestions());
                break;
            default:
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        Assestment assestment = assestmentList.get(position);
        return (assestment.getAssestmentType().getId());
    }

    @Override
    public int getItemCount() {
        return assestmentList.size();
    }

    public Assestment getAssestment(int i) {
        if (i > 0 && i < assestmentList.size())
            return assestmentList.get(i);
        else return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.question)
        public TextView assessment;
        @BindView(R.id.answer)
        public EditText answer;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    public class MyViewHolderOption extends RecyclerView.ViewHolder {
        @BindView(R.id.question)
        public TextView assessment;
        @BindView(R.id.optionGroup)
        public RadioGroup option;

        public MyViewHolderOption(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class MyViewHolderFile extends RecyclerView.ViewHolder {
        @BindView(R.id.question)
        public TextView assessment;
        public MyViewHolderFile(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
