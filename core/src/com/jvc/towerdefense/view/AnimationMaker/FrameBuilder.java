package com.jvc.towerdefense.view.AnimationMaker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Builds a textureRegion with frames in the image.
 */
public class FrameBuilder {
    // Temporary region
    private TextureRegion[][] tmp;
    // returned region
    private TextureRegion[] frames;

    public FrameBuilder(String filename, int columns, int rows) {
        Texture img = new Texture(filename);
        tmp = TextureRegion.split(img, img.getWidth()/columns, img.getHeight()/rows);
        frames = new TextureRegion[columns*rows];
        int index=0;
        for(int i=0; i<rows; i++)
            for(int j=0; j<columns; j++) {
                frames[index++] = tmp[i][j];
            }
    }

    public TextureRegion[] getFrames() {
        return frames;
    }
}
