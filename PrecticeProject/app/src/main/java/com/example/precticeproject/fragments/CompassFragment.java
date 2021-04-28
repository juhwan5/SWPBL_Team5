package com.example.precticeproject.fragments;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.precticeproject.R;

import static android.content.Context.SENSOR_SERVICE;
import static com.example.precticeproject.functions.FindAltAz.findAltAz;


public class CompassFragment extends Fragment implements SensorEventListener {
    public CompassFragment() {}
    private SensorManager mSensorManager;
    private Sensor mOrientation;
    ImageView img;
    float mCurrentDegree = 0f;
    TextView text1;
    Spinner meteorSpinner;
    Spinner citySpinner;
    Spinner timeSpinner;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mOrientation,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v =  inflater.inflate(R.layout.compass_frag,container,false);

        img = (ImageView) v.findViewById(R.id.compass_img);
        text1 = (TextView) v.findViewById(R.id.print_altaz);

        Button b1 = (Button) v.findViewById(R.id.print_altaz_btn);
        b1.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printAltAz(v);
            }
        }));

        meteorSpinner = (Spinner)v.findViewById(R.id.meteor);
        citySpinner = (Spinner)v.findViewById(R.id.city);
        timeSpinner = (Spinner)v.findViewById(R.id.time);

        meteorSpinner.setAdapter(settingAdapter(R.array.meteorshower));
        citySpinner.setAdapter(settingAdapter(R.array.cities));
        timeSpinner.setAdapter(settingAdapter(R.array.times));

        return v;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
            imgRotation(event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){}

    public void imgRotation (float azimuthDegree) {
        RotateAnimation ra = new RotateAnimation(mCurrentDegree, -azimuthDegree,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);

        ra.setFillAfter(true);
        img.startAnimation(ra);
        mCurrentDegree = -azimuthDegree;
    }


    public ArrayAdapter<CharSequence> settingAdapter (int array ){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }


    public void printAltAz (View v){
        String altaz[];
        String m = meteorSpinner.getSelectedItem().toString();
        String c = citySpinner.getSelectedItem().toString();
        String t = timeSpinner.getSelectedItem().toString();

        altaz = findAltAz(getActivity(),m,c,t);
        text1.setText("방위각: " + altaz[0] +", 고도: " + altaz[1]);
    }

}