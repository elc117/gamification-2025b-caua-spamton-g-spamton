<div align="center">
  <h1 align="center">UniVenture UFSM 🗺️</h1> 
  <h3 align="center">Gamification - Paradigmas de programação</h3>
  <p align="center">Universidade Federal de Santa Maria<br><br>Aluno: Cauã Welter da Silva | Curso: Sistemas de Informação</p>
</div>

## 1. Proposta do projeto 🎯 

### 1.1 Objetivo

Conforme o tema de "gamification", proposto pela professora, esse projeto tem como objetivo
desenvolver um jogo, utilizando a biblioteca libGDX da linguagem de programação Java,
que permita ao jogador explorar e aprender sobre fatos relacionados aos cursos de computação da UFSM. Os principais objetivos desse trabalho são:
- Desenvolver um jogo didático e lúdico, que possa passar aos jogador informações importantes sobre a universidade e os cursos de computação;
- Aplicar criações artísticas para garantir maior aproveitamento ao jogador;
- Exercitar conceitos fundamentais do paradigma de programação orientada a objetos.

### 1.2 Inspirações e ideias: 

Pretendo desenvolver um jogo em que seja possível movimentar o personagem pelas 4
direções (cima, baixo, esquerda, direita) para explorar o mapa do jogo. Em cada mapa,
alguns personagens interagíveis estarão presentes, cada um com uma linha de diálogo
diferente, que revelarão informações sobre a UFSM e os cursos de computação. Essa estrutura
básica de jogo já foi explorada em várias obras, com "Undertale" sendo uma das principais.
Se trata de um jogo lançado em 2015 e desenvolvido por um time de apenas uma pessoa.
O jogo contém elementos de RPG, como batalhas e itens, que não serão integrados ao projeto,
por conta de sua complexidade, mas o seu estilo e estrutura servirão de inspiração.

<img width="500" height="500" alt="sprite-plano" src="https://media1.tenor.com/m/pylTHrd5YoAAAAAd/undertale-video.gif" />

> GIF de gameplay do jogo Undertale. A movimentação do personagem do jogador e as interações com personagens não
> jogáveis são inspirações para o projeto.

O mapa do jogo será um recorte da UFSM, onde o jogador pode interagir com personagens para descobrir informações sobre
a UFSM e os cursos de computação. É
importante constar que os diálogos dos personagens servirão como fonte de informações
para concluir o quiz. Isso significa que cada elemento tem uma funcionalidade como objetivo.

## 2. Desenvolvimento:

### 2.1 Primeiros passos:

Para desenvolver o jogo, optei por quebrar o problema em várias partes pequenas e
construir cada funcionalidade aos poucos, revisando e refazendo o código conforme 
necessário.  
  
Meu primeiro objetivo foi criar um objeto que o jogador possa controlar para se mover.
Essa é uma funcionalidade simples ensinada na própria wiki do libGDX. Para implementá-la,
é necessário criar um objeto da classe Sprite para o personagem (um sprite é criado a partir de uma textura, ou seja, uma imagem)
para que o programa o desenhe corretamente conforme sua posição muda entre os vértices X e Y. Além disso,
a classe Sprite possui vários métodos que serão úteis futuramente.  
Para possibilitar o movimento do sprite de acordo com o input do jogador, criei um metódo
básico `moveCharacter()`:

```java
    private void moveCharacter() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            posX -= 5;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            posX += 5;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            posY += 5;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            posY -= 5;
        }
    }
```

O código utiliza métodos importados da classe Gdx (`import com.badlogic.gdx.Gdx;`), que lidam com 
o input das setas direcionais do teclado. Dependendo da direção, o método adiciona à variável posX ou posY
5 unidades, podendo ser um deslocamento positivo (para direita ou para cima) ou negativo
(para esquerda ou para baixo) no plano. Essas variáveis são então associadas ao sprite no método que renderiza
a tela `render()`, atualizando assim a posição do sprite na tela. 

<img width="500" height="500" alt="sprite-plano" src="https://github.com/user-attachments/assets/0a305b17-7b81-4186-9a5a-a3bf2dc9437a" />

> O quadrado se move!. 

Entretanto, essa é uma maneira bem simples e desnecessária de implementar essa funcionalidade.
O tutorial "A Simple Game" da wiki do libGDX utiliza do método da classe Sprite `translateX` (ou Y),
que altera diretamente a posição do sprite de forma que não é necessário especificar para `render()`
sua posição. A velocidade de movimento também foi padronizada por uma única variável.
  
```java
    private void moveCharacter() {
        float speed = 5f;
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
```

### 2.2 Ajustando a câmera
Para o jogo, minha ideia era que a câmera acompanhasse o personagem do jogador conforme ele
anda pelo mapa. Essa foi uma etapa extremamente demorada, e admito que não foi necessária para o trabalho,
visto que suas complicações não são muito relacionadas à orientação a objetos, mas sim a funcionalidades
gráficas da biblioteca e a maneira como elas devem ser implementadas. Era possível implementar a câmera do jogo
de maneiras mais simples sem causar prejuízo ao objetivo do jogo.  
  
Como recomendado pela wiki do libGDX, medidas em pixels para definir o tamanho de elementos do jogo
foram substituídas por uma convenção de unidade de medida. Isso levou à criação das constantes `WORLD_WIDTH`
e `WORLD_HEIGHT` (utilizam o modificador `final` para impedir alterações), que definem para o objeto `viewport` da 
classe `FitViewport` (derivada de uma classe mais genérica `Viewport`) o tamanho da área visível no jogo dada uma 
determinada posição. O objeto `camera` também se relaciona ao `viewport` e as constantes, e ele é quem define a posição
da câmera do jogo. 

```java
public final float WORLD_WIDTH = 16f;
public final float WORLD_HEIGHT = 12f;
```

```java
camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
```

O objeto `camera` é da classe `OrtographicCamera`, um tipo de câmera específico para jogos 2D. Para fazer a câmera seguir
o personagem do jogador conforme ele se desloca pelo mapa, foi necessário, no metódo `render()`, definir a posição da a 
nova posição da câmera após cada movimento e, logo após, atualizá-la. Para definir a posição central do sprite (para que
a câmera fique fixa ali), é necessário extrair o X do sprite (sempre é o ponto inferior esquerdo) do sprite, através do 
método `getX()` e somar com a metade da largura do sprite, pois, dessa maneira, é encontrado o meio do sprite (através 
da divisão da largura), que é então somado à sua posição atual. A mesma coisa acontece para definir o Y, porém nesse caso 
é levado em consideração a altura do sprite.

```java
public void render() {
    ...
    this.camera.position.set(characterSprite.getX() + characterSprite.getWidth() / 2, characterSprite.getY() + characterSprite.getHeight() / 2, 0);
    this.camera.update();

    batch.setProjectionMatrix(camera.combined);
    ...
}
```

<img width="500" height="500" alt="sprite-plano" src="https://github.com/user-attachments/assets/38925a2a-ee52-4fcf-9dd4-5bc957c71390" />

> Desenho para facilitar a visualização do sprite no plano X e Y e o funcionamento do cálculo do meio.

Para finalizar essa implementação, muita consulta externa a vídeos e fóruns foi necessária, considerando que houve muita dificuldade para que eu conseguisse fazer a câmera
funcionar da maneira esperada e a documentação oficial da biblioteca não apresentava explicações detalhadas sobre o funcionamento da classe `Camera` e `OrtographicCamera`.

<img width="500" height="500" src="https://github.com/user-attachments/assets/41a79eaa-0e97-4a49-bbc9-0a8574e95126" />

> Resultado da implementação da câmera. Modelos placeholder.

### 2.3 Refatorando o Código e Definindo as telas
É importante relembrar que um dos objetivos deste trabalho é exercitar a programação
orientada a objetos. Até agora, as funcionalidades do projeto foram todas implementadas na
classe main, visando apenas testar se estavam sendo funcionando corretamente. Entretanto, essa
forma de organização de código, embora possa funcionar, ignora características importantes de 
linguagens orientadas a objetos, como o encapsulamento de dados e operações em classes. 

Comecei tentando refatorar com base no repositório do jogo "Jardim Botânico Quest", da GameJam
passada, entretanto não consegui identificar claramente o que fazer para mover os metódos da classe main
para outras classes de maneira coesa. Assim, resolvi aprender através da série de vídeos "LibGDX & Tiled RPG
Tutorial", no Youtube (O link dos vídeos utilizados como referência para o trabalho estão listados na seção
de referências). Para isso, tive que refazer algumas funcionalidades desde o ínicio para conseguir
acomodá-las em diferentes classes.  
O primeiro passo foi definir uma classe para as telas do jogo. No libGDX, telas são responsáveis
por renderizar e processar um aspecto do jogo, como a tela de menu, a tela de jogo, etc. Através da implementação
de classes "tela", podemos mover a renderização de sprites e mapas para uma classe dedicada, que lida com cada situação
da maneira adequada (muda o mapa e os sprites conforme a localização do jogador, por exemplo). Para renderizar o jogo,
são necessários objetos `camera`, `viewport` (da classe Viewport, define os "limites" da área da câmera), 
`map`(No projeto estão sendo utilizados tiled maps. Esse tipo de mapa será explicado posteriormente) 
e `bash` (da classe Bash, fundamental para renderização).  
Conforme mostrado no vídeo, foi implementado também um cache para armazenar as instâncias das telas
no jogo e economizar certo tempo de processamento e memória na hora de mudar de tela. Esse cache utiliza uma estrutura
hash map para manter telas já criadas em memória e possibilitar sua reutilização. O código relacionado funciona da seguinte
maneira:

```java
 private final Map<Class<? extends Screen>, Screen> screenCache = new HashMap<Class<? extends Screen>, Screen>();
```
- Essa linha de código é bem extensa e apresenta várias funcionalidades que eu não havia visto antes, então tive que
pesquisar sobre hash tables e como elas são aplicadas em java. 
- Basicamente, ela declara a variável `screenCache` que servirá como o cache de telas, através da interface `Map`, utilizada
para representar uma estrutura chave-valor. 
- Dentro das chaves desse `Map`, `Class<? extends Screen>` representa qualquer classe que herde de 
`Screen`, e essa classe é a chave. 
- `Screen` é o valor que armazena a instância real da tela. 
- É então definido o hash map de `screenCache` através do método `HashMap` da classe `Map`.

```java
  public void addScreen(Screen screen) {

        screenCache.put(screen.getClass(), screen);
    }
```
- Esse método é simples e somente adiciona uma tela ao cache de telas, utilizando como chave da tela a ser 
adicionada sua própria classe. Cada tela é diretamente associada à sua classe, ou seja, utilizar um método para
definir a tela atual, basta especificar a classe da tela, sem a necessidade de um objeto único para a tela.

```java
   public void setScreen(Class<? extends Screen> screenClass) {
        Screen screen = screenCache.get(screenClass);
        if (screen == null) {
            throw new GdxRuntimeException("Screen " + screenClass.getSimpleName() + " not found in cache");
        }
        super.setScreen(screen);
    }
```
- Esse método recebe uma classe de tela, busca a tela associada a esta classe no cache, e define a tela atual como 
esta, se existir. 

Com essa memória cache para as telas, falta definir as tais telas. A tela principal vai ser a `GameScreen`, onde toda
lógica do jogo e renderização das texturas vai acontecer. Por enquanto, ambas funcionalidades estavam todas na classe 
principal, então é necessário transferir esse código para a classe dedicada. Não foi muito díficil realizar esse processo,
e o vídeo que utilizei como suporte ("Extending the Simple Game", consta nas referências) facilitou bastante isso.
Uma das peculiaridades desse código é que alguns atributos como a `camera` e o `viewport` são antes definidos na classe 
principal onde também se criam métodos `get` para cada um, para depois serem passados para a classe `GameScreen`. Isso foi 
feito para facilitar a comunicação desses atributos para outras classes, caso se demonstre necessário.

```java
public GameScreen(UniVenture game) {
        this.game = game;
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.batch = game.getBatch();
```
  
Além da tela de jogo, é importante definir também uma tela de menu, para recepcionar os jogadores de maneira mais amigável.
Para isso, foi utilizada a biblioteca Scene2D do próprio libGDX. Ela facilita a criação de botões, tabelas de organização 
e tratamento de cliques. Classes como `TextButton` e `Label` foram utilizadas para criar os botões e o título do jogo, 
respectivamente. Também foi utilizada a classe `Table` para centralizar os botões automaticamente. Para estilizar o menu,
é necessário utilizar uma "skin", que é basicamente um pacote de _assets_ que vão definir a aparência da UI do jogo. No 
repositório oficial do libGDX é possível encontrar diversos desses pacotes já prontos. Inicialmente, vamos utilizar a skin
padrão do libGDX. 

```java
TextButton startButton = new TextButton("Iniciar Jogo", skin);
TextButton exitButton = new TextButton("Sair", skin);
```
> Exemplo da criação dos botões

Além disso, os botões da tela de menu devem ser funcionais. Isso significa que a classe `MenuScreen` deve aceitar inputs 
de mouse, e, quando um botão é pressionado, realizar determinada ação. Utilizando como exemplo o botão de "começar", ao 
ser pressionado, ou seja, quando seu estado muda (changes), ele define a tela atual do programa como a tela `GameScreen`,
onde a lógica do jogo e renderização de texturas acontece. 

```java
@Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(GameScreen.class);
            }
```



### 2.4 Confecção do mapa e Definindo Entidades
Definir a lógica de entidades, com áreas de colisão e suas interações com o jogador, é uma das partes mais importantes
do projeto, e serve como base para a maioria das outras funcionalidades do jogo. Para criar as classes de cada entidade,
o código do repositório do Jardim Botânico quest, que define uma superclasse "Entity", com vários filhos que são classes
mais específicas, como a classe da entidade jogador, a classe da entidade personagem, etc. Essa superclasse define alguns
atributos e métodos comuns para todas as entidades, que depois podem ser complementados ou até substituídos. Vou explicar
aqui algumas das funcionalidades principais de cada subclasse Entity.  

As principais características dos personagens não jogáveis (NPCs) do jogo são a zona de colisão e a caixa de diálogo (pois,
no jogo, é possível interagir com os personagens). Esses dois atributos se complementam para definir essa lógica de interação
com o jogador.

```java
public class NpcEntity extends Entity {
    private String dialogText;
    private Rectangle bounds; //bounds utiliza a classe do libGDX "Rectangle" e define os limites de cada textura
```

Para cada NPC, este deve ter uma textura associada, posição, tamanho e o texto da sua caixa de diálogo. Esses argumentos são
importantes para o construtor da classe. Também é importante notar que para facilitar a renderização dos NPCs (que vão
ser vários), adicionei todos em um ArrayList. 
```java
        this.npcTexture = new Texture("entidade.png");
        this.npcs = new ArrayList<>();

        npcs.add(new NpcEntity(5, 2, 1f, 1f, npcTexture, "Ola, sou um NPC normal."));
        npcs.add(new NpcEntity(5, 2, 1f, 1f, npcTexture, "Ola, sou um NPC normal."));
```

Para fazer a caixa de diálogo funcionar, algumas alterações precisam ser feitas. Na classe GameScreen, criamos um novo 
método para definir a interface gráfica dessa caixa de diálogo. Para isso, utilizamos recursos como a classe Window (do 
próprio Java), que mais tarde é adaptada graficamente com a `skin` que definimos. Nesse caso, assim como na tela de menu,
estamos utilizando a skin padrão do libGDX, adquirida em seu repositório oficial. 

```java
    uiStage = new Stage(new ScreenViewport());
    skin = new Skin(Gdx.files.internal("uiskin.json")); 
    
            setupDialogUI();
            
            private void setupDialogUI() {
            dialogTable = new Table();
            dialogTable.setFillParent(true);
            dialogTable.bottom(); 
            
            dialogLabel = new Label("", skin);
            dialogLabel.setWrap(true); 
                
            Window window = new Window("Dialogo", skin);
            window.add(dialogLabel).width(400).pad(10);
        
            dialogTable.add(window).padBottom(20);
        
            uiStage.addActor(dialogTable);
            dialogTable.setVisible(false);
        }
```
Também foi adicionado lógica para definir as interações do jogador com os NPCs. Caso o jogador pressione a tecla 'E',
a caixa de diálogo do NPC se torna visível. Caso ele pressione 'E' novamente, a caixa fecha (se torna invisível). Antes
de possibilitar que a caixa aparece, o programa deve conferir se o jogador está próximo o suficiente do personagem para 
interagir com o mesmo.

```java
public void render(float delta) {
    if (isDialogVisible) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isDialogVisible = false;
            dialogTable.setVisible(false);
        }
    } else {
        player.moveCharacter();

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            checkInteraction();
        }
    }
    ...
}

private void checkInteraction() {
    for (NpcEntity npc : npcs) {
        if (npc.isCloseTo(player.getX(), player.getY(), 1.5f)) {
            dialogLabel.setText(npc.getDialogText());
            isDialogVisible = true;
            dialogTable.setVisible(true);
            break;
        }
    }
}
```
> Com o for loop, o jogo confere se o jogador está próximo de um NPC ao apertar o botão de interagir 'E'. Caso positivo,
> A caixa de diálogo pega o texto associado ao NPC e é definida como vísivel até o fim da interação.

Utilizando o método da classe `Rectangle` `overlaps`, é possível detectar se um retângulo (nesse caso, a caixa de colisão)
está "invadindo" outro. Assim, o programa pode, através do método `checkCollision`, iterar pela lista de NPCs e encontrar
qualquer colisão. Com essa confirmação, o programa "bloqueia" a movimentação do jogador para dentro da zona de colisão do
NPC. Essa lógica também pode ser implementada para definir paredes que não são necessariamente NPCs.

```java
private boolean checkCollision(List<NpcEntity> obstacles) {
        Rectangle playerRect = characterSprite.getBoundingRectangle();
        for (NpcEntity npc : obstacles) {
            if (npc.getBounds().overlaps(playerRect)) {
                return true; 
            }
        }
        return false;
    }
```

A classe para o jogador `PlayerEntity` segue uma lógica parecida. As principais diferenças são que, para garantir que as 
caixas de colisão não interajam da forma errada. Quando eu estava tentando definir os retângulos de colisão, muitas vezes
eles ficavam muito grandes, por vezes muito pequenos, criando situações onde era muito difícil jogar com paredes invisíveis
que dificultavam muito o movimento. Para resolver isso, tive que diminuir a escala da área de colisão dos sprites, e 
calcular precisamente aonde essas áreas deveriam ficar no mapa.

```java
    private boolean checkCollision(List<NpcEntity> obstacles) {
        Rectangle fullRect = characterSprite.getBoundingRectangle();

        float scaleWidth = 0.6f;
        float scaleHeight = 0.3f;

        float newWidth = fullRect.width * scaleWidth;
        float newHeight = fullRect.height * scaleHeight;


        float newX = (fullRect.x + fullRect.width / 2) - (newWidth / 2);

        float yOffset = fullRect.height * 0.1f;
        float newY = fullRect.y + yOffset;
```

Além disso, algumas pequenas alterações na lógica já existente do projeto (Como para o movimento do jogador) também foram 
feitas para deixar o código mais simples. Para construir o mapa do jogo, utilizei o próprio MS Paint para desenhar em 
estilo pixel art o mapa do jogo. Já que o jogo se trata de um RPG com visão "de cima" (top-down), o mapa é uma textura
que fica no background, então não é necessário se preocupar com a física dos elementos em cima dele. Entretanto, foi 
necessário criar áreas de colisão nas bordas do mapa para que o jogador não saísse dos limites. Essas áreas também são 
retângulos e o tamanho dos retângulos é calculado com base no tamanho do mapa.

https://github.com/user-attachments/assets/82561227-50ef-4254-97ea-97796663f921
> Antes de polir o código, o jogo estava assim

### 2.5 Criando o Menu e o Quiz

Para os quizes, foram criadas algumas classes novas, sendo essas `QuizScreen`, `Question` e `WinScreen`. A classe Question
serve como base para as perguntas do quiz, e em QuizScreen essas questões são exibidas na tela. Quando propus a ideia de 
fazer um jogo de quiz para a professora, foi sugerida a ideia de utilizar um arquivo JSON para guardar as informações das
questões do quiz (Como a pergunta e a resposta), ao invés de utilizar um arquivo padrão txt. Embora qualquer uma das 
abordagens funcione, escolhi seguir o conselho e utilizar o arquivo JSON, já que existem bibliotecas que tornam o processo
de extrair as informações do arquivo muito simples e compacto.  
Depois de organizar o arquivo JSON em "Texto" (Pergunta), "Opções" (Respostas) e "Indice" (Posição da resposta correta 
no vetor), foi simples implementar um método que percorre cada campo de acordo com os nomes dos campos no arquivo JSON. 
A biblioteca que possibilita esse processo é bem simples. Após isso, todas as informações relacionadas às questões são 
armazenadas em um objeto da classe `Question`, que então é armazenado em um `ArrayList`.

```java
    private void createQuestions() {
        questions = new ArrayList<Question>();
        JsonReader json = new JsonReader();
        JsonValue baseValue = json.parse(Gdx.files.internal("questions.json"));

        for (JsonValue entry : baseValue) {
            String text = entry.getString("text");
            String[] options = entry.get("options").asStringArray();
            int correctIndex = entry.getInt("correctAnswerIndex");
            questions.add(new Question(text, options, correctIndex));
        }
    }
```
> Método que extrai as questões e suas informações do arquivo JSON

Além disso, para criar o menu principal e a tela de quiz, foi utilizada novamente a biblioteca scene2d para a parte 
gráfica. Adicionar botões, títulos e fundos é a face mais frontend do projeto, então não irei me aprofundar muito sobre 
o funcionamento e o processo de criação dessa parte. Porém, acho importante destacar que os botões funcionam com uma área 
de colisão e processamento de input (Nessa caso, o clique do mouse), muito similar a outros elementos do projeto. 
Similiarmente, caso o jogador responda corretamente todas as questões, ele é recebido por uma tela de parabéns, que encerra
o jogo.

## 3 Diagrama de Classes
Observar o diagrama de classes após a conclusão me explicitou algumas oportunidades perdidas de encapsulamento e herença.
<img width="2497" height="1917" alt="paradigmas_classes" src="https://github.com/user-attachments/assets/1dc03b04-576d-4197-b115-e02953bbd888" />

## 4 Execução do projeto e Conclusão.

Por se tratar de um trabalho extenso, que requer bastante esforço (especialmente quando um dos objetivos é criar as artes
do jogo por conta própria), foi fácil subestimar quanto tempo seria necessário para fazer tudo que foi inicialmente planejado.
Ao longo do desenvolvimento, por falta de tempo (e até de conhecimento), foi necessário mudar de planos constantemente, 
cortando algumas das funcionalidades propostas inicialmente e encontrando maneiras diferentes de executar os novos objetivos.
Entretanto, mesmo com essas dificuldades, acredito ter feito um bom projeto, que embora simples, exercitou alguns conceitos 
fundamentais de programação orientada a objetos.  
Para executar o projeto localmente, basta:  
- Possuir JDK e Gradle instalados;
- Clonar o repositório com `git-clone https://github.com/elc117/gamification-2025b-caua-spamton-g-spamton`;
- No terminal, utilizar `gradlew lwjgl3:run`.  
Deixo aqui um pequeno vídeo testando o projeto final:

https://github.com/user-attachments/assets/aa0d9a12-b2d4-438f-a38d-53df70a3b77a

## 5 Referências
2.1:
https://libgdx.com/wiki/start/a-simple-game - A Simple Game  
https://youtu.be/2furs-8L1-8?si=h3G4Aq-ZcxJ-Jmcb - Como Instalar a LibGDX e Primeiros Passos - Java #02  
https://javadoc.io/doc/com.badlogicgames.gdx/gdx/1.9.5/com/badlogic/gdx/graphics/g2d/Sprite.html - Class Sprite

2.2:
https://youtu.be/HDflWUtpd7s?si=N7C9x0Dus636L39K - LibGDX & Tiled RPG Tutorial - #10 Camera  
https://libgdx.com/wiki/graphics/2d/orthographic-camera - Orthographic camera  
https://stackoverflow.com/questions/27429520/how-to-scale-sprites-in-libgdx - How to scale sprites in libgdx?  
https://libgdx.com/wiki/graphics/viewports - Viewports  
https://stackoverflow.com/questions/14629653/libgdx-why-doesnt-the-camera-follow-the-character - 
libgdx why doesn't the Camera follow the character?

2.3: https://libgdx.com/wiki/start/simple-game-extended - Extending the Simple Game  
https://www-w3schools-com.translate.goog/java/ref_hashmap_put.asp?_x_tr_sl=en&_x_tr_tl=pt&_x_tr_hl=pt&_x_tr_pto=tc -
Java HashMap put() Method  
https://www.w3schools.com/java/java_hashmap.asp - Java HashMap  
https://github.com/elc117/game-2024b-vmferreira - Repositório Jardim Botânico Quest  
https://libgdx.com/wiki/graphics/2d/scene2d/scene2d - Scene2d  
https://youtu.be/V8QTnsQpDWM?si=Q0cXKPV1aKkQCkSb - Gamedev with libGDX | E05 menu screen  

2.4: https://youtu.be/ie6Ek6f-USY?si=Q-06MN-iHvPgPPv3 - Character Collisions In LibGDX  
https://youtu.be/oYsA9PGCkQA?si=6g4-SgKqAZwmW1Q5 - Criando Colisões e Utilizando Fontes - Java LibGDX #04 Final  
https://stackoverflow.com/questions/33062574/how-to-properly-implement-a-dialog-box-using-libgdx - how to properly implement a Dialog box using libgdx  
https://www.catalinmunteanu.com/design-custom-dialog-libgdx.html - How to create custom Dialog in LibGDX  
https://youtu.be/fxkuHa9FmGw?si=dgaUTx1hoWV9sBB5 - Request 19 (LibGDX) - How to use the Dialog from scene2d.ui  
https://github.com/elc117/game-2024b-vmferreira - Repositório Jardim Botânico Quest  
https://stackoverflow.com/questions/47644078/clamp-camera-to-map-zoom-issue - Clamp Camera to Map (Zoom issue)  
https://gamedev.stackexchange.com/questions/74926/libgdx-keep-camera-within-bounds-of-tiledmap - LibGDX keep camera within bounds of TiledMap  

2.5: https://libgdx.com/wiki/utils/reading-and-writing-json - Reading and writing JSON  
https://stackoverflow.com/questions/35343727/how-to-parse-this-json-with-libgdx - How to Parse this JSON with LibGDX  
https://jackyjjc.wordpress.com/2013/10/07/parsing-json-in-libgdx-tutorial/ - Parsing JSON in Libgdx
https://libgdx.com/wiki/graphics/2d/scene2d/scene2d - Scene2d  
https://libgdx.com/wiki/graphics/2d/scene2d/scene2d-ui - Scene2d.ui
https://youtu.be/DPIeERAm2ao?si=m5nQkXFblU5X4TSC - Introduction to Scene2D in LibGDX

