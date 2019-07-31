package com.d954mas.engine.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import static java.lang.Math.*;

public class HSLColor {

    public float h;//0-360 цвет
    public float s;//0-1 насыщенность
    public float l;//0-1 яркость
    public float a;//alpha

    public Color toGdxColor() {
        float c = (1 - abs(2 * l - 1)) * s;
        float x = c * (1 - abs((h / 60) % 2 - 1));
        float m = l - c / 2;

        float r = m, g = m, b = m;

        if (0 <= h && h < 60) {
            r += c;
            g += x;
            b += 0;
        } else if (60 <= h && h < 120) {
            r += x;
            g += c;
            b += 0;
        } else if (120 <= h && h < 180) {
            r += 0;
            g += c;
            b += x;
        } else if (180 <= h && h < 240) {
            r += 0;
            g += x;
            b += c;
        } else if (240 <= h && h < 300) {
            r += x;
            g += 0;
            b += c;
        } else if (300 <= h && h <= 360) {
            r += c;
            g += 0;
            b += x;
        } else throw new IllegalArgumentException("Hue is bad:" + h);

        return new Color(r, g, b, a);
    }

    public HSLColor(Color gdxColor) {
        a = gdxColor.a;
        float rs = gdxColor.r;
        float gs = gdxColor.g;
        float bs = gdxColor.b;

        float cMax = max(max(rs, gs), bs);
        float cMin = min(min(rs, gs), bs);
        float delta = cMax - cMin;


        //hue
        if (delta == 0) h = 0;
        else if (MathUtils.isEqual(cMax, rs)) h = 60 * (((gs - bs) / delta) % 6);
        else if (MathUtils.isEqual(cMax, gs)) h = 60 * (((bs - rs) / delta) + 2);
        else if (MathUtils.isEqual(cMax, bs)) h = 60 * (((rs - gs) / delta) + 4);

        //light
        l = (cMax + cMin) / 2;

        //sat
        if (delta == 0) s = 0;
        else s = delta / (1 - abs(2 * l - 1));
    }

    public HSLColor(float hue, float sat, float light, float a) {
        this.h = hue;
        this.s = sat;
        this.l = light;
        this.a = a;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getS() {
        return s;
    }

    public void setS(float s) {
        this.s = s;
    }

    public float getL() {
        return l;
    }

    public void setL(float l) {
        this.l = l;
    }

    //h:0-360, sl:0-1
    public static Color hslToRgb(float h, float s, float l, float a) {
        return new HSLColor(h, s, l, a).toGdxColor();
    }

    //rgb:0-1
    public static HSLColor rgbToHsl(float r, float g, float b, float a) {
        return new HSLColor(new Color(r, g, b, a));
    }
}
