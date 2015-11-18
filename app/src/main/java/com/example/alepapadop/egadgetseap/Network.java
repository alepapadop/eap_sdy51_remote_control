package com.example.alepapadop.egadgetseap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by alepapadop on 11/9/15.
 */
public class Network {

    private String  _jsonString;

    public String get_jsonString() {
        return _jsonString;
    }

    public void set_jsonString(String jsonString) {
        _jsonString = jsonString;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public int check_connection(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (cm.getActiveNetworkInfo() != null) {
            return 1;
        }
        return 0;
    }


    public synchronized int get_json(String json_url) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(Constants.CONN_TIME_OUT);
                conn.setConnectTimeout(Constants.CONN_TIME_OUT);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                try {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    _jsonString = convertStreamToString(in);

                    Log.d(Constants.LOG_NETWORK, get_jsonString());

                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                } finally {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
        }


        return 1;
    }

    public int parse_json_devices(String json_string, int loader_id) {
        try {

            Model.get_model().model_list_clear();

            JSONObject jsonObject = new JSONObject(json_string);

            JSONObject status_object = jsonObject.getJSONObject(Constants.JSON_STATUS);

            String result = status_object.getString(Constants.JSON_RESULT);

            if (!result.toLowerCase().equals(Constants.JSON_SUCCESS.toLowerCase())) {
                MyLogger.get_logger().logger_add_msg(loader_id, status_object.getString(Constants.JSON_REASON));
                return 0;
            }

            Integer devices_num = status_object.getInt(Constants.JSON_DEVICES);

            JSONArray devices_array = jsonObject.getJSONArray(Constants.JSON_DEVICES);

            for (int i = 0; i < devices_array.length(); ++i) {
                DeviceData device_data = new DeviceData();

                JSONObject json_array_object = devices_array.getJSONObject(i);

                device_data.set_device_id(json_array_object.getInt(Constants.JSON_DEVICE_ID));
                device_data.set_device_name(json_array_object.getString(Constants.JSON_DEVICE_NAME));
                device_data.set_device_type(json_array_object.getString(Constants.JSON_DEVICE_TYPE));

                Model.get_model().model_list_add(device_data);

                //Model.get_model().debug_model(device_data);

            }

            return 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int parse_json_device_status(String json_string, int loader_id) {
        try {

            JSONObject jsonObject = new JSONObject(json_string);

            JSONObject status_object = jsonObject.getJSONObject(Constants.JSON_STATUS);

            String result = status_object.getString(Constants.JSON_RESULT);

            if (!result.toLowerCase().equals(Constants.JSON_SUCCESS.toLowerCase())) {
                MyLogger.get_logger().logger_add_msg(loader_id, status_object.getString(Constants.JSON_REASON));
                return 0;
            }

            Integer device_id = status_object.getInt(Constants.JSON_DEVICE_ID);
            Integer devices_states = status_object.getInt(Constants.JSON_DEVICE_STATES);

            JSONArray devices_array = jsonObject.getJSONArray(Constants.JSON_DEVICE_STATES);

            DeviceData device_data = Model.get_model().find_device_data(device_id);

            if (device_data == null) {
                new AssertionError("device not found");
                return 0;
            }

            for (int i = 0; i < devices_array.length(); ++i) {

                JSONObject json_array_object = devices_array.getJSONObject(i);

                String description = json_array_object.getString(Constants.JSON_DEVICE_DESCRIPTION);
                Integer state_id = json_array_object.getInt(Constants.JSON_DEVIDE_STATE_ID);
                Integer current_value = json_array_object.getInt(Constants.JSON_DEVICE_CURRENT_VALUE);
                Integer device_min = json_array_object.getInt(Constants.JSON_DEVICE_MIN_VALUE);
                Integer device_max = json_array_object.getInt(Constants.JSON_DEVICE_MAX_VALUE);

                if (Constants.STR_BRIGHTNESS.toLowerCase().equals(description.toLowerCase())) {
                    device_data.set_brightness_current(current_value);
                    device_data.set_brightness_state_id(state_id);
                    device_data.set_brightness_max(device_max);
                    device_data.set_brightness_min(device_min);
                } else if (Constants.STR_POWER.toLowerCase().equals(description.toLowerCase())) {
                    device_data.set_power_current(current_value);
                    device_data.set_power_state_id(state_id);
                    device_data.set_power_max(device_max);
                    device_data.set_power_min(device_min);
                } else {
                    new AssertionError("description is not supported");
                }


            }
            //Model.get_model().debug_model(device_data);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    public synchronized int parse_json_set_device_status(String json_string, int loader_id) {
        try {

            JSONObject jsonObject = new JSONObject(json_string);

            JSONObject status_object = jsonObject.getJSONObject(Constants.JSON_STATUS);

            String result = status_object.getString(Constants.JSON_RESULT);

            if (!result.toLowerCase().equals(Constants.JSON_SUCCESS.toLowerCase())) {
                //Log.d("AAAA", "");
                MyLogger.get_logger().logger_add_msg(loader_id, status_object.getString(Constants.JSON_REASON));
                return 0;
            }

            Integer updated_device = status_object.getInt(Constants.JSON_UPDATED_DEVICE);
            Integer devices_state = status_object.getInt(Constants.JSON_DEVICE_STATE);
            Integer new_value = status_object.getInt(Constants.JSON_DEVICE_NEW_VALUE);

            DeviceData device_data = Model.get_model().find_device_data(updated_device);

            if (device_data == null) {
                new AssertionError("device not found");
                return 0;
            }

            if (device_data.get_power_state_id() == devices_state) {
                device_data.set_power_current(new_value);
            } else if (device_data.get_brightness_state_id() == devices_state) {
                device_data.set_brightness_current(new_value);
            } else {
                new AssertionError("device state not supported");
            }

            //DeviceData device_data = Model.get_model().find_device_data(device_id);

            //Model.get_model().debug_model(device_data);

            return 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    Network() {
        set_jsonString("");
    }
}
