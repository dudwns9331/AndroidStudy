package com.example.diary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    CancellationSignal cancellationSignal;
    private Context context;
    static boolean check = false;

    public FingerprintHandler(Context context) {
        this.context = context;
    }

    //메소드들 정의
    public void startAutho(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("인증 에러 발생" + errString, false);
        check = false;
    }

    @Override
    public void onAuthenticationFailed() {

        this.update("인증 실패", false);
        check = false;
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: " + helpString, false);
        check = false;
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("지문인식이 확인되었습니다..", true);
        check = true;

    }

    public void stopFingerAuth() {
        if (cancellationSignal != null && !cancellationSignal.isCanceled()) {
            cancellationSignal.cancel();
        }
    }

    private void update(String s, boolean b) {
        final TextView tv_message = (TextView) ((Activity) context).findViewById(R.id.textview2);
        final ImageView iv_fingerprint = (ImageView) ((Activity) context).findViewById(R.id.finger);
        final LinearLayout linearLayout = (LinearLayout) ((Activity) context).findViewById(R.id.ll_secure);
        final Activity activity = ((Activity) context);
        //안내 메세지 출력
        tv_message.setText(s);

        if (b == false) {
            tv_message.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else {//지문인증 성공
            tv_message.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            iv_fingerprint.setImageResource(R.mipmap.ic_done);
            linearLayout.setVisibility(LinearLayout.VISIBLE);

            //sound effect
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone((Activity) context, notification);
            r.play();
            Intent intent = new Intent(activity, list.class);
            intent.putExtra("id", "finger");
            activity.startActivity(intent);
            activity.finish();
        }

    }

    public boolean check() {
        return check;
    }


}