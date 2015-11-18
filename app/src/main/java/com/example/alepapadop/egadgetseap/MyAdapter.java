package com.example.alepapadop.egadgetseap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

/**
 * Created by alepapadop on 11/10/15.
 */
public class MyAdapter extends ArrayAdapter<DeviceData> {

    private static class ViewHolder {
        TextView _device_name;
    }

    private Context _context = null;
    private List _objects = null;

    public Context get_context() {
        return _context;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }



    public MyAdapter(Context context) {
        super(context, R.layout.row_layout);
    }

    public void set_data(List<DeviceData> device_data) {
        clear();
        if (device_data != null) {
            addAll(device_data);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeviceData device_data = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_layout, parent, false);

            viewHolder._device_name = (TextView) convertView.findViewById(R.id.list_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder._device_name.setText(device_data.get_device_name());

        return convertView;
    }
}
