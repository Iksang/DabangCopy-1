package kr.co.tjeit.dabangcopy;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kr.co.tjeit.dabangcopy.adapter.PhotoViewPagerAdapter;
import kr.co.tjeit.dabangcopy.data.Room;

public class ViewRoomDetailActivity extends BaseActivity implements OnMapReadyCallback {

    private Room mRoom = null;
    PhotoViewPagerAdapter mPhotoAdapter;

    private android.support.v4.view.ViewPager photosViewPager;
    private android.widget.TextView rentPayTypeTxt1;
    private android.widget.TextView rentPayTxt1;
    private android.widget.TextView roomTypeTxt;
    private android.widget.TextView roomSizeTxt;
    private android.widget.TextView floorNumTxt;
    private android.widget.TextView managePayTxt1;
    private android.widget.TextView descriptionTxt;
    private android.widget.TextView rentPayTypeTxt2;
    private android.widget.TextView rentPayTxt2;
    private android.widget.TextView managePayTxt2;
    private TextView pageIndicator;
    private TextView descriptionTxt1;
    private TextView descriptionTxt2;
    private com.github.mikephil.charting.charts.RadarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room_detail);



        mRoom = (Room) getIntent().getSerializableExtra("선택된 방 정보");
        bindViews();
        setupEvents();
        setValues();

    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

//        지도 프래그먼트를 불러오는 부분
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mPhotoAdapter = new PhotoViewPagerAdapter(mContext, mRoom.getPhotoURLs());
        photosViewPager.setAdapter(mPhotoAdapter);

        if (mRoom.getRentPay() == 0) {
            rentPayTypeTxt1.setText("전세");
            rentPayTypeTxt2.setText("전세");
            int uk = mRoom.getDeposit() / 10000;
            int thousands = mRoom.getDeposit() % 10000;
            String payStr;
            if (uk > 0) {
                payStr = String.format(Locale.KOREA, "%d억%d", uk, thousands);
            } else if (uk == 0) {
                payStr = String.format(Locale.KOREA, "%d억", uk);
            } else {
                payStr = String.format(Locale.KOREA, "%d", thousands);
            }
            rentPayTxt1.setText(payStr);
            rentPayTxt2.setText(payStr);
        } else {
            rentPayTypeTxt1.setText("월세");
            rentPayTypeTxt2.setText("월세");
//          Adapter는 getResources를 바로 활용할수 없어서, mContext에게 대신 가져오도록 시킴
            String payStr = String.format(Locale.KOREA, "%d/%d", mRoom.getDeposit(), mRoom.getRentPay());
            rentPayTxt1.setText(payStr);
            rentPayTxt2.setText(payStr);
        }


        if (mRoom.getRoomCount() == 1) {
            roomTypeTxt.setText("원룸");
        } else if (mRoom.getRoomCount() == 2) {
            roomTypeTxt.setText("투룸");
        } else if (mRoom.getRoomCount() == 3) {
            roomTypeTxt.setText("쓰리룸");
        }

        if (mRoom.getStairCount() == 0) {
            floorNumTxt.setText(String.format(Locale.KOREA, "반지하", mRoom.getStairCount()));
        } else if (mRoom.getStairCount() < 0) {
            floorNumTxt.setText(String.format(Locale.KOREA, "지하%d층", -mRoom.getStairCount()));
        } else {
            floorNumTxt.setText(String.format(Locale.KOREA, "%d층", mRoom.getStairCount()));
        }

        roomSizeTxt.setText(String.format("%.1f㎡", mRoom.getRoomsize()));

        if (mRoom.getManagePay() == 0) {
            managePayTxt1.setText("관리비 없음");
            managePayTxt2.setText("없음");
        } else {
            managePayTxt1.setText("관리비 " + mRoom.getManagePay());
            managePayTxt2.setText("관리비 " + mRoom.getManagePay());
        }

        descriptionTxt1.setText(mRoom.getDescription());



        makeRadarChart();
    }

    private void makeRadarChart() {

//      chart = (RadarChart) findViewById(R.id.chart);
        chart.setBackgroundColor(Color.rgb(60, 65, 82));

        chart.getDescription().setEnabled(false);

        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);


        setData();

        chart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"가격", "관리비", "옵션", "편의시설", "교통"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);

    }

    @Override
    public void bindViews() {
        this.descriptionTxt2 = (TextView) findViewById(R.id.descriptionTxt2);
        this.chart = (RadarChart) findViewById(R.id.chart);
        this.managePayTxt2 = (TextView) findViewById(R.id.managePayTxt2);
        this.rentPayTxt2 = (TextView) findViewById(R.id.rentPayTxt2);
        this.rentPayTypeTxt2 = (TextView) findViewById(R.id.rentPayTypeTxt2);
        this.descriptionTxt1 = (TextView) findViewById(R.id.descriptionTxt1);
        this.managePayTxt1 = (TextView) findViewById(R.id.managePayTxt1);
        this.floorNumTxt = (TextView) findViewById(R.id.floorNumTxt);
        this.roomSizeTxt = (TextView) findViewById(R.id.roomSizeTxt);
        this.roomTypeTxt = (TextView) findViewById(R.id.roomTypeTxt);
        this.rentPayTxt1 = (TextView) findViewById(R.id.rentPayTxt1);
        this.rentPayTypeTxt1 = (TextView) findViewById(R.id.rentPayTypeTxt1);
        this.pageIndicator = (TextView) findViewById(R.id.pageIndicator);
        this.photosViewPager = (ViewPager) findViewById(R.id.photosViewPager);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng roomPoint = new LatLng(mRoom.getLatitude(), mRoom.getLongitude());

//        지도 좌표를 옴기는 작업
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(roomPoint));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        MarkerOptions roomMarker = new MarkerOptions();
        roomMarker.position(roomPoint);
        roomMarker.title("방의 위치");
        roomMarker.snippet("좋은 방이에요.");


//        만들어낸 마커를 실제로 달아준다.
        googleMap.addMarker(roomMarker);


    }

    public void setData() {

        float mult = 80;
        float min = 20;
        int cnt = 5;

        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> entries2 = new ArrayList<RadarEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mult) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mult) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "이 지역 평균");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "이 방");
        set2.setColor(Color.parseColor("#5E90F3"));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set2);
        sets.add(set1);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();
    }
}
