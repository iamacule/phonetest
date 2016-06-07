package phamhungan.com.phonetestv3.util;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

/**
 * Created by Mr An on 26/03/2016.
 */
public class Preferences {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private final String MY_PREF_NAME = "PhoneTestPref";
    public final String RATING_KEY = "RATING";

    public Preferences(Context context){
        pref = context.getSharedPreferences(MY_PREF_NAME, context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void storeData(String key,Object value){
        if(value instanceof String){
            editor.putString(key,(String)value);
        }
        if(value instanceof Boolean){
            editor.putBoolean(key,(Boolean)value);
        }
        if(value instanceof Integer){
            editor.putInt(key,(Integer)value);
        }
        editor.commit();
    }

    public boolean getPermission(String key) {
        return pref.getBoolean(key,true);
    }

    public boolean getRating(){
        return pref.getBoolean(RATING_KEY,false);
    }

    public void deleteKey(String key){
        editor.remove(key);
        editor.commit();
    }
}
