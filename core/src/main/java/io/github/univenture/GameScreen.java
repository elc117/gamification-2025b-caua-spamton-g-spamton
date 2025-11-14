package io.github.univenture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport; // Importante
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    private final UniVenture game;
    private final Viewport viewport; // Viewport do Jogo (Mundo)
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private final PlayerEntity player;

    private final Texture npcTexture;
    private final Texture npcTexture2;
    private final List<NpcEntity> npcs;

    private Stage uiStage;
    private Skin skin;
    private Table dialogTable;
    private Label dialogLabel;
    private boolean isDialogVisible = false;

    public GameScreen(UniVenture game) {
        this.game = game;
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.batch = game.getBatch();
        this.backgroundTexture = new Texture("background.png");
        this.player = new PlayerEntity();

        this.npcTexture = new Texture("npc2.png");
        this.npcTexture2 = new Texture("ct_sprite.png");
        this.npcs = new ArrayList<NpcEntity>();

        npcs.add(new NpcEntity(5, 2, 2f, 2f, npcTexture, "Ola, sou um NPC normal.Ola, sou um NPC normal.Ola, sou um NPC normal.Ola, sou um NPC normal.Ola, sou um NPC normal.Ola, sou um NPC normal.Ola, sou um NPC normal.Ola, sou um NPC normal.Ola, sou um NPC normal.Ola, sou um NPC normal."));
        npcs.add(new NpcEntity(15, 20, 10f, 10f, npcTexture2, "aaaaaaaa"));

        uiStage = new Stage(new FitViewport(640, 360));

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        setupDialogUI();
    }

    private void setupDialogUI() {
        dialogTable = new Table();
        dialogTable.setFillParent(true);
        dialogTable.bottom(); // Alinha no fundo da tela virtual

        dialogLabel = new Label("", skin);
        dialogLabel.setWrap(true);

        Window window = new Window("Dialogo", skin);
        window.add(dialogLabel).width(300).pad(10);

        dialogTable.add(window).padBottom(20);

        uiStage.addActor(dialogTable);
        dialogTable.setVisible(false);
    }

    @Override
    public void render(float delta) {
        if (isDialogVisible) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.E) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                isDialogVisible = false;
                dialogTable.setVisible(false);
            }
        } else {
            float mapWidth = backgroundTexture.getWidth() * UniVenture.UNIT_SCALE;
            float mapHeight = backgroundTexture.getHeight() * UniVenture.UNIT_SCALE;

            player.moveCharacter(this.npcs, mapWidth, mapHeight);

            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                checkInteraction();
            }
        }

        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        this.viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        float mapWidth = backgroundTexture.getWidth() * UniVenture.UNIT_SCALE;
        float mapHeight = backgroundTexture.getHeight() * UniVenture.UNIT_SCALE;
        batch.draw(backgroundTexture, 0, 0, mapWidth, mapHeight);

        for (NpcEntity npc : npcs) {
            npc.draw(batch);
        }

        player.draw(batch);
        batch.end();

        uiStage.act(delta);
        uiStage.draw();
    }

    private void checkInteraction() {
        for (NpcEntity npc : npcs) {
            if (npc.isCloseTo(player.getX(), player.getY(), 1.5f)) {
                dialogLabel.setText(npc.getDialogText());
                dialogTable.getChildren().get(0);
                isDialogVisible = true;
                dialogTable.setVisible(true);
                break;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        this.backgroundTexture.dispose();
        this.npcTexture.dispose();
        this.uiStage.dispose();
        this.skin.dispose();
    }
}
