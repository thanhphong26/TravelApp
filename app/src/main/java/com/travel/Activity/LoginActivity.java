package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.travel.Database.DatabaseHelper;
import com.travel.Model.UserModel;
import com.travel.Utils.Constants;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityLoginBinding;
public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding loginBinding;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        loginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginBinding.edtEmail.getText().toString();
                String password = loginBinding.edtPassword.getText().toString();
                UserModel userModel = getUser(email, password);
                if(email.isEmpty() || password.isEmpty()){
                    loginBinding.txtError.setText("Vui lòng điền đầy đủ thông tin!");
                }
                else if (userModel != null) {
                    SharePreferencesHelper.getInstance().put(Constants.USER_SHARE_PREFERENCES, userModel);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else {
                    loginBinding.txtError.setText("Email hoặc mật khẩu không đúng! Vui lòng kiểm tra lại!");
                }
            }
        });
        loginBinding.txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("Range")
    private UserModel getUser(String email, String password) {
        boolean isValid = false;
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                cursor = database.query("users", null, "email = ? AND password = ?", new String[]{email, password}, null, null, null);
                //cursor = database.rawQuery(query, new String[]{email, password});
                if (cursor != null && cursor.moveToFirst()) {
                    UserModel userModel = new UserModel();
                    userModel.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
                    userModel.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    userModel.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    userModel.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                    userModel.setAvatar(cursor.getString(cursor.getColumnIndex("avatar")));
                    return userModel;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                databaseHelper.closeDatabase(database);
            }
        }
        return null;
    }
}