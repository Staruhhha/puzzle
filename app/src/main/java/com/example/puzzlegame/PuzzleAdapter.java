package com.example.puzzlegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PuzzleAdapter extends RecyclerView.Adapter<PuzzleAdapter.ViewHolder> {

    interface OnPuzzleClickListener{
        void onPuzzleClick(PuzzleItem puzzleItem, int position);
    }

    private final OnPuzzleClickListener onClickListener;

    private Context context;
    private ArrayList<PuzzleItem> listPuzzle;



    PuzzleAdapter(Context context, ArrayList<PuzzleItem> list, OnPuzzleClickListener onClickListener){
        this.context = context;
        this.listPuzzle = list;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.puzzle_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        PuzzleItem puzzle = listPuzzle.get(position);
        holder.imgView.setBackgroundResource(puzzle.getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onPuzzleClick(puzzle, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listPuzzle.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgView;

        ViewHolder(View view){
            super(view);
            imgView = view.findViewById(R.id.img_item);
        }
    }
}
