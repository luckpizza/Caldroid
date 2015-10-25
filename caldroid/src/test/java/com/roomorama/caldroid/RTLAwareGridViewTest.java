package com.roomorama.caldroid;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class RTLAwareGridViewTest {

    private static final int COLUMN_COUNT = 3;
    RTLAwareGridView gridView = new MyRTLAwareGridView(COLUMN_COUNT);
    ListAdapter adapter = new MyFakeAdapter(Arrays.asList(0,1,2,3,4,5,6));
    RTLAwareGridView.RTLAwareRowAdapter tested;
    boolean isRTL;

    @Before
    public void setUp() {
        tested = gridView.new RTLAwareRowAdapter(adapter) {
            @Override
            protected boolean isRTL() {
                return isRTL;
            }
        };
    }

    @Test
    public void should_revert_on_rtl() {
        isRTL = true;

        // first row
        assertEquals(2, tested.getItem(0));
        assertEquals(1, tested.getItem(1));
        assertEquals(0, tested.getItem(2));

        // second row
        assertEquals(5, tested.getItem(3));
        assertEquals(4, tested.getItem(4));
        assertEquals(3, tested.getItem(5));

        // last row
        assertEquals(6, tested.getItem(6));
    }

    @Test
    public void should_not_revert_on_ltr() {

        isRTL = false;

        // first row
        assertEquals(2, tested.getItem(2));
        assertEquals(1, tested.getItem(1));
        assertEquals(0, tested.getItem(0));

        // second row
        assertEquals(5, tested.getItem(5));
        assertEquals(4, tested.getItem(4));
        assertEquals(3, tested.getItem(3));

        // last row
        assertEquals(6, tested.getItem(6));
    }

    private static class MyRTLAwareGridView extends RTLAwareGridView {
        private final int columnCount;

        public MyRTLAwareGridView(int columnCount) {
            super(Mockito.mock(Context.class));
            this.columnCount = columnCount;
        }

        @Override
        public int getNumColumns() {
            return columnCount;
        }
    }


    private static class MyFakeAdapter extends ArrayAdapter<Integer> {
        private final List<Integer> list;

        public MyFakeAdapter(List<Integer> list) {
            super(Mockito.mock(Context.class), 0);
            this.list = list;
        }

        @Override
        public Integer getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}