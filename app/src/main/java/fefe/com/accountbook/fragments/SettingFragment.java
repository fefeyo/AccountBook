package fefe.com.accountbook.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.beardedhen.androidbootstrap.BootstrapButton;

import org.joda.time.DateTime;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fefe.com.accountbook.MainActivity;
import fefe.com.accountbook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnKeyListener {


    @InjectView(R.id.month_max)
    EditText monthMax;
    @InjectView(R.id.month_max_sync)
    BootstrapButton monthMaxSync;
    @InjectView(R.id.rent)
    EditText rent;
    @InjectView(R.id.connect)
    EditText connect;
    @InjectView(R.id.web_id)
    EditText webId;
    @InjectView(R.id.web_pass)
    EditText webPass;
    @InjectView(R.id.web_new)
    BootstrapButton webNew;
    @InjectView(R.id.web_login)
    BootstrapButton webLogin;
    private SharedPreferences mPreferences;

    public SettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.inject(this, v);
        mPreferences = getActivity().getSharedPreferences("accountbook", Context.MODE_PRIVATE);
        DateTime now = DateTime.now();
        if (!mPreferences.getString("lastsetting", "").equals("")) {
            DateTime lastsetting = DateTime.parse(mPreferences.getString("lastsetting", ""));
            if (lastsetting.isBeforeNow() && lastsetting.getMonthOfYear() == now.getMonthOfYear()) {
                monthMaxSync.setVisibility(View.GONE);
                monthMax.setVisibility(View.GONE);
            }
        }
        monthMax.setText(String.valueOf(mPreferences.getInt("max", 175000)));
        rent.setText(String.valueOf(mPreferences.getInt("rent", 0)));
        connect.setText(String.valueOf(mPreferences.getInt("connect", 0)));
        rent.setOnKeyListener(this);
        connect.setOnKeyListener(this);

        return v;
    }

    @OnClick({
            R.id.month_max_sync,
            R.id.web_new,
            R.id.web_login
    })
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.month_max_sync:
                if (monthMax.getText().toString().equals("")) {
                    monthMax.setError("入力してください");
                    return;
                } else {
                    final int max = Integer.parseInt(monthMax.getText().toString());
                    if (max < 0 || 10000000 < max) {
                        monthMax.setError("正しい数値を入力してください");
                        return;
                    }
                    new AlertDialog.Builder(getActivity())
                            .setTitle("今月の予算")
                            .setMessage("今月の予算は" + max + "円でよろしいですか？")
                            .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    monthMax.setText("");
                                    SharedPreferences.Editor editor = mPreferences.edit();
                                    editor.putInt("max", max).apply();
                                    editor.putString("lastsetting", DateTime.now().toLocalDate().toString());
                                    editor.apply();
                                    ((MainActivity) getActivity()).buildTabs();
                                    ((MainActivity) getActivity()).mPager.setCurrentItem(2);
                                }
                            })
                            .setCancelable(false)
                            .setNegativeButton("いいえ", null)
                            .create().show();
                }
                break;
            case R.id.web_new:
                break;
            case R.id.web_login:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //EnterKeyが押されたかを判定
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

            //ソフトキーボードを閉じる
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            switch (v.getId()) {
                case R.id.rent:
                    mPreferences.edit().putInt("rent", Integer.parseInt(rent.getText().toString())).apply();
                    break;
                case R.id.connect:
                    mPreferences.edit().putInt("connect", Integer.parseInt(connect.getText().toString())).apply();
                    break;
            }
            ((MainActivity) getActivity()).buildTabs();
            ((MainActivity) getActivity()).mPager.setCurrentItem(2);

            return true;
        }

        return false;
    }
}
