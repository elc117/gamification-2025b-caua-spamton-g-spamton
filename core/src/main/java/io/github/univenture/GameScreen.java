package io.github.univenture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameScreen extends ScreenAdapter {
    private final UniVenture game;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private final PlayerEntity player;

    private final Map<String, Texture> textureCache;

    private final List<NpcEntity> npcs;
    private Rectangle playableArea;

    private Stage uiStage;
    private Skin skin;
    private Table dialogTable;
    private Label dialogLabel;
    private boolean isDialogVisible = false;

    private TextButton yesButton;
    private TextButton noButton;
    private boolean isQuizPrompt = false;

    public GameScreen(UniVenture game) {
        this.game = game;
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.batch = game.getBatch();
        this.backgroundTexture = new Texture("mapa_ufsm2.png");
        this.player = new PlayerEntity();

        this.textureCache = new HashMap<String, Texture>();
        this.npcs = new ArrayList<NpcEntity>();

        addNpc(10, 10, 2f, 2f, "npc2.png", "Bem vindo a UFSM, Silveira! Sabia que essa foi a primeira universidade do Brasil fora de uma capital? Nos somos pioneiros nesse quesito!");
        addNpc(15, 20, 2f, 2f, "npc.png", "quiz");
        addNpc(20, 5, 2f, 2f, "npc3.png", "Sabia que o curso de Informatica da UFSM nasceu em 1989? Faz um tempinho, mas nao eh muito se voce parar pra pensar...");
        addNpc(25, 25, 2f, 2f, "npc4.png", "Voce conhece a linguagem Java? Nos estudamos muito ela por aqui. Ela eh a principal linguagem da Programacao Orientada a Objetos!");
        addNpc(34, 22, 2f, 2f, "npc5.png", "Os cursos bacharelados da computacao começaram com a ciencia da computacao, mas em 2009 nos recebemos tambem os cursos de Sistemas de Informacao e Engenharia da Computacao. Legal, nao?");
        addNpc(28, 13, 2f, 2f, "npc6.png", "Uma das primeiras e principais linguagens que voce aprende por aqui eh a linguagem C. Complicada, porem necessaria.");
        addNpc(18, 25, 2f, 2f, "npc1.png", "A maioria das aulas dos cursos de computacao acontecem no Centro de Tecnologia (CT), onde ficam varios laboratorios para programar na pratica!");

        addNpc(32, 27, 13f, 8f, "build.png", "Aqui eh o CT, onde voce vai ter aulas de programacao!");
        addNpc(14, 27, 13f, 8f, "build2.png", "Aqui eh o CCNE, onde voce vai ter aulas relacionadas a matematica!");


        float mapWidth = backgroundTexture.getWidth() * UniVenture.UNIT_SCALE;
        float mapHeight = backgroundTexture.getHeight() * UniVenture.UNIT_SCALE;

        float paddingX = 7.5f;
        float paddingY = 3.0f;

        this.playableArea = new Rectangle(paddingX, paddingY, mapWidth - (paddingX * 2), mapHeight - (paddingY * 2));

        uiStage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("level-plane-ui.json"));

        setupDialogUI();
    }

    private void addNpc(float x, float y, float w, float h, String texturePath, String dialog) {
        Texture texture;

        if (textureCache.containsKey(texturePath)) {
            texture = textureCache.get(texturePath);
        } else {
            texture = new Texture(texturePath);
            textureCache.put(texturePath, texture);
        }

        npcs.add(new NpcEntity(x, y, w, h, texture, dialog));
    }

    private void setupDialogUI() {
        dialogTable = new Table();
        dialogTable.setFillParent(true);
        dialogTable.bottom();

        dialogLabel = new Label("", skin);
        dialogLabel.setWrap(true);

        Window window = new Window("Estudante de Computacao", skin);
        window.add(dialogLabel).width(500).pad(10).row();

        Table buttonTable = new Table();
        yesButton = new TextButton("Sim", skin);
        noButton = new TextButton("Nao", skin);

        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isQuizPrompt) {
                    closeDialog();
                    game.setScreen(QuizScreen.class);
                }
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeDialog();
            }
        });

        buttonTable.add(yesButton).padRight(20);
        buttonTable.add(noButton);

        window.add(buttonTable).center();

        dialogTable.add(window).padBottom(20);

        uiStage.addActor(dialogTable);

        dialogTable.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
    }

    private void closeDialog() {
        isDialogVisible = false;
        dialogTable.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
        isQuizPrompt = false;
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        if (isDialogVisible) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.E) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                isDialogVisible = false;
                dialogTable.setVisible(false);
            }
        } else {
            player.moveCharacter(this.npcs, this.playableArea);

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

        float fullMapWidth = backgroundTexture.getWidth() * UniVenture.UNIT_SCALE;
        float fullMapHeight = backgroundTexture.getHeight() * UniVenture.UNIT_SCALE;
        batch.draw(backgroundTexture, 0, 0, fullMapWidth, fullMapHeight);

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

                boolean isQuizNpc = npc.getDialogText().equals("quiz");

                dialogLabel.setText(isQuizNpc ? "Se voce ja falou com os estudantes, venha fazer o quiz final para testar seus conhecimentos!" : npc.getDialogText());

                isDialogVisible = true;
                dialogTable.setVisible(true);

                Gdx.input.setInputProcessor(uiStage);

                if (isQuizNpc) {
                    isQuizPrompt = true;
                    yesButton.setVisible(true);
                    noButton.setVisible(true);
                } else {
                    isQuizPrompt = false;
                    yesButton.setVisible(false);
                    noButton.setVisible(false);
                }
                break;
            }
        }
    }

    public void showFeedback(String message) {
        dialogLabel.setText(message);
        isDialogVisible = true;
        dialogTable.setVisible(true);
        yesButton.setVisible(false);
        noButton.setVisible(false);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        this.backgroundTexture.dispose();

        for (Texture t : textureCache.values()) {
            t.dispose();
        }

        this.uiStage.dispose();
        this.skin.dispose();
    }
}
