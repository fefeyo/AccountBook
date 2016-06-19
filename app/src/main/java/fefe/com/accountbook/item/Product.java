package fefe.com.accountbook.item;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Created by USER on 2015/12/08.
 */
@Table(name = "product")
public class Product extends Model implements Serializable{

//    商品名
    @Column(name = "name")
    public String name;
//    価格
    @Column(name = "cost")
    public int cost;
//    種類（食費など）
    @Column(name = "genre")
    public int genre;
//    登録日時
    @Column(name = "date")
    public String date;
//    何月のものか
    @Column(name = "month")
    public Month month;

    public Product() { super(); }

    @Override
    public String toString() {
        return this.name;
    }

}
