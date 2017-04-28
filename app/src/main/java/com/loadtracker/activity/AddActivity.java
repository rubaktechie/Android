package com.loadtracker.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.loadtracker.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class AddActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private EditText source,destination,transmit_days,truck_type,material_type,loading_point,loading_mamool,unloading_mamool,offer_rate,advance_percentage,advance_amount,other_expenditure,actual_weight,status;
    private Button save;
    private Toolbar toolbar;
    private EditText schedule_date;
    private static final String LOG_TAG = "AddActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView,nAutocompleteTextView;

    private GoogleApiClient mGoogleApiClient;
    private com.loadtracker.activity.PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    final Calendar myCalendar = Calendar.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        source = (AutoCompleteTextView)findViewById(R.id.source);
        destination = (AutoCompleteTextView)findViewById(R.id.destination);
        schedule_date = (EditText)findViewById(R.id.schedule_date);
        transmit_days = (EditText)findViewById(R.id.transmit_days);
        truck_type = (MaterialBetterSpinner)findViewById(R.id.truck_type);
        material_type = (MaterialBetterSpinner)findViewById(R.id.material_description);
        loading_point = (MaterialBetterSpinner)findViewById(R.id.loading_point);
        loading_mamool = (EditText)findViewById(R.id.loaded_mamool);
        unloading_mamool = (EditText)findViewById(R.id.unloaded_mamool);
        offer_rate = (EditText)findViewById(R.id.offer_rate);
        advance_percentage = (EditText)findViewById(R.id.advance_percentage);
        advance_amount = (EditText)findViewById(R.id.advance_amount);
        other_expenditure = (EditText)findViewById(R.id.other_expenditure);
        actual_weight = (EditText)findViewById(R.id.actual_weight);
        save = (Button)findViewById(R.id.save);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String[] items = new String[]{"CONTAINER",
                "CONTAINER - 20 FT",
                "CONTAINER - 32 FT",
                "CONTAINER - 40 FT",
                "OPEN BODY - 10 W",
                "OPEN BODY - 12 W",
                "OPEN BODY - 14 W",
                "PICKUP TRUCK",
                "REEFER TRUCK",
                "TRAILER - 10 W",
                "TRAILER - 12 W",
                "TRAILER - 14 W",
                "TRAILER - 22 W",
                "TRUCK - 6 W"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, items);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.truck_type);
        materialDesignSpinner.setAdapter(arrayAdapter);

        String[] lpoint = new String[]{"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> array = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lpoint);
        MaterialBetterSpinner Spinner = (MaterialBetterSpinner)
                findViewById(R.id.loading_point);
       Spinner.setAdapter(array);


        final String[] material = new String[]{
                "Books or Paper Rolls",
                "Building Materials",
                "Cement",
                "Chemicals",
                "Coal and Ash",
                "Container",
                "Crop or Agriculture Products",
                "Electronics or Consumer Durables",
                "Engineering Goods",
                "Fertilizers",
                "Fruits and Vegetables",
                "Furniture and Wood Products",
                "Granites or Marbel",
                "Household Goods",
                "Industrial Equipments",
                "Iron Pipes or Steel Materials",
                "Liquids or Oil Drums",
                "Machineries",
                "Marble Slab or Marble Block",
                "Medicines",
                "Packed Foods",
                "Plastic Granules or Plastic Industrial Goods",
                "Plastic Pipes",
                "Printed books or Paper rolls",
                "Printed Books or Paper Rolls",
                "Refrigerated Goods",
                "Rice or Agriculture Products",
                "Scraps",
                "Stones or Tiles",
                "Textiles",
                "Tyres and Rubber Products",
                "Vehicles or Automobiles",
                "Others or General",};
        ArrayAdapter<String> arrayAdapters = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, material);
        MaterialBetterSpinner materialDesignSpinners = (MaterialBetterSpinner)
                findViewById(R.id.material_description);
        materialDesignSpinners.setAdapter(arrayAdapters);


        mGoogleApiClient = new GoogleApiClient.Builder(AddActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id.source);
        nAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id.destination);
        mAutocompleteTextView.setThreshold(3);
        nAutocompleteTextView.setThreshold(3);
        nAutocompleteTextView.setOnItemClickListener(nAutocompleteClickListener);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
        nAutocompleteTextView.setAdapter(mPlaceArrayAdapter);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        schedule_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        advance_percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int offerrate = Integer.parseInt( offer_rate.getText().toString());
                int amount = Integer.parseInt( advance_amount.getText().toString());
                int result = amount/offerrate;
                advance_percentage.setText(String.valueOf(result));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*getting values of EditText*/
                final String source_val = source.getText().toString();
                final String destination_val = destination.getText().toString();
                final String schedule_date_val = schedule_date.getText().toString();
                final String transmit_days_val = transmit_days.getText().toString();
                final String truck_type_val = truck_type.getText().toString();
                final String material_val = material_type.getText().toString();
                final String loading_point_val = loading_point.getText().toString();
                final String loading_mamool_val = loading_mamool.getText().toString();
                final String unloading_mamool_val = unloading_mamool.getText().toString();
                final String offer_rate_val = offer_rate.getText().toString();
                final String advance_percentage_val = advance_percentage.getText().toString();
                final String advance_amount_val = advance_amount.getText().toString();
                final String other_expenditure_val = other_expenditure.getText().toString();
                final String actual_weight_val = actual_weight.getText().toString();

                /*Checking is empty*/

                check_required(source,source_val);
                check_required(destination,destination_val);
                check_required(schedule_date,schedule_date_val);
                check_required(transmit_days,transmit_days_val);
                check_required(truck_type,truck_type_val);
                check_required(material_type,material_val);
                check_required(actual_weight,actual_weight_val);
                check_required(offer_rate,offer_rate_val);
                check_required(loading_point,loading_point_val);
                check_required(advance_percentage,advance_percentage_val);
                check_required(advance_amount,advance_amount_val);
                check_required(loading_mamool,loading_mamool_val);
                check_required(unloading_mamool,unloading_mamool_val);
                check_required(other_expenditure,other_expenditure_val);

                /*Checking all values entered*/
                if(check_required(source,source_val) && check_required(destination,destination_val) && check_required(schedule_date,schedule_date_val) && check_required(transmit_days,transmit_days_val) &&check_required(truck_type,truck_type_val) &&check_required(material_type,material_val) &&check_required(actual_weight,actual_weight_val) &&check_required(offer_rate,offer_rate_val) &&check_required(loading_point,loading_point_val) &&check_required(advance_percentage,advance_percentage_val) &&check_required(advance_amount,advance_amount_val) &&check_required(loading_mamool,loading_mamool_val) &&check_required(unloading_mamool,unloading_mamool_val) &&check_required(other_expenditure,other_expenditure_val)){
                    RequestQueue queue = Volley.newRequestQueue(AddActivity.this);
                    String url = "http://rubak.cloudapp.net/android/index.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{

                                        Toast.makeText(AddActivity.this,response,Toast.LENGTH_LONG).show();

                                    }
                                    catch (Exception e){
                                        String msg = e.getMessage();
                                        Toast.makeText(AddActivity.this,msg,Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(AddActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                                }
                            }){
                        protected Map<String,String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("source", source_val);
                            params.put("destination", destination_val);
                            params.put("schedule_date", schedule_date_val);
                            params.put("truck_type", truck_type_val);
                            params.put("material", material_val);
                            params.put("loading_point",loading_point_val);
                            params.put("actual_weight", actual_weight_val);
                            params.put("offer_rate", offer_rate_val);
                            params.put("advance_percentage", advance_percentage_val);
                            params.put("advance_amount", advance_amount_val);
                            params.put("loading_mamool", loading_mamool_val);
                            params.put("unloading_mamool", unloading_mamool_val);
                            params.put("other_expenditure", other_expenditure_val);
                            params.put("transmit_days", transmit_days_val);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
            public boolean check_required(EditText id,String value){
                if(value.length()== 0){
                    id.setError( "field  required!" );
                    return false;
                }else{
                    return true;
                }
            }
        });



        }

        private void updateLabel() {

            String myFormat = "MM-dd-yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            schedule_date.setText(sdf.format(myCalendar.getTime()));
        }


            private AdapterView.OnItemClickListener mAutocompleteClickListener
                    = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final com.loadtracker.activity.PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
                    final String placeId = String.valueOf(item.placeId);
                    PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                            .getPlaceById(mGoogleApiClient, placeId);
                    placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
                }
            };
              private AdapterView.OnItemClickListener nAutocompleteClickListener
                    = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final com.loadtracker.activity.PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
                    final String placeId = String.valueOf(item.placeId);
                    PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                            .getPlaceById(mGoogleApiClient, placeId);
                    placeResult.setResultCallback(nUpdatePlaceDetailsCallback);
                }
            };
            private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
                    = new ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(PlaceBuffer places) {
                    if (!places.getStatus().isSuccess()) {
                        Log.e(LOG_TAG, "Place query did not complete. Error: " +
                                places.getStatus().toString());
                        return;
                    }
                    final Place place = places.get(0);
                    CharSequence attributions = places.getAttributions();

                    if (attributions != null) {
                    }
                }
            };
            private ResultCallback<PlaceBuffer> nUpdatePlaceDetailsCallback
                    = new ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(PlaceBuffer places) {
                    if (!places.getStatus().isSuccess()) {
                        Log.e(LOG_TAG, "Place query did not complete. Error: " +
                                places.getStatus().toString());
                        return;
                    }
                    final Place place = places.get(0);
                    CharSequence attributions = places.getAttributions();

                    if (attributions != null) {
                    }
                }
            };
            @Override
            public void onConnected(Bundle bundle) {
                mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
                Log.i(LOG_TAG, "Google Places API connected.");

            }

            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                        + connectionResult.getErrorCode());

                Toast.makeText(this,
                        "Google Places API connection failed with error code:" +
                                connectionResult.getErrorCode(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectionSuspended(int i) {
                mPlaceArrayAdapter.setGoogleApiClient(null);
                Log.e(LOG_TAG, "Google Places API connection suspended.");
            }
        }






