package io.github.univenture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;
import java.util.List;

public class QuizScreen extends ScreenAdapter {
    private final UniVenture game;
    private Stage stage;
    private Skin skin;

    private Texture backgroundTexture;

    private Label questionLabel;
    private TextButton[] answerButtons;

    private List<Question> questions;
    private int currentQuestionIndex = 0;

    public QuizScreen(UniVenture game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(640, 360));
        this.skin = new Skin(Gdx.files.internal("level-plane-ui.json"));


        this.backgroundTexture = new Texture(Gdx.files.internal("background-title.png"));

        createQuestions();
        setupUI();
    }

    private void createQuestions() {
        questions = new ArrayList<Question>();
        JsonReader json = new JsonReader();

        if (Gdx.files.internal("questions.json").exists()) {
            JsonValue baseValue = json.parse(Gdx.files.internal("questions.json"));
            for (JsonValue entry : baseValue) {
                String text = entry.getString("text");
                String[] options = entry.get("options").asStringArray();
                int correctIndex = entry.getInt("correctAnswerIndex");
                questions.add(new Question(text, options, correctIndex));
            }
        } else {
            String[] opts = {"A", "B", "C", "D"};
            questions.add(new Question("Erro: questions.json nao encontrado", opts, 0));
        }
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);

        questionLabel = new Label("", skin);
        questionLabel.setWrap(true);

        questionLabel.setFontScale(1.5f);

        table.add(questionLabel).width(550).padBottom(30).row();

        answerButtons = new TextButton[4];
        for (int i = 0; i < 4; i++) {
            final int index = i;
            answerButtons[i] = new TextButton("", skin);

            answerButtons[i].getLabel().setFontScale(1.2f);

            answerButtons[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    checkAnswer(index);
                }
            });

            table.add(answerButtons[i]).width(450).height(50).pad(8).row();
        }

        stage.addActor(table);
    }

    private void loadQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            game.setScreen(WinScreen.class);
            return;
        }

        Question q = questions.get(currentQuestionIndex);
        questionLabel.setText(q.getText());

        String[] opts = q.getOptions();
        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(opts[i]);
        }
    }

    private void checkAnswer(int index) {
        Question q = questions.get(currentQuestionIndex);
        if (index == q.getCorrectAnswerIndex()) {
            currentQuestionIndex++;
            loadQuestion();
        } else {
            GameScreen gameScreen = (GameScreen) game.getScreen(GameScreen.class);
            gameScreen.showFeedback("Voce errou! Tente novamente.");
            game.setScreen(GameScreen.class);
            resetQuiz();
        }
    }

    private void resetQuiz() {
        currentQuestionIndex = 0;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        loadQuestion();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().setProjectionMatrix(stage.getCamera().combined);

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
    public void dispose() {
        if (backgroundTexture != null) backgroundTexture.dispose();
        stage.dispose();
        if (skin != null) skin.dispose();
    }
}
