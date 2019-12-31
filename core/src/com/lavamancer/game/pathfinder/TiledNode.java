package com.lavamancer.game.pathfinder;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;
import com.lavamancer.game.Map;

public class TiledNode {

    private Array<Connection<TiledNode>> connections = new Array<>();
    private Map map;

    public int tile;
    public int x;
    public int y;


    public TiledNode(Map map, int x, int y, int tile) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.tile = tile == 1 ? 1 : 0;
    }

    public int getIndex() {
        return x * map.getHeight() + y;
    }

    public Array<Connection<TiledNode>> getConnections() {
        return connections;
    }


}
