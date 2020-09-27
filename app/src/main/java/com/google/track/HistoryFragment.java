package com.google.track;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements View.OnClickListener {
    protected final String[] months = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    private final int count = 12;
    private ImageView imgShowHeightChart, imgShowTemperatureCheckChart, imgHideHeightChart, imgHideTemperatureCheckChart, imgShowWeightChart, imgHideWeightChart;
    private CombinedChart weightChart, heightChart, temperatureChart;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_history, container, false);
        weightChart = convertView.findViewById(R.id.weight_chart);
        heightChart = convertView.findViewById(R.id.height_chart);
        temperatureChart = convertView.findViewById(R.id.temperature_chart);
        imgShowHeightChart = convertView.findViewById(R.id.img_show_height_chart);
        imgHideHeightChart = convertView.findViewById(R.id.img_hide_height_chart);
        imgShowWeightChart = convertView.findViewById(R.id.img_show_weight_chart);
        imgHideWeightChart = convertView.findViewById(R.id.img_hide_weight_chart);
        imgShowTemperatureCheckChart = convertView.findViewById(R.id.img_show_temperature_chart);
        imgHideTemperatureCheckChart = convertView.findViewById(R.id.img_hide_temperature_chart);
        imgShowHeightChart.setOnClickListener(this);
        imgHideHeightChart.setOnClickListener(this);
        imgShowTemperatureCheckChart.setOnClickListener(this);
        imgHideTemperatureCheckChart.setOnClickListener(this);
        imgShowWeightChart.setOnClickListener(this);
        imgHideWeightChart.setOnClickListener(this);
        setUpCombinedChart(weightChart);
        setUpCombinedChart(heightChart);
        setUpCombinedChart(temperatureChart);
        return convertView;
    }

    private void setUpCombinedChart(CombinedChart chart) {
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);

        chart.setDrawOrder(new DrawOrder[]{
                DrawOrder.BAR, DrawOrder.LINE
        });

        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return months[(int) value % months.length];
            }
        });
        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateBarData());

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        chart.setData(data);
        chart.invalidate();
    }

    private BarData generateBarData() {
        ArrayList<BarEntry> entries1 = new ArrayList<>();

        for (int index = 0; index < count; index++) {
            entries1.add(new BarEntry(index + 0.25f, getRandom(25, 25)));
        }

        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColor(Color.rgb(70,130,180));
        set1.setValueTextColor(Color.rgb(70,130,180));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.6f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);

        return d;
    }

    protected float getRandom(float range, float start) {
        return (float) (Math.random() * range) + start;
    }

    private LineData generateLineData() {
        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < count; index++)
            entries.add(new Entry(index + 0.25f, getRandom(15, 5)));

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(220,20,60));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(220,20,60));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(220,20,60));
        set.setMode(LineDataSet.Mode.LINEAR);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_hide_weight_chart:
                imgHideWeightChart.setVisibility(View.GONE);
                imgShowWeightChart.setVisibility(View.VISIBLE);
                weightChart.setVisibility(View.GONE);
                break;
            case R.id.img_show_weight_chart:
                imgHideWeightChart.setVisibility(View.VISIBLE);
                imgShowWeightChart.setVisibility(View.GONE);
                weightChart.setVisibility(View.VISIBLE);
                break;
            case R.id.img_hide_height_chart:
                imgHideHeightChart.setVisibility(View.GONE);
                imgShowHeightChart.setVisibility(View.VISIBLE);
                heightChart.setVisibility(View.GONE);
                break;
            case R.id.img_show_height_chart:
                imgHideHeightChart.setVisibility(View.VISIBLE);
                imgShowHeightChart.setVisibility(View.GONE);
                heightChart.setVisibility(View.VISIBLE);
                break;
            case R.id.img_hide_temperature_chart:
                imgHideTemperatureCheckChart.setVisibility(View.GONE);
                imgShowTemperatureCheckChart.setVisibility(View.VISIBLE);
                temperatureChart.setVisibility(View.GONE);
                break;
            case R.id.img_show_temperature_chart:
                imgHideTemperatureCheckChart.setVisibility(View.VISIBLE);
                imgShowTemperatureCheckChart.setVisibility(View.GONE);
                temperatureChart.setVisibility(View.VISIBLE);
                break;
        }
    }
}
