package fefe.com.accountbook.functions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.beardedhen.androidbootstrap.font.IconSet;

import org.w3c.dom.Text;

import fefe.com.accountbook.R;
import fefe.com.accountbook.item.GenreItem;
import fefe.com.accountbook.item.GenreList;

import static butterknife.ButterKnife.findById;

/**
 * Created by USER on 2015/12/18.
 */
public class BreakdownListAdapter extends ArrayAdapter<GenreItem> {

    private LayoutInflater inflater;

    public BreakdownListAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) convertView = inflater.inflate(R.layout.list_row, null);
        GenreItem item = getItem(position);
        TextView title = findById(convertView, R.id.title);
        title.setText(GenreList.getGenre(item.getGenreId()));
        TextView content = findById(convertView, R.id.content);
        content.setText(item.getSum() + "");
        AwesomeTextView genre = findById(convertView, R.id.genre);
        genre.setBootstrapText(
                new BootstrapText.Builder(getContext())
                        .addFontAwesomeIcon(GenreList.getGenreIcon(item.getGenreId()))
                        .build()
        );

        genre.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
        return convertView;
    }
}
