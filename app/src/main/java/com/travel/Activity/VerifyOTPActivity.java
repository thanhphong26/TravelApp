package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import com.travel.Database.DatabaseHelper;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.SendEmail;
import com.travel.databinding.ActivityVerifyOtpactivityBinding;

import java.util.Random;

public class VerifyOTPActivity extends AppCompatActivity {
    ActivityVerifyOtpactivityBinding verifyOtpactivityBinding;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    private static final int DELAY_TIME_MILLISECONDS = 5000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000;
    SendEmail sendEmail;
    int otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyOtpactivityBinding = ActivityVerifyOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(verifyOtpactivityBinding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        sendEmail = new SendEmail();
        UserModel userModel = (UserModel) getIntent().getParcelableExtra("userModel");
        otp = getIntent().getIntExtra("otp", 0);
        startCountdown();
        if(userModel != null) {
            verifyOtpactivityBinding.txtEmailVerify.setText(userModel.getEmail());
            verifyOtpactivityBinding.btnConfirm.setOnClickListener(v -> {
                if (verifyOtpactivityBinding.firstPinView.getText().toString().isEmpty()) {
                    verifyOtpactivityBinding.txtError.setText("Vui lòng nhập mã OTP!");
                } else {
                    if (Integer.parseInt(verifyOtpactivityBinding.firstPinView.getText().toString()) == otp) {
                        if (registerUser(userModel)) {
                            verifyOtpactivityBinding.txtError.setText("Đăng ký thành công!");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    navigateToLoginActivity();
                                }
                            }, DELAY_TIME_MILLISECONDS);
                        } else {
                            verifyOtpactivityBinding.txtError.setText("Đăng ký thất bại!");
                        }
                    } else {
                        verifyOtpactivityBinding.txtError.setText("Mã OTP không đúng!");
                    }
                }
            });
            verifyOtpactivityBinding.btnBack.setOnClickListener(v -> {
                onBackPressed();
            });
            verifyOtpactivityBinding.btnResend.setOnClickListener(v -> {
                Random otpResend = new Random();
                int codeResend = otpResend.nextInt(8999) + 1000;
                AsyncTask<Void, Void, Boolean> execute = new SendEmail(userModel.getEmail(), codeResend) {
                    @Override
                    protected void onPostExecute(Boolean result) {
                        if (result) {
                            verifyOtpactivityBinding.txtError.setText("Mã OTP mới đã được gửi!");
                            otp = codeResend;
                            timeLeftInMillis = 60000;
                            verifyOtpactivityBinding.btnConfirm.setEnabled(true);
                            verifyOtpactivityBinding.firstPinView.setEnabled(true);
                            startCountdown();
                        } else {
                            verifyOtpactivityBinding.txtError.setText("Gửi mã OTP thất bại!");
                        }
                    }
                }.execute();
                new Handler().postDelayed(() -> {
                    verifyOtpactivityBinding.txtError.setText("");
                }, DELAY_TIME_MILLISECONDS);
            });
        }
    }
    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }
            @Override
            public void onFinish() {
                verifyOtpactivityBinding.txtTimeConfirm.setText("Mã OTP đã hết hạn");
                verifyOtpactivityBinding.btnConfirm.setEnabled(false);
                verifyOtpactivityBinding.firstPinView.setEnabled(false);
            }
        }.start();
    }

    private void updateCountdownText() {
        int seconds = (int) (timeLeftInMillis / 1000);
        String timeLeftFormatted = "Thời gian còn lại: " + seconds + " giây";
        verifyOtpactivityBinding.txtTimeConfirm.setText(timeLeftFormatted);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    private void navigateToLoginActivity() {
        Intent intent = new Intent(VerifyOTPActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private boolean registerUser(UserModel userModel) {
        boolean isSuccess = false;
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                ContentValues values = new ContentValues();
                values.put("username", userModel.getUsername());
                values.put("email", userModel.getEmail());
                values.put("password", userModel.getPassword());
                values.put("created_at", userModel.getCreatedAt().toString());
                long newRowId = database.insert("users", null, values);
                if (newRowId != -1) {
                    isSuccess = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
        return isSuccess;
    }
}