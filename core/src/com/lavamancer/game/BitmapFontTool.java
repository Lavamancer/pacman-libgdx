package com.lavamancer.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class BitmapFontTool {

    private static final GlyphLayout layout = new GlyphLayout();

    public static float getWidth(BitmapFont bitmapFont, String text) {
        layout.setText(bitmapFont, text);
        return layout.width;
    }

}
