package com.loadtracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loadtracker.R;

import org.json.JSONException;
import org.json.JSONObject;


public class viewDetail3 extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView source,destination,schedule_date,transmit_days,truck_type, material_type, loading_mamool, unloading_mamool, offer_rate, advance_percentage, advance_amount, other_expenditure, actual_weight;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view3);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        source = (TextView)findViewById(R.id.source);
        destination = (TextView)findViewById(R.id.destination);
        schedule_date = (TextView)findViewById(R.id.trip_date);
        transmit_days = (TextView)findViewById(R.id.transmit_days);
        truck_type = (TextView)findViewById(R.id.truck_type);
        material_type = (TextView)findViewById(R.id.material_description);
        loading_mamool = (TextView)findViewById(R.id.loaded_mamool);
        unloading_mamool = (TextView)findViewById(R.id.unloaded_mamool);
        offer_rate = (TextView)findViewById(R.id.offer_rate);
        advance_percentage = (TextView)findViewById(R.id.advance_percentage);
        advance_amount = (TextView)findViewById(R.id.advance_amount);
        other_expenditure = (TextView)findViewById(R.id.other_expenditure);
        actual_weight = (TextView)findViewById(R.id.actual_weight);

        Intent intent = getIntent();
        String profile = intent.getStringExtra("array");
        try {
            JSONObject object = new JSONObject(profile);
            source.setText(object.getString("from_location_name"));
            destination.setText(object.getString("to_location_name"));
            schedule_date.setText(object.getString("trip_date").substring(0, 10));
            transmit_days.setText(object.getString("transit_days"));
            truck_type.setText(object.getString("truck_type"));
            material_type.setText("Material:"+object.getString("product"));
            loading_mamool.setText(object.getString("loading_mamool"));
            unloading_mamool.setText(object.getString("unloading_mamool"));
            offer_rate.setText(object.getString("offerrate"));
            advance_percentage.setText("Advance ("+object.getString("advance_percentage")+"%)");
            advance_amount.setText(object.getString("advance_amount"));
            other_expenditure.setText(object.getString("other_expenditure"));
            actual_weight.setText(object.getString("actual_weight"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
