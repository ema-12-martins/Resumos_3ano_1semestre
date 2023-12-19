# Particle Swarm Optimization

Baseado em populacao. Cada individuo tem um posicao e uma velocidade. A velocidade é atualizada por atrcao da melhor posicao passada ou por melhor posicao encontrada pelo grupo.

# Algortimo
1. Inicializar uma populacao
2. Avaliacao do individuo
3. Para cada individuo, escolher um individuo da vizinhanca. Imita esse individus. Update se uma melhor posicao foi encontrada.
4. Iterar para 3 ate a ocndicao de paragem se verificar.

# PSO
Tem em atencao a melhor posicao do passado ou a melhor posicao passada do grupo.

Se a velocidade for muito grande, os individuos passam boas solucoes. Se for lento de mais, podem ficar parados num maximo local. Temos um pareametro que é a velocidade maxima.

# FIPS
FIPS escolhe das redondezas (usa todos os seus vizinhos).

# Bare Bones PSO
Usa um distribuiacao uniforme ao invez da velocidade. Cada particula tenta uma posicao entre o seu melhor e o melhor da populacao.

# Jaya Optimization
Guarda nao so o melhor ponto como o pior ponto. Tanto a melhor como a pior interferem na procura. So vai para a posicao se for melhor do que a que ja esta.

# GWO
Escolhe a melhor de 3 posicao e faz uma combinacao random.

