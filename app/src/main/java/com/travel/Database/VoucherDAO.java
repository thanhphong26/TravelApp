package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.VoucherModel;

import java.util.ArrayList;
import java.util.List;

public class VoucherDAO {
    DatabaseHelper databaseHelper = new DatabaseHelper(App.self());
    SQLiteDatabase database;
    public VoucherDAO() {
    }
    @SuppressLint("Range")
    // get all vouchers from voucher table
    public List<VoucherModel> getAllVouchers() {
        List<VoucherModel> voucherModels = new ArrayList<>();
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                Cursor cursor = database.rawQuery("SELECT * FROM voucher", null);
                if (cursor.moveToFirst() ){
                    do {
                        VoucherModel voucherModel = new VoucherModel();
                        voucherModel.setVoucherId(cursor.getInt(cursor.getColumnIndex("voucher_id")));
                        voucherModel.setVoucherCode(cursor.getString(cursor.getColumnIndex("code")));
                        voucherModel.setVoucherDescription(cursor.getString(cursor.getColumnIndex("description")));
                        voucherModel.setVoucherDiscount(cursor.getFloat(cursor.getColumnIndex("voucher_value")));
                        voucherModels.add(voucherModel);
                    } while (cursor.moveToNext());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
        return voucherModels;
    }
}
