package fefe.com.accountbook.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fefe.com.accountbook.R;


public class StatisticsFragment extends Fragment {

    @InjectView(R.id.statistic_combined)
    CombinedChart statisticCombined;
    @InjectView(R.id.statistic_pie)
    PieChart statisticPie;
    @InjectView(R.id.statistic_spinner)
    AppCompatSpinner statisticSpinner;
    @InjectView(R.id.statistic_genre)
    GridView statisticGenre;
    private OnGraphClick listener;

    public StatisticsFragment() {
    }

    public interface OnGraphClick {
        void onGraphClick();
    }

    public void setOnGraphClickListener(OnGraphClick listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.inject(this, v);
        initCombinedGraph();
        initPieGraph();

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void initPieGraph(){
        statisticPie.setUsePercentValues(true);
        statisticPie.setDrawHoleEnabled(false);
        statisticPie.setDescription("今月使ったお金内訳");
        statisticPie.setData(createPieChartData());
        statisticPie.setRotationEnabled(false);
        statisticPie.setNoDataText("この月のデータがありません");

        statisticPie.invalidate();
        statisticPie.animateXY(2000, 2000);
    }

    private void initCombinedGraph(){
        statisticCombined.setDescription("今月までの使用金額推移");
        statisticCombined.setDrawGridBackground(false);
        statisticCombined.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.LINE
        });
        CombinedData data = new CombinedData(new String[]{"5月", "6月", "7月", "8月", "9月", "10月"});
        data.setData(generateLineData());
        data.setData(generateBarData());
        statisticCombined.setNoDataText("まだデータが登録されていません");
        statisticCombined.setData(data);
        statisticCombined.invalidate();
    }

    private PieData createPieChartData(){
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        int[] c_codes = new int[]{Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.BLACK};
        for(int i = 0; i <   5; i++){
            names.add("村人"+i);
            values.add(new Entry(i * 300, i));
            colors.add(c_codes[i]);
        }
        PieDataSet dataSet = new PieDataSet(values, "");
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(1f);
        dataSet.setColors(c_codes);
        dataSet.setDrawValues(true);

        PieData data = new PieData(names, dataSet);
        data.setValueFormatter(new PercentFormatter());

        data.setValueTextSize(0);
        data.setValueTextColor(Color.WHITE);

        return data;
    }

    private LineData generateLineData() {
        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<>();


        for (int index = 0; index < 6; index++)
            entries.add(new Entry(getRandom(1500, 1000), index, index));

        LineDataSet set = new LineDataSet(entries, "月の予算");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleSize(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setDrawCubic(true);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {

        BarData d = new BarData();

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int index = 0; index < 6; index++)
            entries.add(new BarEntry(getRandom(1500, 3000), index));

        BarDataSet set = new BarDataSet(entries, "月の使用額");
        set.setColor(Color.rgb(60, 220, 78));
        set.setValueTextColor(Color.rgb(60, 220, 78));
        set.setValueTextSize(10f);
        d.addDataSet(set);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return d;
    }

    private float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }


}
