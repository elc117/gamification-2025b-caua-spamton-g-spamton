package io.github.univenture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class PlayerEntity extends Entity {
    private final Sprite characterSprite;

    public PlayerEntity() {
        super();
        texture = new Texture("sprite_protag.png");
        characterSprite = new Sprite(texture);

        float widthInMeters = 32 * UniVenture.UNIT_SCALE;
        float heightInMeters = 37 * UniVenture.UNIT_SCALE;

        characterSprite.setSize(widthInMeters, heightInMeters);
        characterSprite.setPosition(0, 0);

        size = new Vector2(widthInMeters, heightInMeters);
    }

    public void moveCharacter(List<NpcEntity> obstacles, float mapWidth, float mapHeight) {
        float speed = 0.1f;
        float deltaX = 0;
        float deltaY = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))  deltaX -= speed;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) deltaX += speed;
        if(Gdx.input.isKeyPressed(Input.Keys.UP))    deltaY += speed;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))  deltaY -= speed;

        if (deltaX == 0 && deltaY == 0) return;

        if (deltaX != 0) {
            characterSprite.translateX(deltaX);

            if (checkCollision(obstacles)) {
                characterSprite.translateX(-deltaX);
            }
            clampToMap(mapWidth, mapHeight);
        }

        if (deltaY != 0) {
            characterSprite.translateY(deltaY);

            if (checkCollision(obstacles)) {
                characterSprite.translateY(-deltaY);
            }
            clampToMap(mapWidth, mapHeight);
        }
    }


    private void clampToMap(float mapWidth, float mapHeight) {
        float x = characterSprite.getX();
        float y = characterSprite.getY();
        float w = characterSprite.getWidth();
        float h = characterSprite.getHeight();

        if (x < 0) characterSprite.setX(0);

        else if (x + w > mapWidth) characterSprite.setX(mapWidth - w);

        if (y < 0) characterSprite.setY(0);

        else if (y + h > mapHeight) characterSprite.setY(mapHeight - h);
    }

    private boolean checkCollision(List<NpcEntity> obstacles) {
        Rectangle fullRect = characterSprite.getBoundingRectangle();

        float scaleWidth = 0.6f;
        float scaleHeight = 0.3f;

        float newWidth = fullRect.width * scaleWidth;
        float newHeight = fullRect.height * scaleHeight;


        float newX = (fullRect.x + fullRect.width / 2) - (newWidth / 2);

        float yOffset = fullRect.height * 0.1f;
        float newY = fullRect.y + yOffset;

        Rectangle playerFeetBounds = new Rectangle(newX, newY, newWidth, newHeight);

        for (NpcEntity npc : obstacles) {
            if (npc.getBounds().overlaps(playerFeetBounds)) {
                return true;
            }
        }
        return false;
    }

    public float getX() {
        return characterSprite.getX() + characterSprite.getWidth() / 2;
    }

    public float getY() {
        return characterSprite.getY() + characterSprite.getHeight() / 2;
    }

    public void draw(SpriteBatch batch) {
        characterSprite.draw(batch);
    }
}
