package com.app.palpharmacy.activities;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.palpharmacy.R;
import com.app.palpharmacy.adapters.RecyclerViewAdapter;
import com.app.palpharmacy.model.Pharmacy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
private static int splashtime= 4000;
    private final String JSON_URL = "http://www.palpharmacy.com/index.php/getPharmacies" ;
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<Pharmacy> lstPharmacy;
    private RecyclerView recyclerView ;
    FloatingActionButton fabSwitcher;
    boolean isDark = false;
    ConstraintLayout rootLayout;
    List<Pharmacy> mData;



    private DrawerLayout drawer;
    RecyclerViewAdapter myadapter;
     EditText searchinput;
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       textView = findViewById(R.id.textView);
       Calendar calender = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
       String dateTime =simpleDateFormat.format(Calendar.getInstance().getTime());
      // textView.setText("open now");

        // ini view
        fabSwitcher = findViewById(R.id.fab_switcher);
        rootLayout = findViewById(R.id.root_layout);

        searchinput= findViewById(R.id.edittext);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer= findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
drawer.addDrawerListener(toggle);
toggle.syncState();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        lstPharmacy = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);
        jsonrequest();



    }


    @Override
    public void onBackPressed() {
   Intent intent = new Intent(this,MainActivity.class);
   startActivity(intent);
    }

    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject  = null ;

                for (int i = 0 ; i < response.length(); i++ ) {


                    try {
                        jsonObject = response.getJSONObject(i) ;
                        Pharmacy pharmacy = new Pharmacy() ;
                        pharmacy.setName(jsonObject.getString("pharmacy_name_en"));
                        pharmacy.setCity(jsonObject.getString("city_name"));
                        pharmacy.setGovernorate(jsonObject.getString("gov_name"));
                        pharmacy.setOwner(jsonObject.getString("owner"));
                        pharmacy.setOpens(jsonObject.getString("opening_time"));
                        pharmacy.setImage_url(jsonObject.getString("image"));

                        lstPharmacy.add(pharmacy);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                setuprecyclerview(lstPharmacy);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

searchinput.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    myadapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
});

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request) ;


    }

    private void setuprecyclerview(List<Pharmacy> lstPharmacy) {


     myadapter = new RecyclerViewAdapter(this, lstPharmacy) ;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);
        fabSwitcher.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                isDark = !isDark ;
                if (isDark) {

                    rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                }
                else {
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
                }
             //   myadapter = new RecyclerViewAdapter(getApplicationContext(),mData,isDark);
                recyclerView.setAdapter(myadapter);
                saveThemeStatePref(isDark);


            }

            private void saveThemeStatePref(boolean isDark) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isDark",isDark);
                editor.commit();
            }
            private boolean getThemeStatePref () {

                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
                boolean isDark = pref.getBoolean("isDark",false) ;
                return isDark; }

        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()){
            case R.id.nav_aboutus:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new aboutusfragment()).commit();
                break;
            case R.id.nav_language:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Languagefragment()).commit();
                break;
            case R.id.nav_notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new notificationfragment()).commit();
                break;
            case R.id.nav_location:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new locationfragment()).commit();
                break;
            default:
                fragmentClass = Network.class;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;


            }

            }


