package kr.co.tjeit.dabangcopy;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjeit.dabangcopy.adapter.DetailViewPagerAdapter;

public class ViewDetailPhotoActivity extends BaseActivity {

    private DetailViewPagerAdapter mAdapter;

    private List<String> mURL = new ArrayList<>();
    private int initNum;
    private android.support.v4.view.ViewPager photosViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_photo);

        mURL = getIntent().getStringArrayListExtra("사진목록");
        initNum = getIntent().getIntExtra("사진번호", 0);
        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {
        mAdapter = new DetailViewPagerAdapter(mContext, mURL);
        photosViewPager.setAdapter(mAdapter);

//        뷰페이저의 현재 페이지를 설정 : setCurrentitem (페이지쪽수, 애니메이션 여부);
        photosViewPager.setCurrentItem(initNum, false);

    }


    @Override
    public void bindViews() {
        this.photosViewPager = (ViewPager) findViewById(R.id.photosViewPager);
    }
}
