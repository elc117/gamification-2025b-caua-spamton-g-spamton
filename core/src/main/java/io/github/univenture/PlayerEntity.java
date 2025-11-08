package io.github.univenture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerEntity extends Entity {
    private final Sprite characterSprite;
    private TextureRegion region;

    public PlayerEntity() {
        super();
        texture = new Texture("personagem-placeholder.png");
        characterSprite = new Sprite(texture);
        characterSprite.setSize(3, 3);
        characterSprite.setPosition(0, 0);
        size = new Vector2(32, 37);
    }

    public void moveCharacter() {
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

    public float getX() {
        return  characterSprite.getX();
    }

    public float getY() {
        return  characterSprite.getY();
    }

    public float getWidth() {
        return characterSprite.getWidth();
    }

    public float getHeight() {
        return characterSprite.getHeight();
    }

    public void draw(SpriteBatch batch) {
        float x = position.x - size.x / 2.0f;
        float y = position.y;
        batch.draw(region, x, y);
    }
}
