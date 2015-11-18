package com.example.alepapadop.egadgetseap;

/**
 * Created by alepapadop on 11/12/15.
 */
public class DeviceData {
    private Integer     _device_id;
    private String      _device_name;
    private String      _device_type;
    private Integer     _power_current;
    private Integer     _power_state_id;
    private Integer     _power_max;
    private Integer     _power_min;
    private Integer     _brightness_current;
    private Integer     _brightness_state_id;
    private Integer     _brightness_max;
    private Integer     _brightness_min;
    private Integer     _brightness_step = 5;

    public Integer get_brightness_min() {
        return _brightness_min;
    }

    public void set_brightness_min(Integer _brightness_min) {
        this._brightness_min = _brightness_min;
    }

    public Integer get_brightness_step() {
        return _brightness_step;
    }

    public void set_brightness_step(Integer _brightness_step) {
        this._brightness_step = _brightness_step;
    }

    public Integer get_power_max() {
        return _power_max;
    }

    public void set_power_max(Integer _power_max) {
        this._power_max = _power_max;
    }

    public Integer get_power_min() {
        return _power_min;
    }

    public void set_power_min(Integer _power_min) {
        this._power_min = _power_min;
    }

    public Integer get_brightness_max() {
        return _brightness_max;
    }

    public void set_brightness_max(Integer _brightness_max) {
        this._brightness_max = _brightness_max;
    }

    public Integer get_power_state_id() {
        return _power_state_id;
    }

    public void set_power_state_id(Integer _power_state_id) {
        this._power_state_id = _power_state_id;
    }

    public Integer get_brightness_state_id() {
        return _brightness_state_id;
    }

    public void set_brightness_state_id(Integer _brightness_state_id) {
        this._brightness_state_id = _brightness_state_id;
    }

    public Integer get_brightness_current() {
        return _brightness_current;
    }

    public void set_brightness_current(Integer _brightness_current) {
        this._brightness_current = _brightness_current;
    }

    public Integer get_power_current() {
        return _power_current;
    }

    public void set_power_current(Integer _power_current) {
        this._power_current = _power_current;
    }

    public Integer get_device_id() {
        return _device_id;
    }

    public void set_device_id(Integer _device_id) {
        this._device_id = _device_id;
    }

    public String get_device_name() {
        return _device_name;
    }

    public void set_device_name(String _device_name) {
        this._device_name = _device_name;
    }

    public String get_device_type() {
        return _device_type;
    }

    public void set_device_type(String _device_type) {
        this._device_type = _device_type;
    }


    DeviceData() {
        this._device_id = 0;
        this._device_name = "";
        this._device_type = "";
        this._power_current = 0;
        this._power_state_id = 0;
        this._brightness_current = 0;
        this._brightness_state_id = 0;
        _brightness_max = 0;
        _brightness_min = 0;
        _power_max = 0;
        _power_min = 0;

    }

}
