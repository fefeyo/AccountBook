package fefe.com.accountbook.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import fefe.com.accountbook.item.Product;

/**
 * Created by USER on 2015/12/08.
 */
public class ProductTextView extends TextView{
    private Product product;

    public ProductTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "honoka.ttf"));
    }

    public void setProductItem(Product product){
        this.product = product;
        setText(product.name);
    }

    public Product getProduct(){
        return this.product;
    }
}
