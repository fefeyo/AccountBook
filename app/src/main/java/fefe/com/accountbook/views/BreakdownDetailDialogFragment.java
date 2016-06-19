package fefe.com.accountbook.views;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.List;

import fefe.com.accountbook.R;
import fefe.com.accountbook.item.GenreList;
import fefe.com.accountbook.item.Product;
import fefe.com.accountbook.views.MyListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BreakdownDetailDialogFragment extends DialogFragment implements SwipeMenuListView.OnMenuItemClickListener{

    DetailListAdapter mAdapter;

    OnDismissCallback callback;

    public interface OnDismissCallback {
        void onDismiss();
    }

    public void setOnDismissListener(OnDismissCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int genre = getArguments().getInt("genre");
        List<Product> products = new Select().from(Product.class).where("genre = ?", genre).execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MyListView list = (MyListView) inflater.inflate(R.layout.breakdown_detail_dialog, null);
        if(genre == 9) {
            SharedPreferences preferences = getActivity().getSharedPreferences("accountbook", Context.MODE_PRIVATE);
            Product rent = new Product();
            rent.name = "家賃";
            rent.cost = preferences.getInt("rent", 0);
            rent.genre = 9;
            Product connect = new Product();
            connect.name = "通信費";
            connect.cost = preferences.getInt("connect", 0);
            connect.genre = 9;
            products.add(rent);
            products.add(connect);
            list.setMenuCreator(null);
        }else {
            list.setOnMenuItemClickListener(this);
        }
        mAdapter = new DetailListAdapter(getActivity().getApplicationContext(), 0);
        mAdapter.addAll(products);
        list.setAdapter(mAdapter);
        builder.setCancelable(false);
        builder.setView(list);
        builder.setTitle(GenreList.getGenre(products.get(0).genre) + "の詳細内訳");
        builder.setNegativeButton("閉じる", null);

        return builder.create();
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        if(index == 0) {
            Product p = mAdapter.getItem(position);
            p.delete();
            mAdapter.remove(p);
            mAdapter.notifyDataSetChanged();
            if(mAdapter.isEmpty()) {
                dismiss();
                callback.onDismiss();
            }
        }
        return false;
    }

    private class DetailListAdapter extends ArrayAdapter<Product> {

        LayoutInflater mInflater;

        public DetailListAdapter(Context context, int resource) {
            super(context, resource);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView)
                convertView = mInflater.inflate(R.layout.breakdown_detail_row, null);
            Product item = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.breakdown_detail_name);
            name.setText(item.name);
            TextView cost = (TextView) convertView.findViewById(R.id.breakdown_detail_cost);
            cost.setText(String.valueOf(item.cost));

            return convertView;
        }
    }
}
