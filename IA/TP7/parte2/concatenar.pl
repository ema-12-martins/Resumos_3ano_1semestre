concat([], L, L).
concat(L, [], L).
concat([H|T], L2, [H|Res]) :-  
    concat(T, L2, Res).   

