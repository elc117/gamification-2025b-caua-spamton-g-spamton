package io.github.univenture;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private Texture character;
    private Sprite characterSprite;

    public final float WORLD_WIDTH = 16f;
    public final float WORLD_HEIGHT = 12f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        image = new Texture("libgdx.png");
        background = new Texture("background.png");
        character = new Texture("personagem-placeholder.png");
        characterSprite = new Sprite(character);
        characterSprite.setSize(3, 3);
        characterSprite.setPosition(0, 0);
    }

    // metodo resize: atualiza a camera de acordo com o tamanho da janela
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        this.moveCharacter();

        this.camera.position.set(characterSprite.getX() + characterSprite.getWidth() / 2,characterSprite.getY() + characterSprite.getHeight() / 2, 0);
        this.camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, -WORLD_WIDTH, -WORLD_HEIGHT);
        characterSprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        background.dispose();
        character.dispose();
    }

    private void moveCharacter() {
        float speed = 0.5f;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            characterSprite.translateX(-speed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            characterSprite.translateX(speed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            characterSprite.translateY(speed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            characterSprite.translateY(-speed);
        }
    }
}
