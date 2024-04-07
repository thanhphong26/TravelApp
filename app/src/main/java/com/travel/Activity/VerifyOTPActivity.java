package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import com.travel.Database.DatabaseHelper;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.databinding.ActivityVerifyOtpactivityBinding;

public class VerifyOTPActivity extends AppCompatActivity {
    ActivityVerifyOtpactivityBinding verifyOtpactivityBinding;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    private static final int DELAY_TIME_MILLISECONDS = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyOtpactivityBinding = ActivityVerifyOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(verifyOtpactivityBinding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        UserModel userModel = (UserModel) getIntent().getParcelableExtra("userModel");
        int otp = getIntent().getIntExtra("otp", 0);
        if(userModel != null) {
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