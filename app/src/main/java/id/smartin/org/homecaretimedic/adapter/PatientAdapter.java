package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import id.smartin.org.homecaretimedic.AddUserCustomerActivity;
import id.smartin.org.homecaretimedic.DateTimePickActivity;
import id.smartin.org.homecaretimedic.HCAssestmentActivity;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.config.UIConstants;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.Patient;
import id.smartin.org.homecaretimedic.model.submitmodel.SubmitInfo;
import id.smartin.org.homecaretimedic.tools.ConverterUtility;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.PatientAPIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hafid on 11/26/2017.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder> {
    public static final String TAG = "[PatientAdapter]";

    private List<Patient> patientList;
    private Activity context;
    private PatientAPIInterface patientAPIInterface;
    private HomecareSessionManager homecareSessionManager;

    public PatientAdapter(Activity context, List<Patient> patients) {
        this.patientList = patients;
        this.context = context;
        homecareSessionManager = new HomecareSessionManager(context, context.getApplicationContext());
        patientAPIInterface = APIClient.getClientWithToken(homecareSessionManager, context.getApplicationContext()).create(PatientAPIInterface.class);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer, parent, false);
        return new PatientAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Patient patient = patientList.get(position);
        holder.patientsName.setText(patient.getName());
        holder.dateOfBirth.setText(ConverterUtility.getDateString(patient.getDateOfBirth()));
        holder.gender.setText(patient.getGender());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog sdialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                sdialog.setTitleText("Apakah anda yakin?");
                sdialog.setContentText("Data tidak dapat dikembalikan setelah terhapus!");
                sdialog.setConfirmText("Ya");
                sdialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        deletePatient(patient);
                    }
                });
                sdialog.setCancelable(true);
                sdialog.setCancelText("Tidak");
                sdialog.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddUserCustomerActivity.class);
                intent.putExtra("patient_data", patient);
                context.startActivity(intent);
            }
        });
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SubmitInfo.serviceAvailable.equals(UIConstants.HOMECARE_SERVICE)){
                    SubmitInfo.registeredPatient.add(patient);
                    Intent intent = new Intent(context, HCAssestmentActivity.class);
                    context.startActivity(intent);
                    context.finish();
                } else if (SubmitInfo.serviceAvailable.equals(UIConstants.CHECKLAB_SERVICE)){
                    SubmitInfo.registeredPatient.add(patient);
                    Intent intent = new Intent(context, DateTimePickActivity.class);
                    context.startActivity(intent);
                    context.finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public Patient getItem(int position) {
        return patientList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.patientsName)
        public TextView patientsName;
        @BindView(R.id.genderSelector)
        public TextView gender;
        @BindView(R.id.dateOfBirthSelector)
        public TextView dateOfBirth;
        @BindView(R.id.btnEdit)
        public Button edit;
        @BindView(R.id.btnDelete)
        public Button delete;
        @BindView(R.id.btnSelect)
        public Button select;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void deletePatient(Patient patient){
        final SweetAlertDialog progressDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading...");
        progressDialog.setContentText("Menghapus data pasien");
        progressDialog.show();

        final Call<ResponseBody> services = patientAPIInterface.deletePatientById(patient.getId());
        services.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    Log.i(TAG, response.raw().toString());
                    sDialog.setTitleText("Terhapus!")
                            .setContentText("Data pasien telah di hapus!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    context.finish();
                                    context.startActivity(context.getIntent());
                                }
                            });
                    sDialog.show();
                } else if (response.code() == 201) {
                    SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    Log.i(TAG, response.raw().toString());
                    sDialog.setTitleText("Terhapus!")
                            .setContentText("Data pasien telah di hapus!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    context.finish();
                                    context.startActivity(context.getIntent());
                                }
                            });
                    sDialog.show();
                } else {
                    Log.i(TAG, response.raw().toString());
                    Log.i(TAG, "failure");
                    SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                    Log.i(TAG, response.raw().toString());
                    sDialog.setTitleText("Oops!")
                            .setContentText("Data pasien gagal di hapus!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(null);
                    sDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Log.i(TAG, "FAILURE");
                t.printStackTrace();
                SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                sDialog.setTitleText("Oops!")
                        .setContentText("Data pasien gagal di hapus!")
                        .setConfirmText("OK")
                        .setConfirmClickListener(null);
                sDialog.show();
                notifyDataSetChanged();
            }
        });
    }
}
