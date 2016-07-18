package project.bomberos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton searchButton ;
    private EditText ambulanceNumberText;
    private SharedData Data ;
    private ProgressBar mprogressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Data = new SharedData(getPreferences(Context.MODE_PRIVATE),this);
        searchButton = (FloatingActionButton)findViewById(R.id.fab);
        ambulanceNumberText = (EditText)findViewById(R.id.TextAmbulanceNumber);
        mprogressBar = (ProgressBar)findViewById(R.id.progressBar);

        String ambNumber = Data.getAmbulanceNumber();
        if(!ambNumber.equals("")){
            ambulanceNumberText.setText(ambNumber);
        }
        searchButton.setOnClickListener(this);
        ambulanceNumberText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {


                Data.putAmbulanceNumber(s.toString());
            }
        });


    }

    @Override
    public void onClick(View v) {
        String ambulance = Data.getAmbulanceNumber();
        if(!ambulance.equals("")){
            showProgress(true);
            new SearchEmergencyTask().execute(ambulance);

        }else{
            Toast.makeText(this,"Ingrese un número de ambulancia", Toast.LENGTH_LONG).show();
        }
    }

    private void showProgress(final boolean show){
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            searchButton.setVisibility(show ? View.GONE : View.VISIBLE);
            mprogressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mprogressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mprogressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mprogressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            searchButton.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class SearchEmergencyTask extends AsyncTask<String, Void, List<DataEmergency>> {


        @Override
        protected List<DataEmergency> doInBackground(String... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String ambulanceNumber = params[0];
            RequestEmergency request = new RequestEmergency(ambulanceNumber);
            String jsonStr = request.requestEmergencies();
            return request.readJson(jsonStr);

        }

        @Override
        protected void onPostExecute(List<DataEmergency> s) {
            showProgress(false);
            if(s.size()>0){

            }


        }
    }

    public static class DataEmergency {
        private final String id;
        private final String latitude;
        private final String longitude;
        private final String telefone;
        private final String date;
        private final String ambulance;

        public DataEmergency(String id, String latitude, String longitude, String telefone, String date, String ambulance) {
            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.telefone = telefone;
            this.date = date;
            this.ambulance = ambulance;
        }

        public String getId() {
            return id;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getDate() {
            return date;
        }

        public String getTelefone() {
            return telefone;
        }

        public String getAmbulance() {
            return ambulance;
        }
    }
}
