package com.example.puzzlegame;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class CustomGridAdapter extends BaseAdapter {

    private ArrayList<Button> buttons;

    private int columnWidth, columnHeight;

    public CustomGridAdapter(ArrayList<Button> buttons, int columnWidth, int columnHeight) {
        this.buttons = buttons;
        this.columnWidth = columnWidth;
        this.columnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return buttons.size();
    }

    @Override
    public Object getItem(int i) {
        return (Object) buttons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Button button;

        if (view == null){
            button = buttons.get(i);
        }else {
            button = (Button) view;
        }

        AbsListView.LayoutParams params =
                new AbsListView.LayoutParams(columnWidth, columnHeight);
        button.setLayoutParams(params);

        return button;
    }
}
