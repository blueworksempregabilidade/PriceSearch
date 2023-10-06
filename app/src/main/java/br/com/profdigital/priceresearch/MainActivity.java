package br.com.profdigital.priceresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.TextView;

import br.com.profdigital.priceresearch.util.NetworkChangeReceiver;
import br.com.profdigital.priceresearch.util.NetworkUtils;

// https://github.com/airbnb/lottie-android/tree/master/sample/src/main/res

public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver.OnNetworkChangeListener {

    private static String TAG = "MainActivity" ;

   private TextView mTextViewNetworkStatus;
   private TextView mTextViewWifiStatus;
    private TextView mTextViewWifiConnectionInfo;
    private NetworkChangeReceiver mNetworkChangeReceiver;

    private void updateConnectionInfo() {
        String mConnectionType = NetworkUtils.getConnectionType(this);
        mTextViewNetworkStatus.setText("Conexão: " + mConnectionType);

        if (NetworkUtils.isWifiConnected(this)) {
            mTextViewWifiStatus.setText("Conectado ao Wi-Fi");
            mTextViewWifiConnectionInfo.setText(NetworkUtils.getWifiConnectionInfo(this));
        } else {
            mTextViewWifiStatus.setText("Desconectado do Wi-Fi");
            mTextViewWifiConnectionInfo.setText("Nenhuma informação disponível");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTextViewNetworkStatus = findViewById(R.id.networkStatus);
//        mTextViewWifiStatus = findViewById(R.id.wifiStatus);
//        mTextViewWifiConnectionInfo = findViewById(R.id.wifiConnectionInfo);

        updateConnectionInfo();

        mNetworkChangeReceiver = new NetworkChangeReceiver(this);
        registerReceiver(mNetworkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkChangeReceiver);
    }

    @Override
    public void onNetworkChange() {
        updateConnectionInfo();
    }


}