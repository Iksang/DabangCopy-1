package kr.co.tjeit.dabangcopy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjeit.dabangcopy.adapter.SubwayListAdapter;
import kr.co.tjeit.dabangcopy.adapter.UniversityListAdapter;
import kr.co.tjeit.dabangcopy.data.Subway;
import kr.co.tjeit.dabangcopy.data.University;
import kr.co.tjeit.dabangcopy.util.GlobalData;
import kr.co.tjeit.dabangcopy.util.SoundSearcher;

public class RoomSearchActivity extends BaseActivity {


    private android.widget.TabHost searchTabHost;
    private int selectedIndex;
    private android.widget.EditText searchEdt;
    private TabWidget tabs;
    private LinearLayout tab1;
    private LinearLayout tab2;
    private LinearLayout tab3;
    private LinearLayout tab4;
    private FrameLayout tabcontent;

    List<Subway> mDisplaySubwayList = new ArrayList<>();
    private SubwayListAdapter mSubwayListAdapter;
    private android.widget.ListView subwayListView;
    
    List<University> mDisplayUniversityList = new ArrayList<>();
    private UniversityListAdapter mUniversityListAdapter;
    private ListView universityListView;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        

        selectedIndex = getIntent().getIntExtra("index", 0);


        bindViews();
        setupEvents();
        setValues();

    }

    @Override
    public void setupEvents() {

        searchTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tab1")) {
                    searchEdt.setHint("동, 면, 읍 명을 검색하세요.");

                } else if (tabId.equals("tab2")) {
                    searchEdt.setHint("지하철 명을 검색하세요.");

                } else if (tabId.equals("tab3")) {
                    searchEdt.setHint("대학교 명을 검색하세요.");

                } else if (tabId.equals("tab4")) {
                    searchEdt.setHint("단지 명을 검색하세요.");
                }
                searchEdt.setText("");
            }
        });

        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

//            키보드 타이핑 이벤트

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (searchTabHost.getCurrentTab()) {
                    case 0:
                        break;
                    case 1:
                        filterSubwayList(s.toString());
                        searchEdt.getSelectionStart();
                        break;
                    case 2:
                        filterUniversity(s.toString());
                        searchEdt.getSelectionStart();
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        subwayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, RoomListActivity.class);
                intent.putExtra("인근지하철역", mDisplaySubwayList.get(position));
                startActivity(intent);
            }
        });

        universityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, RoomListActivity.class);
                intent.putExtra("인근대학교", mDisplayUniversityList.get(position));
                startActivity(intent);
            }
        });

    }


    private void filterSubwayList(String inputStr) {

//        지하철 역 목록을 필터하는 메쏘드
        mDisplaySubwayList.clear();

//        어떤 지하철 역이 추가되어야 하는가? 가공

//        GlobalData가 가진 모든 지하철역에 대해 하나하나 검사

        for (Subway s : GlobalData.stations) {
//            검사하는 역의 이름이, 입력된 문자로 시작하는지?
            if (SoundSearcher.matchString(s.getStationName(), inputStr)) {
                mDisplaySubwayList.add(s);
            }

//            if(s.getStationName().startsWith(inputStr)){
////                출력용 목록에 현재 검사하던 지하철역을 집어넣음
//                mDisplaySubwayList.add(s);
//            }
        }

        if(mSubwayListAdapter!=null){
            mSubwayListAdapter.notifyDataSetChanged();
        }


    }
    private void filterUniversity(String inputStr) {
        //        지하철 역 목록을 필터하는 메쏘드
        mDisplayUniversityList.clear();

//        어떤 지하철 역이 추가되어야 하는가? 가공

//        GlobalData가 가진 모든 지하철역에 대해 하나하나 검사

        for (University uv : GlobalData.universities) {
//            검사하는 역의 이름이, 입력된 문자로 시작하는지?
            if (SoundSearcher.matchString(uv.getName(), inputStr)) {
                mDisplayUniversityList.add(uv);
            }

//            if(s.getStationName().startsWith(inputStr)){
////                출력용 목록에 현재 검사하던 지하철역을 집어넣음
//                mDisplayUniversityList.add(s);
//            }
        }
        if(mUniversityListAdapter!=null){
            mUniversityListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setValues() {
        makeTabHost();
        searchTabHost.setCurrentTab(selectedIndex);

//        mDisplaySubwayList.addAll(GlobalData.stations);

        mSubwayListAdapter = new SubwayListAdapter(mContext, mDisplaySubwayList);
        subwayListView.setAdapter(mSubwayListAdapter);

//        mDisplayUniversityList.addAll(GlobalData.universities);

        mUniversityListAdapter = new UniversityListAdapter(mContext,mDisplayUniversityList);
        universityListView.setAdapter(mUniversityListAdapter);

    }

    private void makeTabHost() {

//        탭 호스트를 사용하기 위해서는 반드시 setup을 먼저 진행해야함.
        searchTabHost.setup();

//        탭에 들어가는 버튼 (TabSpec)을 생성하는 작업, 구별자(tab1), 표시(지역) 한꺼번에 세팅한다.
        TabHost.TabSpec spec1 = searchTabHost.newTabSpec("tab1").setIndicator("지역");
//        이 버튼이 눌리면 보여질 화면을 달아줌. xml안에 있는 tab1 LinearLayout
        spec1.setContent(R.id.tab1);
//        버튼 / 내용물 설정이 완료된 탭스펙을 탭호스트에 달아줌.
        searchTabHost.addTab(spec1);

        TabHost.TabSpec spec2 = searchTabHost.newTabSpec("tab2").setIndicator("지하철");
        spec2.setContent(R.id.tab2);
        searchTabHost.addTab(spec2);

        TabHost.TabSpec spec3 = searchTabHost.newTabSpec("tab3").setIndicator("대학교");
        spec3.setContent(R.id.tab3);
        searchTabHost.addTab(spec3);

        TabHost.TabSpec spec4 = searchTabHost.newTabSpec("tab4").setIndicator("단지");
        spec4.setContent(R.id.tab4);
        searchTabHost.addTab(spec4);
    }

    @Override
    public void bindViews() {
        this.searchTabHost = (TabHost) findViewById(R.id.searchTabHost);
        this.tabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);
        this.tab4 = (LinearLayout) findViewById(R.id.tab4);
        this.tab3 = (LinearLayout) findViewById(R.id.tab3);
        this.universityListView = (ListView) findViewById(R.id.universityListView);
        this.tab2 = (LinearLayout) findViewById(R.id.tab2);
        this.subwayListView = (ListView) findViewById(R.id.subwayListView);
        this.tab1 = (LinearLayout) findViewById(R.id.tab1);
        this.tabs = (TabWidget) findViewById(android.R.id.tabs);
        this.searchEdt = (EditText) findViewById(R.id.searchEdt);
    }
}
