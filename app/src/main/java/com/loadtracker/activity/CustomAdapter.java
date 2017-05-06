package com.loadtracker.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loadtracker.R;
import com.loadtracker.fragments.OneFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomAdapter extends BaseAdapter{
    public String [] result;
    public Context context;
    public String [] imageId;
    public String [] imageI;
    public String [] imageII;
    public String [] offer_rate;
    public JSONObject [] allvalues ;
    public String fragment_class1;

    private static LayoutInflater inflater=null;
    private String jsonResponse;
//    private TextView txtResponse;
    public CustomAdapter(final Context Fragment ,String[] from_city,String[] to_city,String[] product,String[] truck_type,String[] offerrate,JSONObject[] all_values, String fragment_class ) {
        result = from_city;
        imageId= to_city;
        imageI= product;
        imageII = truck_type;
        offer_rate = offerrate;
        context=Fragment;
        allvalues = all_values;
        fragment_class1 = fragment_class;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        TextView img;
        TextView f;
        TextView g;
        TextView h;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_row, null);
        holder.tv=(TextView) rowView.findViewById(R.id.title0);
        holder.img=(TextView) rowView.findViewById(R.id.title1);
        holder.f=(TextView) rowView.findViewById(R.id.title2);
        holder.g=(TextView) rowView.findViewById(R.id.title3);
        holder.h=(TextView) rowView.findViewById(R.id.title4);
        holder.tv.setText("S: \n"+result[position]);
        holder.img.setText("D: \n"+imageId[position]);
        holder.f.setText(imageI[position]);
        holder.g.setText(imageII[position]);
        holder.h.setText(offer_rate[position]);
        rowView.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch(fragment_class1){
                    case "one_fragment":
                        intent = new Intent(context , viewDetail.class);
                        break;
                    case "two_fragment":
                        intent = new Intent(context , viewDetail2.class);
                        break;
                    case "three_fragment":
                        intent = new Intent(context , viewDetail3.class);
                        break;
                    case "four_fragment" :
                        intent = new Intent(context , viewDetail4.class);
                        break;
                }
                intent.putExtra("array",allvalues[position].toString());
                context.startActivity(intent);
            }
        });
        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v){


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setNeutralButton("Duplicate", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context , EditDetail.class);
                            intent.putExtra("array",allvalues[position].toString());
                            context.startActivity(intent);
                        }
                    });
                    alertDialog.show();
                return true;
            }
        });
        return rowView;
    }

}