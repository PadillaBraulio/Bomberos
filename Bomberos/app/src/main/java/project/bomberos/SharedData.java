package project.bomberos;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 16/07/16.
 */
public class SharedData {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor  sharedEditor;
    private Context context;

    public SharedData(SharedPreferences sharedPrf, Context context) {
        this.sharedPref = sharedPrf;
        this.sharedEditor = sharedPrf.edit();
        this.context = context;
    }

    protected void putAmbulanceNumber(String AmbulanceNumber){
        sharedEditor.putString(context.getString(R.string.AmbulanceNUmber), AmbulanceNumber);
        sharedEditor.commit();
    }
    protected String getAmbulanceNumber(){
        String AmbulanceNumber = context.getString(R.string.AmbulanceNUmber);
        return sharedPref.getString(AmbulanceNumber, "");
    }
}
