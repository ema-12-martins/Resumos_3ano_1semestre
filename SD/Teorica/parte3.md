# Tempo Fisico
Um sistema distribuido sao elementos de computacao autonomos mas que resultam num sistema coerente como um todo. Para isso, estes elementos tem de se coordenar, implicita ou explicitamente.

## Sincronizacao de relogios
Necessaria pois relogios de diferentes dispositivos podem ter comecado em sitios diferentes, mas mesmo acertados corretamente, ao longo do tempo, podem se afastar um do outro. Por exemplo, é complicado quando estamos a escrever em simultaneo num ficheiro. Outro exemple é o make que so atualiza o .o se o .c for mais recente.

Se tivermos dois relogios a tempos diferentes, deveria ser possivel consultar a hora do outro instantaniamente, e ajustar pela diferença observada.

Temos duas situacoes. O relogio de referencia está mais atras do que o relogio que tem de ser acertado. Ta- Tb dará negativo, pelo que o relogio local deveria ser atrasado. Se o fizermos, o instante que foi atrasado vai acontecer duas vezes e induz o make em erro. No outro caso Ta-Tb é positivo. Se saltarmos imediatamente para a frente, faz com que os processos que estao a medir a passagem de tempo, como timeout ou sleep, passem mais rapido ou estantaneamente. **Em ambos os casos, a solucao é ir incrementando os relogios em pequenos incrementos, progressivamente, ao longo de um intervalo de tempo maior**.

No entanto, estas solucoes partem do principio que conseguimos fazer observacoes estantaneas dos relogios num outro dispositivo, o que nao é verdade num sistema distribuido, pois demora algum tempo na rede. Esse tempo na rede nem é conhecido. Temos ainda atrasos de processamento no sistema remoto a quem pedimos para saber o tempo. 
~~~
-------------T2------T3---------------
-------T1---------------------T4------
~~~
Se soubermos T1 e T2, conseguimos fazer um bom ajuste se descontarmos o atraso. Mas nao sabemos como saber o atraso. Nao sabemos o inicio e o fim porque nao temos um referencial unico. Temos de arranjar algoritmos que estimem o atraso ou que trabalhem bem sobre estes atrasos.

## NTP - Network Time Protocol
((T2-T1)+(T4-T3))/2
Faz uma boa aproximacao se o envio e a rececao demoram quase o mesmo tempo, mas pode haver perturbacoes na rede que estragem essa medida.
Para diminuir este problema, o algoritmo faz varias medicoes e escolhe aquela que demorou menor tempo total a ir e voltar, pois esta foi a que teve menor perturbacoes (T4-T1). 

## RBS - Reference Base Synchoronization
Um participante envia uma mensagem pelo canal de difusao e todos os participantes vao dizer a que hora receberam essa mensagem de referencia. Cada um deles envia o valor a todos os outros. Fazendo T2-T1, estamos a calcular a diferenca entre 2 instantes que ocorreram ao mesmo tempo, momento em que ambos houviram a mensagem a ser transmitida na rede. Pode haver inscertezas do tempo que demora a processar a mensagem e da rede mas temos uma estimativa melhor.

# Tempo logico
Outro problema é o da exclusao mutua. Num mutex tem a propriade de excluir mais que uma thread de uma seccao critica e se houver varios threads a tentar entrar, que 1 deles deve conseguir entrar (nao haver deadlocks), e que mais tarde ou mais cedo os processos vao conseguir entrar (nao haver starvation).

Para sistemas distribuidos, deve depender ainda de mais duas coisas, sendo elas, qual o numero de saltos até entrar na seccao critica (medida de latencia) e considerar o balanco de cargas entre os participantes (ver se essa mensagem é trocada com um elemento do grupo ou varios elementos do grupo). Se conseguirmos distribuir a carga pelo grupo, temos um maior numero de entradas e saidas na seccao critica.

## Solucoes
Temos um servidor que guarda o estado atual da seccao critica (se esta ocupado ou nao e se estivar ocupada, qual a lista de espera) e outros clientes que quando pretendem entrar na seccao critica, efetuam pedidos. Quando um cliente quer aceder á seccao critica, faz um pedido ao servidor. Esta solucao tem dois inconvenientes. O primeiro é que o tempo necessario para entrar numa secao critica é de pelo menos um periodo de ida e volta ao servidor. O segundo incoveniente é que o servidor tem de lidar com pelo menos 4 mensagens por cada seccao critica, sendo estas o lock, responder, unlock e responder. Tem de pedir lock, o servidor responde, quando tira o lock manda para o servidor e este responde outro.

Temos outras solucoes que tiram partido do sistema e passagem de mensagens. Organizar os processos num anel e organizar a passagem de 1 destemunho para aceder á seccao critica. 1->2->3->1 e fica em circulacao. Um processo que nao pretenda usar o testemunho, passa imediatamente ao seguinte. No entanto pode levar a starvation se houver um processo que quer constantemente a seccao critica, nunca passando o testemunho. Podemos ter uma regra que cada processo pode usar apenas uma vez o testemunho e depois tem de o passar. Tem o problema que o tempo medio para entrar na seccao critica é em média numero_de_participantes/2. Outro inconveniente é que obriga a mandar mensagens mesmo que naquele momento nenhum participante esteja a entrar na seccao critica, pois o processo nao pode reter o testemunho pois nao sabe o que se passa nos outros. Nao temos uma fila de espera implicita.

## Nova solucao distribuida
Cada participante quando quiser entrar na seccao critica, ira difundir esse facto por todos os outros, por mensagens. Como existe incerteza sobre o tempo de transmissao e muito provavelmente demoram muito mais tempo a transmitir para outros processos do que para si proprios. Isto leva a que por exemplo o 3 ache que a fila é 3,2 e o 2 achar que é 2,3. Assim os processos iriam usar a seccao critica ao mesmo tempo, achando que esta no primeiro lugar. Para evitar esta situacao. Uma hipoteses era comparar o tempo em que os 2 mandaram as mensagens, assumindo que temos sincronizacao de relogios. No entanto isto nao resolve totalmente pois se o 3 envia a mensagem depois de 2 mas so recebe a mensagem do 2 depois, ele acha que está a frente na fila e ja foi. Podemos evitar isto assumindo que conhecemos um delta que é igual ao delay de transmissao + diferenca entre relogios. Quando pretrendemos entrar numa seccao critica, difundimos a mensagem para todos os participantes, incluindo para si proprio. No entanto, so consideramos mensagens candidatas a fila do mutex, depois de ter passado pelo menos delta. Assim, mensagens a caminho teriam chegado.

## Causualidae/ Relacao de causa efeito potencial
Um acontecimento **precede causalmente** outro se os dois acontecimentos sao so mesmo processo e um acontece antes do outro ou se esses dois eventos sao o envio e a rececao dessa mensagem. Esta relacao de causalidade é transitiva, ou seja, se um acontecimento precede outro e o ultimo precede um terceiro, entao o primeiro precede o terceiro. Isto pode se repetir sucessivamente. Se dois eventos, nem A preceder B bem B preceder A, dizem-se eventos **concorrentes**.

Uma propriedade importante de um relogio é que se um acontecimento i precede j, clock(i) é menor que clock(j). Se nao acontecesse, tenho uma mensagem enviada do futuro para o passado, o que nao acontece. 

Isto leva a que se tivermos um j e tivermos a certeza que nao ha acontecimentos com valores maiores que clock(j), entao temos a certeza que nao temos nada desconhecido no passado.

Sera que podemos construir um relogio logico que nao depende da sincronizacao como vimos para termos esta propriedade? Estes relogios funcionam entao da seguinte forma. Cada vez que acontece alguma coisa, o relogio incrementa. Nao mede o tempo real mas de acontecimentos. Sempre que um processo envia uma mensagem, ele tem que colocar o seu relogio logico nessa mensagem. Sempre que um processo responde, ele deve calcular o maximo entre o seu relogio local e o valor que vem na mensagem + 1 e acerta imediatamente o seu relogio para esse valor.

Sera que com este relogio conseguimos resolver os mesmos problemas que com um relogio com tempo real? Vamos para isso retomar ao ultimo algoritmo. Assumiamos que uma mensagem nao demorava mais que um determinado delta a ser transmitida. Aqui nao conseguimos saber esse delta. O que aqui vamos fazer é calcular o minimo de todos os relogios logicos que conhecemos dos outros processos. Este correspondera ao conceito do delta pois é o tempo maximo ate uma mensagem é transmitida. Este algoritmo assume que ha sempre menssagens a circular pois caso contrario ele nunca consegue mandar mensagens para a zona critica de certos processos. Temos entao de mandar mensagens de vez em quando. Se dois pedidos mandarem exatamento com a mesma etiqueta, temos um empate. Uma forma de desempate é usar o identificador do processo, por exemplo, consideracos o que tem identificador de processo menor.

Neste caso, as varias copias das estrututuras de dados que existem nos varios processos sejam submetidas á mesma sequencia de operacoes, entao temos os processos a fazer exatamente a mesma coisa ao mesmo tempo, **maquina de estados replicada (RSM)**

# Acordo

## Transational commit
Vamos considerar uma sequencia de transacoes que deve ser executada de forma atomica, ou seja, ou todas as operacoes sao executadas ou nenhuma delas é executada. Um coordenador de uma **transacao** da uma ordem a um conjunto de participantes de uma transacao para efetuarem a sua parte dessa transacao. Queremos ter a certeza que todos cumprem a sua parte ou nenhum cumpre a sua parte. Em sistemas distribuidos, isto é possivel atraves do envio de uma mensagem. A dificuldade esta se um dos participantes nao conseguir completar, por exemplo, por nao ter os recursos, nao pretendem faze-lo... Para evitar este problema, temos o **algoritmo de confirmacao em duas fases (2PC)**.

## 2PC
Temos o coordenador que conhece os participantes e a transicao a ser efetuada. Antes de dar a ordem, comunica tentativamente aos participantes. Cada um dos participantes ira se preparar para executar essa ordem, verificando se todos os recursos necessarios estao disponiveis. Se sim, escrevem em disco a vontade de executar essa ordem, para que mesmo que faca reboot, que se comprometeu a comprir esta ordem. Ainda pode a qualquer momento o desistir de fazer. Cada um dos participantes entao responde de volta ao coordenador que esta preparado ou nao. O coordenado ve se todos responderam afirmativamente de mandar executar a ordem. Nesta fase, o coordenador ainda tem a hipotese de desistir da transacao. Depois de realizadas as tarefas, os participantes comunucam ao coordenador que a realizaram. Se um participante demorar muito a responder na primeira fase, o coordenador pode tomar a decisao de desistir da transicao, avisando todos os participantes. A unica limitacao deste protocolo é quando há falha do coordenador. Se esta faz o pedido mas depois ele falha, os participantes ficam com uma tarefa pendente e nao podem desistir dessa tarefa(na pratica o que se usa é que quando ele acordar se lembra que tinha esta tarefa pendente).

Vamos dar um exemplo de uma aplicacao distribuida. Imaginemos uma transacao de dinheiro entre dois brancos. Apos o cliente retirar de um banco e dinheiro e colocar no outro, vamos usar o protocolo 2PC. Como é que o coordenador sabe que existe uma transacao que tem de ser confirmada e como é que sabe quem sao os participantes nela. O cliente nao lhe quer dizer quem sao os participantes a partida pois pode so os descobrir a meio. Alem disso, teriam de ser acrescentados parametros para saber se estao a participar numa transacao. 

Primeiramente vamos considerar que primeiro há uma comunicacao com o coordenador,**begin**, depois acontece toda a logica de negocio e por ultimo se executa uma invocacao remota ao coordenador para fazer o **commit**. Isto resolve o problema do coordenador saber quando desencadear o 2PC. 

Agora temos de informar implicatametne os participantes da transacao. Vamos para isso usar uma variante dos mecanismos de invocacao remota, **invocacao remota transacional**. 

## RCP Transacional
Sao capazes de passar alguma informacao adicional, **contexto transacional**, de forma escondida entre clientes e servidores. Quando se da o begin, invoca o coordenador, dando inicio a transacao e devolve ao cliente um identificador dessa transacao que sera colocada numa variavel do cliente. Assim, na logica de negocio, ao inves de levar apenas os parametros da transacao, leva o identificador da transacao. Quando chega ao servidor, o servidor percebe que a transacao pertence a uma transacao distribuida. Assim, regista a si proprio como participante ao coordenador. Quando o coordenador confirma que o servior esta escrito na transacao, pode finalmente executar. No depoisto acontece a mesma coisa. Mais tarde, quando o cliente invoca o commit, ele envia o identificador da transacao ao coordenador, sabendo assim a lista de recursos associada a essa transacao, podendo dar inicio ao 2PC. Estao responde com sucesso ou incussecao e elimina o identificador da transacao. 

# 2P2 
## Naming
Em sistemas distribuidos temos de indicar os nomes e os enderecos, mas esta gestao pode se tornar complicada. A manipulacao direta de endereços nao é conveniente. Assim é utel ter algo que dado um nome devolva um endereço, um **serviço de diretoria**.

## Flat naming - Espaco de nomes plano
Guardar esses nomes num servidor local com um mapa. Quando iniciarmos um novo servidor ou serviço podemos contactar o servidor de diretoria e quando arranca, mandar as suas informacoes ao servidor. Tem o inconveniente de precisarmos de conhecer o endereco do servidor diretoria. Uma alternativa é a utilizazao de primitivas de difusao na rede para contactar diretamente os servidores alvo. Cada servidor está á escuta de perguntas de diretoria e quando queremos contactar um desses servidores difundimos uma pergunta de quem neste momento tem o endereco do servilo x. Quem tive, pode responder diretamente qual é o endereco que intereca (broadcast)(exemplo quem pode projetar). Ambas as alternativas tem o problema de nao poderem ser usados em grande escala. Se o servidor for unico temos um unico ponto de falha e todas as perguntas seriam dirigidas ao mesmo servidor, ficando com uma grande carga. Se usarmos broadcast, ainda acrescentava fazer interrogacoes a todos os servidores. Outra limitacao é que nao permitem facilmente que diferentes partes do espaco de nomes sejam geridos por diferentes entidades.

Para ultrapassar este problema, temos um sistema de nomes hierarquico. Diferentes entidades podem gerir um espaco de nomes. Nao temos so 1 ponto de falha pois diferentes servidores sao responsaveis por diferentes partes do espaco de nomes. Alem disso, em diferentes niveis deste dominio podemos ter uma administracao diferente. Tem a vantagem de poder ser consultado iterativamente ou recursivamente. Na recursiva, o pedido manda a um servidor raiz que depois manda os servidores responsaveis pelo nivel seguinte e redirecionam esse pedido... A resposta viagem em sentido contrario ate ser devolvido ao cliente. Tem a vantagem de o cliente estar a uma grande distancia do servidor e essa distancia so é ultrapassada uma vez. Alem disso, cada um dos servidores intermedios podem guardar as respostas dos servidores das folhas e evitar a travessia e responder mais rapido ao cliente. Tem a desvantagem de fazer com que o servidor de nomes de raiz na hierarquia pode ficar bastante sobrecarregado. Assim, a alternativa é o servidor base responder qual o servidor seguinte a ser contactado. Assim o cliente reinicia esse processo... Neste caso temos o problema de a ligacao de longa distancia ter de ser atravessado varias vezes mas a raiz fica menos sobrecarregada, bem como os outro servidores. O DNS permite ambas as formas. A primeira é permitida quando o cliente esta perto que depois depois resolve iterativamente com os servidores que tem a copia principal de cada dominio. Assim, combinamos as duas alternativas. No entanto, continua a ter um unico ponte de falha que é a raiz.

## Distributed Hash Table (DTH)
É a alternativa ao de cima. Neste caso, partes da tabela de hash sao atribuidas a diferentes servidores. Depois temos de organizar os servidores de forma a quando fizermos uma interrogacao a qualquer um dos servidores seja facil navegar na rede, em pouco passos cheguemos ao servidor que nos interessa. Mas como organizamos estas ligacoes de modo a que seja facil.

**Chord DHT**: Consideremos que temos um anel, ou seja, ao chegar ao maximo da tabela voltamos ao zero. Neste caso, tambem atribuimo uma tabela de hash a cada ponto, em **tabelas de dedos**. Um no é responsavel por todos os que o antecedem. Temos a vantagem de nao ter uma unica raiz. Sao altamente resistentes pois se um falhar continua firme. O que temos é que cada no do no é a raiz de uma arvore, logo é muito eficiente, pois chega em poucos saltos ao chegar ao destino pretendido. So temos de saber uma porta de entrada no sistema. 

Podemos tambem a transparencia se mudarmos o servidor de sitio, o cliente nao nota.

## Gossip - Propagacao de boatos
**Pretendemos que todos os destinos recebam a mesma mensagem.**Para isso, cada vez que um participante pretende espalhar uma mensagem, este seleciona um subconjunto aleatorio de todos os destinos para enviar essa mensagem. Cada um que recebe a mensagem, espalha por um subconjunto de alvos... Apos mandarem as mensagens, podem esquece-la, nao tendo de ficar a espera de confirmacoes. Isto repete-se ate todos os participantes conhecerem a mensagem. Sao conhecidos como **protocolos epidemicos**. O que envia é um agente contagioso e os recetores sao afetados pela mensagem. Cada recetor ignora duplicados das mensagens que vai recebendo, o recetor ou ja esta morto ou imune. Usamos os mesmos mecanismos que uma epidemeologista.

Fanout: Numero de destinos de uma mensagem em cad ronda. Reliability: Percentagem de nos que receberam a mensagem e a proporcao de mensagens que chega a todos os destinos(todas as mensagens serem recebidas por todos). Estes numeros sao proporcionais ao logaritmo de destinos.

Este algoritmos so é interesante com fanout altos, o que lava a que a quantidade de copias recebidas. Existem participantes que recebem um baixo numero de copias e outros que recebem um numero muito alto. Daí nascem novas propostas para reduzir o numero de copias.

Na pratica o que acontece nao é enviar a mensagem toda mas sim um anuncio de que a mensagem esta disponivel, que é muito mais pequena. A mensagem so é mesmo transmitida num pequeno numero de situacoes. Assim, **para cada destino, a mensagem chega apenas uma vez, sem recorrer a confirmacoes explicitas**. Existem variantes destes protocolos que permitem recolher informacoes como calcular maximos, minimos, medias... por valores que estao espalhados por centenas de participantes.


