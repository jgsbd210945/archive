#lang racket

; Stream preliminaries

(define-syntax cons-stream
  (syntax-rules ()
    ((cons-stream a b) (cons a (delay b)))))

(define head car)

(define (tail s) (force (cdr s))) (define stream-car car)

(define stream-cdr tail)

(define the-empty-stream (delay '()))

(define (empty-stream? s)
 (and (not (pair? s))
      (null? (force s))))

(define (map-stream fn S)
  (cons-stream (fn (head S))
              (map-stream fn (tail S))))

(define (filter-stream pred S)
  (if (pred (head S))
      (cons-stream (head S) (filter-stream pred (tail S)))
      (filter-stream pred (tail S))))

(define (1+ x) (+ 1 x))

(define (take n G)
  (if (= n 0)
      '()
      (cons (head G) (take (- n 1) (tail G)))))

(define (drop n G)
  (if (= n 0)
      G
      (drop (- n 1) (tail G))))

(define zeros (cons-stream 0 zeros))

(define (ints n) (cons-stream n (ints (+ n 1))))

(define integers (ints 0))


; Display power series

(define (show n p)
  (define (showit i p)
    (if (> i n)
        '()
        (cons (if (= i 0)
                  (head p)
                  (list '* (head p) 'z^ i))
              (showit (+ i 1) (tail p)))))
  (cons '+ (showit 0 p)))

; Problem 0
(define (series L)
  (if (null? L)
      (cons-stream 0
                   (series L))
      (cons-stream (head L)
                   (series (tail L)))))

; Problem 1
(define (old-sum G H)
  (cons-stream (+ (head G) (head H))
               (old-sum (tail G) (tail H))))

(define (old-star G H)
  (cons-stream (* (head G) (head H))
               (old-star (tail G) (tail H))))

; Problem 2
(define (zip-streams G H)
  (cons-stream (cons (head G) (head H))
               (zip-streams (tail G) (tail H))))

(define (sum G H) (map-cons-stream + (zip-streams G H)))
(define (star G H) (map-cons-stream * (zip-streams G H)))

(define (map-cons-stream fn S)
  (cons-stream (fn (car (head S)) (cdr (head S))); You can't combine a cons cell directly. So you need to separate the two.
              (map-cons-stream fn (tail S))))

; Problem 3
(define (scale c S)
  (cons-stream (* c (head S))
               (scale c (tail S))))

; Problem 4
(define (prod-z S)
  (cons 0 S))

; Problem 5
(define (deriv S) (star (tail integers) (tail S)))

; Problem 6
(define (prod G H)
  (cons-stream (* (head G) (head H))
               (sum (sum (scale (head G) (tail H))
                         (scale (head H) (tail G)))
                    (cons-stream 0
                                 (prod (tail G) (tail H))))))

; Problem 7
(define (divide G H)
  (if (= 0 (head G))
      (cons-stream 0
                   (divide (tail G) H))
      (cons-stream (/ (head G) (head H))
                   (divide (tail (difference G (scale (/ (head G) (head H)) H))) H))))

(define (difference G H) (map-cons-stream - (zip-streams G H)))

; Problem 8
(define (coeff n S)
  (if (= n 0)
      (head S)
      (coeff (- n 1) (tail S))))

; Problem 9
(define (expt S n)
  (if (= n 0)
      '(1)
      (if (= n 1)
          S
          (if (even? n)
              (prod (expt S (/ n 2)) (expt S (/ n 2)))
              (prod S (expt S (- n 1)))))))

(define (pascal n) (take (+ 1 n) (expt (series '(1 1)) n)))

(define (binomial n k) (coeff k (pascal n)))

; Problem 10
(define (hat S) (prod S (divide (series '(1)) (series '(1 -1)))))

; For problem 11

(define golden-mean (/ (+ 1 (sqrt 5)) 2))