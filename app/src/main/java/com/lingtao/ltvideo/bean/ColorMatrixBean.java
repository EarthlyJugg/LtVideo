package com.lingtao.ltvideo.bean;

import android.graphics.ColorMatrix;

public class ColorMatrixBean {

    private String name;
    private ColorMatrix colorMatrix;


    public ColorMatrixBean(String name,ColorMatrix colorMatrix) {
        this.colorMatrix = colorMatrix;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColorMatrix getColorMatrix() {
        return colorMatrix;
    }

    public void setColorMatrix(ColorMatrix colorMatrix) {
        this.colorMatrix = colorMatrix;
    }
}
