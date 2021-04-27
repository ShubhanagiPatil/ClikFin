package com.clikfin.clikfinapplication.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.MainActivity;
import com.clikfin.clikfinapplication.activity.NotificationActivity;
import com.clikfin.clikfinapplication.externalRequests.Request.FCMTokenRequest;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    static Context mContext;
    static int count;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);


      /*  FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        if(token!=null){

                            FCMTokenRequest fcmTokenRequest=new FCMTokenRequest();
                            fcmTokenRequest.setFcmToken(token);
                            fcmTokenRequest.setUserId(getBaseContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE).getString(getString(R.string.user_id),""));

                            Call<String> call = APIClient.getClient(APIClient.type.JSON).postFCMToken(fcmTokenRequest);
                            call.enqueue(new APICallbackInterface<String>(getBaseContext()) {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    super.onResponse(call, response);
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    super.onFailure(call, t);
                                }
                            });
                        }

                    }
                });*/



    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData() != null) {
            try {
                JSONObject notification = new JSONObject(remoteMessage.getData().get("message"));
                JSONObject message = notification.getJSONObject("data");
                sendNotification(message.getString("title"), message
                        .getString("message"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//you can use your launcher Activity insted of SplashActivity, But if the Activity you used here is not launcher Activty than its not work when App is in background.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//Add Any key-value to pass extras to intent
        intent.putExtra("pushnotification", "yes");
        intent.putExtra("Title",title);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//For Android Version Orio and greater than orio.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("bb", "bb", importance);
            mChannel.setDescription(messageBody);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotifyManager.createNotificationChannel(mChannel);
        }
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "bb");
            mBuilder.setContentTitle(title)
                    .setContentText(messageBody)
                    .setSmallIcon(R.mipmap.ic_icon)
                    .setContentIntent(pendingIntent)
                    .setChannelId("bb")
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            mNotifyManager.notify(count, mBuilder.build());
            count++;

//For Android Version lower than oreo.

    }

}
