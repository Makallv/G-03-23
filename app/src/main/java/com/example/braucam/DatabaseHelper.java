package com.example.braucam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Braucam";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create database tables here
        db.execSQL("CREATE TABLE IF NOT EXISTS Users (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "username VARCHAR(10) NOT NULL, " +
                "email VARCHAR(25) NOT NULL, " +
                "password VARCHAR(25) NOT NULL, " +
                "UNIQUE (id), " +
                "UNIQUE (email)" +
                ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS Rides (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "ownerId INTEGER NOT NULL, " +
                "startLocation VARCHAR(20) NOT NULL, " +
                "endLocation VARCHAR(20) NOT NULL, " +
                "seatCount INTEGER NOT NULL, " +
                "bookedSeats INTEGER NOT NULL, " +
                "price DOUBLE NOT NULL, " +
                "carPlate VARCHAR(6) NOT NULL, " +
                "startingDT DATE NOT NULL, " +
                "info TEXT, " +
                "UNIQUE (id), " +
                "FOREIGN KEY (ownerId) REFERENCES Users(id)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Use for database upgrades
    }

    public boolean insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password); // Note: You should hash and salt the password before storing it
        long result = db.insert("users", null, contentValues);
        return result != -1;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        return isAuthenticated;
    }

    // database operations methods here
}

