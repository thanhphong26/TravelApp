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
    public List<VoucherModel> getAllVouchers(int voucherId) {
        database = databaseHelper.openDatabase();
        VoucherModel voucherModel = new VoucherModel();
        List<VoucherModel> listVoucher = new ArrayList<VoucherModel>();
        if (database != null) {
            Cursor cursor = null;
            try {

                // get voucher from voucher table with voucherId
                cursor = database.query("voucher", null, "voucher_id = ?", new String[]{String.valueOf(voucherId)}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String voucherCode = cursor.getString(cursor.getColumnIndex("code"));
                    String voucherDescription = cursor.getString(cursor.getColumnIndex("description"));
                    voucherModel = new VoucherModel(voucherId, voucherCode, voucherDescription);
                    listVoucher.add(voucherModel);
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
        return listVoucher;
    }
    @SuppressLint("Range")
    // delete voucher from voucher table with voucherId
    public void deleteVoucher(int voucherId) {
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                database.delete("voucher", "voucher_id = ?", new String[]{String.valueOf(voucherId)});
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
    }

}
