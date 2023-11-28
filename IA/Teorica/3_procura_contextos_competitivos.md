# Tipos de ambiente
**Acessivel vs Inacessive**: Um ambiente acessivel é aquele em que o agente pode obter informacoes completas, exatas e atualizadas sobre o estado do ambiente.

**Deterministico vs Nao-deterministico**: Num ambiente deterministico, qualquer acao tem um unico efeito garantido.

**Estático vs Dinámico**: Num ambiente estatico, o mundo nao se altera enquanto o agente esta a atuar.

**Discreto vs Continuo**:Num ambiente discreto existe um numero fixo e finito de acoes e percecoes possiveis.

**Episodico vs Nao-episodico**: Num episodico, o tempo de execussao do agente pode ser dividido numa serie de intervalos que sao independentes uns dos outros. O que acontece num episodio nao tem influencia sobre os outros.

# Ambiente
**Sensores**:Fornecem uma lista de perceçoes que é a forma que o agente tem para percecionar o mundo.
**Atuadores**:Lista de acoes que o agente pode tomar no mundo.
Exp:
sensor: Camaras
atuadores: Pode acelerar ou tem que travar

# Jogos como problemas de procura
Neste caso, temos mais que um agente de na procura de solucoes no mesmo espaco de procura. Neste caso, na consideracao das acoes temos de englobar as acoes dos outros agentes e como estas nos vao influenciar.
imprevisibilidade->contigencias no processo de resolucao de problemas.

Neste tipo de contexto, nao sabemos como o adversario ira jogar pelo que a solucao nao é uma sequencia fixa de acoes.

Caracteristicas:
Temos um **agente hostil** (adversario) incluido no mundo. Esse oponente é imprevissivel pelo que a solucao é uma **plano de contegencia**. Se tivermos um tempo objetivo é pouco provavel que encontremos o objetivo.

Como definir estes problemas como um probrlma de procura:
1. Estado inicial: posicao no tabuleiro e qual o proximo jogador a jogar.
2. Conjunto de operadores: Definem os movimentos legais
3. Teste Terminal: Determina se o jogo acabou
4. Funcao de Utilidade: Dá um valor numerico par ao resultado do jogo: Um exeplo é 0 para empate, -1 para derrota e 1 para vitoria.

# Minmax

![Minmax](https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Minimax.svg/400px-Minimax.svg.png)

Se nos formos o max:
1. Gerar a arvore completa ate aos estados terminais
2. Aplicao a funcao de utilidade a estes estados
3. Calcular os valores de utilizade ate á raiz da arvore, uma de cada vez.
4. Escolher o movimento com o valor mais elevado

Assim, assomimos que o adversario faz sempre a melhor jogada possivel. Temos de escolher a jogada que oferece o melhor retorno do pior caso.

Este algoritmo é:
Completo: pois a arvore é finita.
Otimo: Sim, contra um adversario otimo.
Complexidade Tempo: b^m
Complexidade Espaco: bm
Temos o problema que é inviavel para um jogo minimamente complexo.

# Alpha Beta Pruning
![ABP](https://d1m75rqqgidzqn.cloudfront.net/wp-data/2020/05/09000109/Blog-8-5-2020-06-1024x598.jpg)

O alpha contem o valor maximo enquanto que o beta contem o valor minimo. Assim o alpha inicia com menos infinito e o beta com mais infinito.
Se alpha maior ou igaul que beta, vamos cortar os ramos.

O alpha define o melhor valor para max pelo que se v for menor que alpha, o max deve ignorar.

# Monte Carlo Tree Search
Aplicado a jogos com arvores profundas, grande fator de ramificacao e sem boas heuristicas.
1. Selecionar um no folha para expansao 
2. Executar uma **simulacao** usando uma politica padrao(como é exemplo movimentos aleatorios) ate que se atinja um estado terminal.
3. Propagar novamente o resultado par atualizar as estimativas de valores dos nos internos.

selection->expantion->simulacao->back propagation
Pega num no, expande um no e simula uma situacao de jogo. Dependendo dos resultados, atualiza os valores

# Jogos Estocasticos
Este tipo de jogos combina habilidade e sorte. Neste caso, a arvore de procura deve incluir nos de probabilidade.

| Jogo         | Informação      | Classificação    |
|--------------|-----------------|------------------|
| Xadrez       | Perfeita        | Determinístico   |
| Go           | Perfeita        | Estocástico      |
| Damas        | Perfeita        | Determinístico   |
| Gamão        | Perfeita        | Estocástico      |
| Monopólio    | Perfeita        | Determinístico   |
| Batalha Naval| Imperfeita      | Estocástico      |
| Scrabble     | Imperfeita      | Estocástico      |
| Poker        | Imperfeita      | Estocástico      |
| Bridge       | Imperfeita      | Determinístico   |

# Expectiminimax
Para nos sorte somar os valores dos estados sucessores ponderados pela porbabilidade de cada sucessor.

Temos uma profundidade maxima e nessas folhas usar a funcao de avaliacao. Esta funcao neste caso deve indicar a probabilidade de ganahr em cada estado.

![Exepct](https://courses.engr.illinois.edu/cs440/fa2018/lectures/expectiminimax.png)

# Onde usar
Se conhecemos as probalilidades de diferentes configuracoes e queremos maximizar os ganhos medios(por exemplo, se podermos jogar o jogo varias vezes), ai devemos usar o **expectiminimax**.
Se nao temos ideia das probalilidades de diferentes configuracoes ou se somente podermos jogar uma vez, entao devemos usar **minimax**.
Se temos informacao desconhecida delecionada intencionalmente pelo oponente, **teoria de jogos**


Links:
[ABP](https://www.youtube.com/watch?v=_i-lZcbWkps) e
[MC](https://www.youtube.com/watch?v=ghhznqBoESY)
