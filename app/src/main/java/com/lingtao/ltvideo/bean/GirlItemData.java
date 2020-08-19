package com.lingtao.ltvideo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lingtao.ltvideo.service.INetWorkPicture;

import java.io.Serializable;

/**
 * Created by cl on 2018/5/3.
 */

public class GirlItemData implements Parcelable, INetWorkPicture {
    private String title;
    private String url;
    private int id;
    private int width;
    private int height;
    private String subtype;

    public GirlItemData() {
    }

    public GirlItemData(int id) {
        this.id = id;
    }

    public GirlItemData(String url) {
        this.url = url;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String getNetUrl() {
        return url;
    }

    @Override
    public int getRes() {
        return id;
    }

    @Override
    public void onWidth(int width) {
        this.width = width;
    }

    @Override
    public void onHeight(int height) {
        this.height = height;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int detailUrl) {
        this.id = detailUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeInt(id);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(subtype);
    }

    protected GirlItemData(Parcel in) {
        title = in.readString();
        url = in.readString();
        id = in.readInt();
        width = in.readInt();
        height = in.readInt();
        subtype = in.readString();
    }

    public static final Creator<GirlItemData> CREATOR = new Creator<GirlItemData>() {
        @Override
        public GirlItemData createFromParcel(Parcel in) {
            return new GirlItemData(in);
        }

        @Override
        public GirlItemData[] newArray(int size) {
            return new GirlItemData[size];
        }
    };
}
