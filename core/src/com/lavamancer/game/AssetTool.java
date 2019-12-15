package com.lavamancer.game;

import com.badlogic.gdx.assets.AssetManager;

public class AssetTool {

    private static AssetTool instance;
    private AssetTool() { }
    public static AssetTool getInstance() {
        if (instance == null) instance = new AssetTool();
        return instance;
    }

    private AssetManager assetManager = new AssetManager();


    public <T> T load(String assetPath, Class clazz) {
        if (!assetManager.contains(assetPath)) {
            assetManager.load(assetPath, clazz);
            assetManager.finishLoading();
        }
        return (T) assetManager.get(assetPath, clazz);
    }

}
