package com.example.alepapadop.egadgetseap;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.List;


public class GadgetRemoteControl extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<DeviceData>>{

    private DeviceData device_data = null;
    private Toast _toast;
    private TextView _tv;
    private Switch _sw;
    private SeekBar _sb;
    private TextView _tv_device_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gadget_remote_control);

        MyLogger.get_logger().logger_clear_all();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            device_data = Model.get_model().find_device_data(extras.getInt(Constants.EXTRAS_DEVICE_DATA_ID));

        }


        _tv = (TextView) findViewById(R.id.brightness_level);
        _sw = (Switch) findViewById(R.id.switch1);
        _sb = (SeekBar) findViewById(R.id.seekBar);
        _tv_device_name = (TextView) findViewById(R.id.devine_name);

        _sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                progress = ((int)Math.round(progress/device_data.get_brightness_step()))*device_data.get_brightness_step();
                seekBar.setProgress(progress);
                _tv.setText(((Integer) progress).toString() + "%");

            }
        });

        if (savedInstanceState == null) {
            _toast = Toast.makeText(this, getResources().getString(R.string.downloading_data), Toast.LENGTH_LONG);
            _toast.show();

            getLoaderManager().initLoader(0, null, this).forceLoad();
        } else {
            getLoaderManager().initLoader(0, null, this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_e_gadget_remote_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        MyLogger.get_logger().logger_clear_all();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        } else if (id == R.id.action_refresh2) {

            _toast = Toast.makeText(this, getResources().getString(R.string.downloading_data), Toast.LENGTH_LONG);
            _toast.show();

            getLoaderManager().restartLoader(0, null, this).forceLoad();

            return true;
        } else if (id == R.id.action_set) {

            _toast = Toast.makeText(this, getResources().getString(R.string.sending_data), Toast.LENGTH_LONG);
            _toast.show();

            getLoaderManager().restartLoader(1, null, this).forceLoad();

            getLoaderManager().restartLoader(2, null, this).forceLoad();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<DeviceData>> onCreateLoader(int id, Bundle args) {

        if (id == 0) {

            return new MyAsyncTaskLoader(this, Constants.AsynTaskAction.GET_DEVICE_STATUS, device_data.get_device_id(), 0, 0);
        } else if (id == 1) {

            int value = device_data.get_power_min();
            if (_sw.isChecked() == true) {
                value = device_data.get_power_max();
            }

            return new MyAsyncTaskLoader(this, Constants.AsynTaskAction.SET_DEVICE_STATUS, device_data.get_device_id(), device_data.get_power_state_id(), value);
        } else if (id == 2) {

            return new MyAsyncTaskLoader(this, Constants.AsynTaskAction.SET_DEVICE_STATUS, device_data.get_device_id(), device_data.get_brightness_state_id(), _sb.getProgress());
        } else {

            new AssertionError("loader id not supported");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<DeviceData>> loader, List<DeviceData> data) {
        if (_toast != null) {
            _toast.cancel();
        }

        if (loader.getId() == 0) {

            _tv_device_name.setText(device_data.get_device_name());

            Boolean bool = false;
            if (device_data.get_power_current() == device_data.get_power_max()) {
                bool = true;
            }
            _sw.setChecked(bool);

            _tv.setText(device_data.get_brightness_current().toString() + "%");

            _sb.setMax(device_data.get_brightness_max());
            _sb.setProgress(device_data.get_brightness_current());

        } else  if (loader.getId() == 1) {

            Boolean bool = false;
            if (device_data.get_power_current() == device_data.get_power_max()) {
                bool = true;
            }
            _sw.setChecked(bool);

        } else if (loader.getId() == 2) {

            _tv.setText(device_data.get_brightness_current().toString() + "%");

            _sb.setMax(device_data.get_brightness_max());

            _sb.setProgress(device_data.get_brightness_current());

        } else {

            new AssertionError("loader id is wrong");
        }

        my_toast(MyLogger.get_logger().get_logger_string(loader.getId()));

    }

    @Override
    public void onLoaderReset(Loader<List<DeviceData>> loader) {

    }

    private void my_toast(String str) {

        if (!str.equals("")) {
            str = "ALERT: Data is not updated\n" + str;
            _toast = Toast.makeText(this, str, Toast.LENGTH_LONG);
            _toast.show();

            /*LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    (ViewGroup) findViewById(R.id.toast_layout_root));

            TextView text = (TextView) layout.findViewById(R.id.textView);
            text.setText(str);

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();*/

        }
    }
}
