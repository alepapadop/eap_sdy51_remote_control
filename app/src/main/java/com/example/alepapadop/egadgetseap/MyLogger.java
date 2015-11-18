package com.example.alepapadop.egadgetseap;

import android.app.LoaderManager;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alepapadop on 11/17/15.
 */
public class MyLogger {

    private final Integer default_logger_key = -1;

    private static MyLogger logger = null;

    private HashMap<Integer, ArrayList<String>>  logs_map = null;

    MyLogger() {
        if (logs_map == null || logs_map.isEmpty()) {
            logs_map = new HashMap<Integer, ArrayList<String>>();
        }
    }

    public static MyLogger get_logger() {

        if (logger == null) {
            logger = new MyLogger();
        }

        return logger;
    }

    public synchronized void logger_add_msg(String msg) {
        ArrayList<String> list = logs_map.get(default_logger_key);
        if (list != null) {
            list.add(msg);

        } else {
            new AssertionError("problem");
        }
    }


    public synchronized void logger_add_msg(Integer key, String msg) {
        ArrayList<String> list = logs_map.get(key);
        if (list != null) {
            list.add(msg);
        } else {
            ArrayList<String> alist = new ArrayList<>();
            alist.add(msg);
            logs_map.put(key, alist);
        }
    }

    public String logger_get_msg(int index) {
        ArrayList<String> list = logs_map.get(default_logger_key);
        if (list != null) {
            return list.get(index);
        } else {
            new AssertionError("problem");
            return null;
        }
    }

    public String logger_get_msg(Integer key, int index) {
        ArrayList<String> list = logs_map.get(key);
        if (list != null) {
            return list.get(index);
        } else {
            new AssertionError("problem");
            return null;
        }
    }

    public int logger_get_msg_num() {
        ArrayList<String> list = logs_map.get(default_logger_key);
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public int logger_get_msg_num(int key) {
        ArrayList<String> list = logs_map.get(key);
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void logger_clear_all() {
        if (!logs_map.isEmpty()) {
            logs_map.clear();
        }
    }

    public String get_logger_string() {
        ArrayList<String> list = logs_map.get(default_logger_key);
        String logs = "";

        if (list != null) {
            for (String str : list) {
                logs.concat(str + "\n");
            }
        } else {
            new AssertionError("probelm");
        }

        return logs;
    }

    public synchronized String get_logger_string(Integer key) {
        ArrayList<String> list = logs_map.get(key);
        String logs = "";

        if (list != null) {
            for (String str : list) {

                logs = logs + str + "\n";
            }
        } else {
            new AssertionError("probelm");
        }
        return logs;
    }

    public void logger_clear() {
        ArrayList<String> list = logs_map.get(default_logger_key);
        if (list != null) {
            logs_map.remove(default_logger_key);
        }
    }

    public void logger_clear(Integer key) {
        ArrayList<String> list = logs_map.get(key);
        if (list != null) {
            logs_map.remove(key);
        }
    }

    public boolean logger_empty() {
        return logs_map.isEmpty();
    }


}
