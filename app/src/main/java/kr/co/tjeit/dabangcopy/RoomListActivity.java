package kr.co.tjeit.dabangcopy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjeit.dabangcopy.adapter.RoomListAdapter;
import kr.co.tjeit.dabangcopy.data.Room;
import kr.co.tjeit.dabangcopy.data.Subway;
import kr.co.tjeit.dabangcopy.data.University;
import kr.co.tjeit.dabangcopy.util.GlobalData;

public class RoomListActivity extends BaseActivity {


    private final int REQ_CODE_ROOMFILTER = 1;

    private android.widget.ListView roomListView;
    //    필터되서 출력될수 있도록 지원해주는 출력용 리스트
    private List<Room> mDisplayRoomArray = new ArrayList<>();
    private RoomListAdapter mAdapter;
    private android.widget.ImageView roomListFilterImg;
    private boolean isMonthPay = true;
    private boolean isChaterPay = true;
    private boolean isOneRoom = true;
    private boolean isTwoRoom = true;
    private boolean isThreeRoom = true;
    private int depositSelectedMin = 0;
    private int depositSelectedMax = 500000000;


    private Subway mSubway = null;

    private University mUniversity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        mDisplayRoomArray.addAll(GlobalData.allRooms);
        mSubway =(Subway)getIntent().getSerializableExtra("인근지하철역");
        mUniversity = (University)getIntent().getSerializableExtra("인근대학교");
        bindViews();
        setupEvents();
        setValues();
        filterRoomList();
    }

    @Override
    public void setupEvents() {
        roomListFilterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RoomFilterActivity.class);
                startActivityForResult(intent, REQ_CODE_ROOMFILTER);
            }
        });


        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, ViewRoomDetailActivity.class);
                intent.putExtra("선택된 방 정보", mDisplayRoomArray.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_ROOMFILTER) {
            if (resultCode == RESULT_OK) {
                isMonthPay = data.getBooleanExtra("월세선택", true);
                isChaterPay = data.getBooleanExtra("전세선택", true);
                isOneRoom = data.getBooleanExtra("원룸선택", true);
                isTwoRoom = data.getBooleanExtra("투룸선택", true);
                isThreeRoom= data.getBooleanExtra("쓰리룸선택", true);
                depositSelectedMin = data.getIntExtra("보증금최소금액", 0);;
                depositSelectedMax = data.getIntExtra("보증금최대금액", 500000000);;

                filterRoomList();


            }
        }
    }

    private void filterRoomList() {

        mDisplayRoomArray.clear();

        boolean isPaymentOK = false;
        boolean isRoomCountOK = false;
        boolean isDepositOk = false;
        boolean isNearSubway = false;
        boolean isNearUniversity = false;


        for (Room rm : GlobalData.allRooms) {
            if (isMonthPay && rm.getRentPay() > 0) {
                isPaymentOK = true;
            }
            if (isChaterPay && rm.getRentPay() == 0) {
                isPaymentOK = true;
            }

            if(isOneRoom && rm.getRoomCount() == 1){
                isRoomCountOK = true;
            }
            if(isTwoRoom && rm.getRoomCount() == 2){
                isRoomCountOK = true;
            }
            if(isThreeRoom && rm.getRoomCount() == 3){
                isRoomCountOK = true;
            }

            if(depositSelectedMin <= rm.getDeposit() &&  rm.getDeposit() <= depositSelectedMax){
                isDepositOk = true;
            }



            if(mSubway != null){
                if(rm.getNearStations().contains(mSubway)){
                    isNearSubway = true;
                }
            }
            else{
                isNearSubway = true;
            }




            if(mUniversity != null){
                if(rm.getNearUniversities().contains(mUniversity)){
                    isNearUniversity = true;
                }
            }
            else {
                isNearUniversity=true;
            }


            if(isPaymentOK&&isRoomCountOK&&isDepositOk&&isNearSubway&&isNearUniversity){
                mDisplayRoomArray.add(rm);

            }
            isPaymentOK = false;
            isRoomCountOK = false;
            isDepositOk = false;
            isNearSubway = false;
            isNearUniversity = false;
        }

        mAdapter.notifyDataSetChanged();


    }
    private void filterSubwayRoom() {

        mDisplayRoomArray.clear();


        for(Room rm : GlobalData.allRooms){
            if(rm.getNearStations().contains(mSubway)){
                mDisplayRoomArray.add(rm);
            }
        }
    }


    @Override
    public void setValues() {


        mAdapter = new RoomListAdapter(mContext, mDisplayRoomArray);

        roomListView.setAdapter(mAdapter);

    }



    @Override
    public void bindViews() {
        this.roomListView = (ListView) findViewById(R.id.roomListView);
        this.roomListFilterImg = (ImageView) findViewById(R.id.roomListFilterImg);

    }
}
