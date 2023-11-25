pertence(X,[X|_]).
pertence(X,[_,T]):-
    pertence(X,T).

ultimo(X,[X]).
ultimo(X,[_,T]):- 
    ultimo(X,T).

consecutivos(X,Y,[X,Y|_]).
consecutivos(X,Y,[_|T]):-
    consecutivos(X,Y,H).

tamanho([],0).
tamanho([_,T],Resultado):-
    tamanho(T,ResAux),
    Resultado is ResAux+1.