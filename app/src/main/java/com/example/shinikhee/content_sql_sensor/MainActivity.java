package com.example.shinikhee.content_sql_sensor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String state;
    private TextView tv;
    private SensorManager mSm;
    private Sensor accelerometer;
    private MySQLiteOpenHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new MySQLiteOpenHelper(this, "accele_file.db", null, 1);
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e("SQLite", "데이터베이스를 얻어올 수 없음");
            finish();
        }

        Button accelero_Button = (Button)findViewById(R.id.acceleroButton);
        Button stop_Button = (Button)findViewById(R.id.stopButton);
        tv = (TextView)findViewById(R.id.textView);
        mSm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        accelero_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSm.registerListener(mAcc, mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        stop_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSm.unregisterListener(mAcc);
                tv.setText(selectAll());
            }
        });

    }

    SensorEventListener mAcc = new SensorEventListener(){
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        public void onSensorChanged(SensorEvent event) {
            if(event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE){

            }
            float[] v = event.values;
            insert(v);
        }
    };

    public void insert(float[] v){
        db.execSQL("insert into testTable values(null, " + v[0] +", " + v[1] + ", " + v[2] + ")");

    }

    public String selectAll() {
        Cursor c = db.rawQuery("select * from testTable", null);
        String result = "";
        while(c.moveToNext()) {
            int id = c.getInt(0);
            float v1 = c.getFloat(1);
            float v2 = c.getFloat(2);
            float v3 = c.getFloat(3);
            result += "id: " + id + " X-axis: " + v1 + " Y-axis: " + v2 + " Z-axis: " + v3 + "\n";
        }

        return result;
    }

}
