package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.travel.Model.UserModel;
import com.travel.Database.DatabaseHelper;
import com.travel.Utils.SendEmail;
import com.travel.databinding.ActivitySignUpBinding;

import java.sql.Timestamp;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding signUpBinding;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    SendEmail sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());
        sendEmail = new SendEmail();
        databaseHelper = new DatabaseHelper(this);
        signUpBinding.btnSignup.setEnabled(false);
        signUpBinding.chkAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            signUpBinding.btnSignup.setEnabled(isChecked && !isAnyFieldEmpty());
        });
        signUpBinding.btnSignup.setOnClickListener(v -> {
            String username = signUpBinding.edtUsername.getText().toString();
            String email = signUpBinding.edtEmail.getText().toString();
            String password = signUpBinding.edtPassword.getText().toString();
            String retypedPassword = signUpBinding.edtRetypePassword.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(retypedPassword)) {
                signUpBinding.txtError.setText("Vui lòng điền đầy đủ thông tin!");
                return;
            }else if (!password.equals(retypedPassword)) {
                signUpBinding.txtError.setText("Mật khẩu không khớp!");
            } else {
                Timestamp createdAt = new Timestamp(System.currentTimeMillis());
                UserModel userModel=new UserModel(username,email,password, createdAt);
                Random otp=new Random();
                int code=otp.nextInt(8999)+1000;
                AsyncTask<Void, Void, Boolean> execute = new SendEmail(email, code) {
                    @Override
                    protected void onPostExecute(Boolean result) {
                        if (result) {
                            Intent intent = new Intent(SignUpActivity.this, VerifyOTPActivity.class);
                            intent.putExtra("userModel", userModel);
                            intent.putExtra("otp", code);
                            startActivity(intent);
                        } else {
                            signUpBinding.txtError.setText("Gửi mã OTP thất bại!");
                        }
                    }
                }.execute();
            }
        });
        signUpBinding.txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean isAnyFieldEmpty() {
        return TextUtils.isEmpty(signUpBinding.edtUsername.getText().toString().trim())
                || TextUtils.isEmpty(signUpBinding.edtEmail.getText().toString().trim())
                || TextUtils.isEmpty(signUpBinding.edtPassword.getText().toString().trim())
                || TextUtils.isEmpty(signUpBinding.edtRetypePassword.getText().toString().trim());
    }
    /*private boolean registerUser(UserModel userModel) {
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
    }*/
}