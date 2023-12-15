# Procura de solucoes
Nao interessa o caminho ate ao objetivo. Num  problema de otimizacao pode nao se saber se ja se atingiu o otimo.

Tentamos ter uma solucao o mais perto possivel do otimo num numero limitado de tentativas. Para isso:
**exploration**: Exploracao geral do espaco de porcura.
**explotation**:Procura focada nas zonas mais promissoras.

Se so exploration temos uma visao geral do espaco de procura mas nao chegamos a um valor otimo. Se so exploitation pode levar a que a procura fique presa a um maximo local. Mata balancear temos de usar **meta-heuristicas**.
**mata-heuristicas**: Utilizam uma combinacao de escolhas aleaotio e de conhecimento historico dos resultados anteriormente adquiridos pelo metodo para de guiarem e realizarem as suas procuras em vizinhancas dentro do espaco de procura. Evita otimos lociais. Como sao aleatorios, nao sao deterministicos.

Estes metodos so dao uma solucao proxima, num menor tempo de execucao e utilizando menos recursos computacionais.

# Algoritmos de melhoria Iterativa
Mantem um unico estado (corrente) e tentam melhora-lo. Comecar com uma colucao e fazer alteracoes de forma a melhorar a sua qualidade.

**Procura Local**: As novas solucoes sao vizinhas de solucoes anteriores. Simulated annealing, Tabu Search.
**Procura global**: Distribuem o processo de procura por todo o espaco de procura.

**solucao unica**:Orientam o processo de procura atraves da melhoria da solucao anterior

**population-based**:Utilizam uma procura em paralelo por parte de varios membros da populacao, podendo ou nao existir a troca de informacao entre os individous(particle swarm optimization, genetic algorithms, ant colony optmization).

# Algoritmos melhoria iterativa e solucao unica

**hill-climbing search**: Bom em maximos locais. Inicia -se num ponto aleatorio e avalia se esse ponto. Move-se desse ponto para um vizinho. Se a nova posicao for melhor, fica-se nessa posicao. Caso contrario volta-se ao ponto inicial.
Procede-se a visitar outro vizinho.
Para encontrar uma melhor solucao, reinicia-se a pesquisa em outro sitio aleatorio. Faz se no maximo X iteracoes ou ate os resultados nao apresentarem uma melhoria significativa.
Escolhe o melhor resultado de todas as procuras.


**simulated annealing**: Permite alguns maus movimentos mas gradualmente diminui a sua dimensao e frequencia. Semelhante ao antigo so que se for para um y pior mas com probabilidaed de ir para um ponto melhor, ele vai.

**tabu search**:mantem uma sequencia de nos ja visitados. Move-se a cada iteracao para uma melhor solucao da vizinhanca nao aceitando solucoes ja visitadas. Guarda os ja visitados por certo esapco de tempo ou por numero de iteracoes.









