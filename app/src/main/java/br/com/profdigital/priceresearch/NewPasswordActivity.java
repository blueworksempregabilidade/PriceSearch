package br.com.profdigital.priceresearch;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class NewPasswordActivity extends AppCompatActivity {

    private static final String TAG = "NewPasswordActivity";
    EditText mEditTextNewPassword, mEditTextOtp;
    Button mButtonSubmitOtp;
    ProgressBar mProgressBarNewPassword;

    String mEmail;

    private void performSubmit(){

        mProgressBarNewPassword.setVisibility(View.VISIBLE);

        String mUrl = "http://192.168.0.14/app-login-register/new-password.php";



//        Intent mIntent = new Intent(getApplicationContext() ,  LoginActivity.class);
//        startActivity(mIntent);
//        finish();

//        String mMessage = "Exception: ";
//        Toast.makeText(getApplicationContext(), mMessage + e.getMessage(), Toast.LENGTH_LONG).show();
//        Log.e(TAG, mMessage + e.getMessage());
//        e.printStackTrace();

    }

    public class ClickButtonSubmitNewPassword implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            performSubmit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        mEditTextNewPassword = findViewById(R.id.editText_new_password);
        mEditTextOtp = findViewById(R.id.editText_otp);
        mButtonSubmitOtp = findViewById(R.id.button_submit_otp);
        mButtonSubmitOtp.setOnClickListener(new ClickButtonSubmitNewPassword());

        mProgressBarNewPassword = findViewById(R.id.progressBar_new_password);

        mEmail = getIntent().getExtras().getString("email");

    }
}