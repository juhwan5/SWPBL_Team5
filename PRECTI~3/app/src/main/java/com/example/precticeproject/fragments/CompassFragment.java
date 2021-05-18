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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.precticeproject.R;
import com.example.precticeproject.functions.ProcessJSONData;
import com.example.precticeproject.network.CompassRequest;

import org.json.JSONObject;
import org.w3c.dom.Text;

import static android.content.Context.SENSOR_SERVICE;
import static com.example.precticeproject.functions.FindAltAz.findAltAz;


public class CompassFragment extends Fragment implements SensorEventListener {
    public CompassFragment() {}
    private SensorManager mSensorManager;
    private Sensor mOrientation;
    ImageView needleImg,arrowImg;
    float mCurrentDegree = 0f;
    TextView text1;
    TextView text2;
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

        needleImg = (ImageView) v.findViewById(R.id.compass_needle);
        arrowImg = (ImageView) v.findViewById(R.id.compass_arrow);
        text1 = (TextView) v.findViewById(R.id.print_altaz);
        text2 = (TextView) v.findViewById(R.id.display_azimuth);

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
            imgRotation(needleImg, event.values[0]);
            text2.setText("방위각: " + (int)event.values[0] + ", 고도: " + (int)event.values[1] );
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){}

    public void imgRotation (ImageView imageView, float azimuthDegree) {
        RotateAnimation ra = new RotateAnimation(mCurrentDegree, -azimuthDegree,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);

        ra.setFillAfter(true);
        imageView.startAnimation(ra);
        mCurrentDegree = -azimuthDegree;
    }


    public ArrayAdapter<CharSequence> settingAdapter (int array ){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    public void printAltAz (View v){
        String keys[];

        boolean notFail = true;
        String m = meteorSpinner.getSelectedItem().toString();
        String c = citySpinner.getSelectedItem().toString();
        String t = timeSpinner.getSelectedItem().toString();

        keys = findAltAz(m,c,t);

        if (keys[0].equals("fail")){
                notFail = false;
        }


        if(notFail){

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            String[] altaz = ProcessJSONData.processCompassData(jsonResponse.getJSONObject("data"));
                            text1.setText("방위각: " + altaz[1] +", 고도: " + altaz[0]);
                        } else {
                            Toast.makeText(getContext(),"실패", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(),"불능", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            };

            CompassRequest compassRequest = new CompassRequest(keys, responseListener);
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(compassRequest);

        }else{
            Toast.makeText(getContext(),"검색 실패", Toast.LENGTH_SHORT).show();
        }

    }

}