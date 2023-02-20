; Write a program in the assembly language that computes the following arithmetic expression, considering the following data types for the variables:
; b,c - byte, e,f,g - word
; [(e+f-g)+(b+c)*3]/5
; ex: b=10, c=2, e=13, f=5, g=8 ; Result: [(13+5-8)+(10+2)*3]/5=[10+36]/5=46/5=9 rest 1
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    b db 10
    c db 2
    e dw 13
    f dw 5
    g dw 8

; our code starts here
segment code use32 class=code
    start:
        mov BX, [e] ; BX <- e
        add BX, [f] ; BX <- BX + f = e + f
        sub BX, [g] ; BX <- BX - g = e + f - g
        mov AL, [b] ; AL <- b
        add AL, [c] ; AL <- AL + c = b + c
        mov AH, 3   ; AH <- 3
        mul AH ; AL * AH -> result in AX
        add AX, BX  ; AX <- AX + BX = (e+f-g)+(b+c)*3
        mov BL, 5 ; divisor in BL
        div BL ; AX/BL -> quotient in AL (9), remainder in AH (1)       
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
