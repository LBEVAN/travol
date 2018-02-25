package lbevan.github.io.travol.component.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Luke on 25/02/2018.
 */

public class CustomOnItemTouchListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener itemClickListener;
    private GestureDetector gestureDetector;

    public CustomOnItemTouchListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {

        this.itemClickListener = listener;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent)  {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(childView != null && itemClickListener != null)
                {
                    itemClickListener.onItemLongClick(childView, recyclerView.getChildPosition(childView));
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        View childView = view.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        if(childView != null && itemClickListener != null && gestureDetector.onTouchEvent(motionEvent)) {
            itemClickListener.onItemClick(childView, view.getChildPosition(childView));
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }

    /**
     * Item click listener interface, defining actions to be handled for click events.
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
