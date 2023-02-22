; 7.
; Return the level of a node X in a tree of type (1). 
; The level of the root element is 0. 
; For example:
;  A
; / \
; B C
;  / \
;  D E
;(A 2 B 0 C 2 D 0 E 0) 

; Mathematical model:
; 
; isNode (l1, ....ln, e) = { false, if l is empty
;                           true, if e = l1
;                           isNode(l3, ..., ln, e), otherwise
;                         }
;
; crossLeftSubtree (p1, ..., pn, nr_v, nr_m) = { [], if p is empty
;                                                [], if nr_v = 1 + nr_m
;                                                p1 (+) p2 (+) crossLeftSubtree(p3, ..., pn, nr_v + 1, nr_m + p2), otherwise
;                                              }
;
; leftSubtree (p1, ..., pn) = crossLeftSubtree(p3, ...,pn, 0, 0)
; 
;
; crossRightSubtree (q1, ..., qn, nr2_v, nr2_m) = { [], if q is empty
;                                                   q, if nr2_v = 1 + nr2_m
;                                                   crossRightSubtree(q3, ..., qn, nr2_v + 1, nr2_m + q2), otherwise
;                                                 }
;
; rightSubtree (q1, ..., qn) = crossRightSubtree(q3, ...,qn, 0, 0)
;
; treeLevel(l1, ..., ln, e) = { -1, if l is empty
;                               0, if e = l1 
;                               1 + treeLevel(leftSubtree(l1, ..., ln), e) , if isNode(leftSubtree(l1, ..., ln), e)
;                               1 + treeLevel(rightSubtree(l1, ..., ln), e), otherwise
;                             }
;
; main (l1, ..., ln, e) = { -1, if isNode(l1, ..., ln, e) is false
;                           treeLevel(l1, ..., ln, e), otherwise
;                         }

(defun isNode (l e)
    (cond 
        ((null l) nil)
        ((eq (car l) e) t)
        (t(isNod (cddr l) e ))
    )
)
;
(defun crossLeftSubtree(p nr_vs nr_ms)
 (cond
 ((null p) nil)
 ((= nr_vs (+ 1 nr_ms)) nil)
 (t (cons (car p) (cons (cadr p) (crossLeftSubtree (cddr p) (+ 1 nr_vs) (+ (cadr p) nr_ms)))))
 )
)
;
(defun leftSubtree(p)
    (crossLeftSubtree (cddr p) 0 0)
)
;
(defun crossRightSubtree(q nr2_vd nr2_md)
 (cond
 ((null q) nil)
 ((= nr2_vd (+ 1 nr2_md)) q)
 (t (crossRightSubtree (cddr q) (+ 1 nr2_vd) (+ (cadr q) nr2_md)))
 )
)
;
(defun rightSubtree(q)
    (crossRightSubtree (cddr q) 0 0)
)
;
(defun treeLevel (l e)
    (cond
        ((null l) nil)
        ((eq (car l) e) 0)
        ((isNode(leftSubtree l) e ) (+ 1 (treeLevel (leftSubtree l) e)))
        (t (+ 1 (treeLevel (rightSubtree l) e)))
    )
)
;
(defun main (l e)
    (cond
        ((not (isNode l e)) -1)
        (t(treeLevel l e))
    )
)
