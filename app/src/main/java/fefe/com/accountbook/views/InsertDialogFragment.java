package fefe.com.accountbook.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fefe.com.accountbook.R;
import fefe.com.accountbook.item.Product;

import static butterknife.ButterKnife.findById;

/**
 * Created by USER on 2015/12/18.
 */
public class InsertDialogFragment extends DialogFragment{

    private OnInsertDialogClick listener;

    public void setOnInsertClickListener(OnInsertDialogClick listener){
        this.listener = listener;
    }

   public interface OnInsertDialogClick {
        void onPositiveClick(Product item);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.insert_dialog, null);
        builder.setView(content);
        final EditText name = findById(content, R.id.name);
        final EditText cost = findById(content, R.id.cost);
        final TextInputLayout name_content = findById(content, R.id.name_content);
        name_content.setErrorEnabled(true);
        final TextInputLayout cost_content = findById(content, R.id.cost_content);
        cost_content.setErrorEnabled(true);

        builder.setMessage("買ったもの");
        builder.setPositiveButton("決定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (name.getText().toString().equals("")) {
                    name_content.setError("買ったものを入力してください");
                    return;
                }
                if (cost.getText().toString().equals("")) {
                    cost_content.setError("買ったものの値段を入力してください");
                    return;
                }
                final Product product = new Product();
                product.name = name.getText().toString();
                product.cost = Integer.parseInt(cost.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                product.date = sdf.format(new Date());
                product.month = Calendar.getInstance().get(Calendar.MONTH) ;
                listener.onPositiveClick(product);
            }
        });
        builder.setNegativeButton("キャンセル", null);

        return builder.create();
    }

}
