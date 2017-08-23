package kr.co.tjeit.dabangcopy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kr.co.tjeit.dabangcopy.R;
import kr.co.tjeit.dabangcopy.data.Room;

/**
 * Created by tjoeun on 2017-08-22.
 */

public class RoomListAdapter extends ArrayAdapter<Room>{

    private Context mContext;
    private List<Room> mList;
    private LayoutInflater inf;



    public RoomListAdapter(Context context, List<Room> list) {
        super(context, R.layout.room_list_item, list);


        mContext = context;
        mList = list;
        inf = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if(row == null) {

            row = inf.inflate(R.layout.room_list_item, null);
        }

        Room data = mList.get(position);

        TextView rentPayTypeTxt = (TextView)row.findViewById(R.id.rentPayTypeTxt);
        TextView rentPayTxt = (TextView)row.findViewById(R.id.rentPayTxt);
        TextView roomTypeTxt = (TextView)row.findViewById(R.id.roomTypeTxt);
        TextView floorNumTxt = (TextView)row.findViewById(R.id.floorNumTxt);
        TextView roomSizeTxt = (TextView)row.findViewById(R.id.roomSizeTxt);
        TextView managePayTxt = (TextView)row.findViewById(R.id.managePayTxt);
        TextView hashTagTxt1 = (TextView)row.findViewById(R.id.hashTagTxt1);
        TextView hashTagTxt2 = (TextView)row.findViewById(R.id.hashTagTxt2);
        TextView descriptionTxt = (TextView)row.findViewById(R.id.descriptionTxt);

        if(data.getRentPay()==0){
            rentPayTypeTxt.setText("전세");
            rentPayTxt.setTextColor(mContext.getResources().getColor(R.color.not_month_pay_color));
//            몇억인지 저장하는 변수
//            보증금을 만으로 나누면, 몇억인지 구해짐.
            int uk = data.getDeposit()/10000;
            int thousands = data.getDeposit()%10000;
            String payStr;
            if(uk>0){
                payStr = String.format(Locale.KOREA, "%d억%d",uk,thousands);
            }
            else if(thousands==0){
                payStr = String.format(Locale.KOREA, "%d억",uk);
            }
            else {
                payStr = String.format(Locale.KOREA, "%d",thousands);
            }
            rentPayTxt.setText(payStr);
        }
        else{
            rentPayTypeTxt.setText("월세");
//          Adapter는 getResources를 바로 활용할수 없어서, mContext에게 대신 가져오도록 시킴
            rentPayTxt.setTextColor(mContext.getResources().getColor(R.color.month_pay_color));
            String payStr = String.format(Locale.KOREA, "%d/%d", data.getDeposit(),data.getRentPay());
            rentPayTxt.setText(payStr);
        }


        if(data.getRoomCount()==1){
            roomTypeTxt.setText("원룸");
        }
        else if (data.getRoomCount()==2){
            roomTypeTxt.setText("투룸");
        }
        else if (data.getRoomCount()==3){
            roomTypeTxt.setText("쓰리룸");
        }

        if(data.getStairCount()==0){
            floorNumTxt.setText(String.format(Locale.KOREA,"반지하",data.getStairCount()));
        }
        else if(data.getStairCount()<0){
            floorNumTxt.setText(String.format(Locale.KOREA,"지하%d층",-data.getStairCount()));
        }
        else{
            floorNumTxt.setText(String.format(Locale.KOREA,"%d층",data.getStairCount()));
        }

        roomSizeTxt.setText(String.format("%.1f㎡",data.getRoomsize()));

        if(data.getManagePay()==0){
            managePayTxt.setText("관리비 없음");
        }
        else{
            managePayTxt.setText("관리비 "+data.getManagePay()+"만");
        }

        descriptionTxt.setText(data.getDescription());

        return row;
    }

}
