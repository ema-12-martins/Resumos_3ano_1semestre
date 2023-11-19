# Inteligencia Artificial
A inteligencia artificial pode ser dividida em dois tipos:
**Weak AI**-> Sistemas desenhados e treinados para resolver problemas especificos e limitados. Ex: Reconhecimento de voz, jogos, recomendações de produtos... Não tem consciencia do que realmente está a ser feito. Operam dentro de limites definidos e não tem capacidade de aprender nem adaptacao. 
**Strong AI**->Tenta igualar ou seperar a inteligencia humana nas suas variadas capacidades(raciocinio, compreensao, aprendizagem...). Tem a capacidade de pensar autonomamente, aprender continuamente com novas informaçoes e ter consciencia de si mesma e do ambiente ao seu redor.


<pre>
Dados->Informacao->Conhecimento->Sabedoria
</pre>
**Dados**->São factos/observaçeos, que por si só nao tem significado. Ex.10
**Informacao**->Refere-se aos dados organizados e contextualizados de modo a terem significado. Ex. 10 anos
**Conhecimento**-> Compreender e aplicar as informaçoes. Ex. Se tem 10 anos é uma criança
**Sabedoria**->Aplicaçao do conhecimento para tomar decisoes adequadas para diferentes situaçoes. Ex. Se é criança, nao pode ser este filme.

<pre>
Programacao: Input + Program = Output
Machine Learning: Input + Output = Model
</pre>

# IA Generativa
Maquina com a capacidade de perceber, aprender e fazer qualquer atividade humana. Pode fazer diversas coisas como, textos, audio e gerar dados. Ex.ChatGPt.

# Metodosdde resolucao de problemas de procura
**Agente**-> Algo que perceciona o ambiente através de sensores e atua no ambiente atravez de **atuadores**. Agente = Arquitetura + Programa.
Este formula um objetivo e um problema. Posteriormente procura uma sequencia de acoes que resolvam o problema, executando-as uma de cada vez. Este ciclo repete-se -> **problema de procura**

Metodo paar definir o problema:
-> Representacao do Estado
-> Estado inicial(atual)
-> Estado objetivos (estado ou estados que queremos alcancar)
-> Operadores(nome, pre-condicao, efeitos, custo)
-> Custo da solucao

A solucao ideial é a sequencia de acoes que fornece o menor custo de caminho para alcançar o objetivo.

# Tipos de problemas
**Estado Unico**-> Ambiente deterministico, totalmente observavel. O agente sabe onde estará. A solucao é uma sequencia.
**Multiplos Estados**-> Ambiente deterministico, nao acessivel. O agente nao sabe onde esta. A solucao é uma sequencia.
**Contigencia**-> Ambiente nao deterministico e/ou parcialmente acessivel. Por cada acao realizada, o agente recolhe informacoes atraves do seu sensor e so depois decide a proxima acao a executar.
**Exploracao**-> Espaço de estados desconhecidos.

Ex: Problema de estado unico
Estado: 8 estados (posicao do robo e lixo (e quadrados))
Estado Inicial: Qualquer 1
Operadores: Esquerda, direita e aspirar
Teste Objetivo: Nao há lixo em nenhum dos quadrados
Custo da solucao: Cada acao custa 1 (custo total = numero de passos da solucao)

# Tipos de solucao
Apos executar a funcao de custo, o custo pode ser:
**Satisfatorio**-> É uma solução
**Semi-otima**->É a que tem proximidade ao menos custo de todas as solucoes
**Otima**->Tem o menor custo de entre todas as solucoes.

# Como fazer procura de solucao
1. Começar com o estado inicial.
2. Executar o teste do objetivo.
3. Se nao foi encontrada a solucao, usar os operadores para expandir o estado atual, gerando novos estados-sucessores.
4. Executar teste objetivo.
5. Se nao tivermos enocntrado a solucao, escolher qual o estado a expandir a seguir(estrategia de procura) e realizar essa expansao.
6. Voltar a 4.

Podemos formar arvores de procura on o estado inicial é o no raiz e os ramos de cada no sao as acoes possiveis. As folhas ou nao tem sucessores ou ainda nao foram expandidos.

# Avaliar algoritmos de procura
**Completude**: Se garante que se encontra solucao.
**Complexidade no tempo**: Quando tempo demora a encontrar a solucao.
**Complexidade no espaco**: Quanta memoria necessita para fazer a procura.
**Otimizacao**: Se encontra a melhor solucao.

A complexidade de tempo e no espaco sao medidos olhando para.
b: o maximo fator de ramificacao da arvore de procura
d: a profundidade da melhor solucao
m. A maxima profundidade do espaco de estados

# Estrategias de procura
**Nao informadas (1)**-> Usam apenas a informacao disponivel pela definicao do problema.
**Informada (2)**-> Da-se ao algoritmo dicas sobre adequacao de diferentes estados. Se o universo é totalmetne conhecido, a heuristica sera desenvolvida atraves da atibuicao de numeros. Caso contrario, a heuristica sera desenvolvidade atraves da aplicacao de regras.

## Procura Primeiro em Largura (Breadth-first search) -> 1
Todos os nos de menor profundidade sao expandidos primeiro. É bom pois é uma procura sistematica e é mau pois normalmente demora muito tempo e sobretudo muito espaco. **So pode resolver pequnos problemas**.
Completo de b for finito. Otimo se o custo de cada passo for 1.

![BFS](https://techvidvan.com/tutorials/wp-content/uploads/sites/2/2021/07/TV-BFS-normal-image03.jpg)

## Procura Primeiro em Profundidade (Depth-First_search) -> 1
Expande empre um dos nos. È bom porque usar pouca memoria e para problemas com muitas solucoes e é mau porque nao pode ser usado em arvores com profundidade infinita (pode se perder nos ramos).
Nao é comleto pois falha oara espaços com profundidade infinita (com loops). Não é otimo pois devolve a primeira solucao que encontra.

## Procura custo uniforme -> 1
Para cada no da lista de estados nao expandidos, guardar o custo total do caminho estado inicial para esse no. Expandir sempre o no com menor custo da lista de estados nao expandidos(medido pela funcao de custo da solucao).
Semelhante a BFS se os custos fossem 1.
É completa se p custo da etapa for maior que alguma constante. É otima.

## Procura Iterativa Aprodunfamente Progressivo  -> 1
É o DFS com uma subrotina
1. Verifica a raiz
2. Desenvolve DFS procurando um caminho de comprimento 1
3. Se nao houver um caminho de comprimento 1, desenvolve DFS procurando um caminho de comprimento 2
4. ...

![PIAP](https://slideplayer.com.br/slide/5612379/2/images/35/Busca+de+Aprofundamento+Iterativo+em+Profundidade+l+%3D3.jpg)

È completo e é otimo se o custo for 1. **Melhor estrategia para problemas com um grnade espaco de procura e em que a profundidade da solucao nao é conhecida**

## Procura bidirecional -> 1
Executa uma procura para a frente desde o estado incial e para tras desde o objetivo, simultaneamente. É bom pois pode reduzir drasticamente a complexidade de tempo. Tem varios problemas como: sera possivel gerar os estados predecessores? O que fazer se tem mais que 1 estado objetivo? Como juntar as 2 procuras? Que tipo de procura fazer nas 2 metades?

## Procura Gulosa (Greedy-Shearch) -> 2
Expande o no que parece estar mais perto da solucao. A heuristica corresponde ao custo estimado do caminho mais curto do estado n para o objetivo(Exp.Distancia em linha reta).
Não é completo pois pode entrar em ciclos. O tempo pode diminuir com uma boa funcao heuristica. Mantem todos os nos em memoria. Nao é otimo porque nao encontra sempre a solucao otima.

![GS](https://www.researchgate.net/profile/Inne-Husein/publication/323137457/figure/fig1/AS:664823503273985@1535517683556/Greedy-Search-using-h-value-11-Figure-2-illustrates-greedy-search-for-Bucharest-with_Q320.jpg)

## A*
Evita expandir caminhos que sejam dispendiosos. Juncao de gulosa com custo uniforme.
f(n)=g(n)+h(n) em que g(n) é o custo total ate agora para chegar ao estado n. h(n) é o custo estimado para chegar ao objetivo.
È otimo e completo. Mantem todos os nos em memoria.

![A*](https://www.101computing.net/wp/wp-content/uploads/A-Star-Search-Algorithm-Step-6.png)

# Resumo dos algortimos

| Algoritmo      | Completo | Ótimo | Tempo                       | Espaço                      |
|----------------|----------|-------|-----------------------------|-----------------------------|
| BFS            | Sim      | Se todos os custos escalonados forem iguais | B^d | B^d    |
| DFS            | Não      | Não   | b^m                         | bm                          |
| Iterativa      | Sim      | Se todos os custos escalonados forem iguais | b^d | bd                          |
| Custo Uniforme | Sim      | Sim   | nº nodos com g(n)<=C*      | nº nodos com g(n)<=C*       |
|----------------|----------|-------|-----------------------------|-----------------------------|
| Gulosa         | Não      | Não   | Pior caso=b^m melhor caso bd| Pior caso=b^m melhor caso bd|
| A*             | Sim      | Sim se heurística for admissível | nº nodos com g(n)+h(n)<=C* (cria a tabela direita em md) |

