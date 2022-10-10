package com.example.puzzlegame;


import android.os.Parcel;
import android.os.Parcelable;

public class PuzzleItem  implements Parcelable {
    private int img;
    private int[] puzzle_items;

    public PuzzleItem(Integer img, int[] puzzle_items) {
        this.img = img;
        this.puzzle_items = puzzle_items;
    }


    protected PuzzleItem(Parcel in) {
        img = in.readInt();
        puzzle_items = in.createIntArray();
    }

    public static final Creator<PuzzleItem> CREATOR = new Creator<PuzzleItem>() {
        @Override
        public PuzzleItem createFromParcel(Parcel in) {
            return new PuzzleItem(in);
        }

        @Override
        public PuzzleItem[] newArray(int size) {
            return new PuzzleItem[size];
        }
    };

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public int[] getPuzzle_items() {
        return puzzle_items;
    }

    public void setPuzzle_items(int[] puzzle_items) {
        this.puzzle_items = puzzle_items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(img);
        parcel.writeIntArray(puzzle_items);
    }
}
