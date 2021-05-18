package com.example.precticeproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.precticeproject.ObservatoryListActivity;
import com.example.precticeproject.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

public class ObservatoryFragment extends Fragment implements OnMapReadyCallback {
    //지도 객체 변수
    private MapView mapView;

    public ObservatoryFragment() {
    }

    public static ObservatoryFragment newInstance() {
        ObservatoryFragment fragment = new ObservatoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.observatory_frag,
                container, false);

        mapView = (MapView) rootView.findViewById(R.id.navermap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return rootView;
    }

    public void onMapReady(@NonNull NaverMap naverMap)
    {
        //배경 지도 선택
        naverMap.setMapType(NaverMap.MapType.Satellite);

        //건물 표시
        naverMap.setLayerGroupEnabled(naverMap.LAYER_GROUP_BUILDING, true);

        //위치 및 각도 조정
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(37.941523, 128.368204),   // 위치 지정
                5.4,                                     // 줌 레벨
                3,                                       // 기울임 각도
                10                                     // 방향
        );
        naverMap.setCameraPosition(cameraPosition);

        Marker marker1 = makeMarker("강원도", naverMap);
        Marker marker2 = makeMarker("경상도", naverMap);
        Marker marker3 = makeMarker("전라도", naverMap);
        Marker marker4 = makeMarker("충청도", naverMap);
        Marker marker5 = makeMarker("경기도", naverMap);
        Marker marker6 = makeMarker("서울", naverMap);

    }

    public Marker makeMarker(String key, NaverMap naverMap){
        double[] latlong = getLatLong(key);
        Marker marker = new Marker();

        marker.setPosition(new LatLng(latlong[0], latlong[1]));
        marker.setMap(naverMap);
        marker.setCaptionText(key);
        marker.setOnClickListener(overlay -> {
            Intent intent = new Intent(getActivity(), ObservatoryListActivity.class);
            intent.putExtra("Observatory", getListItem(key));
            startActivity(intent);
            return true;
        });

        return marker;
    }

    public double[] getLatLong(String key){
        double latitude;
        double longitude;

        switch(key){
            case "강원도":
                latitude = 37.941523;
                longitude = 128.368204;
                break;
            case "경상도":
                latitude = 36.052153;
                longitude = 128.707223;
                break;
            case "전라도":
                latitude = 35.755887;
                longitude = 127.110642;
                break;
            case "충청도":
                latitude = 36.732081;
                longitude = 127.145472;
                break;
            case "경기도":
                latitude = 37.241875;
                longitude = 127.255485;
                break;
            case "서울":
                latitude = 37.617247;
                longitude = 126.873528;
                break;
            default:
                latitude = 0;
                longitude = 0;
                break;
        }

        double[] latlong = {latitude,longitude};
        return latlong;
    }

    public String[] getListItem(String key){
        String[] list;

        switch(key){
            case "강원도":
                list = new String[]{"양구국토정중앙천문대", "평창청소년수련원","횡성우리별천문대",
                        "화천청소년수련관","영월별마로천문대","횡성천문인마을"};
                break;
            case "경상도":
                list = new String[]{"거창월성청소년수련원", "김해천문대","부산금련산천문대",
                        "영양반딧불이천문대","영천보현산천문과학관","영천보현산천문대",
                "예천천문우주센터", "한국우주전파관측망 울산전파천문대"};
                break;
            case "전라도":
                list = new String[]{"고흥우주천문과학관", "곡성섬진강천문대","남원하늘별마을만행산천문체험관",
                        "남원항공우주천문대","담양성암천문대","무주 반디별 천문과학관",
                "무주부남천문대","변산 금구원조각공원 천문대","빛고을천문대","순천만천문대","장흥정남진천문과학관"};
                break;
            case "충청도":
                list = new String[]{"국립중앙과학관", "대덕전파천문대","대전만인산푸른학습원",
                        "대전시민천문대","보은서당골 천문대","서산류방택천문기상과학관",
                        "소백산 천문대","제천별새꽃들 자연탐사과학관","조치원을지천문대","진천선두천문대",
                        "천안청소년수련원","청양칠갑산천문대"};
                break;
            case "경기도":
                list = new String[]{"가평자연과별천문대", "가평코스모피아천문대","과천정보과학도서관",
                        "국립과천과학관","군포누리천문대","안성천문대",
                        "양주송암천문대","양평국제천문대","양평중미산천문대","어린이천문대(구둔)",
                        "어린이천문대(분당)","어린이천문대(일산)","여주세종천문대",
                        "의정부과학도서관(천문우주체험실)","평택무봉산청소년수련원"};
                break;
            case "서울":
                list = new String[]{"광진청소년수련관", "국립서울과학관","노원우주학교",
                        "창동청소년문화의집","테코천문대","한국우주전파관측망 연세대학교천문대"};
                break;
            default:
                list = new String[]{"error"};
                break;
        }

        return list;
    }
}



