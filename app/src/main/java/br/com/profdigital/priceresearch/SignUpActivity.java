package br.com.profdigital.priceresearch;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private EditText mEditTextFullName;
    private EditText mEditTextEmail;
    private EditText mEditTextUserName;
    private EditText mEditTextPasswordSignUp, mEditTextPasswordSignUp2;
    private Button mButtonSignUp;
    private TextView mTextViewAlreadyLogin;
    private ProgressBar mProgressBar;

    String mStringName, mStringEmail, mStringPassword, mStringFullName;

    private boolean isRequired(){
        if(TextUtils.isEmpty(mEditTextFullName.getText()) || TextUtils.isEmpty(mEditTextEmail.getText()) ||  TextUtils.isEmpty(mEditTextUserName.getText()) || TextUtils.isEmpty(mEditTextPasswordSignUp.getText()) || TextUtils.isEmpty(mEditTextPasswordSignUp2.getText())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSamePassword(){
        String mPassword1 = mEditTextPasswordSignUp.getText().toString();
        String mPassword2 = mEditTextPasswordSignUp2.getText().toString();
        return mPassword1.equals(mPassword2);
    }

    private void performActivityLogin(){
        Intent mIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(mIntent);
        finish();
    }

    private void postData(){
        if(isRequired()){
            String mTextMessage = getString(R.string.text_error_all_fields_required);
            Toast.makeText(getApplicationContext(), mTextMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isSamePassword()){
            String mTextMessage = getString(R.string.text_password_are_not_same);
            Toast.makeText(getApplicationContext(), mTextMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        mStringName = String.valueOf(mEditTextUserName.getText()).toLowerCase(Locale.ROOT);
        mStringEmail = String.valueOf(mEditTextEmail.getText());
        mStringPassword = String.valueOf(mEditTextPasswordSignUp.getText());
        mStringFullName = String.valueOf(mEditTextFullName.getText());

        mProgressBar.setVisibility(View.VISIBLE);

        User mUser = new User(mStringFullName, mStringName, mStringPassword, mStringEmail, 0, "post", "otp", System.currentTimeMillis() );

        int vResult = UserDao.insertUser(mUser, getApplicationContext());

        String mTextMessage;

        mButtonSignUp.setVisibility(View.GONE); //aluna Karen 3F dois clique no botao

        if(vResult <= 0){
            mTextMessage = getString(R.string.text_insert_error);
        } else {
            mTextMessage = getString(R.string.text_insert_success);
        }

        mProgressBar.setVisibility(View.GONE);

        Toast.makeText(getApplicationContext(), mTextMessage, Toast.LENGTH_SHORT).show();

        Intent mIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(mIntent);
        finish();

    }

    public class ClickButtonSignUp implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            postData();
        }
    }

    public class ClickTextViewAlreadyLogin implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            performActivityLogin();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mEditTextEmail = findViewById(R.id.editText_email);
        mEditTextUserName = findViewById(R.id.editText_user_name);
        mEditTextFullName = findViewById(R.id.editText_full_name);
        mEditTextPasswordSignUp = findViewById(R.id.editText_password_sign_up);
        mEditTextPasswordSignUp2 = findViewById(R.id.editText_password_sign_up_2);

        mTextViewAlreadyLogin = findViewById(R.id.textView_already);
        mTextViewAlreadyLogin.setOnClickListener(new ClickTextViewAlreadyLogin());

        mProgressBar = findViewById(R.id.progressBarSignUp);

        mButtonSignUp = findViewById(R.id.button_sign_up);
        mButtonSignUp.setOnClickListener(new ClickButtonSignUp());

    }
}
