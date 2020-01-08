package com.lavamancer.game;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lavamancer.game.entity.Dot;
import com.lavamancer.game.entity.Entity;
import com.lavamancer.game.pathfinder.TiledGraph;
import com.lavamancer.game.pathfinder.TiledManhattanDistance;
import com.lavamancer.game.pathfinder.TiledNode;

public class Map {

    public static final int TILE_SIZE = 30;
    public static final int OFFSET_X = 85;
    public static final int OFFSET_Y = 85;

    private Main main;
    private Sprite wallSprite;
    private int[][] matrix = new int[][] {
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,2,2,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2,1,0},
        {0,1,3,1,1,2,1,1,1,2,1,2,1,1,1,2,1,1,3,1,0},
        {0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0},
        {0,1,2,1,1,2,1,2,1,1,1,1,1,2,1,2,1,1,2,1,0},
        {0,1,2,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,2,1,0},
        {0,1,1,1,1,2,1,1,1,2,1,2,1,1,1,2,1,1,1,1,0},
        {0,0,0,0,1,2,1,0,0,0,0,0,0,0,1,2,1,0,0,0,0},
        {1,1,1,1,1,2,1,0,1,1,0,1,1,0,1,2,1,1,1,1,1},
        {0,0,0,0,0,2,2,0,1,0,0,0,1,0,2,2,0,0,0,0,0},
        {1,1,1,1,1,2,1,0,1,1,1,1,1,0,1,2,1,1,1,1,1},
        {0,0,0,0,1,2,1,0,0,0,0,0,0,0,1,2,1,0,0,0,0},
        {0,1,1,1,1,2,1,2,1,1,1,1,1,2,1,2,1,1,1,1,0},
        {0,1,2,2,2,2,2,2,2,2,1,2,2,2,2,2,2,2,2,1,0},
        {0,1,2,1,1,2,1,1,1,2,1,2,1,1,1,2,1,1,2,1,0},
        {0,1,3,2,1,2,2,2,2,2,0,2,2,2,2,2,1,2,3,1,0},
        {0,1,1,2,1,2,1,2,1,1,1,1,1,2,1,2,1,2,1,1,0},
        {0,1,2,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,2,1,0},
        {0,1,2,1,1,1,1,1,1,2,1,2,1,1,1,1,1,1,2,1,0},
        {0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0}
    };
    private DefaultGraphPath<TiledNode> graphPath = new DefaultGraphPath<>();
    private TiledManhattanDistance heuristic = new TiledManhattanDistance();
    private TiledGraph tiledGraph;
    private IndexedAStarPathFinder<TiledNode> pathFinder;

    public Map(Main main) {
        this.main = main;
        matrix = translateMatrix(matrix);
        Texture wallTexture = new Texture("stoneCenter.png");
        wallSprite = new Sprite(wallTexture);
        wallSprite.setColor(Color.BLUE);


        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                int tile = matrix[x][y];
                if (tile == 2 || tile == 3) {
                    matrix[x][y] = 0;
                    main.entities.add(new Dot(main, x, y, tile == 3));
                }
            }
        }

        tiledGraph = new TiledGraph(this, matrix);
        pathFinder = new IndexedAStarPathFinder<>(tiledGraph, false);
    }

    public void draw(SpriteBatch spriteBatch) {
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                int tile = matrix[x][y];
                if (tile == 1) {
                    wallSprite.setBounds(x * TILE_SIZE + OFFSET_X, y * TILE_SIZE + OFFSET_Y, TILE_SIZE, TILE_SIZE);
                    wallSprite.draw(spriteBatch);
                }
            }
        }
    }

    public int getTile(int x, int y) {
        if (x < 0 || x > getWidth() - 1 || y < 0 || y > getHeight() - 1) return 0;
        return matrix[x][y];
    }


    public boolean existsWall(Entity entity, Entity.Direction direction) {
        switch (direction) {
            case UP: return getTile(entity.x, entity.y + 1) == 1;
            case DOWN: return getTile(entity.x, entity.y - 1) == 1;
            case LEFT: return getTile(entity.x - 1, entity.y) == 1;
            case RIGHT: return getTile(entity.x + 1, entity.y) == 1;
        }
        return false;
    }

    public int getWidth() {
        return matrix[0].length;
    }

    public int getHeight() {
        return matrix.length;
    }

    public int[][] translateMatrix(int[][] matrix) {
        int[][] aux = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                aux[i][j] = matrix[matrix.length - j - 1][i];
            }
        }
        return aux;
    }

    public Entity.Direction getPath(Entity from, Entity to) {
        graphPath.clear();
        pathFinder.searchNodePath(tiledGraph.getNode(from.x, from.y), tiledGraph.getNode(to.x, to.y), heuristic, graphPath);
        if (graphPath.nodes.size > 1) {
            TiledNode node = graphPath.nodes.get(1);
            if (node.x > from.x) return Entity.Direction.RIGHT;
            if (node.x < from.x) return Entity.Direction.LEFT;
            if (node.y > from.y) return Entity.Direction.UP;
            if (node.y < from.y) return Entity.Direction.DOWN;
        }
        return null;
    }

}
