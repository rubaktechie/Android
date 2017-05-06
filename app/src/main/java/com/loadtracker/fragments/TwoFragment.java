package com.loadtracker.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loadtracker.R;
import com.loadtracker.activity.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class TwoFragment extends Fragment {
    ListView lv;
    public String [] from_city;
    public String [] to_city ;
    public String [] offerrate;
    public String [] product;
    public String [] truck_type;
    public View v;
    public JSONObject[] all_values;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_one, container, false);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://rubak.cloudapp.net/android/closed.php";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("My_RESPONSE", response.toString());
                        try {
                            JSONArray results = response.getJSONArray("results");
                            from_city = new String[results.length()];
                            to_city = new String[results.length()];
                            product = new String[results.length()];
                            truck_type = new String[results.length()];
                            offerrate = new String[results.length()];
                            all_values = new JSONObject[results.length()];
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject object = results.getJSONObject(i);
                                all_values[i] = object;
                                from_city[i] = object.getString("from_location_name");
                                to_city[i] = object.getString("to_location_name");
                                product[i] = object.getString("product");
                                truck_type[i] = object.getString("truck_type");
                                offerrate[i] = object.getString("offerrate");
                            }
                        }
                        catch (Exception e){
                            String msg = e.getMessage();
                            Toast.makeText(getActivity().getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                        }
                        perform(v);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext() ,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }){
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("status", "inprogress");
                return params;
            }
        };
        queue.add(jsonRequest);
        return v;
    }

    public void perform(View v) {
        lv = (ListView) v.findViewById(R.id.list);
        lv.setAdapter(new CustomAdapter(getActivity(), from_city, to_city, product, truck_type, offerrate, all_values, "two_fragment"));
    }
}
