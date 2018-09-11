package com.kesatriakeyboard.kesatriadictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WordModel implements Parcelable {

    private int id;
    private String word;
    private String translation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.translation);
    }

    public WordModel() {
        // empty constructor
    }

    public WordModel(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public WordModel(int id, String word, String translation) {
        this.id = id;
        this.word = word;
        this.translation = translation;
    }

    protected WordModel(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.translation = in.readString();
    }

    public static final Parcelable.Creator<WordModel> CREATOR = new Parcelable.Creator<WordModel>() {
        @Override
        public WordModel createFromParcel(Parcel source) {
            return new WordModel(source);
        }

        @Override
        public WordModel[] newArray(int size) {
            return new WordModel[size];
        }
    };
}