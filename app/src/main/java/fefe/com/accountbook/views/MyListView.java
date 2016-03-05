package fefe.com.accountbook.views;

import android.content.Context;
import android.util.AttributeSet;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import fefe.com.accountbook.R;

/**
 * Created by USER on 2015/12/18.
 */
public class MyListView extends SwipeMenuListView{

    private Context context;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createOptionMenu();
    }

    private void createOptionMenu(){
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(context);
                deleteItem.setBackground(R.color.red);
                deleteItem.setWidth(180);
                deleteItem.setTitle("削除");
                deleteItem.setTitleColor(R.color.white);
                deleteItem.setTitleSize(20);
                menu.addMenuItem(deleteItem);
            }
        };
        setMenuCreator(creator);
    }
}
