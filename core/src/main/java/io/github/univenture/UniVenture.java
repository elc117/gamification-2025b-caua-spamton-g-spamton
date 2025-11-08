package io.github.univenture;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class UniVenture extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private Texture image;
    private Texture background;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private PlayerEntity playerEntity;

    public final float WORLD_WIDTH = 16f;
    public final float WORLD_HEIGHT = 12f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        image = new Texture("libgdx.png");
        background = new Texture("background.png");
        tiledMap = new TmxMapLoader().load("protomap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    // metodo resize: atualiza a camera de acordo com o tamanho da janela
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        playerEntity.moveCharacter();

        //define posiçao da camera para o centro do sprite, segue
        this.camera.position.set(playerEntity.getX() + playerEntity.getWidth() / 2,playerEntity.getY() + playerEntity.getHeight() / 2, 0);
        this.camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        playerEntity.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        background.dispose();
    }

}
