;8.
;a) Write a function to return the difference of two sets

is_member(el1-number, set2-list)
(defun is_member (el1 set2)
  (cond
    ((null set2) nil)
    ((equal el1 (car set2)) t)
    (t (is_member el1 (cdr set2)))))

set_diff(set1-list, set2-list)
(defun set_diff (set1 set2)
  (cond
    ((null set1) nil)
    ((null (is_member (car set1) set2)) (cons (car set1) (set_diff (cdr set1) set2)))
    (t (set_diff (rest set1) set2))))
    
(print (set_diff '(1 2 3 4 5) '(2 3 4 5 6 7)))

;b) Write a function to reverse a list with its all sublists, on all levels.

reverse_another_list(l-list)
(defun reverse_another_list (l)
  (cond
    ((null l) l)
    ((numberp (car l)) (append (reverse_list (cdr l)) (list (car l))))
    ((listp (car l)) (append (reverse_another_list (cdr l)) (list (reverse_list (car l)))))))

reverse_list(l-list)
(defun reverse_list (l)
  (cond
    ((null l) l)
    ((numberp (car l)) (append (reverse_list (cdr l)) (list (car l))))
    ((listp (car l)) (append (reverse_list (cdr l)) (list (reverse_another_list (car l)))))))
    
(print (reverse_list '(1 2 (3 (4 5) (6 7)) 8 (9 10 11))))


;c) Write a function to return the list of the first elements of all list elements of a given list with an odd number of elements at superficial level. 
my_length(l-list)
(defun my_length (l)
    (cond
    ((null l) 0)
    (t (+ 1 (my_length (cdr l))))))

odd_list_length (l-list)
(defun odd_list_length (l)
    (= (mod (my_length l) 2) 1)
)

first_elem (l-list)
(defun first_elem (l)
    (cond
        ((atom l) nil)
        ((odd_list_length l) (cons (car l) (apply 'append (mapcar 'first_elem (cdr l)))))
        (T (apply 'append (mapcar 'first_elem (cdr l))))
     )
)


;d) Write a function to return the sum of all numerical atoms in a list at superficial level.

list_sum(list-list)
(defun list_sum (list)
  (cond
    ((null list) 0)
    ((numberp (car list)) (+ (car list) (list_sum (cdr list))))
    (t (list_sum (cdr list)))))
    
(print (list_sum '(1 2 (3 (4 5) (6 7)) 8 (9 10 11))))
    
(print (first_elem '(1 2 (3 (4 5) (6 7)) 8 (9 10 11))))
