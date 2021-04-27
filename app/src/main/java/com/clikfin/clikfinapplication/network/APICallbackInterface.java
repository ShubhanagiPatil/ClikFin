package com.clikfin.clikfinapplication.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract public class APICallbackInterface<T> implements Callback<T> {
    private ProgressDialog mProgressDialog;
    Context context;

    public APICallbackInterface(Context context) {
        this.context = context;
        mProgressDialog = new ProgressDialog(context);
        ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @EverythingIsNonNull
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
