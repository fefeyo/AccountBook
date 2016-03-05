package fefe.com.accountbook.item;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by USER on 2015/12/08.
 */
@Table(name = "product")
public class Product extends Model {

    @Column(name = "name")
    public String name;
    @Column(name = "cost")
    public int cost;
    @Column(name = "genre")
    public int genre;
    @Column(name = "date")
    public String date;
    @Column(name = "month")
    public int month;
}
