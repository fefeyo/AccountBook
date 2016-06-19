package fefe.com.accountbook.item;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by fefe on 16/05/16.
 */
@Table(name = "month")
public class Month extends Model{
//    予算
    @Column(name = "max")
    public int max;
//    何月か
    @Column(name = "month")
    public int month;
//    何年か
    @Column(name = "year")
    public int year;


    public List<Product> products() {
        return getMany(Product.class, "month");
    }

    public Month(){super();}
}
