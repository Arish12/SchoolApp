package it.arisetech.app.arish;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Arish on 3/13/2017.
 */

public class Session {
    public static final String LOGIN_SUCCESS = "success";


    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "login";

    //This would be used to store the email of current logged in user
    public static final String ROLL_SHARED_PREF = "roll";
    public static final String FACULTY_SHARED_PREF = "faculty";
    public static final String SECTION_SHARED_PREF = "section";
    public static final String GRADE_SHARED_PREF = "grade";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    public static final String KEY_IS_USER_ADDED = "isuseradded";


    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "CollegeTest";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String Book_ID = "bookid";
    // Email address (make variable public to access from outside)

    public static final String KEY_USER_ID= "userid";

    // Constructor
    public Session(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
}
