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

equacao(2, Resultado) :- Resultado is 1.
equacao(N, Resultado) :-
    N > 2,
    Naux is N - 1,
    equacao(Naux, ResultadoAux),
    Resultado is ResultadoAux - 3 * (N * N).