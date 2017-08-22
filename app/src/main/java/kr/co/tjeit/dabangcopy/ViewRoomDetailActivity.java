package kr.co.tjeit.dabangcopy;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

    }

    @Override
    public void bindViews() {
        this.descriptionTxt2 = (TextView) findViewById(R.id.descriptionTxt2);
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
}
