package config;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import ke.co.lt.com.skilllite.LoginActivity;

public class SessionManagement {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    public SessionManagement(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(BaseVariables.PREFS_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }

    public void setUserSession(String user_id, String username, String name, String phone, String gender) {
        editor.putString(BaseVariables.USER_ID, user_id);
        editor.putString(BaseVariables.USERNAME, username);
        editor.putString(BaseVariables.NAME, name);
        editor.putString(BaseVariables.PHONE, phone);
        editor.putString(BaseVariables.GENDER, gender);
        editor.commit();
    }

    public HashMap<String, String> getUserInfo() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(BaseVariables.USER_ID, prefs.getString(BaseVariables.USER_ID, null));
        user.put(BaseVariables.USERNAME, prefs.getString(BaseVariables.USERNAME, null));
        user.put(BaseVariables.NAME, prefs.getString(BaseVariables.NAME, null));
        user.put(BaseVariables.PHONE, prefs.getString(BaseVariables.PHONE, null));
        user.put(BaseVariables.GENDER, prefs.getString(BaseVariables.GENDER, null));
        return user;
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();
        Intent logout = new Intent(context, LoginActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(logout);
    }

}
