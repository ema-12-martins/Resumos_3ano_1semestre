# Encaminhamento IP
Os routers armazenam e reenciam datagramas IP. Desencapsula, no interface de entrada e encapsulam no interface de saida. 

# Forwarding (Reenvio)
Utiliza a tabela de reenvio previamente preenchida pelos protocolos de encaminhamento ou pelo administrador. Procura na tabela, para um dado destino, o proximo salto e o interface de saida. Comuta o pacote pelo interface respetivo, encapsulando-o numa trama de acordo com o tipo de interface.

Estamos no **plano de dados** no que toca a arquitetura do router.

# Routing (Encaminhamento)
Preenche a tabela de encaminhamento com as melhores rotas para as redes destino (classfull) ou para um conjunto de prefixos de endereços (classless). Pode ser um processo manual feito pelo administrador (**encaminhamento estatico**) ou um processo automático resultante da operacao de um protocolo de **encaminhamento dinamico**.

Estamos no **plano de controle** no que toca a arquitetura do router.

# Protocolos
1. RIP, OSPF,BGP: Protocolos de encaminhamento que selecionam o melhor caminho.
2. IP: Trata do enderecamento, formato do pacote e packet handling convetions.
3. ICMP: Reporta erros e é router signaling.

# Encaminhamento IP
**Plano de controlo distribuido**: Existe uma componente de controlo em todos os routers, ou seja, os algoritmos de routing sao distribuidos.

**Plano de controlo centralizado**:Um controlador remoto que interage com agentes locais (CA). Algoritmos de routing centralizados.

# Algoritmos de routing
Dada um topologia de rede, representar como um grafo e temos de determinar o no de origem para o de destino. O grafo nasce de os nos serem routers e as arestas links. O custo pode depender de varias funcoes como o atraso, o ritmo nominal, a congestao, o custo operacional, distancia... 

Os algoritmos de encaminhamento geralmente procuram caminhos de custo minimo.

# Tipos de algoritmos
Podem gerir a informacao de duas formas diferentes:

**Global**: Todos os routers tem conhecimento completo da topologia e custo das ligacoes. Sao algoritmos de estado de ligacoes: **Link State-LS**.

**Descentralizada**: Os routers so conhecem os vizinhos a que estao fisicamente/logicamente ligados e o custo das ligacoes respetivas. O processo de computacao é iterativo, havendo troca de informacao entre vizinhos. Estes algoritmos sao de algoritmos de vetor distancia:**Distance Vector-DV**.

# LS-Link State
Toda os nos da topologia espalham pela rede informacao osbre o estdado das duas ligacoes de forma a construirem a base de dados da topologia, segundo o metodo de **Reliable Flooding**.

Inicialmente necessitam de conhecer qpenas os seus vizinhos diretos, para quem enviam a identificacao de todos os outros seus vizinhos, bem como o custo das respetivas ligacoes. Um no ao receber a informacao, atualiza a sua base de dados topologica e reeenvia a informacao para todos os seus nos viiznhos. Ao fim de algum tempo, todos os nos possuem um cinhecimento completo da topologia e dos custos de todas as ligacoes. Com essa informacao, cada no calcula o caminho de custo minimo, normalmente com o **Dijkstra**. Com o resultado obtido da aplicacao do algoritmo anterior é preenchida a tabela de encaminhamento do no.

Lembrar que cada no gera uma mensagem **LSA** com o estado dos seus links. Cada no envia a sua mensagem LSA a todos os outros **Flooding**. Os vizinhos enviam a todos os vizinhos, execeto de quem recebeu. A rececao tem de ser confirmada. Quando todos tem conhecimento da topologia, volta para tras um ACK de quem  mandou.

Quando aploicamos o Dijkstra mas da empates, podemos escolher arbitrariamente.

# DV-Distance Vector
Sao algoritmos distribuicos, iterativos e assincronos. 

Cada no recebe informacao de encaminhamento de algum dos seus vizinhos diretos, recalcula a tabela de encaminhamento e envia essa informcao de encaminhamento de volta. O processo continua ate que nao haja informacao de encaimnhamento a ser trocada entre nos vizinhos. Nao exige que os nos esteja, sincronizados uns com os outros em relacao á topologia completa da rede.

Se soubermos dv(z)=5, dw(z)=3 e dx(z)=3 , entao du(Z)=min{ c(u,v)+dv(z), c(u,x)+dx(z), c(u,w) + dw(z)}

Segue entao os seguintes passos:
1. **wait**: Espera por mensagem do vizinho com info alteracao menor-custo do link local
2. Recalcula a tabela de distancias
3. Se mudou o menos custo, entao nofifica os vizinhos
4. volta a 1

Iterativo e assincrono: A alteracao local pode ocorrer devido a mudanca do custo do link ou porque um vizinho anunciou novo custo. Distribuido: Cada no notifica os vizinhos apenas quando muda o menor custo para qualquer destino.

Para de atualizar  tabela quando ja tiver atingido o melhor, ou seja, quando nao alterou a tabela.

# Problemas DV
Ele pode estar a aprender em loop. Para o evitar temos 2 formas.

1. **Divisao do horizonte (Split Horizon)**: Se Y aprendeu a rota para X com Z, nunca ensina essa rota a Z.
2. **Envenenamento do percurso inverso(Poison Reverse)**: Se Y aprendeu rota para X com Z, entao mente a Z anunciando que o custo da sua rota para X é infinito. 

# Comparar LS e DV
No LS todos os nos necesistam de conhecr o custo de todas as ligacoes o que leva a que sempre que uma ligacao mude o custo, menagem com novo custo tem de ser enviada para que todos os nos conhecam a nova topologia. No DV a mudanda de custo de uma ligacao so provoca o envio de mensagens se resultar na mudanca da tabela de encaminhamento.

No LS covergem mais depressa. Os DV convergem mais lentamente e podem apresentar ciclos enquanto nao convergem pelo que temos de incluir ciclos para resolver problemas de loops de eventuais contagens ate ao infinito.

O LS é mais robusto pois cada router calcula a sua tabela. No DV, se um algoritmo calcular mal a tabela, os erros vao se propagar por toda a topologia.

O LS sao mais exigente computacionalmente do que os DV quer em memoria quer em capacidade de processamento.

LS: OSPF, OSI ISIS
DV: RIP, IGRP, EIGRP

# Internet
O numero de routers é muito grande pelo que cada organizacao deve escolher um algoritmo para usar nas suas redes.

**Sistemas Autonomos (AS)**: Dentro da mesma AS, os routers usam os mesmo aslguritmos e possuem informacao sobre todos os routers que fazem parte do sistema. Os protocolos dentro de um sistema chamam-se **protocolos Intra-Dominio ou internos**.

Para interligar diferentes AS, é necessario usar, pelo menos, um router de fronteira por AS e com eles constituir uma nova rede de nivle hierarquico superior. Estes, alem de executar o protocolo intradominio, utilizam protocolos de encaminhamento Inter-Dominio ou exteriores.

As AS podem ser publicas ou privadas. Essa informacao é usada nas trocas de informacao de routers com sistemas autonomos vizinhos.

As AS que fazem negocio com a conetividade tambem se designam por **Provedoras de Servico Internet-ISP**. Estas estabelecem protocolos de parceria entre si, restrigindo trocas de rodas que estao ao mesmo nivel. Se nao estao ao mesmo nivel, o nivel inferior é cliente e o servico de nivel superior é o fornecedor do serviço.

# Tipos de protocolos (IGP e EGP)
**IGP**: Processos automaticos de descoberta e troca de informacao. Todos os routers sao de confianca, sujeitos as mesma administracao e as mesmas regras. As rotas e informacao de encaminhamento pode ser difundida livremente enre todos os routers.

**EGP**: AS relacoes com os pares sao previamente definidas manualmente. A conectividade com redes externas é definida por politicas. Definem-se limites administrativos.

No encaminhamento inter-dominio, temos de ter controle sobre como é feito o encaminhamento. No intra dominio é pouco inportante pois todos os nos estao sob a mesma autoridade administrativa.

O encaminhamneto hierarquico reduz o tamanho das tabelas de encaminhamento e a quantidade de mensagens de atualizacao de informacao de encaminhamento.

No encaminhamento intra-dominio, o desempenho é a preocupacao principal, No inter-dominio é mais importante a definicao de politicas de encaminhamento.