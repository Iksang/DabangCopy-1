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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        GlobalData.initGlobalData();
        mDisplayRoomArray.addAll(GlobalData.allRooms);
        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {
        roomListFilterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RoomFilterActivity.class);
                startActivityForResult(intent,REQ_CODE_ROOMFILTER);
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
        if(requestCode == REQ_CODE_ROOMFILTER){
            if(resultCode == RESULT_OK){
                isMonthPay = data.getBooleanExtra("월세선택",true);
                isChaterPay= data.getBooleanExtra("전세선택",true);

                mDisplayRoomArray.clear();

                if(isMonthPay){
                    for(Room rm : GlobalData.allRooms){
                        if(rm.getRentPay()>0){
                            mDisplayRoomArray.add(rm);
                        }
                    }
                }

                if(isChaterPay){
                    for(Room rm : GlobalData.allRooms){
                        if(rm.getRentPay()==0){
                            mDisplayRoomArray.add(rm);
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    public void setValues() {

        mAdapter = new RoomListAdapter(mContext,mDisplayRoomArray);
        roomListView.setAdapter(mAdapter);

    }

    @Override
    public void bindViews() {
        this.roomListView = (ListView) findViewById(R.id.roomListView);
        this.roomListFilterImg = (ImageView) findViewById(R.id.roomListFilterImg);

    }
}
