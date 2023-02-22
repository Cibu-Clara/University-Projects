% 7. a)
% max(L-List, M-Maximum element, R-Result)
% flow model(i i i) (i i o)
max([],M,M).
max([H|T],M,R):- 
    H > M, 
    !,
    max(T,H,R).
max([_|T],M,R):-
    max(T,M,R).

% findMax(L-List, R-Result)
% flow model(i i) (i o)
findMax([H|T],R):- max(T,H,R).

% maxPositions(L-List, MAX-Maximum element, P-Position, R-Result list)
% flow model(i i i i) (i i i o)
maxPositions([],_,_,[]):-!.
maxPositions([MAX|T],MAX,P,R):-
    !,
    P1 is P+1,
    maxPositions(T,MAX,P1,R1),
    R = [P1|R1].
maxPositions([_|T],MAX,P,R):-
    P1 is P+1,
    maxPositions(T,MAX,P1,R).

% maxPositionsList(L-List, R-Result list)
% flow model(i,i) (i,o)
maxPositionsList([],[]).
maxPositionsList(L,R):-
    findMax(L,MAX),
    maxPositions(L,MAX,0,R).

% b)
% replaceLists(L-List,R-Result list)
% flow model(i,i)(i,o)
replaceLists([],[]):-!.
replaceLists([H|T],[H|R]):- 
    not(is_list(H)),
    !,
    replaceLists(T,R).
replaceLists([H|T],[H1|R]):-
    maxPositionsList(H,H1),
    replaceLists(T,R).
