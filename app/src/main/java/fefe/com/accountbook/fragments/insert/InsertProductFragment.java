package fefe.com.accountbook.fragments.insert;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fefe.com.accountbook.MainApplication;
import fefe.com.accountbook.R;
import fefe.com.accountbook.views.InsertDialogFragment;
import fefe.com.accountbook.functions.InsertListAdapter;
import fefe.com.accountbook.views.MyListView;
import fefe.com.accountbook.item.Product;

import static butterknife.ButterKnife.findById;

public class InsertProductFragment extends Fragment implements SwipeMenuListView.OnMenuItemClickListener, InsertDialogFragment.OnInsertDialogClick{


    @InjectView(R.id.insert_list)
    MyListView insertList;
    @InjectView(R.id.insert_empty)
    TextView insertEmpty;
    @InjectView(R.id.insert_commit)
    BootstrapButton insertCommit;

    private ArrayList<Product> mProducts;
    private OnNextClickListener listener;
    private InsertListAdapter mAdapter;

    public InsertProductFragment() {}

    public interface OnNextClickListener {
        void onInsertNextClick();
        void onInsertCancelClick();
    }

    public void setOnNextClickListener(OnNextClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_insert_product, container, false);
        ButterKnife.inject(this, v);
        mProducts = new ArrayList<>();
        insertList.setEmptyView(insertEmpty);
        mAdapter = new InsertListAdapter(getActivity().getApplicationContext(), 0);
        insertList.setAdapter(mAdapter);
        insertList.setOnMenuItemClickListener(this);
        checkListExists();

        return v;
    }

    @OnClick({
            R.id.insert_add,
            R.id.insert_commit,
            R.id.insert_cancel
    })
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert_add:
                InsertDialogFragment fragment = new InsertDialogFragment();
                fragment.setOnInsertClickListener(this);
                fragment.show(getFragmentManager(), "");
                break;
            case R.id.insert_commit:
                MainApplication app = (MainApplication) getActivity().getApplication();
                app.setInsertProducts(mProducts);
                listener.onInsertNextClick();
                break;
            case R.id.insert_cancel:
                listener.onInsertCancelClick();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        if(0 == index){
            mAdapter.remove(mProducts.get(position));
            mProducts.remove(position);
            checkListExists();

            return true;
        }
        return false;
    }

    @Override
    public void onPositiveClick(Product item) {
        mProducts.add(item);
        mAdapter.add(item);
        mAdapter.notifyDataSetChanged();
        checkListExists();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void checkListExists(){
        if(mProducts.size() == 0) {
            insertCommit.setVisibility(View.GONE);
        }else{
            insertCommit.setVisibility(View.VISIBLE);
        }
    }
}

