package kr.co.tjeit.dabangcopy;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kr.co.tjeit.dabangcopy.util.GlobalData;

public class MainActivity extends BaseActivity {

    private android.widget.LinearLayout homeFragment;
    private android.widget.LinearLayout myProfileFragment;
    private LinearLayout homeBtn;
    private LinearLayout favoriteBtn;
    private LinearLayout mapListBtn;
    private LinearLayout seeMoreBtn;
    private LinearLayout myProfileBtn;
    private LinearLayout favoriteListFragment;
    private LinearLayout mapListFragment;
    private LinearLayout seeMoreFragment;
    private LinearLayout myPofileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        GlobalData.initGlobalData();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "kr.co.tjeit.dabangcopy",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {

        }




        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

//        1. onClickListener 변수화;
//        2. Tag 이용
        final LinearLayout fragments[] = {homeFragment, favoriteListFragment, mapListFragment, myProfileFragment, seeMoreFragment};
        View.OnClickListener fragListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (LinearLayout ll : fragments) {
                    ll.setVisibility(View.GONE);
                }
                fragments[Integer.parseInt(v.getTag().toString())].setVisibility(View.VISIBLE);
            }
        };


        homeBtn.setOnClickListener(fragListener);
        favoriteBtn.setOnClickListener(fragListener);
        mapListBtn.setOnClickListener(fragListener);
        myPofileBtn.setOnClickListener(fragListener);
        seeMoreBtn.setOnClickListener(fragListener);


    }

    @Override
    public void setValues() {

    }

    @Override
    public void bindViews() {
        this.seeMoreBtn = (LinearLayout) findViewById(R.id.seeMoreBtn);
        this.myPofileBtn = (LinearLayout) findViewById(R.id.myPofileBtn);
        this.mapListBtn = (LinearLayout) findViewById(R.id.mapListBtn);
        this.favoriteBtn = (LinearLayout) findViewById(R.id.favoriteBtn);
        this.homeBtn = (LinearLayout) findViewById(R.id.homeBtn);
        this.seeMoreFragment = (LinearLayout) findViewById(R.id.seeMoreFragment);
        this.mapListFragment = (LinearLayout) findViewById(R.id.mapListFragment);
        this.myProfileFragment = (LinearLayout) findViewById(R.id.myProfileFragment);
        this.favoriteListFragment = (LinearLayout) findViewById(R.id.favoriteListFragment);
        this.homeFragment = (LinearLayout) findViewById(R.id.homeFragment);
    }
}
