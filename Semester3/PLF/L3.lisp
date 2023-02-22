; 12.Write a function that substitutes an element through another one at all levels of a given list.

(defun mySubs(l elem newE)
    (cond
        ((and (atom l) (not (equal l elem))) l)
        ((and (atom l) (equal l elem)) newE)
        (T (mapcar #' (lambda (a) (mySubs a elem newE)) l))
    )
)

(defun test()
    (assert
        (and
            (equal nil (mySubs '() 1 2))
            (equal '(1 3 3 3) (mySubs '(1 2 2 3) 2 3))
            (equal '(1 (1 0 (0 (A 0 B) 1) 0)) (mySubs '(1 (1 2 (2 (A 2 B) 1) 2)) 2 0))
        )
    )
)

(test)
