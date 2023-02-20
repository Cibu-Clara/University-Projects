; Write a program in assembly language which computes the following arithmetic expression, considering the following domains for the variables 
; a,b,c - byte; e - doubleword; x - qword - unsigned representation
; a/2 + b*b - a*b*c + e + x
; ex: a=40, b=5, c=2, e=20, x=5 ; Result: 20+25-400+20+5=-355+25=-330
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 40
    b db 5
    c db 2
    e dd 20
    x dq 5

; our code starts here
segment code use32 class=code
    start:
        mov al, [a] ; AL <- a
        mov ah, 0 ; AH <- 0
        ; AX <- a
        mov bl, 2 ; BL <- 2
        div bl ; AL <- a/2
        mov ah, 0 ; AX <- a/2
        mov bx, ax ; BX <- a/2
        mov al, [b] ; AL <- b
        mul byte [b] ; AX <- b*b
        add ax, bx ; AX <- b*b + a/2
        mov ebx, 0 ; EBX <- 0
        mov bx, ax ; EBX <- b*b + a/2
        mov al, [a] ; AL <- a
        mul byte [b] ; AX <- a*b
        mov dx, ax ; DX <- a*b
        mov al, [c] ; AL <- c
        mov ah, 0 ; AX <- c
        mov cx, ax ; CX <- c
        mov ax, dx ; AX <- a*b
        mul cx ; DX:AX <- a*b*c
        push dx
        push ax
        pop eax ; EAX <- a*b*c
        sub ebx, eax ; EBX <- a/2 + b*b - a*b*c 
        mov eax, [e] ; EAX <- e
        add eax, ebx ; EAX <- a/2 + b*b - a*b*c + e
        mov ebx, dword [x+0] 
        mov ecx, dword [x+4] ; ECX:EBX <- x
        clc ; Clear Carry Flag
        add ebx, eax 
        adc ecx, 0 ; ECX:EBX <- a/2 + b*b - a*b*c + e + f + CF
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
