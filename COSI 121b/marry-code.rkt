
#lang racket

(define (match-make proposers proposees)
  (send proposers 'reset)
  (send proposees 'reset)
  (courtship proposers proposers proposees)
  (zip-together (send proposers 'name)
                (send (send proposers 'intended) 'name)))

(define (courtship unengaged-proposers proposers proposees); Problem 1
  (if (null? unengaged-proposers)
      '()
      (begin (send unengaged-proposers 'propose)
             (courtship (currently-unengaged proposers) proposers proposees))))
  

(define (send list-of-people message)
  (if (null? list-of-people)
      '()
      (cons ((car list-of-people) message)(send (cdr list-of-people) message))))

(define (couple? p1 p2)
  (if (and (eq? (p1 'intended) p2) (eq? (p2 'intended) p1))
      #t
      #f))

(define (currently-unengaged list-of-people)
  (if (null? list-of-people)
      '()
      (if (eq? ((car list-of-people) 'intended) '())
          (cons (car list-of-people) (currently-unengaged (cdr list-of-people)))
          (currently-unengaged (cdr list-of-people)))))

(define (i-like-more? p1 p2)
  (lambda (love-list)
    (if (> (list-index p1 love-list) (list-index p2 love-list))
        p2
        p1)))

(define (list-index item list)
  (if (null? list)
      -1000000
      (if (eq? item (car list))
          0
          (+ 1 (list-index item (cdr list))))))

(define (make-person my-name)
  (let ((name my-name)
        (preference-list '())
        (possible-mates '())
        (current-intended '()))
    (define (i-like-more? person1 person2)
      (equal? (list person1 person2)
           (filter (lambda (person)
                      (or (eq? person person1) (eq? person person2)))
                   preference-list)))
    (define (me message)
      (cond ((eq? message 'name) name)
            ((eq? message 'intended) current-intended)
            ((eq? message 'loves) preference-list)
            ((eq? message 'possible) possible-mates)
            ((eq? message 'reset)
               (set! current-intended '())
               (set! possible-mates preference-list)
               'reset-done)
            ((eq? message 'load-preferences)
               (lambda (plist)
                  (set! preference-list plist)
                  (set! possible-mates plist)
                  (set! current-intended '())
                  'preferences-loaded))
            ((eq? message 'propose)
               (let ((beloved (car possible-mates)))
                 (set! possible-mates (cdr possible-mates))
                 (if (eq? ((beloved 'i-love-you) me)
                          'i-love-you-too)
                     (begin (set! current-intended beloved)
                            (write "we-are-engaged") (newline)
                            'we-are-engaged)
                     (begin (write "no-one-loves-me") (newline)
                            'no-one-loves-me))))
            ((eq? message 'i-love-you); Problem 2
             (lambda (suitor)
               (if (null? current-intended); Since messages are only sent one at a time, it's impossible to choose between multiple sent at once.
                   (begin
                     (set! current-intended suitor)
                     'i-love-you-too)
                   (if (> (list-index suitor preference-list) (list-index current-intended preference-list))
                       (begin
                         (write "Ew-no")
                         (newline)
                         'Ew-no)
                       (begin ((current-intended 'i-changed-my-mind) me)
                              (set! current-intended suitor)
                              (write "i-love-you-too"); Part of Problem 3
                              (newline)
                              'i-love-you-too)))))                   
            ((eq? message 'i-changed-my-mind)
               (lambda (lost-love)
                  (cond ((eq? current-intended lost-love)
                            (set! current-intended '())
                            (write "dumped!") (newline)
                            'dumped!)
                        (else (error 
                                 "Dumper must be engaged to dumpee! "
                                 name me lost-love)))))
            (else 
              (error "Bad message to a person " me name message))))

      me))


 (define (zip-together list1 list2)
   (if (null? list1)
       '()
       (cons (list (car list1) (car list2))
	     (zip-together (cdr list1) (cdr list2)))))

 (define (filter pred lst)
   (cond ((null? lst) '())
	 ((pred (car lst)) (cons (car lst) (filter pred (cdr lst))))
	 (else (filter pred (cdr lst)))))

;; This is a companion file for -- Stable Marriage
;; Here are some people for you to experiment with:

(define alan (make-person 'Alan))
(define bob (make-person 'Bob))
(define charles (make-person 'Chuck))
(define david (make-person 'Dave))
(define ernest (make-person 'Ernie))
(define frank (make-person 'Frank))

(define agnes (make-person 'Agnes))
(define bertha (make-person 'Bertha))
(define carol (make-person 'Carol))
(define deborah (make-person 'Debbie))
(define ellen (make-person 'Ellen))
(define francine (make-person 'Fran))

((alan 'load-preferences) 
   (list agnes carol francine bertha deborah ellen))
((bob 'load-preferences) 
   (list carol francine bertha deborah agnes ellen))
((charles 'load-preferences) 
   (list agnes francine carol deborah bertha ellen))
((david 'load-preferences) 
   (list francine ellen deborah agnes carol bertha))
((ernest 'load-preferences) 
   (list ellen carol francine agnes deborah bertha))
((frank 'load-preferences) 
   (list ellen carol francine bertha agnes deborah))

((agnes 'load-preferences) 
   (list charles alan bob david ernest frank))
((bertha 'load-preferences) 
   (list charles alan bob david ernest frank))
((carol 'load-preferences) 
   (list frank charles bob alan ernest david))
((deborah 'load-preferences) 
   (list bob alan charles frank david ernest))
((ellen 'load-preferences) 
   (list frank charles bob alan ernest david))
((francine 'load-preferences) 
   (list alan bob charles david frank ernest))

(define men (list alan bob charles david ernest frank))

(define women (list agnes bertha carol deborah ellen francine))

