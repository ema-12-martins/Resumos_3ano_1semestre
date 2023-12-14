soma_lista([], 0).
soma_lista([H|T], Res) :-
    soma_lista(T, ResAux),
    Res is ResAux + H.
    
