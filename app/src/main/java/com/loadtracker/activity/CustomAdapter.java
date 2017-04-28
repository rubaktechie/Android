package com.loadtracker.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loadtracker.R;
import com.loadtracker.fragments.OneFragment;

public class CustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    String[] imageId;
    String[] imageI;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context OneFragment, String[] prgmNameList, String[] prgmImages, String[] values) {
        result=prgmNameList;
        context=OneFragment;
        imageId=prgmImages;
        imageI=values;
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
        TextView i;
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
        holder.i=(TextView) rowView.findViewById(R.id.title5);
        holder.tv.setText(result[position]);
        holder.img.setText(result[position]);
        holder.f.setText(imageId[position]);
        holder.g.setText(imageId[position]);
        holder.h.setText(imageI[position]);
        holder.i.setText(imageI[position]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}