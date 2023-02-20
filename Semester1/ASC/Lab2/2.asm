; Write a program in the assembly language that computes the following arithmetic expression, considering the following data types for the variables:
; a,b,c,d - byte
; (a+b-c)-(a+d)
; ex: a=10, b=30, c=5, d=20 ; Result: (10+30-5)-(10+20)=35-30=5
 
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 10
    b db 30
    c db 5
    d db 20

; our code starts here
segment code use32 class=code
    start:
        mov AL, [a]
        add AL, [b]
        sub AL, [c]
        mov AH, [a]
        add AH, [d]
        sub AL, AH
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
