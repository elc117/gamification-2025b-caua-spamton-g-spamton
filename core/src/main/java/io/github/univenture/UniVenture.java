package io.github.univenture;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class UniVenture extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture image;
    private Texture background;
    private Texture character;
    private Sprite characterSprite;
    private float posX, posY;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        image = new Texture("libgdx.png");
        background = new Texture("background.png");
        character = new Texture("char-placeholder.png");
        characterSprite = new Sprite(character);
    }

    @Override
    public void render() {
        this.moveCharacter();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(image, 140, 210);
        batch.draw(characterSprite, posX, posY);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    private void moveCharacter() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            posX -= 10;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            posX += 10;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            posY += 10;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            posY -= 10;
        }
    }
}
