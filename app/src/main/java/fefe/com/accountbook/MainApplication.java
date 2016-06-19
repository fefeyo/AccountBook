package fefe.com.accountbook;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.beardedhen.androidbootstrap.TypefaceProvider;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;

import fefe.com.accountbook.item.Product;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {

    private static ArrayList<Product> insertProducts;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
//        Configuration.Builder builder = new Configuration.Builder(this);
//        builder.addModelClass(Product.class);
        ActiveAndroid.initialize(this);
        TypefaceProvider.registerDefaultIconSets();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public ArrayList<Product> getInsertProducts() {

        return this.insertProducts;
    }

    public void setInsertProducts(ArrayList<Product> items) {
        this.insertProducts = items;
    }

    public static void resetProducts() {
        insertProducts = new ArrayList<>();
    }
}
