# esse jogo ainda esta em dev!
# caso voce descubra um bug sinta-se a vontade para postar ele em `Issues`

## trocando texturas

para começar entre em `sua-home/.GAME/graphics/default` e la onde estao localizadas as texturas do jogo porque eu ainda nao fiz o sistema para automaticamente reconhecer texturas novas mas saira em breve!<br>

### como trocar

todas as "imagens" do jogo sao gifs (`.gif`) para suportar animacoes ate mesmo em blocos (exemplo agua ainda nao adicionada)<br>
mas e se eu nao quiser colocar o gif do player andando na pasta `sua-home/.GAME/graphics/default/player/player-walking.gif`? simples crie um arquivo la chamado `player-walking.gif.redirect` contendo o seguinte [link pro arquivo](https://github.com/henrique-o-developer/jogo/blob/master/res/graphics/textures/default/player/player-walking.gif.redirect) 
```redirect
# isso é um comentario ;)

file: /caminho/pro/arquivo.gif # indica o caminho pro arquivo tambem pode ser
file: ./caminho/pro/arquivo.gif

folder: /pasta/do/gif/ # indica a pasta do gif tambem pode ser
folder: ./pasta/do/gif/

# caso o 'folder' não seja especificado ele sera extraido do 'file'
# caso o 'file' não seja especificado ele sera o nome do arquivo por exemplo esse arquivo se chama redirect.gif.redirect o nome do gif sera redirect.gif
```
mas e se eu quiser dar infos adicionais sobre o gif? bom [aqui](https://github.com/henrique-o-developer/jogo/blob/master/res/graphics/textures/default/player/player-walking/player-walking.gif.props) há um exemplo
```props
# já sabe né? :3

drawBGColor: false # inibe que a cor de fundo seja desenhada
restartGifAtIndex: 10 # quando o gif acabar ele recomeçara desse index
stopGifAtIndex: 13 # index do "ultimo" frame
endAnimationAtIndex: 14 # apartir desse index sera o "fim" da animação por exemplo o player ta correndo e quando ele para de correr ele tem que ficar reto então essa animação é rodada

# exemplos:
# frame 1: player a 90º
# frame 2: player a 92º
# ...
# frame 10: player correndo [é daqui que ele ira reiniciar]
# ...
# frame 13: player correndo [aqui ele vai parar]
# frame 14: player parando de correr [aqui ele roda quando o player começa a parar de correr]
```

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