package com.example.alepapadop.egadgetseap;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by alepapadop on 11/12/15.
 */
public class MyAsyncTaskLoader extends AsyncTaskLoader<List<DeviceData>> {

    Constants.AsynTaskAction    _action;
    Integer                     _device_id;
    Integer                     _device_state;
    Integer                     _device_value;

    public MyAsyncTaskLoader(Context context, Constants.AsynTaskAction action, Integer id, Integer state, Integer value) {
        super(context);
        _action = action;
        _device_id = id;
        _device_state = state;
        _device_value = value;
    }


    @Override
    public List<DeviceData> loadInBackground() {
        Network network = new Network();
        String url;

        switch (_action) {
            case GET_DEVICES:
                if (network.check_connection(getContext()) == 1) {
                    network.get_json(Constants.CONN_URL_GET_DEVICES);
                    if (!network.get_jsonString().equals("")) {
                        network.parse_json_devices(network.get_jsonString(), getId());
                    } else {
                        MyLogger.get_logger().logger_add_msg(getId(), getContext().getString(R.string.connection_error));
                    }
                } else {
                    MyLogger.get_logger().logger_add_msg(getId(), getContext().getString(R.string.no_connection));
                }
                break;
            case GET_DEVICE_STATUS:
                url = Constants.CONN_URL_GET_DEVICE_STATUS + "?id=" + _device_id.toString();
                if (network.check_connection(getContext()) == 1) {
                    network.get_json(url);
                    if (!network.get_jsonString().equals("")) {
                        network.parse_json_device_status(network.get_jsonString(), getId());
                    } else {
                        MyLogger.get_logger().logger_add_msg(getId(), getContext().getString(R.string.connection_error));
                    }
                } else {
                    MyLogger.get_logger().logger_add_msg(getId(), getContext().getString(R.string.no_connection));
                }
                break;
            case SET_DEVICE_STATUS:
                url = Constants.CONN_URL_SET_DEVICE_STATUS + "?device=" + _device_id.toString() + "&state=" + _device_state.toString() + "&value=" + _device_value.toString();
                if (network.check_connection(getContext()) == 1) {
                    network.get_json(url);
                    if (!network.get_jsonString().equals("")) {
                        network.parse_json_set_device_status(network.get_jsonString(), getId());
                    } else {
                        MyLogger.get_logger().logger_add_msg(getId(), getContext().getString(R.string.connection_error));
                    }
                } else {
                    MyLogger.get_logger().logger_add_msg(getId(), getContext().getString(R.string.no_connection));
                }
                break;
            default:
                new AssertionError("loader background action not supported");
                break;
        }

        return Model.get_model().get_model_list();
    }



}
