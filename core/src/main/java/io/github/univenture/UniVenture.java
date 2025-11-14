package io.github.univenture;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;


public class UniVenture extends Game {
    public static final float WORLD_WIDTH = 16f;
    public static final float WORLD_HEIGHT = 9f;
    public static final float UNIT_SCALE = 1f/16f; // unidades de medida sao representadas por base 16

    private Batch batch; //para render
    private OrthographicCamera camera; //camera top-down
    private Viewport viewport;
    private AssetManager manager;
//tamanho da camera

    private final Map<Class<? extends Screen>, Screen> screenCache = new HashMap<Class<? extends Screen>, Screen>();

    @Override
    public void create() {
        this.batch = new SpriteBatch(); //classe que renderiza graficos na tela
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        addScreen(new GameScreen(this));
        setScreen(GameScreen.class);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void addScreen(Screen screen) {

        screenCache.put(screen.getClass(), screen);
    }

    public void setScreen(Class<? extends Screen> screenClass) {
        Screen screen = screenCache.get(screenClass);
        if (screen == null) {
            throw new GdxRuntimeException("Screen " + screenClass.getSimpleName() + " not found in cache");
        }
        super.setScreen(screen);
    }

    @Override
    public void dispose() {
        screenCache.clear();
        this.batch.dispose();
    }

    public Batch getBatch() {
        return batch;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
