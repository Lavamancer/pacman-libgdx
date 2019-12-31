package com.lavamancer.game.pathfinder;

import com.badlogic.gdx.ai.pfa.Heuristic;

public class TiledManhattanDistance implements Heuristic<TiledNode> {

    @Override
    public float estimate (TiledNode node, TiledNode endNode) {
        return Math.abs(endNode.x - node.x) + Math.abs(endNode.y - node.y);
    }
}
