paresAux([], Lista, Lista).

paresAux([H|T], Pares, Res) :-
    VerSePar is H mod 2,
    VerSePar =:= 0,
    paresAux(T, [H|Pares], Res);
    paresAux(T, Pares, Res).

pares(Lista, Resultado) :-
    paresAux(Lista, [], ParesReversos),
    reverse(ParesReversos, Resultado).

