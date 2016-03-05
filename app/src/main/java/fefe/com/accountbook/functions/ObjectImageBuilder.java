package fefe.com.accountbook.functions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.view.View;

import com.beardedhen.androidbootstrap.font.FontAwesome;

/**
 * Created by USER on 2015/12/08.
 */
public class ObjectImageBuilder extends View.DragShadowBuilder{

    private Bitmap dragIcon;

    public ObjectImageBuilder(View v, Bitmap dragIcon){
        super(v);
        this.dragIcon = dragIcon;
    }

    /**
     * ドラッグ中のイメージを描画するメソッド
     */
    @Override
    public void onDrawShadow(Canvas canvas) {
        super.onDrawShadow(canvas);

//     ドラッグ対象
        View view = getView();

        view.setDrawingCacheEnabled(true);
        view.destroyDrawingCache();

//        Viewのキャプチャをキャンバスへ描画
        Bitmap bitmap = view.getDrawingCache();

        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(dragIcon, 1f, 1f, null);
    }
}
