# esse jogo ainda esta em dev!
# caso voce descubra um bug sinta-se a vontade para postar ele em `Issues`

## trocando texturas

para começar entre em `sua-home/.GAME/graphics/default` e la onde estao localizadas as texturas do jogo porque eu ainda nao fiz o sistema para automaticamente reconhecer texturas novas mas saira em breve!<br>

### como trocar

todas as "imagens" do jogo sao gifs (`.gif`) para suportar animacoes ate mesmo em blocos (exemplo agua ainda nao adicionada)<br>
mas e se eu nao quiser colocar o gif do player andando na pasta `sua-home/.GAME/graphics/default/player/player-walking.gif`? simples crie um arquivo la chamado `player-walking.gif.redirect` contendo o seguinte [link pro arquivo](https://github.com/henrique-o-developer/jogo/blob/master/res/graphics/textures/default/player/player-walking.gif.redirect) (nao entende ingles? bota no tradutor kkkk) <br>

## traduçoes

bom eu quero fazer algo parecido com as texturas mas ainda nenhuma ideia

## mods e addons
aaa mas eu quero MODS e ADDONS como q eu poderia fazer um? simples tambem AINDA nao foi adicionado mas seria so colocar um arquivo .jar ou .class (ou um .redirect) em `sua-home/.GAME/modifications` e rodar o jogo 

### como fazer

o jogo conta com um sistema de eventos (tipo o minecraft) para adicionar um evento use `EventHandler.addHandler(NewEventInterface)` e pronto cada vez que um novo evento disparar seu mod/plugin sera notificado<br>

mas qual seria o codigo?<br>

algo tipo isso: <br>
```java
    class pluginMuintuLegaul {
        public void onGameInit() {
            // ativa quando o jogo inicia (quando a janela do jogo abre)
        }

        public void onGameStart() {
            // ativa quando o player clica em entrar no jogo
        }
        
        public void onGameEnd() {
            // quando o player clica em sair
        }
        
        public void onGameExit() {
            // quando o player fecha o jogo
        }
        
        public void onSave() {
            // quando o jogo e salvo
        }
    }
```


# explicaçoes
por enquanto esta `.GAME` por que eu ainda nao decidi o nome do jogo :)

nao tem nenhum acento porque por exemplo vou tentar escrever e com acento agudo `'e` entendeu?

nossa mas o jogo nem tem personagem direito e voce ja esta adicionando ele ao github e colocando licensa? Obvio quanto mais cedo melhor!

ta demorando muito pra sair novas verçoes! pq? preguiça!

onde posso baixar o jogo? bom [aqui](https://github.com/henrique-o-developer/jogo/blob/master/out/artifacts/compiled/GAME.jar) mas eu nao garanto que esteja atualizado pq eu me esqueço
# participantes

programador, graphics designer, sound designer, etc...: henrique franchesco de almeida do rosario<br>
beta tester e algumas ideias: grabriel cassol naoMeLembro naoMeLembro