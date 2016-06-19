package fefe.com.accountbook.fragments.insert;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.AwesomeTextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fefe.com.accountbook.MainApplication;
import fefe.com.accountbook.R;
import fefe.com.accountbook.functions.ObjectDragListener;
import fefe.com.accountbook.functions.ObjectTouchListener;
import fefe.com.accountbook.views.ProductTextView;
import fefe.com.accountbook.item.Product;


public class SortingFragment extends Fragment implements ObjectDragListener.OnDragFinishedListener{

    @InjectView(R.id.dropzone1)
    AwesomeTextView dropzone1;
    @InjectView(R.id.dropzone2)
    AwesomeTextView dropzone2;
    @InjectView(R.id.dropzone3)
    AwesomeTextView dropzone3;
    @InjectView(R.id.dropzone4)
    AwesomeTextView dropzone4;
    @InjectView(R.id.target)
    ProductTextView target;
    @InjectView(R.id.dropzone5)
    AwesomeTextView dropzone5;
    @InjectView(R.id.dropzone6)
    AwesomeTextView dropzone6;
    @InjectView(R.id.dropzone7)
    AwesomeTextView dropzone7;
    @InjectView(R.id.dropzone8)
    AwesomeTextView dropzone8;

    private ObjectDragListener dragListener;
    private ObjectTouchListener touchListener;
    private OnSortingFinishedListener listener;

    private ArrayList<Product> sordItems;

    public SortingFragment() {}

    public interface OnSortingFinishedListener{
        void onSortingFinished();
    }

    public void setOnSortingFinishedListener(OnSortingFinishedListener listener){
        this.listener = listener;
    }

    private void init(){
        dragListener = new ObjectDragListener();
        dragListener.setOnDragFinishedListener(this);
        touchListener = new ObjectTouchListener(BitmapFactory.decodeResource(getResources(), R.drawable.drag_image));
        dropzone1.setOnDragListener(dragListener);
        dropzone2.setOnDragListener(dragListener);
        dropzone3.setOnDragListener(dragListener);
        dropzone4.setOnDragListener(dragListener);
        dropzone5.setOnDragListener(dragListener);
        dropzone6.setOnDragListener(dragListener);
        dropzone7.setOnDragListener(dragListener);
        dropzone8.setOnDragListener(dragListener);
        target.setOnTouchListener(touchListener);
        sordItems = ((MainApplication)getActivity().getApplication()).getInsertProducts();
        target.setProductItem(sordItems.get(0));
        sordItems.remove(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_select, container, false);
        ButterKnife.inject(this, v);
        init();

        return v;
    }

    @Override
    public void onDragFinished() {
        if(sordItems.size() != 0){
            target.setProductItem(sordItems.get(0));
            sordItems.remove(0);
        }else{
            listener.onSortingFinished();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
