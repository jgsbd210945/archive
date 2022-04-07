#lang racket
;; this is the code for problem set -- Lunar Lander

; Updating the ship state through one time unit when given a (constant) burn rate
;  You'll need to modify this procedure
(define (update ship-state fuel-burn-rate)
  (make-ship-state
   (+ (height ship-state) (* (velocity ship-state) dt)) ; height
   (if (<= (fuel ship-state) 0)
      (+ (velocity ship-state) ; Velocity if fuel is 0, Problem 1
      (* (- (* engine-strength 0) gravity) dt))
      (+ (velocity ship-state) ; velocity if fuel is fine
      (* (- (* engine-strength
               (if (<= fuel-burn-rate 1); making sure burn rate never exceeds 1, Problem 10
                   fuel-burn-rate
                   1))
               gravity) dt)))
   (if (<= (fuel ship-state) 0)
      (fuel ship-state) ; Fuel if fuel is 0, Problem 1
      (- (fuel ship-state) (*
                            (if (<= fuel-burn-rate 1); making sure burn rate never exceeds 1, Problem 10
                                fuel-burn-rate
                                1)dt)))))
; How to begin the "game"
;  You'll need to modify this procedure
(define (play x) (lander-loop (initial-ship-state) x)); adding a variable (Problem 2)

; Basic loop for the "game"
;  You'll need to modify this procedure
(define (lander-loop ship-state strategy)
  (show-ship-state ship-state) ; Display the current state
  (if (landed? ship-state) ; Run the next step 
      (end-game ship-state)
      (lander-loop (update ship-state (strategy ship-state)) strategy)))

; !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
; !!!!!!!!!!!!!! WRITE YOUR NEW PROCEDURES HERE !!!!!!!!!!!!!!
; !!!!!! (this includes code-based exercise solutions!) !!!!!!
; !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

; Strategies
(define (full-burn ship-state) 1)
(define (no-burn ship-state) 0)
(define (ask-user ship-state) (get-burn-rate))

; Problem 3
(define (old-random-choice x y)
  (lambda (ship-state)
    (if (= (random 2) 0)
        (x ship-state)
        (y ship-state))))

; Problem 4
(define (old-height-choice x y z)
  (lambda (ship-state)
    (if (> (height ship-state) z)
        (x ship-state)
        (y ship-state))))

; Problem 5
(define (choice strategy-1 strategy-2 predicate)
  (lambda (ship-state)
    (if (predicate ship-state)
        (strategy-1 ship-state)
        (strategy-2 ship-state))))

(define (height-choice x y z)
  (choice x
          y
          (lambda (ship-state) (> (height ship-state) z))))

(define (random-choice x y); testing for choice
  (choice x
          y
          (lambda (ship-state) (= (random 2) 0))))

; Problem 6
(define (h40)
  (choice no-burn
          (random-choice full-burn ask-user)
          (lambda (ship-state) (> (height ship-state) 40))))
  

; Problem 8
(define (constant-acc ship-state)
  (+ (/ (* (velocity ship-state) (velocity ship-state)) (* 2 (height ship-state))) 0.5))

; Problem 11
(define (optimal-constant-acc)
  (choice no-burn
          constant-acc
          (lambda (ship-state) (> (/ (+ (height ship-state) (* (velocity ship-state) dt)) (+ 0.0000001 (velocity ship-state)))
                                  (/ (height ship-state) (+ 1 (velocity ship-state)))))))
; I'm not quite sure what logic I'm messing up bere, but this is always highly sensitive and dislikes waiting for the ship to fall a bit.


; !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
; !!!!!!!!!!!!!!!!!!!!! WRITE NEW CODE ABOVE HERE !!!!!!!!!!!!!!!!!!!!!
; !!!!! (code below here is still useful to read and understand!) !!!!!
; !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

; Writing the ship's state to console
(define (show-ship-state ship-state)
  (write-line
   (list 'height (height ship-state)
         'velocity (velocity ship-state)
         'fuel (fuel ship-state))))

; Determining if the ship has hit the ground
(define (landed? ship-state)
  (<= (height ship-state) 0))

; Ending the game
(define (end-game ship-state)
  (let ((final-velocity (velocity ship-state)))
    (write-line final-velocity)
    (cond ((>= final-velocity safe-velocity)
           (write-line "good landing")
           'game-over)
          (else
           (write-line "you crashed!")
           'game-over))))

; Used in player-controlled burn strategy
(define (get-burn-rate)
  (if (= (player-input) burn-key)
      1
      0))

; Starting state of the ship
(define (initial-ship-state)
  (make-ship-state
   50  ; 50 km high
   0   ; not moving (0 km/sec)
   20)); 20 kg of fuel left

; Global constants for the "game"
(define engine-strength 1) ; 1 kilonewton-second
(define safe-velocity -0.5) ; 0.5 km/sec or faster is a crash
(define burn-key 32) ;space key
(define gravity 0.5) ; 0.5 km/sec/sec
(define dt 0.5) ; 1 second interval of simulation

; Getting the player's input from the console
(define (player-input)
  (char->integer (prompt-for-command-char " action: ")))


; You’ll learn about the stuff below here in Chapter 2. For now, think of make-ship-state,
; height, velocity, and fuel as primitive procedures built in to Scheme.
(define (make-ship-state height velocity fuel)
  (list 'HEIGHT height 'VELOCITY velocity 'FUEL fuel))
(define (height state) (second state))
(define (velocity state) (fourth state))
(define (fuel state) (sixth state))
(define (second l) (cadr l))
(define (fourth l) (cadr (cddr l)))
(define (sixth l) (cadr (cddr (cddr l))))
; Users of DrScheme or DrRacket: add these for compatibility with MIT Scheme...
; for input and output
(define (write-line x)
  (display x)
  (newline))
(define (prompt-for-command-char prompt)
  (display prompt)
  (read-char))
; for random number generation
(#%require (only racket/base random))
; a ridiculous addendum
; (you’ll need this for the exercises)
(define (1+ x) (+ 1 x))