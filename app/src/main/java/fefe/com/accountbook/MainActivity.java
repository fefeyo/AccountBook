package fefe.com.accountbook;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fefe.com.accountbook.fragments.BreakdownFragment;
import fefe.com.accountbook.fragments.SettingFragment;
import fefe.com.accountbook.fragments.StatisticsFragment;
import fefe.com.accountbook.fragments.insert.InsertProductFragment;
import fefe.com.accountbook.fragments.insert.SortingFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements
        InsertProductFragment.OnNextClickListener,
        SortingFragment.OnSortingFinishedListener{

    @InjectView(R.id.main_toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.main_pager)
    ViewPager mPager;
    @InjectView(R.id.main_tab)
    TabLayout mTab;

    private FragmentTransaction mTransaction;
    private SortingFragment mSortingFragment;
    private InsertProductFragment mInsertProductFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        mInsertProductFragment = new InsertProductFragment();
        mInsertProductFragment.setOnNextClickListener(this);
        mSortingFragment = new SortingFragment();
        mSortingFragment.setOnSortingFinishedListener(this);
        buildTabs();

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
        if(R.id.insert == id){
            mTransaction = getSupportFragmentManager().beginTransaction();
            mTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top);
            mTransaction.add(R.id.front, mInsertProductFragment);
            mTransaction.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    private void buildTabs(){
        mTab.addTab(mTab.newTab().setText("今月の内訳"));
        mTab.addTab(mTab.newTab().setText("近況グラフ"));
        mTab.addTab(mTab.newTab().setText("設定"));
        mPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
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
    }

    @Override
    public void onSortingFinished() {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top);
        mTransaction.remove(mSortingFragment);
        mTransaction.commit();
        MainApplication.resetProducts();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private class MainPagerAdapter extends FragmentPagerAdapter{

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new BreakdownFragment();
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
            switch (position){
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