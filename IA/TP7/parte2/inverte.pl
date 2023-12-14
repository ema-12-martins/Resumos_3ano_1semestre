inverte([], []).
inverte([H|T], Res) :-
    inverte(T, Resto),
    append(Resto, [H], Res).


