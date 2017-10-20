package uk.co.botondbutuza.currency.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by brotond on 20/10/2017.
 */

public class OnRecyclerItemTouchListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;

    public OnRecyclerItemTouchListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            int position = view.getChildAdapterPosition(childView);
            return mListener.onItemClick(childView, view.findViewHolderForAdapterPosition(position), position);
        }

        return false;
    }

    @Override public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
    @Override public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}

    public void onDestroy() {
        mListener = null;
        mGestureDetector = null;
    }


    public interface OnItemClickListener {
        /**
         * Handles item clicks on the adapter.
         * @param view The view clicked.
         * @param viewHolder The view holder clicked.
         * @param position The position of the item clicked.
         * @return True if the click is consumed, false otherwise
         */
        boolean onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position);
    }
}
