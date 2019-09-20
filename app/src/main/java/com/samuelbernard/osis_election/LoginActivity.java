package com.samuelbernard.osis_election;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.samuelbernard.osis_election.preference.LoginPref;
import com.samuelbernard.osis_election.rest.ApiClient;
import com.samuelbernard.osis_election.rest.ApiInterface;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.input_username)
    TextInputEditText inputUsername;
    @BindView(R.id.input_password)
    TextInputEditText inputPassword;
    @BindView(R.id.btn_login)
    MaterialButton btnLogin;
    Boolean isEmptyField;
    LoginPref loginPref;
    SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPref = new LoginPref(this);

        if (loginPref.getIdKandidat() != 100 || loginPref.getIdMesin() != 100) {
            switchActivity();
        }
    }

    private void submitData(String username, String password) {
        SweetAlertDialog dialogFail = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        dialogFail.getProgressHelper().setBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.loginMesin(username, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    int status = object.getInt("Status");
                    if (status == 200) {
                        dialog.dismissWithAnimation();
                        Toast.makeText(LoginActivity.this, object.getJSONObject("data").getString("nama_ketua"), Toast.LENGTH_SHORT).show();
                        loginPref.setIdKandidat(object.getJSONObject("data").getInt("id"));
                        loginPref.setIdMesin(100);
                        loginPref.setNamaKetua(object.getJSONObject("data").getString("nama_ketua"));
                        loginPref.setNamaWakil(object.getJSONObject("data").getString("nama_wakil"));
                        loginPref.setVisi(object.getJSONObject("data").getString("visi"));
                        loginPref.setMisi(object.getJSONObject("data").getString("misi"));
                        loginPref.setFoto(object.getJSONObject("data").getString("foto"));
                        loginPref.setVote(object.getJSONObject("data").getString("PersenPemilih"));
                        switchActivity();
                    } else {
                        dialog.dismissWithAnimation();
                        dialogFail.setTitleText("Failed");
                        dialogFail.setContentText("Please check your username & password");
                        dialogFail.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismissWithAnimation();
                dialogFail.setTitleText("Failed");
                dialogFail.setContentText("Something went wrong!");
                dialogFail.show();
            }
        });
    }

    private void validateForm() {
        isEmptyField = false;
        String username = inputUsername.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            isEmptyField = true;
            inputUsername.setError("Field ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(password)) {
            isEmptyField = true;
            inputPassword.setError("Field ini tidak boleh kosong");
        }
        if (!isEmptyField) {
            dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            dialog.getProgressHelper().setBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            dialog.setTitleText("Loading");
            dialog.setCancelable(false);
            dialog.show();
            submitData(username, password);
        }
    }

    public void switchActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.btn_login)
    public void onButtonSubmit() {
        validateForm();
    }
}