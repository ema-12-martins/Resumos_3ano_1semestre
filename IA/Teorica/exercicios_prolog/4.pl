maior_que_100():-
    write('Escreva um numero'),
    read(X),
    (
        (X>100,write('Numero maior que 100'));
        (X<100, write('Numero menor que 100'));
        (X=:=100, write('Numero igual a 100'))
    ).


estado_aluno(X):-
    nota(X,Nota),
    (
        (Nota >= 7.0, write('Aprovado'));
        (Nota >= 6.0, Nota < 7.0, write('Recuperacao'));
        (Nota >= 0.0, Nota < 6.0, write('Reprovado'))
    ).


nota(joao,5.0).
nota(mariana,9.0).
nota(joaquim,4.5).
nota(maria,6.0).
nota(cleuza,8.5).
nota(mara,4.0).
nota(joana,8.0).
nota(jose,6.5).
nota(mary,10.0).

imc(Peso,Altura):-
    X is Peso/(Altura*Altura),
    write('IMC: '), write(X).