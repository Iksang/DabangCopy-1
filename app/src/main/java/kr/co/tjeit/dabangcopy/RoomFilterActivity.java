package kr.co.tjeit.dabangcopy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Locale;

import io.apptik.widget.MultiSlider;

public class RoomFilterActivity extends BaseActivity {

    private android.widget.Button okBtn;
    private android.widget.ToggleButton selectMonthPayBtn;
    private android.widget.ToggleButton selectCharterPayBtn;
    private ToggleButton selectOneRoomBtn;
    private ToggleButton selectTwoRoomBtn;
    private ToggleButton selectThreeRoomBtn;
    private io.apptik.widget.MultiSlider depositSlider;
    private android.widget.TextView depositRange;
    private int depositSelectedMin = 0;
    private int depositSelectedMax = 500000000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_filter);



        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        depositSlider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                if(thumbIndex == 0){
                    depositSelectedMin = thumb.getValue() * 500;
                }
                else if(thumbIndex == 1){
                    depositSelectedMax = thumb.getValue() * 500;
                }

                int minUK = depositSelectedMin / 10000;
                int minThousands = depositSelectedMin % 10000;
                int maxUK = depositSelectedMax / 10000;
                int maxThousands = depositSelectedMax % 10000;

                String minDepositStr;
                if(minUK == 0){
                    minDepositStr = String.format(Locale.KOREA,"%d",minThousands);
                }
                else{
                    if(minThousands == 0){
                        minDepositStr = String.format(Locale.KOREA,"%d억",minUK);
                    }
                    else{
                        minDepositStr = String.format(Locale.KOREA,"%d억 %d",minUK,minThousands);
                    }

                }

                String maxDepositStr;
                if(maxUK == 0){
                    maxDepositStr = String.format(Locale.KOREA,"%d",maxThousands);
                }
                else{
                    if(maxThousands == 0){
                        maxDepositStr = String.format(Locale.KOREA,"%d억",maxUK);
                    }
                    else{
                        maxDepositStr = String.format(Locale.KOREA,"%d억 %d",maxUK,maxThousands);
                    }
                }


                depositRange.setText(minDepositStr+" ~ "+maxDepositStr);
            }
        });


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("월세선택", selectMonthPayBtn.isChecked());
                intent.putExtra("전세선택", selectCharterPayBtn.isChecked());
                intent.putExtra("원룸선택", selectOneRoomBtn.isChecked());
                intent.putExtra("투룸선택", selectTwoRoomBtn.isChecked());
                intent.putExtra("쓰리룸선택", selectThreeRoomBtn.isChecked());
                intent.putExtra("보증금최소금액", depositSelectedMin);
                intent.putExtra("보증금최대금액", depositSelectedMax);

                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

    @Override
    public void setValues() {
        depositSlider.setMin(0);
        depositSlider.setMax(100);
    }

    @Override
    public void bindViews() {
        this.selectThreeRoomBtn = (ToggleButton) findViewById(R.id.selectThreeRoomBtn);
        this.selectTwoRoomBtn = (ToggleButton) findViewById(R.id.selectTwoRoomBtn);
        this.selectOneRoomBtn = (ToggleButton) findViewById(R.id.selectOneRoomBtn);
        this.depositSlider = (MultiSlider) findViewById(R.id.depositSlider);
        this.depositRange = (TextView) findViewById(R.id.depositRange);
        this.selectCharterPayBtn = (ToggleButton) findViewById(R.id.selectCharterPayBtn);
        this.selectMonthPayBtn = (ToggleButton) findViewById(R.id.selectMonthPayBtn);
        this.okBtn = (Button) findViewById(R.id.okBtn);

    }
}
