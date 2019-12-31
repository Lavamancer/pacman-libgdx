package com.lavamancer.game.pathfinder;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.lavamancer.game.Map;

import java.util.ArrayList;

public class TiledGraph implements IndexedGraph<TiledNode> {

    private ArrayList<TiledNode> nodes = new ArrayList<>();
    private int[][] matrix;
    private Map map;


    public TiledGraph(Map map, int[][] matrix) {
        this.map = map;
        this.matrix = matrix;

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                nodes.add(new TiledNode(map, x, y, matrix[x][y]));
            }
        }

        for (TiledNode node : nodes) {
            if (node.x > 0) addConnection(node, -1, 0);
            if (node.y > 0) addConnection(node, 0, -1);
            if (node.x < map.getWidth() - 1) addConnection(node, 1, 0);
            if (node.y < map.getHeight() - 1) addConnection(node, 0, 1);
        }
    }

    public TiledNode getNode(int x, int y) {
        return nodes.get(x * map.getHeight() + y);
    }

    private void addConnection (TiledNode node, int xOffset, int yOffset) {
        TiledNode target = getNode(node.x + xOffset, node.y + yOffset);
        if (target.tile != 1) {
            node.getConnections().add(new DefaultConnection<>(node, target));
        }
    }

    @Override
    public int getIndex(TiledNode tiledNode) {
        return tiledNode.getIndex();
    }

    @Override
    public int getNodeCount() {
        return nodes.size();
    }

    @Override
    public Array<Connection<TiledNode>> getConnections(TiledNode tiledNode) {
        return tiledNode.getConnections();
    }

}
