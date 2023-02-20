; Write a program in the assembly language that computes the following arithmetic expression, considering the following data types for the variables:
; a,b,c,d - word
; a+b-(c+d)+100h
; ex: a=15, b=5, c=30, d=20 ; Result: 15+5-(30+20)+100h=20-50+256=-30+256=226

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 15
    b dw 5
    c dw 30
    d dw 20

; our code starts here
segment code use32 class=code
    start:
        mov AX, [a]
        add AX, [b]
        mov BX, [c]
        add BX, [d]
        sub AX, BX
        add AX, 100h
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
