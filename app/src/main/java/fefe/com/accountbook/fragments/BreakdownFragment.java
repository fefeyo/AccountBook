package fefe.com.accountbook.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fefe.com.accountbook.R;
import fefe.com.accountbook.functions.BreakdownListAdapter;
import fefe.com.accountbook.functions.InsertListAdapter;
import fefe.com.accountbook.item.GenreItem;
import fefe.com.accountbook.views.MyListView;
import fefe.com.accountbook.item.Product;

public class BreakdownFragment extends Fragment implements SwipeMenuListView.OnMenuItemClickListener{

    @InjectView(R.id.budget_text)
    TextView budgetText;
    @InjectView(R.id.breakdown_list)
    MyListView breakdownList;
    @InjectView(R.id.breakdown_empty)
    TextView breakdownEmpty;
    private OnGenreClick listener;
    private List<GenreItem> mGenres;
    private BreakdownListAdapter mAdapter;

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
        Log.d("今月は", Calendar.getInstance().get(Calendar.MONTH) + "です。");
        mAdapter = new BreakdownListAdapter(getActivity().getApplicationContext(), 0);
        mAdapter.addAll();

        return v;
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        if(0 == index){

        }
        return false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
