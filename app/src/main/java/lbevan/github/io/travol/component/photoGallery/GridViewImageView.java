package lbevan.github.io.travol.component.photoGallery;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Luke on 18/02/2018.
 */
class GridViewImageView extends android.support.v7.widget.AppCompatImageView {

    public GridViewImageView(Context context) {
        super(context);
    }

    public GridViewImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // make the height equivalent to its width
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
