; Write a program in assembly language which computes the following arithmetic expression, considering the following domains for the variables 
; a,b,c - byte; e - doubleword; x - qword - signed interpretation
; a/2 + b*b - a*b*c + e + x
; ex: a=-40, b=5, c=2, e=20, x=5 ; Result: -20+25-400-20+5=-415+25=-390
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db -40
    b db 5
    c db 2
    e dd -20
    x dq 5

; our code starts here
segment code use32 class=code
    start:
        mov al, [a] ; AL <- a
        cbw ; AX <- a
        mov bl, 2 ; BL <- 2
        idiv bl ; AL <- a/2
        cbw ; AX <- a/2
        cwde ; EAX <- a/2
        mov ebx, eax ; EBX <- a/2
        mov al, [b] ; AL <- b
        imul byte [b] ; AX <- b*b
        cwde ; EAX <- b*b 
        add ebx, eax ; EBX <- a/2 + b*b
        mov al, [a] ; AL <- a
        imul byte [b] ; AX <- a*b
        mov dx, ax ; DX <- a*b
        mov al, [c] ; AL <- c
        cbw ; AX <- c
        mov cx, ax ; CX <- c
        mov ax, dx ; AX <- a*b
        imul cx ; DX:AX <- a*b*c
        push dx
        push ax
        pop eax ; EAX <- a*b*c
        sub ebx, eax ; EBX <- a/2 + b*b - a*b*c
        mov eax, [e] ; EAX <- e 
        add eax, ebx ; EAX <- a/2 + b*b - a*b*c + e 
        cdq ; EDX:EAX <- a/2 + b*b - a*b*c + e
        mov ebx, dword [x+0]
        mov ecx, dword [x+4] ; ECX:EBX <- x
        clc ; Clear Carry Flag
        add eax, ebx 
        adc edx, ecx ; EDX:EAX <- a/2 + b*b - a*b*c + e + x + CF
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
