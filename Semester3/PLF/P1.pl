% 8. a)
% even_number(L-List)
% flow model (i) (i)
even_number([]).
even_number([_,_|T]):-
    even_number(T).

% b)
% min(L-List, E-Element, R-Result)
% flow model (i i i) (i i o)
min([],M,M).
min([H|T],M,R):-
    H<M,
    !,
    min(T,H,R).
min([_|T],M,R):-
    min(T,M,R).

% findMin(L-List, R-Result)
% flow model (i i) (i o)
findMin([H|T],R):-
    min(T,H,R).

% remove(L-List, E-Element,  R-Result)
% flow model (i i i) (i i o)
remove([],_,[]).
remove([H|T],MIN,[H|R]):-
    H=\=MIN,
    !,
    remove(T,MIN,R).
remove([_|T],_,R):-
    R=T.

% mainRemove(L-List, R-Result)
% flow model (i i) (i o)
mainRemove(L,R):-
    findMin(L,MIN),
    remove(L,MIN,R).
