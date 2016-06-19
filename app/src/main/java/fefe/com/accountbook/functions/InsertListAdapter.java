package fefe.com.accountbook.functions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import fefe.com.accountbook.R;
import fefe.com.accountbook.item.Product;

import static butterknife.ButterKnife.findById;

/**
 * Created by USER on 2015/12/18.
 */
public class InsertListAdapter extends ArrayAdapter<Product> {

    private LayoutInflater inflater;

    public InsertListAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) convertView = inflater.inflate(R.layout.list_row, null);
        final Product item = getItem(position);
        TextView title = findById(convertView, R.id.title);
        title.setText(item.name);
        TextView content = findById(convertView, R.id.content);
        content.setText(item.cost+"円");
        findById(convertView, R.id.genre).setVisibility(View.GONE);

        return convertView;
    }
}