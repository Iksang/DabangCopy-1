package kr.co.tjeit.dabangcopy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import kr.co.tjeit.dabangcopy.R;

/**
 * Created by tjoeun on 2017-08-23.
 */

public class DetailViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater inf;

    public DetailViewPagerAdapter (Context context, List<String> list){
        mContext = context;
        mList = list;
        inf = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View row = inf.inflate(R.layout.photo_detail_item, container, false);

        String url = mList.get(position);

        PhotoView photoView = (PhotoView)row.findViewById(R.id.photo_view);

        Glide.with(mContext).load(url).into(photoView);

//        return super.instantiateItem(container, position);
        container.addView(row);
        return row;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
