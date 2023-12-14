soma_lista([], 0).
soma_lista([H|T], Res):-
    soma_lista(T, Res_Aux),
    Res is Res_Aux + H.

tamanho_lista([], 0).
tamanho_lista([_|T], Res):-
    tamanho_lista(T, Res_Aux),
    Res is Res_Aux + 1.

media([], 0).
media(Lista, Media):-
    soma_lista(Lista, Soma),
    tamanho_lista(Lista, Tamanho),
    Tamanho > 0,  % Certifica-se de que o divisor não seja zero
    Media is Soma / Tamanho.

	
