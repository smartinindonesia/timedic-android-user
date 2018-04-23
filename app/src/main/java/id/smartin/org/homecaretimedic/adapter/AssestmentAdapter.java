package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.Assessment;
import id.smartin.org.homecaretimedic.model.AssessmentOption;
import id.smartin.org.homecaretimedic.model.parammodel.AssessmentAnswerParam;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

/**
 * Created by Hafid on 11/2/2017.
 */

public class AssestmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "[AssestmentAdapter]";

    private List<Assessment> assessmentList;
    private Context context;
    private Activity activity;

    public AssestmentAdapter(Context context, Activity activity, List<Assessment> assessmentList) {
        this.assessmentList = assessmentList;
        this.context = context;
        this.activity = activity;
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 1:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assestment_item_text, parent, false);
                return new AssestmentAdapter.MyViewHolder(itemView);
            case 2:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assestment_item_option, parent, false);
                return new AssestmentAdapter.MyViewHolderOption(itemView);
            case 3:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assestment_item_file, parent, false);
                return new AssestmentAdapter.MyViewHolderFile(itemView);
            case 4:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assestment_item_number, parent, false);
                return new AssestmentAdapter.MyViewHolderNumber(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Assessment assessment = assessmentList.get(position);
        holder.setIsRecyclable(false);
        switch (holder.getItemViewType()) {
            case 1:
                MyViewHolder viewHolderText = (MyViewHolder) holder;
                viewHolderText.assessment.setText(assessment.getQuestions());
                viewHolderText.answer.setText(assessment.getAnswer());
                viewHolderText.answer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        assessmentList.get(position).setAnswer(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                break;
            case 2:
                final MyViewHolderOption viewHolderOption = (MyViewHolderOption) holder;
                viewHolderOption.assessment.setText(assessment.getQuestions());

                for (int i = 0; i < assessment.getOptions().size(); i++) {
                    RadioButton rdbtn = new RadioButton(AssestmentAdapter.this.context);
                    rdbtn.setId(assessment.getOptions().get(i).getId());
                    rdbtn.setText(assessment.getOptions().get(i).getOption());
                    rdbtn.setTag(assessment.getOptions().get(i));
                    viewHolderOption.option.addView(rdbtn);
                    ViewFaceUtility.applyFont(rdbtn, activity, "fonts/Dosis-Medium.otf");
                }
                if (assessment.getId_multiple_choice() > 0) {
                    viewHolderOption.option.check(assessment.getId_multiple_choice());
                }
                viewHolderOption.option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        int result = viewHolderOption.option.getCheckedRadioButtonId();
                        if (result != -1) {
                            RadioButton b = (RadioButton) viewHolderOption.itemView.findViewById(result);
                            AssessmentOption res = (AssessmentOption) b.getTag();
                            assessmentList.get(position).setAnswer(b.getText().toString());
                            assessmentList.get(position).setId_multiple_choice(result);
                            assessmentList.get(position).setSelectedPrice(res.getPriceAdded());
                            Log.i(TAG, "Selected " + assessment.getAnswer());
                        }
                    }
                });

                break;
            case 3:
                MyViewHolderFile viewHolderFile = (MyViewHolderFile) holder;
                viewHolderFile.assessment.setText(assessment.getQuestions());
                break;

            case 4:
                MyViewHolderNumber viewHolderNumber = (MyViewHolderNumber) holder;
                viewHolderNumber.assessment.setText(assessment.getQuestions());
                viewHolderNumber.answer.setText(assessment.getAnswer());
                viewHolderNumber.answer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        assessmentList.get(position).setAnswer(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Assessment assessment = assessmentList.get(position);
        return (assessment.getAssestmentType());
    }

    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    public Assessment getAssessment(int i) {
        if (i > 0 && i < assessmentList.size())
            return assessmentList.get(i);
        else return null;
    }

    public boolean isAssessmentFullfilled() {
        for (int i = 0; i < assessmentList.size(); i++) {
            if (assessmentList.get(i).getAnswer() == null) {
                return false;
            }
        }
        return true;
    }

    public List<Assessment> getAssessmentResult() {
        return assessmentList;
    }

    public List<AssessmentAnswerParam> getAssesmentAnswerParams() {
        List<AssessmentAnswerParam> assessmentAnswerParams = new ArrayList<>();
        for (int i = 0; i < assessmentList.size(); i++) {
            Assessment assessment = assessmentList.get(i);
            AssessmentAnswerParam assessmentAnswerParam = new AssessmentAnswerParam(new AssessmentAnswerParam.IdAssessment((long) assessment.getId()), assessment.getAnswer(), assessment.getPath_file());
            assessmentAnswerParam.setPrice(assessment.getSelectedPrice());
            assessmentAnswerParams.add(assessmentAnswerParam);
        }
        return assessmentAnswerParams;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.question)
        public TextView assessment;
        @BindView(R.id.answer)
        public EditText answer;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ViewFaceUtility.applyFont(assessment, activity, "fonts/Dosis-Medium.otf");
            ViewFaceUtility.applyFont(answer, activity, "fonts/Dosis-Medium.otf");
        }
    }

    public class MyViewHolderNumber extends RecyclerView.ViewHolder {
        @BindView(R.id.question)
        public TextView assessment;
        @BindView(R.id.answer)
        public EditText answer;

        public MyViewHolderNumber(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ViewFaceUtility.applyFont(assessment, activity, "fonts/Dosis-Medium.otf");
            ViewFaceUtility.applyFont(answer, activity, "fonts/Dosis-Medium.otf");
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
            ViewFaceUtility.applyFont(assessment, activity, "fonts/Dosis-Medium.otf");
        }
    }

    public class MyViewHolderFile extends RecyclerView.ViewHolder {
        @BindView(R.id.question)
        public TextView assessment;

        public MyViewHolderFile(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ViewFaceUtility.applyFont(assessment, activity, "fonts/Dosis-Medium.otf");

        }
    }
}
