package fefe.com.accountbook.functions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import fefe.com.accountbook.R;
import fefe.com.accountbook.item.GenreItem;
import fefe.com.accountbook.item.GenreList;

import static butterknife.ButterKnife.findById;

/**
 * Created by USER on 2015/12/18.
 */
public class BreakdownListAdapter extends ArrayAdapter<GenreItem>{

    private LayoutInflater inflater;

    public BreakdownListAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView) convertView = inflater.inflate(R.layout.list_row, null);
        GenreItem item = getItem(position);
        TextView title = findById(convertView, R.id.title);
        title.setText(GenreList.getGenre(item.getGenreId()));
        TextView content = findById(convertView, R.id.content);
        content.setText(item.getSum()+"");

        return convertView;
    }
}
