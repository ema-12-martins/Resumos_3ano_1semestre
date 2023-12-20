# HTTP (Hypertext Transfer Protocol)

Uma pagina consiste num conjunto de objetos incluidos num ficheiro base HTML.
Cada objeto é referenciado por um **URL (Uniform Resource Locator)**.

~~~
http://www.di.uminho.pt/cursos/miei.html
~~~
host name:www.di.uminho.pt
path name: cursos/miei.html

# Como funciona
É um protocolo de nivel de aplicacao. Segue o modelo cliente/servidor. O browser pede recebe e mostra objetos web. O servidor envia objetos como resposta a pedidos.

O cliente cria um socket e inicia uma ligacao TCP a um servidor HTTP(porta 80). TCP aceita pedido. SAo trocadas menssagens HTTP entre o browser(cliente) e o servidor. A ligacao TCP é terminada.

Ha que salientar que o HTTP **nao mantem estado** acerca dos pedidos anteriores.

# Pedido HTTP
![HTTP](https://sabercomlogica.com/wp-content/uploads/2015/05/Figura-14-5.jpg)


# Metodos
**GET**(read): Vai buscar o objeto passado no URL.

**POST**(create): Introduz formulario para introduzir dados. Semelhante ao GET mas o objeto requirido depende do input introduzido pelo utilizador. O input introduzido é mandado para o servidor HTTP no corpo da HTTP Request Menssage com o metodo POST. 

**HEAD**: Pede ao servidor para nao incluir o objeto requerido na resposta, apenas o cabeçalho do objeto.

**PUT**(update): Faz o upload do objeto para a localizacao especificada no campo URL.

**DELETE**(delete): Apaga o ficheiro especificado no URL.

**URL**: O input é mandado para o servidor HTTP utilizando o campo URL HTTP Request Message com o metodo GET.

O HTTP/1.0 so contem os metodos GET, POST e HEAD enquanto que o HTTP/1.1 contem GET, POST, HEAD, PUT, DELETE.

# Respostas HTTP
![Respostas HTTP](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Overview/http_response.png)
O **200 OK** quer dizer que o servidor encontrou o objeto. Existem mais codigos de tipo.

# Utilizacao da camada de trasnporte TCP
**HTTP nao persistente**: No maximo pode ser enviado um objeto por coneccao estabelecida. Temos no minimo 2 RTT por objeto. Leva a utilizar mais recursos de SO. Alguns browsers abrem varias conneccoes em paralelo para pedir varios objetos em simultaneo. Ex.HTTP/1.0

Se pedir um objeto e nele conter referencias para outros, tem de abrir novamente um socket...

**HTTP persistente**: Podem ser enviados multiplos objetos web por cada ligacao estabelecidade entre cliente servidor pois o servidor mantem a conneccao TCP aberta. Pode ter ou nao estrategia de pipelining. Ex.HTTP/1.1. Se **nao tiver pipeline** o cliente envia um novo pedido apenas quando recebe resposta do anterior. Neste caso, sendo otimista, consome um RTT por cada objeto referido. **Com pipeline**, o cliente envia o pedido assim que encontra o objeto referenciado. Sendo otimista, consome um RTT para o conjunto de todos os objetos referenciados.
Por default, o HTTP/1.1 é persistente e com pipeline.

# Tempo de resposta
~~~
RTT=2*TP + N*TEQ + N*PR
~~~
TP: Tempo de propagacao.

TEQ: Tempo de espera nas filas de todos os sistemas(origem,destino e intermedios).

PR: Tempo de processamento em todos os sistemas.

~~~
Tempo de resposta: 2*RTT + TT
~~~
Temos um RTT para iniciar a coneccao, outro para enviar a request menssage e comecar a receber o primeiro bit do ficheiro na response menssage. Temos ainda o TT que é o tempo de transmissao do ficheiro.

# Informacoes de estado - Cookies
Temos 4 componentes:
1. Linha com cookie no cabeçalho da mensagem HTTP response.
2. Linha com cookie no cabeçalho da mensagem HTTP resquest.
3. Ficheiro com cookies mantido na maquina do utilizador, gerido pelo seu browser.
4. Base de dados de suporte do lado servidor Web

Quando entra num site pela primeira vez, guarda o identificador e o uma entrada na base de dados com esse identificador. Na response, vai o set-cookie com o numero de identificacao. Nos proximos pedidos, esse numero vai na menssagem.

As entidades protocolares guardam estado por emissor/recetor entre trasacoes distintas.

# Servidores Proxy-Cache
Reduz o tempo de resposta para os pedidos do cliente. Este servidor tem de atuar simultaneamente como cliente e servidor. Sao normalmente instalados pelas ISP ou pelas proprias instituicoes.

Tem como objetivo satisfazer o cliente sem envolver o servidor HTTP alvo (que esta longe).

O browser envia todas as HTTP request messages para o servidor proxy. Se ele contiver copias do objeto requerido em cache, entao retorna essa copia. Se nao tiver, contacta o servidor HTTP alvo, enviando-lhe um HTTP request message. Aguarda a resposta que guarda em cache e retorna o cliente.

# GET Condicional
O objetivo é nao mandar o objeto se a copia mantida em cache esta atualizada. Para isso, no cabecalho do pedido inclui-se a data da copia guardada na cache. A resposta do servidor nao contem nenhum objeto se a copia mantida em cache estiver atualizada.

# Problemas de desempenho HTTP/1

**Paralelismo limitado**:O paralelismo esta limitado ao numero de coneccoes (+/- 6 por origem).

**Head-of-line blocking**:Acumula pedidos em queue e atrasa solicitacao por parte do cliente pois o servidor é obrigado a responder por uma ordem especifica.

**Overhead protocolar elevado**: Metade dos cabeçalhos nao sao compactados. Os cabeçalhos dao 800 bytes sem os cookies.

# Paralelismo limitado
Cada conecao implica um overhead de handshake inicial e se for HTTPS ainda tem mais o handshake do TLS.

**domain sharding**: Dividir em n subdominios em vez de um unico dominio por servidor. Assim temos 6 coneccoes por subdominio porem aumenta as consultas de DNS. Teremos de ter mais servidores

**Concatenated assets**: Reduzir pedidos concatenando objetos. Atrasa no entanto o processamento no cliente e o uso da cache.

**Inline objects**:Incluir recursos em linha no HTML. Mesmo problema que o anterior.

# HTTP2
È um extensao e nao uma substituicao do HTTP/1.1

1. Temos apenas uma coneccao TCP
2. As streams sao multiplexadas
3. Camada de framing binario. Temos priorizacao, controlo de fluxo e server push
4. Compressao do cabeçalho(HPACK)

Temos que as menssagens HTTP sao divididas em uma ou mais frames: Headers frame e data frame. Cada frame tem um cabeçalho comum (9 bytes)

**Streaming**: Fluxo bidirecional de dados, dentro de uma coneccao, que pode carregar uma ou mais mensagens.

**Mensagem**: Sequencia completa de frames que mapeiam um pedido ou uma resposta HTTP.

**Frame**: Unidade de comunicacao mais pequena no HTTP2, contendo um cabecalho que no minimo identifica a que stream é que pertence.

As streams sao multiplexadas pois as frames podem ser intercaladas. Todas as frames sao enviadas numa unica coneccao TCP. AS frames sao entregues por prioridades, tendo em conta os pesos e dependencias. AS **frame DATA** estao sujeitas a controlo de fluxo por stream e por conexao.

# Tipos de frames
**Headers**: Headres de um pedido ou de uma resposta.

**DATA**: Corpo dos objetos(dados).

**PRIORITY**: Define a prioridade da strem para o originador.

**RST_STREAM**: Permite o termino imediato da stream.

**SETTINGS**: Para definir parametros de configuracao.

**PUSH_PROMISE**: Permite push de conteudos.

**WINDOW_UPDATE**: Permite reajustar a janela de fluxo da stream.

**CONTINUATION**:Perminte prolongar frames com HEADERS

# Compressao do cabecalho
Textos sao codificados em codigo Huffman estatico.

Podemos ter tabelas indexadas estaticas(2 corresponde ao metodo GET) ou dinamicas(valores anteriormente enviados podem ser indexados).

# Server push
Manda logo varios objetos.
1. Server observa o trafego de entrada e dele constroi um modelo de dependencias baseado nos cabecalhos(se pediu htlm depois vai pedir css...).
2. Servidor indica um push inteligente de acordo com as dependencias que aprendeu(primeiro htlm, depois css e js...)

# Controlo de fluxo
Permite ao cliente fazer uma pausa na stream e retomar o envio mais tarde
NAOOOOOOOOOOOO PERCEBIIIIIIIIIII

# Priorizacao
Servidor tem que entregar os pedidos pela ordem mais adequada(html e depois css...).

# Pesos e dependencias
Cada stream pode ter um peso(1-256) e dependencias(id de outra stream).
NAO PERCEBIIIIIIIIII

# Negociacao protocolar
Temos 3 formas de o cliente usar HTTP2
1. Cemca HTTP/1 e pede upgrade da coneccao.
2. Usando HTTPS e negociando o protocolo HTTP2 durante o hanshake TLS inicial.
3. Sabendo que o servidor é HTTP2 envia sequencia inicial HTTP2. Manda uma sequencia de 24 octetos e depois os SETTINGS para definir os parametros da coneccao.

# QUIC -> Origem HTTP3
Controlo de congestao. Cifragem e parte do HTTP2 muda-se para o QUIC que vai correr sobre UDP.



