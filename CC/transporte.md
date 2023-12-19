# Serviço e protocolo de transporte
Possibilita uma **ligacao logica entre aplicacoes** que estao a ser executadas em sistemas terminais diferentes.
Os protocolos de transporte sao executados nos **sistemas terminais**. O **emissor** parte a mensagem gerada pela aplicacao em **segmentos** que passa a camada de rede. O **recetor** junta os diferentes segmentos que constituem uma mensagem e passa para a camada de aplicacao.

A camada de rede fornece uma licaçao logica entre dois sistemas terminais enquanto que a camada de transporte fornece a comunicacao logica entre processos.

# Protocolos da camada de transporte
**TCP**: Troca de dados fiavel e ordenada pois temos controlo de fluxo, controlo de erros e controle de congestionamentos. É orientado a conneccao.
**UDP**:Nao é fiavel e desordenado. Não é orientado á conneccao.

# Multiplexagem
No emissor ha **multiplexagem** pois recolhe-se informacao de diferentes sockets e delimita-os com os respetivos cabeçalhos, construindo segmentos.
No recetor temos **desmultiplexagem** pois temos de entregar os segmentos ao socket certo. **É realizada pelo sistema terminal destino ao receber um datagrama IP**. O sistema terminal usa os endereços IP e os numeros de porta para encaminhar o segmento para o socket correto.

# Desmultiplexagem nao orientada a coneccao.
Quando o host recebe um segmento UDP, primeiro verifica que a porta destino do segmetno. Depois, direciona o segmento UDP para p socket com essa porta. Datagramas IP com o mesmo IP destino e porta destino mas com IP de origem diferentes e/ou portas de origem diferentes sao dirigidas para o mesmo socket.

# Desmultiplexagem orientado a coneccao
A aplicacao cria um socket e uma coneccao com o servidor destino para enviar dados. O servidor pode ter varias coneccoes TPc rem simultaneo.
Os sockets tpc indicam-se com 4 campos:endereco ip origem, endereco ip destino, nº porta origem e nº porta destino.
Mesmo que tenham a mesma porta destino, serao direcionados para sockets diferentes pois tem ip de origem e/ou portas de origem diferentes.

# UDP
Protocolo fim-a-fim nao fiavel. Orientado ao datagrama (sem coneccao). Portas reservadas 0 a 1023. Dinamicas das 1024 a 65535. È usado quando as aplicacoes querem controlar o fluxo de dados e gerir erros de transmissao diretamente.
Relativamente á desmultiplexagem...O socket UDP é identificado por um endereco ip destino e um numero de porta. Quando o sistema terminal recebe o UDP, verifica qual o numero da porta destino que consta no segmento UDP e redireciona o segmento para o socket com esse numero de porta. Temos entao que diferentes ip origem e/ou portas origem podem ser redirecionados para o mesmo socket.

Largura:32 bits
Comprimento: ate 64 kBytes
![UDP](https://i.imgur.com/BxixsOO.png)
O comprimento e o controle de erros incluem cabecalho e dados.

Relativamente ao **conrtrole de erros**. O controle de erros é sobre o datagrama completo, ou seja, cabecalho e dados. É usado o complemento para 1 da soma de grupos de 16bits. O calculo é facultativo mas a verificacao é obrigatoria. Se checksum=0 o calculo nao foi efetuado. Se o checksum for diferente de 0 e o recetor detetar erro, o datagrama é descartado, nao é gerada menssagem de erro para o transmissor, a aplicacao de rececao é notificada.

# Porque usar UDP
Pois a aplicacao pode ter maior controlo sobre o envio dos dados, envia ou reenvia os dados sem deixar essa decisao ao transporte(foge ao controlo de congestao do TCP) e decide quantos bytes manda realmente de cada vez.
Nao ha estabelicimento e termino de coneccao.
Nao se tem de manter informacao sobre o estado da coneccao.
Menos overhead por pacote(cabeçalho sao apenas 8 bytes).

# TCP
Transforte fiavel fim a fim. Tem conecoes, efetua associacoes logicas fim-a-fim.
Orientado ao fluxo.
Cada coneccao(circuito virtual entre portas de aplicacoes) é identificada por um par de sockets.
Multiplexa os dados de varias aplicacoes atraves de numero de porta.
Tem controle de erros, controlo de fluxo e controlo de congestao.
Quanto á desmultiplexagem...Um socket é identificado por 4 numeros, IP origem, IP destino, Porta origem e porta destino. O sistema terminal com esses 4 numeros redireciona o segmento para o socket correto.
Um servidor suporta varios sockets TCP simultaneamente. Os servidores web tem sockets diferentes para cada cliente.

Comprimento: 32bits
Largura: Ate ao apontador de urgencia sao 20 octetos.
![TCP](https://materiais.imd.ufrn.br/materialV2/assets/imagens/redes-de-computadores-i/red_compt_a13_f04_a.jpg)

Numero de Sequecia: Numero do primeiro octeto de dados do segmetento (Se syn=1).

Numero de ack: Numero do octeto que o TCP espera receber.

Janela: Numero de octetos que o recetor é capaz de receber.
Apontador de Urgencia=?

Flags: Por o bit a 1

URG: Indica se o apontador de urgencia é valido.

ACK: Indica se o numero de sequencia de confirmacao valido.

PSH: O recetor deve passar os dados imediatamente á apliacacao.

RST: Indica que a coneccao TCP vai ser reiniciada.

SYN: Indica que os numeros de sequencia devem ser sincronizados para se inicaiar uma coneccao.

FIN: Indica que o transmissor terminou o envio de dados.

# Estabelecimento de ligacao
1. Cliente envia segmento SYn para o servidor. Especifica o numero de sequencia inicial, sem dados.
2. O servidor recebe o SYN e responde com um segmento SYNACK. Aloca espaco para o armazenamenteo e especifica o numero de sequencia inicial.
3. O cliente recebe o segmento SYNACK e responde com um segmento ACK que pode conter dados.

cliente: listen->synsent->estab
servidos:listen->syn rcvd->estab

# Terminar coneccao
1. Cliente manda segmento FIN. 
2. Servidor responde com FINACK.
3. Cliente responde com ACK.
cliente:estab->fin_wait_1->fin_wait_2->timed_wait->closed
servidor:estab->close_wait->last_ack->closed

# MSS(Maximum Segment Size)
Opcao que apenas aparece em segmentos SYN.É o maior bloco de dados que que pode enviar a aplicacao. Quando se inicia a ligacao, dada um indica ao outro o seu MSS.
**O maior MSS possivel é igual ao MTU do interface menos os comprimentos dos cabecalhos TPC e IP**.

# ARQ (Automatic Repeat ReQuest)
Deteta erros->Feedback do recetor->retransmissao

Os segmentos TCP tem um numero pois é necessario para ordenar os segmentos na chegada. O numero de sequencia é incrementado pelo numero de sequencia do campo de dados. Cada segmento tcp tem de ser confirmado, ACK. É valido o ACK de multiplos segmentos. O campo ACK indica o proximo byte que o recetor espera receber.
O tempo maximo de vida dos sementos é **MSL (maximum segment lifetime)**.
No TCP so ha confirmacoes positivas.

# Como definir o Timeout
Temos de estimar o RTT.

estimativa=(1-alpha)estimativaRTT + alpha*sampleRTT.
O tipico é alpha=0.125
O peso de uma amostra passada descresce exponencialmente. O sampleRTT é medido desde a transmissao do segemento ate ao repetivo ACK.

Timeout = EstimatedRTT + 4DevRTT

O DevRTT corresponde a uma margem de seguranca.

# Gestao de ACKS

| Evento no Receptor                                       | Ação da entidade TCP                               |
|----------------------------------------------------------|----------------------------------------------------|
| Chegada de um segmento com o número de sequência esperado e tudo para trás confirmado. | Atrasa envio de ACK 500ms para verificar se chega novo segmento. Senão chegar, envia ACK |
| Chegada de um segmento com o número de sequência esperado e um segmento por confirmar | Envias imediatamente um ACK cumulativo que confirma os dois segmentos. |
| Chegada de um segmento com o número de sequência superior ao esperado. Buraco detectado | Envias imediatamente um ACK duplicado indicando o número de sequência esperado. |
| Chegada de um segmento que preenche completa ou incompletamente um buraco | Se o número do segmento coincidir com o limite inferior do buraco, envia ACK imediatamente. |

# Fast Retransmit
O timout por vezes é longo demais, o que leva a atrasos na retransmissao de um pacote perdido. Assim, recetor percebe perdas atraves de acks duplicados.
Normalmente o emissor manda varios segmentos seguidos. NO caso de algum deles se perder, vai haver ACKS duplicados. Se o emissor recerbe 3 ACKS duplicados, supoes que o segmento repetido foi perdido e retransmite-o **Fast Retransmit**.

# Fast Recovery
Implementado junto com o fast retransmit. Nao é obrigatorio mas é recomendado. Por cada ACK recebido, enquanto nao for recebido o ACK em falta, estica-se temporariamente a janela em 1 MSS. Quando chega um ACK emf alta ou comulativo que cubra o ACK em falta, abandona-se o estado de recuperacao rapida.

# Controle de Fluxo
O recetor controla o emissor para que o emissor nao extravasse o buffer de rececao, enviando demasiados dados ou demasiado depressa.

O espaco do buffer do recetor é limitado. O espaco livre é anunciado ao emissor num campo de segmento TCP enviado no ensido contrario **RCVWindows**. O emissor sabe sempre o maximo que pode mandar.
RCvWIndows= RCVBuffer-(LastBYteRCV-LastByteRead)

# Controle de congestao
1. AIMD(Additive Increase/Multiplicate Decrease): Sempre que chega um ACK esperado, o tamanho da janela de congestao é incrementado. Quando chega AKCS duplicados, o tamanho da janela de congestao diminui para metade.
2. Slow start: No inicio, temos 1MSS. Sempre que é recebido um ACk, a Janela aumenta 1MSS(cresce exponencialemente) até ser detetada a primeria perda ou o patamar de congestao. No TCP Reno (+ recente) de a perda corresponder a 1 timeout, a janela de congestao volta a 1MSS e reinicia o slowstart. Se corresponder a ACKS duplicados, entao diminuir para metade e a partir dai a janela cresce linear. De TCP Tahoe, em qualquer dos casos a janela era reiniciada para 1 MSS.
3. Conservativo depois de um timenout

Janela de congestao: LastByteSent- LAstByteAcked <=MIN(RecvWin,CongWin)

