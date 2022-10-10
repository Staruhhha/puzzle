package com.example.puzzlegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.puzzle_recycler);

        int[] imgsCat = new int[]{R.drawable.cat_1, R.drawable.cat_2,R.drawable.cat_3, R.drawable.cat_4,R.drawable.cat_5,R.drawable.cat_6,R.drawable.cat_7,R.drawable.cat_8,R.drawable.cat_9};
        int[] imgsDog = new int[]{R.drawable.shenok_1, R.drawable.shenok_2,R.drawable.shenok_3, R.drawable.shenok_4,R.drawable.shenok_5,R.drawable.shenok_6,R.drawable.shenok_7,R.drawable.shenok_8,R.drawable.shenok_9};
        ArrayList<PuzzleItem> listPuzzles = new ArrayList<PuzzleItem>();
        listPuzzles.add(new PuzzleItem(R.drawable.cotenek, imgsCat));
        listPuzzles.add(new PuzzleItem(R.drawable.shenok, imgsDog));

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);


        //Событие при нажатии на карточку
        PuzzleAdapter.OnPuzzleClickListener puzzleClick = new PuzzleAdapter.OnPuzzleClickListener() {
            @Override
            public void onPuzzleClick(PuzzleItem puzzleItem, int position) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("puzzle", puzzleItem);
                startActivity(intent);
            }
        };

        PuzzleAdapter adapter = new PuzzleAdapter(this, listPuzzles, puzzleClick);

        recyclerView.setAdapter(adapter);


    }


}