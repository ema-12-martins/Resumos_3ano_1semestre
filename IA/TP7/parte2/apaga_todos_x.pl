apagaAcumulado(_, [], Lista, Lista).
apagaAcumulado(X, [X | T], Lista, Res) :-
    apagaAcumulado(X, T, Lista, Res).
apagaAcumulado(X, [H | T], Lista, Res) :-
    append([H], Lista, Acumulado),
    apagaAcumulado(X, T, Acumulado, Res).

apagaTodos(X, Lista, Res) :-
    apagaAcumulado(X, Lista, [], Res).


