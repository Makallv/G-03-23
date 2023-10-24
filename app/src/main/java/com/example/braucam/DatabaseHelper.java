package com.example.braucam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
                "bookerIDs VARCHAR(50), " +
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
                int ID = cursor.getInt(cursor.getColumnIndex("id"));
                String bookerIDs = cursor.getString(cursor.getColumnIndex("bookerIDs"));

                // Create Ride object and add it to the list
                rides.add(new Ride(ID, ownerId, startLocation, endLocation, startingDT, carPlate, info, price, seatCount, reservedSeats, bookerIDs));

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
        values.put("bookerIDs", ride.getbookerIDs());

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


    public String addBooking(Ride ride) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("DetailActivity", ride.getEndDestination());

        // Retrieve the existing bookerIDs string from the ride
        String existingBookerIDs = ride.getbookerIDs();

        if (existingBookerIDs == null) {
            existingBookerIDs = "";
        }

        String[] bookerIDs = existingBookerIDs.split(" ");
        List<String> bookerIDList = new ArrayList<>(Arrays.asList(bookerIDs));

        String rideOwnString = ride.getOwnerId() + "";
        String sessionIdString = MainActivity.getSession().getId() + "";
        if (!bookerIDList.contains(sessionIdString) && !sessionIdString.equals(rideOwnString) && bookerIDList.size() <= ride.getSeats()) {
            // User can book the ride
            bookerIDList.add(sessionIdString);
            // Join the list to create the updated bookerIDs string
            String updatedBookerIDs = String.join(" ", bookerIDList);
            ride.setbookerIDs(updatedBookerIDs);
            ride.addReservedSeats();

            // Update the bookerIDs field in the database
            ContentValues values = new ContentValues();
            values.put("bookerIDs", updatedBookerIDs);

            // Construct the WHERE clause for the specific ride based on its ID
            String selection = "id = ?";
            String[] selectionArgs = { String.valueOf(ride.getID()) };

            // Execute the update query
            int rowsAffected = db.update("rides", values, selection, selectionArgs);

            if (rowsAffected > 0) {
                return "You booked this ride";
            } else {
                return "Something went wrong";
            }
        } else if (bookerIDList.contains(sessionIdString)){
            return "You have already booked this ride" + ride.getbookerIDs();
        } else if (sessionIdString.equals(rideOwnString)) {
            return "You can't book your own rides" + ride.getbookerIDs();
        } else if (bookerIDList.size() > ride.getSeats()) {
            return "This ride if full";
        }

        // Close the database connection
        db.close();
        return "Something went wrong";
    }
    // database operations methods here
}

