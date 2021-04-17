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
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.precticeproject.R;

import static android.content.Context.SENSOR_SERVICE;


public class CompassFragment extends Fragment implements SensorEventListener {
    public CompassFragment() {}
    private SensorManager mSensorManager;
    private Sensor mOrientation;
    ImageView img;
    float mCurrentDegree = 0f;

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
}