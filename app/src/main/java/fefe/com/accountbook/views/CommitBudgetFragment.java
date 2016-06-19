package fefe.com.accountbook.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.joda.time.DateTime;

import fefe.com.accountbook.MainActivity;
import fefe.com.accountbook.R;

/**
 * Created by fefe on 16/06/18.
 */
public class CommitBudgetFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View content = inflater.inflate(R.layout.budget_text, null);
        final EditText edit = (EditText)content.findViewById(R.id.budget_input);
        builder.setTitle("今月の予算を入力してください");
        builder.setView(content);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().equals("")) {
                            edit.setError("入力してください");
                        }else {
                            if(Integer.parseInt(edit.getText().toString()) < 1000) {
                                edit.setError("1000円以上を入力してください");
                            }else {
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences("accountbook", Context.MODE_PRIVATE).edit();
                                editor.putInt("max", Integer.parseInt(edit.getText().toString()));
                                editor.putString("lasttime", DateTime.now().toLocalDate().toString());
                                editor.apply();
                                alertDialog.dismiss();
                                ((MainActivity)getActivity()).buildTabs();
                            }
                        }
                    }
                });
            }
        });

        return alertDialog;
    }
}
