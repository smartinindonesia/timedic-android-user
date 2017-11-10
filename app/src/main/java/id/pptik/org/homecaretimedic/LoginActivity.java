package id.pptik.org.homecaretimedic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import id.pptik.org.homecaretimedic.config.Constants;
import id.pptik.org.homecaretimedic.manager.HomecareSessionManager;
import id.pptik.org.homecaretimedic.model.User;
import id.pptik.org.homecaretimedic.tools.HomecareRestClient;

public class LoginActivity extends AppCompatActivity {
    public String TAG = "[LoginActivity]";

    @BindView(R.id.btnSignIn)
    Button signIn;
    @BindView(R.id.emailAddress)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.mainLayout)
    CoordinatorLayout mainLayout;
    @BindView(R.id.btnSignup)
    Button signUp;

    private HomecareSessionManager homecareSessionManager;
    private User user;
    private boolean checkPermission = false;

    private static final int MY_PERMISSIONS_REQUEST = 999;
    String[] listPermission = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        if (homecareSessionManager.isLogin()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User();
                Log.e(TAG, "Sudah pencet tombol sign in");
                gotoMainPage(user);
                //doLogin();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newinten = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(newinten);
            }
        });
        setPermission();
    }

    private void setPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, listPermission, MY_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST:
                checkPermission = true;
        }
    }

    public void doLogin(){
        RequestParams params = new RequestParams();
        params.put("username", username.getText());
        params.put("password", password.getText());
        HomecareRestClient.post(Constants.ROUTE_LOGIN, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String token = response.getString("token");
                    if (!token.equals(null)) {
                        Log.i(TAG, response.toString());
                        JSONObject userJSON = response.getJSONObject("user");
                        JSONArray roles = userJSON.getJSONArray("roles");
                        user = new User();
                        user.setNama(userJSON.getString("name"));
                        user.setId(userJSON.getInt("id"));
                        user.setUsernama(userJSON.getString("username"));
                        user.setSession(token);
                        homecareSessionManager.createLoginSession(user);
                        gotoMainPage(user);
                    } else {
                        Snackbar mSnackbar = Snackbar.make(mainLayout, R.string.login_failed, Snackbar.LENGTH_LONG);
                        View mView = mSnackbar.getView();
                        TextView mTextView = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        else
                            mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                        mSnackbar.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Snackbar mSnackbar = Snackbar.make(mainLayout, R.string.login_failed, Snackbar.LENGTH_LONG);
                View mView = mSnackbar.getView();
                TextView mTextView = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                    mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                else
                    mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                mSnackbar.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Snackbar mSnackbar = Snackbar.make(mainLayout, R.string.login_failed, Snackbar.LENGTH_LONG);
                View mView = mSnackbar.getView();
                TextView mTextView = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                    mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                else
                    mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                mSnackbar.show();
            }
        });
    }

    public void gotoMainPage(User usr){
        homecareSessionManager.createLoginSession(usr);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
