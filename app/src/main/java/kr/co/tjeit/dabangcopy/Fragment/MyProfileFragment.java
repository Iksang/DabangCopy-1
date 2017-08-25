package kr.co.tjeit.dabangcopy.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.co.tjeit.dabangcopy.MyProfileSettingActivity;
import kr.co.tjeit.dabangcopy.R;

/**
 * Created by tjoeun on 2017-08-25.
 */

public class MyProfileFragment extends Fragment {

    private de.hdodenhof.circleimageview.CircleImageView profileimage;
    private android.widget.LinearLayout callServicecenterBtn;
    private LinearLayout myProfileBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_my_profile, container, false);
        this.myProfileBtn = (LinearLayout) v.findViewById(R.id.myProfileBtn);
        this.callServicecenterBtn = (LinearLayout) v.findViewById(R.id.callServicecenterBtn);
        this.profileimage = (CircleImageView) v.findViewById(R.id.profile_image);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupEvent();
        setValues();

    }

    private void setupEvent() {
        callServicecenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:070-4211-3951"));
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:07042113951"));
                startActivity(intent);
            }
        });
        myProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfileSettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setValues() {
    }

}
