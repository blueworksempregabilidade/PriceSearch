package br.com.profdigital.priceresearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    TextView mTextViewNewUser, mTextViewForgotPassword, mTextViewNetworkIp;
    Button mButtonLogin;
    EditText mEditTextEmail, mEditTextPassword;
    ProgressBar mProgressBarLogin;
    String mStringUser, mStringPassword, mStringEmail, mStringApiKey, mStringIpV4, mStringNetwork;
    SharedPreferences mSharedPreferences;

    private boolean isRequiredPassword(){
        return  TextUtils.isEmpty(mEditTextPassword.getText());
    }

    private boolean isValidEmail(String mStringEmail){
        if(mStringEmail == null || mStringEmail.isEmpty()){
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(mStringEmail).matches();
    }

    private void showProduct(){
        Intent mIntent = new Intent(getApplicationContext(), ProductActivity.class);
        mIntent.putExtra("EXTRA_FULL_NAME", mSharedPreferences.getString("fullName",""));
        startActivity(mIntent);
        finish();
    }

    private void verifyLogged(){
        if(mSharedPreferences.getString("logged", "false").equals("true")){
            showProduct();
        }
    }

    private void getIpNetwork(){
        WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
        int vIp = mWifiInfo.getIpAddress()+1;
        mStringIpV4 = Formatter.formatIpAddress(vIp);
        mStringNetwork = mWifiInfo.getSSID();
    }

    private void postData(){

        mStringEmail = String.valueOf(mEditTextEmail.getText()).toLowerCase(Locale.ROOT);
        mStringPassword = String.valueOf(mEditTextPassword.getText());

        if(!isValidEmail(mStringEmail)){
            String mTextMessage = getString(R.string.text_email_not_valid);
            Toast.makeText(getApplicationContext(), mTextMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        if(isRequiredPassword()){
            String mTextMessage = getString(R.string.text_error_fill_mandatory_information);
            Toast.makeText(getApplicationContext(), mTextMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressBarLogin.setVisibility(View.VISIBLE);

        User mUser = new User(mStringPassword, mStringEmail);

        String vResult = UserDao.authenticateUser(mUser, getApplicationContext());

        mProgressBarLogin.setVisibility(View.GONE);

        // https://stackoverflow.com/questions/218384/what-is-a-nullpointerexception-and-how-do-i-fix-it
        if(vResult.isEmpty() || vResult.equals("") || vResult.equals("Exception")){
            String mTextMessage;
            mTextMessage = getString(R.string.text_email_or_password_incorrect);
            if(vResult.equals("Exception")){
                mTextMessage  = getString(R.string.text_connection_error);;
            }
            Toast.makeText(getApplicationContext(), mTextMessage, Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("logged", "true");
        mEditor.putString("email", mStringEmail);
        mEditor.putString("fullName", vResult);
        mEditor.apply();

        Intent mIntent = new Intent(getApplicationContext(), ProductActivity.class);
        mIntent.putExtra("EXTRA_FULL_NAME", vResult);
        startActivity(mIntent);
        finish();

    }

    public class ClickButtonLogin implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            postData();
        }
    }

    private void showSignUp(){
        Intent mIntent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(mIntent);
        finish();
    }

    public class ClickNewUserSignUp implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            showSignUp();
        }
    }

    private void showForgotPassword(){
        Intent mIntent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
        startActivity(mIntent);
        finish();
    }

    public class ClickForgotPassword implements  View.OnClickListener{
        @Override
        public void onClick(View view) {
            showForgotPassword();
        }
    }

    public class EditTextAction implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                postData();
            }
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mEditTextEmail = findViewById(R.id.editText_email_login);

        mEditTextPassword = findViewById(R.id.editText_password_login);
        mEditTextPassword.setOnEditorActionListener(new EditTextAction());

        mButtonLogin = findViewById(R.id.button_log_in);
        mButtonLogin.setOnClickListener(new ClickButtonLogin());

        mProgressBarLogin = findViewById(R.id.progressBarLogin);

        mTextViewNewUser = findViewById(R.id.textView_new_user);
        mTextViewNewUser.setOnClickListener(new ClickNewUserSignUp());

        mTextViewForgotPassword = findViewById(R.id.textView_forgot_password);
        mTextViewForgotPassword.setOnClickListener(new ClickForgotPassword());

        mSharedPreferences = getSharedPreferences("MyAppName" , MODE_PRIVATE);

        getIpNetwork();

        mTextViewNetworkIp = findViewById(R.id.textView_ssid_ip);
        mTextViewNetworkIp.setText(mStringIpV4);

        verifyLogged();

    }
}
