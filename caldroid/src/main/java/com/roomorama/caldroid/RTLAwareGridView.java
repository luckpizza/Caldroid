package com.roomorama.caldroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

public class RTLAwareGridView extends GridView {

    public RTLAwareGridView(Context context) {
        super(context);
    }

    public RTLAwareGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RTLAwareGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RTLAwareGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setAdapter(final ListAdapter adapter) {
        super.setAdapter(new RTLAwareRowAdapter(adapter));
    }

    class RTLAwareRowAdapter implements ListAdapter {
        private final ListAdapter adapter;

        public RTLAwareRowAdapter(ListAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return adapter.areAllItemsEnabled();
        }

        @Override
        public boolean isEnabled(int i) {
            return adapter.isEnabled(i);
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            adapter.registerDataSetObserver(dataSetObserver);
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            adapter.unregisterDataSetObserver(dataSetObserver);
        }

        @Override
        public int getCount() {
            return adapter.getCount();
        }

        @Override
        public Object getItem(int i) {
            return adapter.getItem(verifyPosition(i));
        }

        @Override
        public long getItemId(int i) {
            return adapter.getItemId(verifyPosition(i));
        }

        @Override
        public boolean hasStableIds() {
            return adapter.hasStableIds();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return adapter.getView(verifyPosition(i), view, viewGroup);
        }

        @Override
        public int getItemViewType(int i) {
            return adapter.getItemViewType(verifyPosition(i));
        }

        @Override
        public int getViewTypeCount() {
            return adapter.getViewTypeCount();
        }

        @Override
        public boolean isEmpty() {
            return adapter.isEmpty();
        }

        private int verifyPosition(int position) {
            int modulo = position % getNumColumns();
            int startRow = position - modulo;
            int endRow = position + getNumColumns() - modulo -1;
            return isRTL() ? Math.min(adapter.getCount() - 1, endRow - (position - startRow)) : position;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected boolean isRTL() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
            }
            return false;
        }
    }
}
