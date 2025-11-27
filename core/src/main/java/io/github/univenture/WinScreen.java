package io.github.univenture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class WinScreen extends ScreenAdapter {
    private final UniVenture game;
    private Stage stage;
    private Skin skin;

    private Texture backgroundTexture;
    private Texture parabensTexture;

    public WinScreen(UniVenture game) {
        this.game = game;
        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("level-plane-ui.json"));

        backgroundTexture = new Texture(Gdx.files.internal("background-title.png"));
        parabensTexture = new Texture(Gdx.files.internal("parabens.png"));

        Table table = new Table();
        table.setFillParent(true);

        Image parabensImage = new Image(parabensTexture);

        table.add(parabensImage).center();

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.getBatch().setProjectionMatrix(stage.getCamera().combined);

        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        game.getBatch().end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {

        if (backgroundTexture != null) backgroundTexture.dispose();
        if (parabensTexture != null) parabensTexture.dispose();

        stage.dispose();
        skin.dispose();
    }
}
