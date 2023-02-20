; Write a program in assembly language which computes the following arithmetic expression, considering the following domains for the variables 
; a - byte, b - word, c - double word, d - qword - signed representation
; c+b-(a-d+b)
; ex: a=80, b=20, c=-50, d=15 ; Result: -50+20-(80-15+20)=-30-85=-115
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 80
    b dw 20
    c dd -50
    d dq 15

; our code starts here
segment code use32 class=code
    start:
       mov al, [a] ; AL <- a
       cbw ; AX <- a
       cwde ; EAX <- a
       cdq ; EDX:EAX <- a
       mov ebx, eax 
       mov ecx, edx ; ECX:EBX <- a
       mov eax, dword [d+0]
       mov edx, dword [d+4] ; EDX:EAX <- d
       clc ; Clear Carry Flag
       sub ebx, eax
       sbb ecx, edx ; ECX:EBX <- a-d-CF
       mov ax, [b] ; AX <- b
       cwde ; EAX <- b
       cdq ; EDX:EAX <- b
       clc ; Clear Carry Flag
       add ebx, eax 
       adc ecx, edx ; ECX:EBX <- a-d+b+CF
       mov ax, [b] ; AX <- b
       cwde ; EAX <- b
       add eax, dword [c] ; EAX <- b+c
       cdq ; EDX:EAX <- b+c
       clc ; Clear Carry Flag
       sub eax, ebx 
       sbb edx, ecx ; EDX:EAX <- c+b-(a-d+b)-CF
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
