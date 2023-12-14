max_lista([],0).
max_lista([H|T],Res):-
	max_lista(T,Res_Aux),
	Res_Aux>H,
	Res is Res_Aux;
	Res is H.
    
