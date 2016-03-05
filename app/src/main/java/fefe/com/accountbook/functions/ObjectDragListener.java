package fefe.com.accountbook.functions;

import android.view.DragEvent;
import android.view.View;

import fefe.com.accountbook.R;
import fefe.com.accountbook.item.Product;
import fefe.com.accountbook.views.ProductTextView;

/**
 * Created by USER on 2015/12/08.
 */
public class ObjectDragListener implements View.OnDragListener {

    private OnDragFinishedListener listener;

    public ObjectDragListener() {}

    public interface OnDragFinishedListener{
        void onDragFinished();
    }

    public void setOnDragFinishedListener(OnDragFinishedListener listener){
        this.listener = listener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
            case DragEvent.ACTION_DRAG_ENTERED:
            case DragEvent.ACTION_DRAG_LOCATION:
            case DragEvent.ACTION_DRAG_EXITED:
                return true;
            case DragEvent.ACTION_DROP:
//                startDragの第三引数で渡したデータを取得
                ProductTextView src = (ProductTextView) event.getLocalState();
                final Product saveItem = src.getProduct();
                switch (v.getId()){
                    case R.id.dropzone1:
                        saveItem.genre = 1;
                        break;
                    case R.id.dropzone2:
                        saveItem.genre = 2;
                        break;
                    case R.id.dropzone3:
                        saveItem.genre = 3;
                        break;
                    case R.id.dropzone4:
                        saveItem.genre = 4;
                        break;
                    case R.id.dropzone5:
                        saveItem.genre = 5;
                        break;
                    case R.id.dropzone6:
                        saveItem.genre = 6;
                        break;
                    case R.id.dropzone7:
                        saveItem.genre = 7;
                        break;
                    case R.id.dropzone8:
                        saveItem.genre = 8;
                        break;
                }
                saveItem.save();
                listener.onDragFinished();
                return true;
            default:
                break;
        }
        return false;
    }
}
