package io.github.univenture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class PlayerEntity extends Entity {
    private final Sprite characterSprite;

    private Animation<TextureRegion> walkDown;
    private Animation<TextureRegion> walkLeft;
    private Animation<TextureRegion> walkRight;
    private Animation<TextureRegion> walkUp;

    private float stateTime;
    private int currentDirection;

    private static final int DIR_DOWN = 3;
    private static final int DIR_LEFT = 2;
    private static final int DIR_RIGHT = 1;
    private static final int DIR_UP = 0;

    public PlayerEntity() {
        super();

        texture = new Texture("spritesheet.png");

        int frameCols = 2;
        int frameRows = 4;

        TextureRegion[][] tmp = TextureRegion.split(texture,
            texture.getWidth() / frameCols,
            texture.getHeight() / frameRows);

        walkDown  = new Animation<TextureRegion>(0.15f, tmp[3]);
        walkLeft  = new Animation<TextureRegion>(0.15f, tmp[2]);
        walkRight = new Animation<TextureRegion>(0.15f, tmp[1]);
        walkUp    = new Animation<TextureRegion>(0.15f, tmp[0]);

        characterSprite = new Sprite(tmp[0][0]);

        float widthInMeters = 32 * UniVenture.UNIT_SCALE;
        float heightInMeters = 37 * UniVenture.UNIT_SCALE;

        characterSprite.setSize(widthInMeters, heightInMeters);
        characterSprite.setPosition(10, 5); // Posição inicial

        size = new Vector2(widthInMeters, heightInMeters);
        stateTime = 0f;
        currentDirection = DIR_DOWN;
    }

    public void moveCharacter(List<NpcEntity> obstacles, Rectangle mapBounds) {
        float speed = 0.1f;
        float deltaX = 0;
        float deltaY = 0;
        boolean isMoving = false;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            deltaX -= speed;
            currentDirection = DIR_LEFT;
            isMoving = true;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            deltaX += speed;
            currentDirection = DIR_RIGHT;
            isMoving = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            deltaY += speed;
            currentDirection = DIR_UP;
            isMoving = true;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            deltaY -= speed;
            currentDirection = DIR_DOWN;
            isMoving = true;
        }

        TextureRegion currentFrame = null;

        if (isMoving) {
            stateTime += Gdx.graphics.getDeltaTime();

            switch (currentDirection) {
                case DIR_DOWN:  currentFrame = walkDown.getKeyFrame(stateTime, true); break;
                case DIR_LEFT:  currentFrame = walkLeft.getKeyFrame(stateTime, true); break;
                case DIR_RIGHT: currentFrame = walkRight.getKeyFrame(stateTime, true); break;
                case DIR_UP:    currentFrame = walkUp.getKeyFrame(stateTime, true); break;
            }
        } else {
            stateTime = 0;
            switch (currentDirection) {
                case DIR_DOWN:  currentFrame = walkDown.getKeyFrame(0); break;
                case DIR_LEFT:  currentFrame = walkLeft.getKeyFrame(0); break;
                case DIR_RIGHT: currentFrame = walkRight.getKeyFrame(0); break;
                case DIR_UP:    currentFrame = walkUp.getKeyFrame(0); break;
            }
        }

        characterSprite.setRegion(currentFrame);


        if (deltaX == 0 && deltaY == 0) return;

        if (deltaX != 0) {
            characterSprite.translateX(deltaX);
            if (checkCollision(obstacles)) {
                characterSprite.translateX(-deltaX);
            }
            clampToMap(mapBounds);
        }

        if (deltaY != 0) {
            characterSprite.translateY(deltaY);
            if (checkCollision(obstacles)) {
                characterSprite.translateY(-deltaY);
            }
            clampToMap(mapBounds);
        }
    }

    private void clampToMap(Rectangle bounds) {
        float x = characterSprite.getX();
        float y = characterSprite.getY();
        float w = characterSprite.getWidth();
        float h = characterSprite.getHeight();

        if (x < bounds.x) characterSprite.setX(bounds.x);
        else if (x + w > bounds.x + bounds.width) characterSprite.setX(bounds.x + bounds.width - w);

        if (y < bounds.y) characterSprite.setY(bounds.y);
        else if (y + h > bounds.y + bounds.height) characterSprite.setY(bounds.y + bounds.height - h);
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
