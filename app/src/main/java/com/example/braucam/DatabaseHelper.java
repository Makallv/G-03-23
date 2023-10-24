package com.example.braucam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                "availableSeats INTEGER NOT NULL, " +
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

    public List<Ride> getAvailableRides() {
        List<Ride> rides = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM rides WHERE strftime('%Y-%m-%d %H:%M:%S', startingDT) > strftime('%Y-%m-%d %H:%M:%S', 'now')";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String startLocation = cursor.getString(cursor.getColumnIndex("startLocation"));
                String endLocation = cursor.getString(cursor.getColumnIndex("endLocation"));
                String startingDTString = cursor.getString(cursor.getColumnIndex("startingDT"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date startingDT = null;
                try {
                    startingDT = dateFormat.parse(startingDTString);
                } catch (ParseException e) {
                    e.printStackTrace(); // Handle the parse exception according to your needs
                }
                String info = cursor.getString(cursor.getColumnIndex("info"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                String carPlate = cursor.getString(cursor.getColumnIndex("carPlate"));
                int seatCount = cursor.getInt(cursor.getColumnIndex("seatCount"));
                int reservedSeats = cursor.getInt(cursor.getColumnIndex("availableSeats"));
                int ownerId = cursor.getInt(cursor.getColumnIndex("ownerId"));


                // Create Ride object and add it to the list
                rides.add(new Ride(ownerId, startLocation, endLocation, startingDT, carPlate, info, price, seatCount, reservedSeats));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return rides;
    }

    public void addRide(Ride ride) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ownerId", ride.getOwnerId());
        values.put("startLocation", ride.getStartDestination());
        values.put("endLocation", ride.getEndDestination());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        values.put("startingDT", dateFormat.format(ride.getDateAndTime()));
        values.put("carPlate", ride.getCarPlate());
        values.put("info", ride.getAdditionalInfo());
        values.put("price", ride.getPrice());
        values.put("seatCount", ride.getSeats());
        values.put("availableSeats", 0);

        // Insert the new ride into the rides table
        db.insert("rides", null, values);
        db.close();
    }

    public int getUserId(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1; // Default value indicating user not found

        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE username=?", new String[]{userName});
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("id"));
        }

        cursor.close();
        return userId;
    }
    // database operations methods here
}

