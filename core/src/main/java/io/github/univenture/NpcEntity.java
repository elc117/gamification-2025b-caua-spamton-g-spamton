package io.github.univenture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class NpcEntity extends Entity {
    private String dialogText;
    private Rectangle bounds;

    public NpcEntity(float x, float y, float width, float height, Texture texture, String text) {
        super();
        this.position = new Vector2(x, y);
        this.texture = texture;
        this.dialogText = text;
        this.size = new Vector2(width, height);


        float boundsWidth = width * 0.7f;
        float boundsHeight = height;


        float yOffset = height * 0.2f;

        this.bounds = new Rectangle(x - boundsWidth / 2, y + yOffset, boundsWidth, boundsHeight
        );
    }

    public String getDialogText() {
        return dialogText;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isCloseTo(float playerX, float playerY, float distanceThreshold) {
        return position.dst(playerX, playerY) < distanceThreshold;
    }

    @Override
    public void draw(SpriteBatch batch) {
        float drawX = position.x - size.x / 2.0f;
        float drawY = position.y;
        batch.draw(texture, drawX, drawY, size.x, size.y);
    }
}
