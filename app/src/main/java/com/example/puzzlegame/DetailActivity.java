package com.example.puzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class DetailActivity extends AppCompatActivity {

    private static final int COLUMNS = 3;
    private static final int FILED = COLUMNS * COLUMNS;

    public  final String up = "up";
    public  final String down = "down";
    public  final String left = "left";
    public  final String right = "right";

    private static String[] items;

    private static PuzzleItem item;

    private static DetectGridView gridView;

    private static int columnWidth, columnHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        scramble();

        setDimension();
    }

    private void init(){
        //Инициализируем кастомный GridView
        gridView = (DetectGridView) findViewById(R.id.grid);

        //Назначаем количество столбцов
        gridView.setNumColumns(COLUMNS);
        //Берем передаваемый объект
        item = (PuzzleItem) getIntent().getParcelableExtra("puzzle");

        //Создаем массив строк размер
        items = new String[FILED];
        for (int i = 0; i < FILED; i++){
            //Заполняем массив значениями от 0 до 9
            items[i] = String.valueOf(i);
        }
    }

    private void scramble(){
        int index;
        String temp;
        Random random = new Random();

        for (int i = items.length-1; i > 0; i--){
            index = random.nextInt(i + 1);
            temp = items[index];
            items[index] =  items[i];
            items[i] = temp;
        }
    }

    private void setDimension() {

        ViewTreeObserver vto = gridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                //Получаем отображаемую ширину в зависимости от размера экрана
                int displayWidth = gridView.getMeasuredWidth();

                //Получаем отображаемую высоту в зависимости от размера экрана
                int displayHeight = gridView.getMeasuredHeight();
                //Берем высоту шапки
                int statusBarHeight = getStatusBarHeight(getApplicationContext());

                int requiredHeight = displayHeight - statusBarHeight;


                columnWidth = displayWidth/COLUMNS;
                columnHeight = requiredHeight/COLUMNS;

                display(getApplicationContext());

            }
        });

    }

    private int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen","android");
        if (resourceId > 0){
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    private static void display(Context context){
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;
        for (int i = 0; i < items.length; i++){
            button = new Button(context);
            String position = items[i];
            switch (position){
                case "0":
                    button.setBackgroundResource(item.getPuzzle_items()[0]);
                    break;
                case "1":
                    button.setBackgroundResource(item.getPuzzle_items()[1]);
                    break;
                case "2":
                    button.setBackgroundResource(item.getPuzzle_items()[2]);
                    break;
                case "3":
                    button.setBackgroundResource(item.getPuzzle_items()[3]);
                    break;
                case "4":
                    button.setBackgroundResource(item.getPuzzle_items()[4]);
                    break;
                case "5":
                    button.setBackgroundResource(item.getPuzzle_items()[5]);
                    break;
                case "6":
                    button.setBackgroundResource(item.getPuzzle_items()[6]);
                    break;
                case "7":
                    button.setBackgroundResource(item.getPuzzle_items()[7]);
                    break;
                case "8":
                    button.setBackgroundResource(item.getPuzzle_items()[8]);
                    break;

            }
            buttons.add(button);
        }

        gridView.setAdapter(new CustomGridAdapter(buttons, columnWidth, columnHeight));

    }

    private static void swap(Context context, int position, int swap) {
        String newPosition = items[position + swap];
        items[position + swap] = items[position];
        items[position] = newPosition;
        display(context);

        if (isSolved()){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder.setTitle("Победа")
                    .setMessage("Поздравляю, вы собрали пазл")
                    .setPositiveButton("Завершить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }

    }




    public void movePuzzle(Context context ,String direction, int position) {

        //Условие верхняя линия левая
        if (position == 0) {

            if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

        }
        //Условие верхняя линия центральная
        else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();


        }
        // Условие верхняя линия правая
        else if (position == COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();


        }
        // Условие центральная линия левая
        else if (position > COLUMNS - 1 && position < FILED - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= FILED - COLUMNS - 1) swap(context, position,
                        COLUMNS);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-left corner tile
        } else if (position == FILED - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < FILED - 1 && position > FILED - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();


        } else {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else swap(context, position, COLUMNS);
        }

    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }

        return solved;
    }



}