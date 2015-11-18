package com.example.alepapadop.egadgetseap;

/**
 * Created by alepapadop on 11/9/15.
 */
public class Constants {
    public static final int SPLASH_TIME_OUT = 1000;

    public static final int CONN_TIME_OUT = 10000;

    private static final String CONN_URL_BASE = "http://150.140.15.50/sdy51/2015/";

    public static final String CONN_URL_GET_DEVICES = CONN_URL_BASE + "get_devices.php";

    public static final String CONN_URL_GET_DEVICE_STATUS = CONN_URL_BASE + "get_device_state.php/id=";

    public static final String CONN_URL_SET_DEVICE_STATUS = CONN_URL_BASE + "change_device_state.php/";

    public static final String LOG_NETWORK = "LOG_NETWORK";

    public static final String LOG_MODEL = "LOG_MODEL";

    public enum AsynTaskAction {
        GET_DEVICES,
        GET_DEVICE_STATUS,
        SET_DEVICE_STATUS
    }

    public static final String JSON_SUCCESS = "Success";

    public static final String JSON_STATUS = "status";
    public static final String JSON_RESULT = "result";
    public static final String JSON_DEVICES = "devices";
    public static final String JSON_DEVICE_ID = "device_id";
    public static final String JSON_DEVICE_NAME = "device_name";
    public static final String JSON_DEVICE_TYPE = "device_type";
    public static final String JSON_DEVICE_STATES = "states";
    public static final String JSON_DEVICE_DESCRIPTION = "description";
    public static final String JSON_DEVIDE_STATE_ID = "state_id";
    public static final String JSON_DEVICE_CURRENT_VALUE = "current_value";
    public static final String JSON_DEVICE_MIN_VALUE = "min_value";
    public static final String JSON_DEVICE_MAX_VALUE = "max_value";
    public static final String JSON_REASON = "reason";
    public static final String JSON_UPDATED_DEVICE = "updated_device";
    public static final String JSON_DEVICE_STATE = "state";
    public static final String JSON_DEVICE_NEW_VALUE = "new_value";

    public static final String EXTRAS_DEVICE_DATA_ID = "extras_device_data_id";

    public static final String STR_POWER = "Power";
    public static final String STR_BRIGHTNESS = "Brightness";

}
