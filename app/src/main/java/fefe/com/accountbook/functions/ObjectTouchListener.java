package fefe.com.accountbook.functions;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import fefe.com.accountbook.item.Product;
import fefe.com.accountbook.views.ProductTextView;

/**
 * Created by USER on 2015/12/08.
 */
public class ObjectTouchListener implements View.OnTouchListener{

    private Bitmap dragIcon;

    public ObjectTouchListener(Bitmap dragIcon){
        this.dragIcon = dragIcon;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final Product item = ((ProductTextView)v).getProduct();
//        受け渡しデータ
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", item.name);
            obj.put("cost", item.cost);
        }catch (JSONException e){
            e.printStackTrace();
        }
        ClipData clip = ClipData.newPlainText("product", obj.toString());
//        ドラッグ中のイメージビルダー
        View.DragShadowBuilder shadow = new ObjectImageBuilder(v, dragIcon);
//        ドラッグを開始
        v.startDrag(clip, shadow, v, 0);

        return true;
    }
}
