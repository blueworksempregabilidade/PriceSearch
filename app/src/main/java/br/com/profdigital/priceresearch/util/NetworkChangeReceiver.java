package br.com.profdigital.priceresearch.util;

// https://www.youtube.com/watch?v=uMT0KoPjgs4
// https://www.youtube.com/watch?v=AECDQFTnc4k

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private OnNetworkChangeListener mOnNetworkChangeListener;

    public NetworkChangeReceiver(OnNetworkChangeListener mListener) {
        this.mOnNetworkChangeListener = mListener;
    }

    @Override
    public void onReceive(Context mContext, Intent mIntent) {
        if (mOnNetworkChangeListener != null) {
            mOnNetworkChangeListener.onNetworkChange();
        }
    }

    public interface OnNetworkChangeListener {
        void onNetworkChange();
    }
}
