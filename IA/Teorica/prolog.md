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
**Exercicio 1**:
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

**EXERCICIO 2**
Considerando a seguinte base de conhecimento:
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
~~~
alunos_do_professor(A,P):-professor(P,C),aluno(A,C).
~~~
2. Quem sao as pessoas que estao associadas a uma universidade X? (professores e alunos)
~~~
associados_a_faculdade(A,F):-funcionario(A,F);frequencia(A,F).
~~~

**EXERCICIO 2**
Segue uma tabela de quem pode doar e receber. Faca um programa que determine quais tipos sanguineos podem doar/receber de quais tipos.
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
