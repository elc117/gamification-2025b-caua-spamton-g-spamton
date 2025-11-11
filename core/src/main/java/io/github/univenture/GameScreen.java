package io.github.univenture;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    private final UniVenture game;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final Batch batch;
    private final TiledMap tiledMap;

    private final OrthogonalTiledMapRenderer tiledMapRenderer;


    public GameScreen(UniVenture game) {
        this.game = game;
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.tiledMap = new TmxMapLoader().load("maps/protomap.tmx");
        this.batch = game.getBatch();
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(null, UniVenture.UNIT_SCALE, this.batch);
    }

    public void show(){
        this.tiledMapRenderer.setMap(tiledMap);
    }

    public void render(float delta){
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f); //importante! limpa a tela

        this.viewport.apply();
        this.batch.setColor(Color.WHITE);

        this.tiledMapRenderer.setView(this.camera);
        this.tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
    }

    public void dispose(){
        this.tiledMapRenderer.dispose();
    }
}
