package br.com.profdigital.priceresearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ResetPasswordActivity";
    TextView mTextViewAbandonReset;
    Button mButtonSubmit;
    EditText mEditTextEmailReset;
    ProgressBar mProgressBarReset;
    String mEmail;

    private void performAbandonReset(){
        Intent mIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(mIntent);
        finish();
    }

    public class ClickAbandonReset implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            performAbandonReset();
        }
    }

    private boolean isValidEmail(String mEmail){
        if(mEmail == null || mEmail.isEmpty()){
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(mEmail).matches();
    }

    private void performSubmit(){

        mEmail = String.valueOf(mEditTextEmailReset.getText());

        if(!isValidEmail(mEmail)){
            String mTextMessage = getString(R.string.text_email_not_valid);
            Toast.makeText(this, mTextMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressBarReset.setVisibility(View.VISIBLE);

        String mUrl = "http://192.168.0.14/app-login-register/reset-password.php";

//        Intent mIntent = new Intent(getApplicationContext() ,  NewPasswordActivity.class);
//        mIntent.putExtra("email", mEditTextEmailReset.getText().toString());
//        startActivity(mIntent);
//        finish();

//        String mMessage = "Exception: ";
//        Toast.makeText(getApplicationContext(), mMessage + e.getMessage(), Toast.LENGTH_LONG).show();
//        Log.e(TAG, mMessage + e.getMessage());
//        e.printStackTrace();


    }

    public class ClickButtonSubmit implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            performSubmit();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mTextViewAbandonReset = findViewById(R.id.textView_abandon_reset);
        mTextViewAbandonReset.setOnClickListener(new ClickAbandonReset());

        mButtonSubmit = findViewById(R.id.button_submit_reset_password);
        mButtonSubmit.setOnClickListener(new ClickButtonSubmit());

        mEditTextEmailReset = findViewById(R.id.editText_email_reset);

        mProgressBarReset = findViewById(R.id.progressBar_reset_password);

    }

}
