package com.clikfin.clikfinapplication.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.clikfin.clikfinapplication.R;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class Common {

    public static void setError(TextView view, String error, Context context) {
        view.setVisibility(View.VISIBLE);
        view.setTextColor(context.getResources().getColor(R.color.error));
        view.setText(error);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static boolean isNetworkConnected(Context context) {
        try {
            NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void networkDisconnectionDialog(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Alert!");

        alertDialogBuilder.setMessage(context.getString(R.string.internet_connectivity_issue));
        // alertDialogBuilder.setMessage("(daily 9 AM -8 PM, except public holidays) for any queries");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    /* public static void hideKeyboardFrom(Context context, View view) {
         InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
         imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
     }
     public static void disableTouchOnScreen(Activity activity){
         if(activity != null && activity.getWindow() != null ){
             activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
         }
     }

     public static void enableTouchOnScreen(Activity activity){
         if(activity != null && activity.getWindow() != null ){
             activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
         }
     }
 */
    public static SharedPreferences.Editor getSharedPreferencesEditor(Activity activity) {
        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            return sharedPreferences.edit();
        }
        return null;
    }

    public static void logExceptionToCrashlaytics(Exception e) {
        FirebaseCrashlytics.getInstance().recordException(e);
    }

    public static void logAPIFailureToCrashlyatics(Throwable t) {
        FirebaseCrashlytics.getInstance().recordException(t);
    }

    public static boolean isValidEmail(CharSequence email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    public static class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean isInRange(int min, int max, int input) {
            return max > min ? input >= min && input <= max : input >= max && input <= min;
        }
    }


    public static class InputFilterMin implements InputFilter {

        private int min, max;

        public InputFilterMin(int min) {
            this.min = min;

        }

        public InputFilterMin(String min) {
            this.min = Integer.parseInt(min);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (input < min)
                    return null;
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

    }

    public static InputFilter letterFilter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.equals("")) { // for backspace
                return source;
            }
            if (source.toString().matches("[a-zA-Z ]+")) {
                return source;
            }
            return "";
        }

    };


}

