# Sintaxe
**Variaveis**: Letra maiuscula ou underscore. Define uma variavel anonima. Ex: X, Y1, _Nome
**Atomos**:Letra minuscula ou devem ser indicadas entre aspas simples. Constantes. Ex. joao, 'Joao'
**Inteiros**:Sequencias numericas sem ponto ou caracteres ascii entre aspas duplas:1,-6,"a"
**Floats**:Numero com ponto. Ex:5.3
**Listas**: Sequencia ordenada dentro de [] e separados por virgulas. Ex: [a,b,c], [a|b,c]

Nao pode haver espacos entre as palavras. Para o fazer, colocar dentro de aspas.
Tambem nao podemos por 65. pois temos nesse caso de assinalar o numero de casas decimais. 
O _ é uma variavel anonima.

**write(X)**: Escrever na tela
**read(X)**: Coloca o conteudo lido na variavel X. Logo, nao poderiamos fazer read(x), pois x nao é uma variavel.

Nota:
\n:Quebra de linha
nl:Quebra de linha (usar este que nao da problemas normalmente)
\r:Retorna ao inicio da linha
\t:Tabulaçao
\%:Imprime o %

Comentarios:
%:Comentario de 1 linha
/**/:Comentario de varias linhas

# Primeiro Programa
**Exercicio 1**:Escreva um programa para ler algo do teclado e escrever o que leu.
~~~
start() :-
    write('Digite o valor de X:'),
    nl, 
    read(X),
    nl,
    write(X).
~~~
Quando pedir para digitar e tiver acabado de o fazer, digitar um ponto final.

# Factos
Factos sao sempre verdadeiros. Em seguida representamos alguns factos.
~~~
homem(x).
pai(x,y).
~~~
No ultimo facto, lemos que x é pai de y. Temos ainda que o **genitor** é pai e a **aridade** é 2.

Podemos fazer consultas
~~~
mulher(sara).
pai(jose,X).
~~~

# Regras
Fonte logica de primeira ordem.
Conclusao(argumentos):-Condicao1 Conectivo Condicao2

| Elementos | Significado  |
|-----------|--------------|
| :-        | Se           |
| ,         | E            |
| ;         | Ou           |


~~~
filho(X,Y):-pai(Y,X).
avo(X,Z):-pai(X,Y),pai(Y,Z)
~~~

**EXERCICIO 2**: Considerando a seguinte base de conhecimento:
~~~
aluno(joao,calculo).
aluno(maria,calculo).
aluno(joel,programacao).
aluno(joel,estrutura).
frequencia(joao,puc).
frequencia(maria,puc).
frequencia(joel,ufrj).
professor(carlos,calculo).
professor(ana_paula,estrutura).
professor(pedro,programacao).
funcionario(pedro,ufrj).
funcionario(ana_paula,puc).
funcionario(carlos,puc).
~~~
1. Quem sao os alunos do professor X?

2. Quem sao as pessoas que estao associadas a uma universidade X? (professores e alunos)

As solucoes sao as seguintes:
~~~
alunos_do_professor(A,P):-professor(P,C),aluno(A,C).
~~~
~~~
associados_a_faculdade(A,F):-funcionario(A,F);frequencia(A,F).
~~~

**EXERCICIO 3**: Segue uma tabela de quem pode doar e receber. Faca um programa que determine quais tipos sanguineos podem doar/receber de quais tipos.
|       | A            | B            | AB               | O                |
|-------|--------------|--------------|------------------|------------------|
| A     | Doar/Receber | -            | Doar             | Receber          |
| B     | -            | Doar/Receber | Doar             | Receber          |
| AB    | Receber      | Receber      | Doar/Receber     | Receber          |
| O     | Doar         | Doar         | Doar             | Doar/Receber     |

Temos de construir a base de conhecimento. De resto, as consultas sao diretas.
~~~
doa(a,a).
doa(a,ab).
doa(b,b).
doa(b,ab).
doa(ab,ab).
doa(o,a).
doa(o,b).
doa(o,ab).
doa(o,o).
recebe(a,a).
recebe(a,o).
recebe(b,b).
recebe(b,o).
recebe(ab,a).
recebe(ab,b).
recebe(ab,ab).
recebe(ab,o).
recebe(o,o).
~~~

# Manipular a Base de Conhecimento
Para criar predicados dinamicos é preciso colocar no inicio do ficheiro. O **consult** apenas carrega o conhecimento estatica.
:-dynamic Nome/Aridade.
No entanto as alteracoes sao volateis, ou seja, o arquivo original nao é alterado.
~~~
:-dynamic homem/1.
~~~
Posso usar o comando **listing(homem)** para listar todos os homens.


Util para robos que vao conhecendo o ambiente ao longo da sua procura:
**assert(homem(Jorge)).**: Insere no final da Base de Conhecimento.
**asserta(homem(Jorge)).**: Inserida no inicio da Base de Conhecimento.
**retract(homem(Jorge)).**: Remove o primeiro match
**retractall(pai(_,Bob)).**: Remove todos os factos que dao match.

# Aritmetica
O Prolog aceita notação infixa (2 * a + b + c) como prefixada `(+(*(2, a), +(b, c)))`.


| Operador | Função                    |
|----------|---------------------------|
| +        | Soma                      |
| -        | Diferença                 |
| *        | Multiplicação             |
| /        | Divisão                   |
| is       | Atribuição de variável    |
| mod      | Resto da Divisão          |
| ^        | Potência                  |
| cos      | Cosseno                   |
| sen      | Seno                      |
| tan      | Tangente                  |
| exp      | Exponenciação             |
| ln       | Logaritmo Natural         |
| log      | Logaritmo                 |
| sqrt     | Raiz Quadrada             |

**integer(X)**: Tranforma string em integer
**float(X)**: Converte X para ponto flutuante

| Operador | Significado   |
|----------|---------------|
| >        | Maior         |
| <        | Menor         |
| >=       | Maior igual   |
| <=       | Menor igual   |
| =:=      | Igual         |
| \=       | Diferente     |
| \+       | Negação       |

Nota se que so "=" compara os objetos de vao iguais enquanto que "=:=" se tem os mesmo valores.
1+2=2+1 ira ser falso porque o prolog tenta unificar 1 a 1, ou seja, compra o 1 com o 2 e depois o + com o + e por ultimo o 2 com o 1. No entanto, ponto(A,B)=ponto(1,2). da verdadeiro, pois uma variavel unifica com qualquer coisa.

Exp:
~~~
soma(A,B,Resultado):- Resultado is A+B
~~~

**EXERCICIO 4**
1. Crie um programa que peca no terminal um numero inteiro e imprima se o numero é maior que 100, menor ou igual.
2. Escreva uma regra que identifique a situacao de um dado aluno tendo em consideracoa que notas de 7 a 10 esta aprovado, de 5 a 6.9 esta em recuperacao e de 0 a 5 esta reprovado, tendo em consideracao os seguintes factos
~~~
nota(joao,5.0).
nota(mariana,9.0).
nota(joaquim,4.5).
nota(maria,6.0).
nota(cleuza,8.5).
nota(mara,4.0).
nota(joana,8.0).
nota(jose,6.5).
nota(mary,10.0).
~~~
3. Calcule o indice de massa corporal.

As solucoes sao entao as seguintes:
~~~
    write('Escreva um numero'),
    read(X),
    (
        (X>100,write('Numero maior que 100'));
        (X<100, write('Numero menor que 100'));
        (X=:=100, write('Numero igual a 100'))
    ).
~~~
~~~
estado_aluno(X):-
    nota(X,Nota),
    (
        (Nota >= 7.0, write('Aprovado'));
        (Nota >= 6.0, Nota < 7.0, write('Recuperacao'));
        (Nota >= 0.0, Nota < 6.0, write('Reprovado'))
    ).
~~~
~~~
imc(Peso,Altura):-
    X is Peso/(Altura*Altura),
    write('IMC: '), write(X).
~~~

# Regras recursivas
Comecamos sempre pelo caso de paragens e depois pela recursao. Seguem alguns exemplos.
~~~
descendente(X,Y):-filho(X,Y).
descendente(X,Y):-
    filho(X,W),
    descendente(W,Y).

fatorial(0, 1).
fatorial(X, Resultado) :-
    X > 0,
    X1 is X-1,
    fatorial(X1, SubResultado),
    Resultado is X * SubResultado.
~~~

**EXERCICIO 5**
1. Defina uma regra que determine que animais pertencem a cadeira alimentar de outro
~~~
animal(urso).
animal(peixe).
animal(peixinho).
animal(lince).
animal(raposa).
animal(coelho).
animal(veado).
animal(guaxinim).

planta(alga).
planta(erva).

come(urso,peixe).
come(lince,veado).
come(urso,raposa).
come(urso,veado).
come(peixe,peixinho).
come(peixinho,alga).
come(guaxinim,peixe).
come(raposa,coelho).
come(coelho,erva).
come(veado,erva).
come(urso,guazinim).
~~~
2. Cire um prigrama que resolve esta equacao:
X1=2 e Xn=Xn-1 -3n²

As solucoes sao:
~~~
pertence_cadeia(X,Y):-
    animal(X),
    animal(Y),
    (
        come(X,Y);
        come(Y,X)
    ).
pertence_cadeia(X,Y):-
    animal(X),
    animal(Y),
    (
        (come(X,W),pertence_cadeia(W,Y));
        (come(W,Y),pertence_cadeia(X,W))
    ).
~~~

# Clausulas
**!**: Corte a execucao do problema. Considerado sempre verdadeiro. Se algo ja se tiver encontrado uma solucao, n faz sentido ter de encontrar mais.
Considere f(x) que diz que se X<3 da 0, se x>=3 e x<6 da 2 e se x>6 da 4.
~~~
f(X,0):- X<3.
f(X,2):- 3=<X,X<6.
f(X,4):- 6=<X.
~~~
Neste caso, se X<3 ele vai executar as outras duas linhas na mesma, desnecessariamente. Logo:
~~~
f(X,0):- X<3,!.
f(X,2):- X<6,!.
f(X,4).
~~~

Nota: **trace** mostra o fluxo de execussao.

Considerando
~~~
m(1).
m(2):-!.
m(3).
m1(X,Y):- m(X),m(Y).
m2(X,Y):- m(X),!,m(Y)
~~~
Teriamos:
?-m(1). --------->True
?-m1(X,Y) ------->X=Y, Y=1 mas ele da mais combinacao X=1 e Y=2 , X=2 e Y=3 e depois ambos 2
?-m2(X,Y) ------->False pois encontra os X mas nao os Y
?-m(3). --------->False pois antes ele corta.

**fail**: Forca o backTrack.
**repeat**:Repete varias vezes. Ter atencao de n entrar em loop infinito.
**random**: Um random(5) gera um numero random de 0 a 4.
~~~
adivinhar_numero:-
    N is random(5)+1,
    repeat,
        lerDados(G),
        processarDados(G,N).

lerDados(G):-
    write('Escreva numero de 1 a 5'),
    read(G).

processarDados(G,N):-
    G=:=N,
    write('Acertou'),
    nl.
processarDados(G,N):-
    G\=N,
    write('Errou'),
    fail.
~~~

# Listas
A lista ou é vazia ou possui duas partes, a cabeca e a cauda.

**EXERCICIO 6**
1. Ver se um elemento pertence a uma lista.
2. Elemento é o ultimo elemento da lista.
3. A lista tem 2 elementos estao seguidos/consecutivos na lista.
4. Retornar tamanho da lista.
~~~
pertence(X,[X|_]).
pertence(X,[_,T]):-
    pertence(X,T).
~~~
~~~
ultimo(X,[X]).
ultimo(X,[_,T]):- 
    ultimo(X,T).
~~~
~~~
consecutivos(X,Y,[X,Y|_]).
consecutivos(X,Y,[_|T]):-
    consecutivos(X,Y,H).

~~~
~~~
tamanho([],0).
tamanho([_,T],Resultado):-
    tamanho(T,ResAux),
    Resultado is ResAux+1.
~~~
