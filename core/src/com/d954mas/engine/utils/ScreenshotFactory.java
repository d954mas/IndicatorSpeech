package com.d954mas.engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.nio.ByteBuffer;

public class ScreenshotFactory {


    //TODO отрефакторить, сейчас вроде работает, не хочу смотреть и тестировать
    private static int counter = 1;

    public static void saveScreenshot() {
        counter++;
        String name="screenshot" + counter++ + ".png";
        saveScreenshot(name,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public static void saveScreenshot(String name,int x,int y,int width,int height) {

        try {
            FileHandle fh;
            fh = Gdx.files.external(name);
            Pixmap pixmap = getScreenshot(x, y, width, height, true);
            PixmapIO.writePNG(fh, pixmap);
            pixmap.dispose();
        } catch (Exception e) {
        }
    }

    public static Pixmap getScreenshot(int x, int y, int w, int h, boolean yDown) {

        Gdx.app.log("ScreenshotFactory", "saveScreenshot");
        long begin = TimeUtils.millis();
        Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, w, h);

        if (yDown) {
            pixmap = yDownPixmap(pixmap);
        }
        System.out.println(("ScreenshotFactory " + "time:" + (TimeUtils.millis() - begin)));
        return pixmap;

    }

    public static Pixmap yDownPixmap(Pixmap pixmap) {
        // Flip the pixmap upside down
        ByteBuffer pixels = pixmap.getPixels();
        int w = pixmap.getWidth();
        int h = pixmap.getHeight();
        int numBytes = w * h * 4;
        byte[] lines = new byte[numBytes];
        int numBytesPerLine = w * 4;
        for (int i = 0; i < h; i++) {
            pixels.position((h - i - 1) * numBytesPerLine);
            pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
        }
        pixels.clear();
        pixels.put(lines);
        return pixmap;
    }

    public static Pixmap flipPixmap(Pixmap pixmap) {
        final int width = pixmap.getWidth();
        final int height = pixmap.getHeight();
        Pixmap flipped = new Pixmap(width, height, pixmap.getFormat());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                flipped.drawPixel(x, y, pixmap.getPixel(x, height - y - 1));
            }
        }
        pixmap.dispose();
        return flipped;
    }
}