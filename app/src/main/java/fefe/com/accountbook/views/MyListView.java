package fefe.com.accountbook.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(120);
                deleteItem.setIcon(android.R.drawable.ic_menu_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        setMenuCreator(creator);
    }
}
