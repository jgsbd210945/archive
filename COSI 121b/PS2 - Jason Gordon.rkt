#lang Racket

; This procedure, not to be used, marks where in the code you have to fill stuff in...

(define ***fill-in-your-code-here*** '())

;;;; Beginning...

(define (ints from to)
  (if (> from to)
      '()
      (cons from (ints (+ 1 from) to))))

; a tail recursive reverse

(define (reverse L)
  (rev L '()))

(define (rev L S)
  (if (null? L)
      S
      (rev (cdr L) (cons (car L) S))))

;;;; Exercise 3 melding two trees

(define (meld a b)
  (if (and (integer? a) (integer? b))
      (if (< a b)
          (list b a)
          (list a b))
      (if (< (car a) (car b))
          (cons (car b) (cons a (cdr b)))
          (cons (car a) (cons b (cdr a))))))

;;;; Exercise 4  evenmeld

(define (length lst)
  (if (null? lst)
      0
      (+ 1 (length (cdr lst)))))

(define (evenmeld L)
  (cond ((null? L) '())
    ((>= (length L) 2) (cons (meld (car L) (cadr L)) (evenmeld (cddr L))))
    (else (meld (car L) (cdr L)))))

;;;; Exercise 5 trees

(define (trees L)
  (if (>= 1 (length L))
      L
      (if (even? (length L))
          (cons '() (trees (evenmeld L)))
          (cons (car L) (trees (evenmeld (cdr L)))))))

(define (queue L)  (reverse (trees L)))

;;;; binary numbers

(define (binary n)
  (if (= n 0)
      (list 0)
      (bin n)))

(define (bin n)
  (if (= n 0)
      '()
      (if (even? n)
          (cons 0 (bin (/ n 2)))
          (cons 1 (bin (/ (- n 1) 2))))))

(define (decimal bs)
  (if (null? bs) 0 (+ (car bs) (* 2 (decimal (cdr bs))))))


;;;; Exercise 6 increment

(define (increment B)
  (if (null? B)
      (list 1)
      (if (= 0 (car B))
          (cons 1 (cdr B))
          (cons 0 (increment (cdr B))))))

;;;; Exercise 7  add

(define (plus a b)
  (if (< (length a) (length b))
      (add a b 0)
      (add b a 0)))

(define (add S L c)
  (if (= c 0)
      (if (null? S)
          L
          (if (= (car S) 0)
              (cons (car L) (add (cdr S) (cdr L) 0))
              ; (car S)= 1
              (if (= (car L) 0)
                  (cons (car S) (add (cdr S) (cdr L) 0))
                  ; (cdr L)= 1
                  (cons 0 (add (cdr S) (cdr L) 1)))))
      ; c= 1
      (if (null? S)
          (if (null? L)
              1
              ; L isn't empty, but is 0
              (if (= (car L) 0)
                  (cons 1 (add S (cdr L) 0))
                  ; (car L)= 1
                  (cons 0 (add S (cdr L) 0))))
          (if (= (car S) 0)
              (if (= (car L) 0)
                  (cons 1 (add (cdr S) (cdr L) 0))
                  (cons 0 (add (cdr S) (cdr L) 1)))
              ; (car S)= 1
              (if (= (car L) 0)
                  (cons 0 (add (cdr S) (cdr L) 1))
                  ; (car L)= 1
                  (cons 1 (add (cdr S) (cdr L) 1)))))))

(define (check a b)
  (let ((as (binary a))
        (bs (binary b)))
    (let ((cs (plus as bs)))
      (write (list a '+ b '= (+ a b))) (newline)
      (write (list 'as '= as)) (newline)
      (write (list 'bs '= bs)) (newline)
      (write (list 'cs '= cs '=_10 (decimal cs))) (newline)
      cs)))

;;;; Exercise 8  max-queue

(define (max-queue Q)
  (if (null? Q)
      -1
      (if (integer? (car Q))
          (max (car Q) (max-queue (cdr Q)))
          (max (max-queue (car Q)) (max-queue (cdr Q))))))

;;;; Exercise 9  insert

(define (make-queue)
  (list '()))

(define (insert x Q)
  (if (null? Q)
      (list x)
      (if (null? (car (reverse Q)))
          (if (integer? x)
              (reverse (cons x (cdr (reverse Q))))
              (reverse (cons '() (cons x (cdr (reverse Q))))))
          (if (integer? (car (reverse Q)))
              (reverse (cons '() (reverse (insert (meld (meld (car (reverse Q)) x) (cadr (reverse Q))) (reverse (cddr (reverse Q)))))))
              (reverse (cons '() (reverse (insert (meld x (car (reverse Q))) (cdr Q)))))))))

;;;; Exercise 10 find

(define (find v Q)
  (if (null? Q)
      '()
      (if (integer? Q)
          (if (= v Q)
              v
              '())
          (if (null? (car Q))
              (find v (cdr Q))
              (if (integer? (car Q))
                  (if (< v (car Q))
                      (find v (cdr Q))
                      (if (> v (car Q))
                          '()
                          v))
                  (if (< v (caar Q))
                      (if (null? (find v (cdar Q)))
                          (find v (cdr Q))
                          (find v (cdar Q)))
                      (if (> v (caar Q))
                          (find v (cdr Q))
                          v)))))))
              

;;;; Exercise 11 remove

(define (remove v Q)
  (if (null? Q)
      '()
      (if (integer? (find v Q))
          (if (null? (car Q))
              (cons (car Q) (remove v (cdr Q)))
              (if (integer? (car Q))
                  (if (= v (car Q))
                      (cons '() (cdr Q))
                      (if (null? (cdr Q))
                          Q
                          (if (integer? (cdr Q))
                              (if (= v (cdr Q))
                                  (cons (car Q) '())
                                  Q)
                              (cons (car Q) (remove v (cdr Q))))))              
                  (if (= v (caar Q))
                      (cdr Q)
                      (if (> v (caar Q))
                          (if (integer? (find v (cadr Q)))
                              (cons (car Q) (remove v (cdr Q)))
                              (cons (car Q) (remove v (cdr Q))))
                          (if (integer? (find v (car Q)))
                              (cons (remove v (car Q)) (cdr Q))
                              (if (integer? (cadr Q))
                                  (if (= v (cadr Q))
                                      (cons (car Q) (cons '() (cddr Q)))
                                      (cons (car Q) (remove v (cdr Q))))
                                  (if (integer? (find v (caadr Q)))
                                      (cons (car Q) (cons '() (remove v (cdr Q))))
                                      (cons (car Q) (remove v (cdr Q))))))))))
          Q)))

;;;; Exercise 12 merge

(define (merge Q1 Q2)
  (if (> (length Q2) (length Q1))
      (reverse (merge-queue (reverse Q2) (reverse Q1) '()))
      (reverse (merge-queue (reverse Q1) (reverse Q2) '()))))

(define (merge-queue Q1 Q2 T)
  (if (null? T)
      (if (null? Q2)
          Q1
          (if (null? (car Q2))
              (cons (car Q1) (merge-queue (cdr Q1) (cdr Q2) '()))
              (if (null? (car Q1))
                  (cons (car Q2) (merge-queue (cdr Q1) (cdr Q2) '()))
                  (cons '() (merge-queue (cdr Q1) (cdr Q2) (meld (car Q1) (car Q2)))))))
      (if (null? Q2)
          (if (> (length (list T)) (length Q1))
              (merge-queue (list T) Q1 '())
              (merge-queue Q1 (list T) '()))
          (if (null? (car Q2))
              (cons '() (merge-queue (cdr Q1) (cdr Q2) (meld (car Q1) T)))
              (if (null? (car Q1))
                  (cons '() (merge-queue (cdr Q1) (cdr Q2) (meld (car Q2) T)))
                  (cons T (merge-queue (cdr Q1) (cdr Q2) (meld (car Q1) (car Q2)))))))))
              
                  

;;;; Exercise 13 remove-max

(define (remove-max Q)
  (define top-Q (find-max Q '(-100000000)))
  (if (null? Q)
      '()
      (if (and (null? (car Q)) (null? (cdr Q)))
          '()
          (merge (remove (car top-Q) Q) (cdr top-Q)))))

(define (find-max Q mQ); modified find function to find and return the queue of the largest.
  (if (null? Q)
      mQ
      (if (null? (car Q))
          (find-max (cdr Q) mQ)
          (if (integer? (car Q))
              (if (> (car Q) (car mQ))
                  (find-max (cdr Q) (cons (car Q) '())); if it's the last one
                  (find-max (cdr Q) mQ))
              (if (> (caar Q) (car mQ))
                  (find-max (cdr Q) (car Q))
                  (find-max (cdr Q) mQ))))))

(define (test Q)
   (write Q)
   (newline)
   (if (null? Q)
       '()
       (test (remove-max Q))))