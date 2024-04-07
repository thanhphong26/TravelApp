package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.travel.Database.DatabaseHelper;
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
                if(email.isEmpty() || password.isEmpty()){
                    loginBinding.txtError.setText("Vui lòng điền đầy đủ thông tin!");
                }
                else if (checkUser(email, password)) {
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

    private boolean checkUser(String email, String password) {
        boolean isValid = false;
        database = databaseHelper.openDatabase();
        if (database != null) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            Cursor cursor = null;
            try {
                cursor = database.rawQuery(query, new String[]{email, password});
                if (cursor != null && cursor.moveToFirst()) {
                    isValid = true;
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
        return isValid;
    }
}