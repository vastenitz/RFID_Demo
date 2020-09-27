package com.google.track;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.track.Utils.CommonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class CardFragment extends Fragment {
    private RecyclerView rcvCard;
    private DatabaseReference mDatabaseReference;
    private CardAdapter mCardAdapter;
    private ArrayList<CardDate> arrAllCardTime = new ArrayList<>();
    private ArrayList<String> arrChooseCardTime = new ArrayList<>();
    private Query query;
    private DatePicker mDatePicker;
    private Button btnGetTime;
    private AutoCompleteTextView inputCardID;
    private TextView tvResult, tvNotFound;
    private SharedPreferences mSharedPreferences;
    private ArrayList<String> arrSuggestCardID = new ArrayList<>();
    private ArrayAdapter suggestAdapter;

    public CardFragment() {
        // Required empty public constructor
    }

    public static CardFragment newInstance() {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("RFID");
        mSharedPreferences = getActivity().getSharedPreferences("RFIDPreference",  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("suggest", "");
        if(!json.isEmpty()) {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            arrSuggestCardID = gson.fromJson(json, type);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_card, container, false);
        rcvCard = convertView.findViewById(R.id.rcv_data);
        mDatePicker = convertView.findViewById(R.id.card_date_picker);
        btnGetTime = convertView.findViewById(R.id.btn_get_card);
        inputCardID = convertView.findViewById(R.id.input_card);
        tvResult = convertView.findViewById(R.id.tv_result);
        tvNotFound = convertView.findViewById(R.id.tv_not_found);
        tvResult.setVisibility(View.GONE);
        tvNotFound.setVisibility(View.GONE);
        rcvCard.setVisibility(View.GONE);

        mCardAdapter = new CardAdapter(arrChooseCardTime);
        suggestAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrSuggestCardID);

        inputCardID.setThreshold(1);
        inputCardID.setAdapter(suggestAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rcvCard.setLayoutManager(mLayoutManager);
        rcvCard.setAdapter(mCardAdapter);

        btnGetTime.setOnClickListener(v -> {
            if (!inputCardID.getText().toString().equals("")) {
                query = mDatabaseReference.child(inputCardID.getText().toString()).orderByKey();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                        Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                        arrAllCardTime.clear();

                        while (iterator.hasNext()) {
                            DataSnapshot next = iterator.next();
                            arrAllCardTime.add(new CardDate(CommonUtils.getDate((Long) next.getValue()), CommonUtils.getTime((Long) next.getValue())));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                String searchDate = mDatePicker.getDayOfMonth() + "";
                int month = mDatePicker.getMonth() + 1;
                searchDate += month > 9 ? month : "0" + month;
                searchDate += mDatePicker.getYear();
                getCardHistoryOfDate(searchDate);
                arrSuggestCardID.add(inputCardID.getText().toString());
                suggestAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrSuggestCardID);
                inputCardID.setAdapter(suggestAdapter);
                Gson gson = new Gson();
                String json = gson.toJson(arrSuggestCardID);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("suggest",json );
                editor.commit();
            } else {
                tvResult.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.VISIBLE);
                rcvCard.setVisibility(View.GONE);
            }
        });

        return convertView;
    }

    private void getCardHistoryOfDate(String searchDate) {
        arrChooseCardTime.clear();
        boolean isFound = false;
        for (int i = 0; i < arrAllCardTime.size(); i++) {
            if (arrAllCardTime.get(i).getCardDay().equals(searchDate)) {
                isFound = true;
                arrChooseCardTime.add(arrAllCardTime.get(i).getCardTime());
            } else if (isFound) {
                break;
            }
        }
        if (arrChooseCardTime.size() > 0) {
            tvResult.setText("History of " + mDatePicker.getDayOfMonth() + "/" + mDatePicker.getMonth() + "/" + mDatePicker.getYear());
            tvResult.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            rcvCard.setVisibility(View.VISIBLE);
        } else {
            tvResult.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.VISIBLE);
            rcvCard.setVisibility(View.GONE);
        }
        mCardAdapter.notifyDataSetChanged();
    }

}
