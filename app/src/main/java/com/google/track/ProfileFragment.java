package com.google.track;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private TextView tvName, tvBirthday, tvClass, tvSchool, tvCheckInTime, tvCheckoutTime, tvWaterCup, tvHeight, tvWeight;
    private DatabaseReference mDatabaseReference;
    private String formatDate;
    private ImageView imgProfile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Date dateObj = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        formatDate = df.format(dateObj);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("user");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_profile, container, false);
        tvName = convertView.findViewById(R.id.tv_name);
        tvBirthday = convertView.findViewById(R.id.tv_birthday);
        tvSchool = convertView.findViewById(R.id.tv_school);
        tvClass = convertView.findViewById(R.id.tv_class);
        tvCheckInTime = convertView.findViewById(R.id.tv_check_in_time);
        tvCheckoutTime = convertView.findViewById(R.id.tv_check_out_time);
        tvWaterCup = convertView.findViewById(R.id.tv_water_cup);
        tvHeight = convertView.findViewById(R.id.tv_height);
        tvWeight = convertView.findViewById(R.id.tv_weight);
        imgProfile = convertView.findViewById(R.id.img_profile);

        mDatabaseReference.child(formatDate).child("user1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfo mUser = dataSnapshot.getValue(UserInfo.class);
                if (mUser == null) {
                    return;
                }

                tvName.setText(mUser.getName());
                tvBirthday.setText(mUser.getBirthday());
                tvClass.setText(mUser.getClassName());
                tvHeight.setText(mUser.getHeight() + " cm");
                tvWeight.setText((int)(mUser.getWeight()) + " kg");
                tvCheckInTime.setText(mUser.getCheckInTime());
                tvCheckoutTime.setText(mUser.getCheckOutTime());
                tvWaterCup.setText(mUser.getCupOfWater() + "");
                tvSchool.setText(mUser.getSchool());
                Glide.with(getView())
                        .load(mUser.getUrl())
                        .placeholder(R.drawable.ic_person)
                        .error(R.drawable.ic_person)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imgProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Unable to load data", Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
