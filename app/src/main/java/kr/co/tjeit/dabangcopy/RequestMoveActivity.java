package kr.co.tjeit.dabangcopy;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RequestMoveActivity extends BaseActivity {


    private android.widget.TextView typeOfMovingTxt1;
    private android.widget.TextView typeOfMovingTxt2;
    private android.widget.TextView typeOfMovingTxt3;
    private android.widget.TextView typeOfMovingTxt4;
    private TextView inputDateTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_move);


        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {
        final TextView[] moveType = {typeOfMovingTxt1,typeOfMovingTxt2,typeOfMovingTxt3,typeOfMovingTxt4};

        View.OnClickListener selectMoveTypeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(TextView tv : moveType){
                    tv.setBackgroundColor(Color.parseColor("#C2C2C2"));
                }
                v.setBackgroundColor(Color.parseColor("#176DE1"));
            }
        };

        typeOfMovingTxt1.setOnClickListener(selectMoveTypeListener);
        typeOfMovingTxt2.setOnClickListener(selectMoveTypeListener);
        typeOfMovingTxt3.setOnClickListener(selectMoveTypeListener);
        typeOfMovingTxt4.setOnClickListener(selectMoveTypeListener);



        inputDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        날짜 => 글자 : SimpleDateFormat

//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String str = String.format(Locale.KOREA, "%4d-%02d-%02d",year,month+1,dayOfMonth);
                        inputDateTxt.setText(str);

                    }
                },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    public void setValues() {

    }

    @Override
    public void bindViews() {
        this.inputDateTxt = (TextView) findViewById(R.id.inputDateTxt);
        this.typeOfMovingTxt4 = (TextView) findViewById(R.id.typeOfMovingTxt4);
        this.typeOfMovingTxt3 = (TextView) findViewById(R.id.typeOfMovingTxt3);
        this.typeOfMovingTxt2 = (TextView) findViewById(R.id.typeOfMovingTxt2);
        this.typeOfMovingTxt1 = (TextView) findViewById(R.id.typeOfMovingTxt1);
    }
}
