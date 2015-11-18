package com.example.alepapadop.egadgetseap;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alepapadop on 11/9/15.
 */
public class Model {

    private static Model model = null;
    private ArrayList<DeviceData> _model_list = null;

    public static Model get_model() {
        if (model == null) {
            model = new Model();
        }

        return model;
    }

    Model() {
        if (get_model_list() == null) {
            init_model_list();
        }
    }

    private void init_model_list() {
        _model_list = new ArrayList<>();
    }

    public ArrayList<DeviceData> get_model_list() {
        return _model_list;
    }

    public void model_list_add(DeviceData device_data) {
        get_model_list().add(device_data);
    }

    public DeviceData model_list_index(int index) {

        if (index > get_model_list().size()) {
            return null;
        }

        return get_model_list().get(index);
    }

    public void model_list_clear() {
        get_model_list().clear();
    }

    public DeviceData find_device_data(int id) {
        for (DeviceData device_data : get_model_list()) {
            if (device_data.get_device_id() == id) {
                return device_data;
            }
        }

        return null;
    }

    public void debug_model_list() {
        for (int i = 0; i < get_model_list().size(); ++i) {
            Log.d(Constants.LOG_MODEL, " \nid: " + model_list_index(i).get_device_id().toString() +
                                       " \nname: " + model_list_index(i).get_device_name() +
                                       " \ntype: " + model_list_index(i).get_device_type() +
                                       " \npower status id: " + model_list_index(i).get_power_state_id() +
                                       " \npower current: " + model_list_index(i).get_power_current() +
                                       " \nbrightness status id: " + model_list_index(i).get_brightness_state_id() +
                                       " \nbrightness current: "  + model_list_index(i).get_brightness_current());
        }
    }

    public void debug_model(DeviceData model) {

        Log.d(Constants.LOG_MODEL, " \nid: " + model.get_device_id().toString() +
                " \nname: " + model.get_device_name() +
                " \ntype: " + model.get_device_type() +
                " \npower status id: " + model.get_power_state_id() +
                " \npower current: " + model.get_power_current() +
                " \nbrightness status id: " + model.get_brightness_state_id() +
                " \nbrightness current: "  + model.get_brightness_current());

    }

}
