package fefe.com.accountbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fefe.com.accountbook.functions.ProductAdapter;
import fefe.com.accountbook.item.Product;
import fefe.com.accountbook.views.BreakdownDetailDialogFragment;
import fefe.com.accountbook.fragments.BreakdownFragment;
import fefe.com.accountbook.fragments.SettingFragment;
import fefe.com.accountbook.fragments.StatisticsFragment;
import fefe.com.accountbook.fragments.insert.InsertProductFragment;
import fefe.com.accountbook.fragments.insert.SortingFragment;
import fefe.com.accountbook.views.CommitBudgetFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements
        InsertProductFragment.OnNextClickListener,
        SortingFragment.OnSortingFinishedListener,
        BreakdownFragment.OnGenreClick,
        BreakdownDetailDialogFragment.OnDismissCallback {

    @InjectView(R.id.main_toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.main_pager)
    public ViewPager mPager;
    @InjectView(R.id.main_tab)
    TabLayout mTab;

    private FragmentTransaction mTransaction;
    private SortingFragment mSortingFragment;
    private InsertProductFragment mInsertProductFragment;
    private BreakdownFragment breakdownFragment;
    private MainPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        breakdownFragment = new BreakdownFragment();
        breakdownFragment.setOnGenreClickListener(this);
        mInsertProductFragment = new InsertProductFragment();
        mInsertProductFragment.setOnNextClickListener(this);
        mSortingFragment = new SortingFragment();
        mSortingFragment.setOnSortingFinishedListener(this);
        checkElapse();
        buildTabs();
        List<Product> productList = new Select().from(Product.class).execute();
        Gson gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductAdapter()).create();
        if(productList.size() != 0) {
            Log.d("list", gson.toJson(productList));
            Log.d("object", gson.toJson(productList.get(0)));
        }
    }

    private void checkElapse() {
        SharedPreferences preferences = getSharedPreferences("accountbook", MODE_PRIVATE);
        if (!preferences.getString("lasttime", "").equals("")) {
            DateTime lasttime = DateTime.parse(preferences.getString("lasttime", ""));
            DateTime now = DateTime.now();
            if (lasttime.isBeforeNow() && lasttime.getMonthOfYear() < now.getMonthOfYear()) {
                CommitBudgetFragment fragment = new CommitBudgetFragment();
                fragment.show(getSupportFragmentManager(), "test");
            }
        }else {
            CommitBudgetFragment fragment = new CommitBudgetFragment();
            fragment.show(getSupportFragmentManager(), "test");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.getItem(0).setIcon(R.drawable.pen);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (R.id.insert == id) {
            mTransaction = getSupportFragmentManager().beginTransaction();
            mTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top);
            mTransaction.add(R.id.front, mInsertProductFragment);
            mTransaction.commit();
            findViewById(R.id.front).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    public void buildTabs() {
        mTab.removeAllTabs();
        mTab.addTab(mTab.newTab().setText("今月の内訳"));
        mTab.addTab(mTab.newTab().setText("近況グラフ"));
        mTab.addTab(mTab.newTab().setText("設定"));
        mAdapter = new MainPagerAdapter(getSupportFragmentManager(), breakdownFragment);
        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);
    }

    @Override
    public void onInsertNextClick() {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        mTransaction.replace(R.id.front, mSortingFragment);
        mTransaction.commit();
    }

    @Override
    public void onInsertCancelClick() {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top);
        mTransaction.remove(mInsertProductFragment);
        mTransaction.commit();
        findViewById(R.id.front).setOnTouchListener(null);
    }

    @Override
    public void onSortingFinished() {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top);
        mTransaction.remove(mSortingFragment);
        mTransaction.commit();
        MainApplication.resetProducts();
        mTab.getTabAt(0).select();
        findViewById(R.id.front).setOnTouchListener(null);
        buildTabs();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onGenreClick(int genre) {
        Bundle bundle = new Bundle();
        bundle.putInt("genre", genre);
        BreakdownDetailDialogFragment f = new BreakdownDetailDialogFragment();
        f.setArguments(bundle);
        f.setOnDismissListener(this);
        f.show(getSupportFragmentManager(), "ジャンル");
    }

    @Override
    public void onDismiss() {
        buildTabs();
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {

        BreakdownFragment mBreakdownFragment;

        public MainPagerAdapter(FragmentManager fm, BreakdownFragment fragment) {
            super(fm);
            mBreakdownFragment = fragment;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mBreakdownFragment;
                case 1:
                    return new StatisticsFragment();
                case 2:
                    return new SettingFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "今月の内訳";
                case 1:
                    return "近況グラフ";
                case 2:
                    return "設定";
                default:
                    return "";
            }
        }
    }

}