package com.example.alepapadop.egadgetseap;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<DeviceData>> {


    private ListView lv = null;
    private MyAdapter adapter = null;
    private Toast _toast = null;
    private Integer _pause = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyLogger.get_logger().logger_clear_all();
        new Model();
        adapter = new MyAdapter(this);
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), GadgetRemoteControl.class);
                DeviceData device_data = adapter.getItem(position);
                intent.putExtra(Constants.EXTRAS_DEVICE_DATA_ID, device_data.get_device_id());
                startActivity(intent);

            }
        });

        if (savedInstanceState == null || _pause == 0) {
            _toast = Toast.makeText(this, getResources().getString(R.string.downloading_data), Toast.LENGTH_LONG);
            _toast.show();
            getLoaderManager().initLoader(0, null, this).forceLoad();
        } else {
            adapter.set_data(Model.get_model().get_model_list());
            getLoaderManager().initLoader(0, null, this);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        _pause = 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_list, menu);
        MyLogger.get_logger().logger_clear_all();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_refresh) {
            _toast = Toast.makeText(this, getResources().getString(R.string.downloading_data), Toast.LENGTH_LONG);
            _toast.show();
            getLoaderManager().restartLoader(0, null, this).forceLoad();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<List<DeviceData>> onCreateLoader(int id, Bundle args) {
        return new MyAsyncTaskLoader(this, Constants.AsynTaskAction.GET_DEVICES, 0, 0 ,0);
    }

    @Override
    public void onLoadFinished(Loader<List<DeviceData>> loader, List<DeviceData> data) {

        if (_toast != null) {
            _toast.cancel();
        }
        adapter.clear();

        if (data.size() > 0) {
            adapter.set_data(data);
            adapter.notifyDataSetChanged();
        } else {
            _toast = Toast.makeText(this, getResources().getString(R.string.no_data), Toast.LENGTH_LONG);
        }

        my_toast(MyLogger.get_logger().get_logger_string(loader.getId()));
    }

    @Override
    public void onLoaderReset(Loader<List<DeviceData>> loader) {
        adapter.set_data(null);
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
