<div align="center">
  <h1 align="center">UniVenture UFSM 🗺️</h1> 
  <h3 align="center">Gamification - Paradigmas de programação</h3>
  <p align="center">Universidade Federal de Santa Maria<br><br>Aluno: Cauã Welter da Silva | Curso: Sistemas de Informação</p>
</div>

## 1. Objetivo 🎯 

Conforme o tema de "gamification", proposto pela professora, esse projeto tem como objetivo
desenvolver um jogo, utilizando a biblioteca libGDX da linguagem de programação Java,
que permita ao jogador explorar e aprender sobre locais da UFSM. Os principais objetivos desse trabalho são:
- Desenvolver um jogo didático e lúdico, que possa passar aos jogador informações importantes sobre a universidade;
- Aplicar criações artísticas para garantir maior aproveitamento ao jogador;
- Exercitar conceitos fundamentais do paradigma de programação orientada a objetos.

### 1.1 Inspirações e ideias: 

Pretendo desenvolver um jogo em que seja possível movimentar o personagem pelas 4
direções (cima, baixo, esquerda, direita) para explorar o mapa do jogo. Em cada mapa,
alguns personagens interagíveis estarão presentes, cada um com uma linha de diálogo
diferente, que revelarão informações sobre algumas das áreas da UFSM. Essa estrutura
básica de jogo já foi explorada em várias obras, com "Undertale" sendo uma das principais.
Se trata de um jogo lançado em 2015 e desenvolvido por um time de apenas uma pessoa.
O jogo contém elementos de RPG, como batalhas e itens, que não serão integrados ao projeto,
por conta de sua complexidade, mas o seu estilo e estrutura servirão de inspiração.

![giphy](https://media1.tenor.com/m/pylTHrd5YoAAAAAd/undertale-video.gif)
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

```java
    private void moveCharacter() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            posX -= 10;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            posX += 10;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            posY += 10;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            posY -= 10;
        }
    }
```
![giphy](https://github.com/user-attachments/assets/0a305b17-7b81-4186-9a5a-a3bf2dc9437a)
> O quadrado se move! Isso é como o "hello world!" dos jogos. 
