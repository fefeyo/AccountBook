package fefe.com.accountbook.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fefe.com.accountbook.R;
import fefe.com.accountbook.functions.BreakdownListAdapter;
import fefe.com.accountbook.functions.InsertListAdapter;
import fefe.com.accountbook.item.GenreItem;
import fefe.com.accountbook.item.GenreList;
import fefe.com.accountbook.views.CommitBudgetFragment;
import fefe.com.accountbook.views.MyListView;
import fefe.com.accountbook.item.Product;

public class BreakdownFragment extends Fragment implements AdapterView.OnItemClickListener{

    @InjectView(R.id.budget_text)
    TextView budgetText;
    @InjectView(R.id.breakdown_list)
    ListView breakdownList;
    @InjectView(R.id.breakdown_empty)
    TextView breakdownEmpty;
    private OnGenreClick listener;
    private List<GenreItem> mGenres;
    private BreakdownListAdapter mAdapter;
    int max = 0;

    public BreakdownFragment() {
    }

    public interface OnGenreClick {
        void onGenreClick(int genre);
    }

    public void setOnGenreClickListener(OnGenreClick listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_breakdown, container, false);
        ButterKnife.inject(this, v);
        breakdownList.setEmptyView(breakdownEmpty);
        max = getActivity().getSharedPreferences("accountbook", Context.MODE_PRIVATE).getInt("max", 175000);
        mGenres = new ArrayList<>();
        mAdapter = new BreakdownListAdapter(getActivity().getApplicationContext(), 0);
        int sumall = 0;
        for(int i = 1; i <= 8; i++) {
            List<Product> productList = new Select().from(Product.class).where("genre = ?", i).execute();
            if(productList.isEmpty()) continue;
            GenreItem genreItem = new GenreItem();
            genreItem.setGenreId(i);
            int sum = 0;
            for(Product p : productList) {
                sum += p.cost;
            }
            sumall += sum;
            genreItem.setSum(sum);
            mGenres.add(genreItem);
        }
        GenreItem item = getDefaultGenreItem();
        if(item != null) mGenres.add(item);
        max = max - sumall;
        mAdapter.addAll(mGenres);
        breakdownList.setAdapter(mAdapter);
        breakdownList.setOnItemClickListener(this);
        budgetText.setText(String.valueOf(max));

        return v;
    }

    private GenreItem getDefaultGenreItem() {
        GenreItem item = new GenreItem();
        SharedPreferences preferences = getActivity().getSharedPreferences("accountbook", Context.MODE_PRIVATE);
        int rent = preferences.getInt("rent", 0);
        int connect = preferences.getInt("connect", 0);
        item.setGenreId(9);
        item.setSum(rent + connect);
        if(item.getSum() == 0) return null;

        return item;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GenreItem genreItem = (GenreItem) parent.getItemAtPosition(position);
        listener.onGenreClick(genreItem.getGenreId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
