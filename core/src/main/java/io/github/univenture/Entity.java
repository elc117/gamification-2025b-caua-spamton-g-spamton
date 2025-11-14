package io.github.univenture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Entity {
    protected Vector2 position;
    protected Texture texture;
    protected Vector2 size;

    public Entity(Vector2 position, Texture texture, Vector2 size) {
        this.position = position;
        this.texture = texture;
        this.size = size;
    }

    public Entity() {
        position = new Vector2(0, 0);
        texture = new Texture("libgdx.png");
        size = new Vector2(32, 32);
    }

    public void draw(SpriteBatch batch) {
        float x = position.x - size.x / 2.0f;
        float y = position.y;
        batch.draw(texture, x, y);
    }
}
