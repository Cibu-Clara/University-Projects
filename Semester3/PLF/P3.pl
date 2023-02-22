% 9.
% insert(L-List, E-Element, R-Result list)
% flow model(i i i)(i i o)

insert([], E, [E]).
insert([H|T], E, [E,H|T]).
insert([H|T], E, [H|R]) :- 
         insert(T, E, R).


% permutations(L-List, R-Result list)
% flow model(i i)(i o)

permutations([], []).
permutations([H|T], R) :-
    permutations(T, RP),
    insert(RP, H, R).

% createList(N-Number, R-Resultlist)
% flow model(i i)(i o)

createList(0, []).
createList(N, [N|R]) :-
    N > 0,
    NN is N - 1,
    createList(NN, R).

% checkPerm(L-List, E-Element)
% flow model(i i)(i i)
 	
checkPerm([], _).
checkPerm([H|T], L) :-
    check(L, H),
    checkPerm(T, [H|L]).

% diff(A-Number, B-Number, R-Number)
% flow model(i i i)(i i o)

diff(A, B, R) :-
    A > B,
    R is A - B.
diff(A, B, R) :-
    A =< B,
    R is B - A.

% check(L-List, E-Element)
% flow model(i i)(i i)

check([], _).
check([H|_], X) :-
    diff(X, H, R),
    R =:= 1, !.
check([_|T], X) :-
    check(T, X).

% onesolution(L-List, R-Result list)
% flow model(i i)(i o)
onesolution(L, R) :-
    permutations(L, R),
    checkPerm(R, []).

% allsolutions(N-Number, R-Result list)
% flow model(i i)(i o)

allsolutions(N, R) :-
    createList(N, RL),
    findall(RP, onesolution(RL, RP), R).
