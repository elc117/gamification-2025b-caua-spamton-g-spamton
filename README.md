<div align="center">
  <h1 align="center">UniVenture UFSM 🗺️</h1> 
  <h3 align="center">Gamification - Paradigmas de programação</h3>
  <p align="center">Universidade Federal de Santa Maria<br><br>Aluno: Cauã Welter da Silva | Curso: Sistemas de Informação</p>
</div>

## 1. Proposta do projeto 🎯 

### 1.1 Objetivo

Conforme o tema de "gamification", proposto pela professora, esse projeto tem como objetivo
desenvolver um jogo, utilizando a biblioteca libGDX da linguagem de programação Java,
que permita ao jogador explorar e aprender sobre locais da UFSM. Os principais objetivos desse trabalho são:
- Desenvolver um jogo didático e lúdico, que possa passar aos jogador informações importantes sobre a universidade;
- Aplicar criações artísticas para garantir maior aproveitamento ao jogador;
- Exercitar conceitos fundamentais do paradigma de programação orientada a objetos.

### 1.2 Inspirações e ideias: 

Pretendo desenvolver um jogo em que seja possível movimentar o personagem pelas 4
direções (cima, baixo, esquerda, direita) para explorar o mapa do jogo. Em cada mapa,
alguns personagens interagíveis estarão presentes, cada um com uma linha de diálogo
diferente, que revelarão informações sobre algumas das áreas da UFSM. Essa estrutura
básica de jogo já foi explorada em várias obras, com "Undertale" sendo uma das principais.
Se trata de um jogo lançado em 2015 e desenvolvido por um time de apenas uma pessoa.
O jogo contém elementos de RPG, como batalhas e itens, que não serão integrados ao projeto,
por conta de sua complexidade, mas o seu estilo e estrutura servirão de inspiração.

<img width="500" height="500" alt="sprite-plano" src="https://media1.tenor.com/m/pylTHrd5YoAAAAAd/undertale-video.gif" />

> GIF de gameplay do jogo Undertale. A movimentação do personagem do jogador e as interações com personagens não
> jogáveis são inspirações para o projeto.

Além disso, o jogo contará com algumas fases, a cada fase terá um mapa diferente. 
Os mapas do jogo serão alguns recortes de regiões da UFSM. Serão selecionados
alguns pontos de interesse, e os diálogos dos personagens de cada mapa contarão um pouco da história 
e funcionalidades desses pontos. Para avançar de fase, o jogador deve, ao final
de cada fase, responder um quiz sobre os pontos de interesse apresentados no mapa. É
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


## Referências
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
