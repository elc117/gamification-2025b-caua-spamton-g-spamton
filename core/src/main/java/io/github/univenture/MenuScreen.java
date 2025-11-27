package io.github.univenture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {

    private final UniVenture game;
    private Stage stage;
    private Skin skin;

    private Texture logoTexture;
    private Texture backgroundTexture;

    public MenuScreen(UniVenture game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("level-plane-ui.json"));

        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        backgroundTexture = new Texture(Gdx.files.internal("background-title.png"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Image logoImage = new Image(logoTexture);

        TextButton startButton = new TextButton("Iniciar Jogo", skin);
        TextButton exitButton = new TextButton("Sair", skin);

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(GameScreen.class);
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.add(logoImage).padBottom(50);
        table.row();
        table.add(startButton).width(200).height(50).padBottom(20);
        table.row();
        table.add(exitButton).width(200).height(50);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        game.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        if (logoTexture != null) logoTexture.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();

        stage.dispose();
        skin.dispose();
    }
}
