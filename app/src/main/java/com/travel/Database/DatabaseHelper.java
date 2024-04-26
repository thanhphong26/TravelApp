package com.travel.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DB_PATH_SUFFIX = "/databases/";

    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public SQLiteDatabase openDatabase() throws SQLException {
        File dbFile = mContext.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error copying database");
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }
    private void copyDatabase(File dbFile) throws IOException {
        InputStream myInput = mContext.getAssets().open(DATABASE_NAME);
        String outFileName = dbFile.getPath();
        File directory = new File(dbFile.getParent());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    public void closeDatabase(SQLiteDatabase database) {
        if (database != null) {
            database.close();
        }
    }
    public void checkAndUpgradeDatabase() {
        SQLiteDatabase db = null;
        try {
            String dbPath = mContext.getDatabasePath(DATABASE_NAME).getPath();
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            int currentVersion = db.getVersion();
            if (currentVersion != DATABASE_VERSION) {
                db.close();
                copyUpdatedDatabase();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    private void copyUpdatedDatabase() {
        try {
            File dbFile = mContext.getDatabasePath(DATABASE_NAME);
            if (dbFile.exists()) {
                dbFile.delete();
            }
            copyDatabase(dbFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
