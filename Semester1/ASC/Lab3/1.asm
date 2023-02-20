; Write a program in assembly language which computes the following arithmetic expression, considering the following domains for the variables 
; a - byte, b - word, c - double word, d - qword - unsigned representation
; (d+c)-(c+b)-(b+a)
; ex: a=12, b=15, c=30, d=100 ; Result: (100+30)-(30+15)-(15+12)=130-45-27=85-27=58 
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 12
    b dw 15
    c dd 30
    d dq 100

; our code starts here
segment code use32 class=code
    start:
        mov ebx, [c] ; EBX <- c
        mov eax, dword [d+0] 
        mov edx, dword [d+4] ; EDX:EAX <-d
        clc ; Clear Carry Flag
        add eax, ebx
        adc edx, 0 ; EDX:EAX <- d+c+CF 
        mov ebx, 0 
        mov bx, [b] ; EBX <- b
        mov ecx, [c] ; ECX <- c
        add ecx, ebx ; ECX <- c+b
        clc ; Clear Carry Flag
        sub eax, ecx 
        sbb edx, 0 
        mov ebx, 0 ; EDX:EAX <- (d+c) - (c+b) - CF
        mov bl, [a] ; BL <- a
        mov bh, 0 ; BH <- 0
        ; BX <- a
        add bx, [b] ; BX <- a+b
        clc ; Clear Carry Flag
        sub eax, ebx 
        sbb edx, 0 ; EDX:EAX <- (d+c) - (c+b) - (a+b) - CF
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
