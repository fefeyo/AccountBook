package fefe.com.accountbook.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.activeandroid.query.Select;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fefe.com.accountbook.R;
import fefe.com.accountbook.item.GenreList;
import fefe.com.accountbook.item.Month;
import fefe.com.accountbook.item.Product;


public class StatisticsFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    @InjectView(R.id.statistic_combined)
    CombinedChart statisticCombined;
    @InjectView(R.id.statistic_pie)
    PieChart statisticPie;
    @InjectView(R.id.statistic_spinner)
    AppCompatSpinner statisticSpinner;
    @InjectView(R.id.statistic_genre)
    GridView statisticGenre;
    private List<Month> currentMonthItems;
    private DateTime now;
    private List<String> mMonthList;


    public StatisticsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.inject(this, v);
        now = DateTime.now();
        currentMonthItems = new ArrayList<>();
        currentMonthItems.add(mathMonth(5));
        currentMonthItems.add(mathMonth(4));
        currentMonthItems.add(mathMonth(3));
        currentMonthItems.add(mathMonth(2));
        currentMonthItems.add(mathMonth(1));
        currentMonthItems.add(mathMonth(0));
        mMonthList = new ArrayList<>();
        for (int i = 0; i < currentMonthItems.size(); i++) {
            if (null != currentMonthItems.get(i)) {
                mMonthList.add(currentMonthItems.get(i).year+"-"+currentMonthItems.get(i).month);
            } else {
                if (i == currentMonthItems.size() - 1) {
                    mMonthList.add(DateTime.now().getYear()+"-"+DateTime.now().getMonthOfYear());
                }
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(),
                R.layout.spinner_text
        );
        adapter.addAll(mMonthList);
        statisticSpinner.setAdapter(adapter);
        statisticSpinner.setOnItemSelectedListener(this);
        statisticSpinner.setSelection(mMonthList.size()-1);

        initCombinedGraph();
        initPieGraph();
        statisticPie.setOnChartValueSelectedListener(null);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void initPieGraph() {
        statisticPie.setUsePercentValues(true);
        statisticPie.setDrawHoleEnabled(false);
        statisticPie.setDescription("");
        statisticPie.setDescriptionTextSize(15);
        statisticPie.setData(createPieChartData(DateTime.now().getYear(), DateTime.now().getMonthOfYear()));
        statisticPie.setNoDataText("この月のデータがありません");
        statisticPie.setTouchEnabled(false);
        Legend legend = statisticPie.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setYEntrySpace(5);

        statisticPie.animateXY(1500, 1500);
        statisticPie.invalidate();
    }

    private void initCombinedGraph() {
        statisticCombined.setDescription("");
        statisticCombined.setDrawGridBackground(false);
        statisticCombined.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.LINE
        });
        CombinedData data = new CombinedData(new String[]{
                now.minusMonths(5).toLocalDate().toString().substring(0, 7),
                now.minusMonths(4).toLocalDate().toString().substring(0, 7),
                now.minusMonths(3).toLocalDate().toString().substring(0, 7),
                now.minusMonths(2).toLocalDate().toString().substring(0, 7),
                now.minusMonths(1).toLocalDate().toString().substring(0, 7),
                now.toLocalDate().toString().substring(0, 7),
        });
//        data.setData(generateLineData());
        data.setData(generateBarData());
        data.setData(generateMultiLineData());
        statisticCombined.setNoDataText("まだデータが登録されていません");
        statisticCombined.setData(data);
        statisticCombined.setTouchEnabled(false);
        statisticCombined.invalidate();
    }

    private PieData createPieChartData(int year, int month) {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        int[] c_codes = new int[]{
                Color.parseColor("#2ecc71"),
                Color.parseColor("#3498db"),
                Color.parseColor("#9b59b6"),
                Color.parseColor("#34495e"),
                Color.parseColor("#f1c40f"),
                Color.parseColor("#e67e22"),
                Color.parseColor("#e74c3c"),
                Color.parseColor("#1abc9c")
        };
        Month now_month = new Select().from(Month.class).where("month = ?", month).and("year = ?", year).executeSingle();
        if (null != now_month) {
            for (int i = 1; i <= 8; i++) {
                int sum = 0;
                for (Product p : now_month.products()) {
                    if (p.genre == i) {
                        sum += p.cost;
                    }
                }
                if (sum == 0) continue;
                names.add(GenreList.getGenre(i));
                values.add(new Entry(sum, i));
                colors.add(c_codes[i - 1]);
            }
        }
        PieDataSet dataSet = new PieDataSet(values, "");
//        dataSet.setColors(c_codes);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(names, dataSet);
        data.setValueFormatter(new PercentFormatter());

        return data;
    }

    private LineData generateLineData() {
        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < 6; index++) {
            if (null != currentMonthItems.get(index)) {
//                entries.add(new Entry(currentMonthItems.get(index).max, index));
                entries.add(new Entry(getActivity().getSharedPreferences("accountbook", Context.MODE_PRIVATE).getInt("max", 175000), index, index));
            }
        }

        LineDataSet set = new LineDataSet(entries, "月の予算");
        set.setColor(Color.parseColor("#e74c3c"));
        set.setLineWidth(3);
        set.setCircleColor(Color.parseColor("#e74c3c"));
        set.setCircleSize(8);
        set.setFillColor(Color.parseColor("#e74c3c"));
        set.setDrawCubic(true);
        set.setDrawValues(true);
        set.setValueTextSize(13);
        set.setValueTextColor(Color.parseColor("#e74c3c"));
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {
        BarData d = new BarData();
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int index = 0; index < 6; index++) {
            int sum = 0;
            if (null != currentMonthItems.get(index)) {
                for (Product p : currentMonthItems.get(index).products()) {
                    sum += p.cost;
                }
            }
            entries.add(new BarEntry(sum, index));
        }

        BarDataSet set = new BarDataSet(entries, "月の使用額");
        set.setColor(Color.parseColor("#27ae60"));
        set.setValueTextColor(Color.parseColor("#27ae60"));
        set.setValueTextSize(13);
        d.addDataSet(set);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return d;
    }

    private LineData generateMultiLineData() {
        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();

        for (int index = 0; index < 6; index++) {
            int sum = 0;
            if (null != currentMonthItems.get(index)) {
                for (Product p : currentMonthItems.get(index).products()) {
                    sum += p.cost;
                }
                entries2.add(new Entry(getActivity().getSharedPreferences("accountbook", Context.MODE_PRIVATE).getInt("max", 175000), index, index));
            }
            entries.add(new Entry(sum, index, index));
        }

        LineDataSet set = new LineDataSet(entries, "使用額推移");
        set.setColor(Color.parseColor("#a66bbe"));
        set.setLineWidth(2);
        set.setCircleColor(Color.parseColor("#a66bbe"));
        set.setCircleSize(5);
        set.setFillColor(Color.parseColor("#a66bbe"));
        set.setValueTextSize(0);
        set.setValueTextColor(Color.parseColor("#a66bbe"));
//        d.addDataSet(set);

        LineDataSet set2 = new LineDataSet(entries2, "月の予算");
        set2.setColor(Color.parseColor("#e74c3c"));
        set2.setLineWidth(3);
        set2.setCircleColor(Color.parseColor("#e74c3c"));
        set2.setCircleSize(8);
        set2.setFillColor(Color.parseColor("#e74c3c"));
        set2.setDrawCubic(true);
        set2.setDrawValues(true);
        set2.setValueTextSize(13);
        set2.setValueTextColor(Color.parseColor("#e74c3c"));
        d.addDataSet(set2);

        return d;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        DateTime dateTime = DateTime.parse(parent.getItemAtPosition(position).toString());
        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        statisticPie.setData(createPieChartData(year, month));
        statisticPie.animateXY(1500, 1500);
        statisticPie.invalidate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private Month mathMonth(int number) {
        DateTime dt = now.minusMonths(number);
        Month month = new Select().from(Month.class).where("month = ?", dt.monthOfYear().get()).and("year = ?", dt.year().get()).executeSingle();
        if (month == null) return null;

        return month;
    }

}
