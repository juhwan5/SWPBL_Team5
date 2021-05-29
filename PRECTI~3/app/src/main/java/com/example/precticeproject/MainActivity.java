package com.example.precticeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.precticeproject.fragments.*;

public class MainActivity extends AppCompatActivity {
    TextView title;
    TextView userName;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (TextView)findViewById(R.id.txt_username);
        title = (TextView)findViewById(R.id.page_title);
        listenCall(0);

        Intent intent =getIntent();
        name = intent.getStringExtra("userName");
        userName.setText(name + "님\n환영합니다.");
    }

    public void homeListener (View v){
        listenCall(0);
    }
    public void astroListener (View v){
        listenCall(1);
    }
    public void communityListener (View v){
        listenCall(2);
    }
    public void compassListener (View v){
        listenCall(3);
    }
    public void observeListener (View v){
        listenCall(4);
    }

    public void logoutListener (View v) {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void mytextualListener (View v){
        //Toast.makeText(getApplicationContext(), "미구현", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),MyTextualActivity.class);
        intent.putExtra("username",name);
        startActivity(intent);
    }


    public void listenCall(int i){
        // 버튼이 눌렸을 때 동작들을 묶었습니다.
        String[] tag = {"홈","천문현상","커뮤니티",
                "나침반","천문대","메뉴1"};
        Fragment frag = findFragment(i);

        if(i == 2){
            Bundle bundle = new Bundle();
            bundle.putString("username", name);
            frag.setArguments(bundle);
        }

        fragCall(tag[i],frag);
        title.setText(tag[i]);



        if(i >= tag.length){
            drawerOut();
        }
    }

    public Fragment findFragment(int i){
        //fragCall에 필요한 프레그먼트 객체를 찾는 역할을 합니다.
        switch (i){
            case 0:
                return new HomeFragment();
            case 1:
                return new AstroFragment();
            case 2:
                return new CommunityFragment();
            case 3:
                return new CompassFragment();
            case 4:
                return new ObservatoryFragment();
            default:
                return new Fragment();
        }
    }

    public void fragCall(String tag, Fragment frag){
        //프레그먼트에 화면을 띄우는 역할을 합니다.
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.replace(R.id.frame_container, frag, tag);
        ft.commitAllowingStateLoss();
    }

    public void drawerOut(){
        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawers();
    }
}