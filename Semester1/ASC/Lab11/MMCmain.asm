; Read a string of integers s1 (represented on doublewords) in base 10. Determine and display the string s2 composed by the digits in the hundreds place of each integer in the string s1.
; Example:    
;         The string s1: 5892, 456, 33, 7, 245
;         The string s2: 8, 4, 0, 0, 2
bits 32 

global start        

extern printf, exit              
import  printf msvcrt.dll
import exit msvcrt.dll    

extern MMCdigit
; MMCdigit(int): int
;                - 1 parameter N:integer
;                - calculates the digit in the hundreds place of N
;                - return value in EAX: integer 
;                - uses/modifies eax, ebx, edx 

segment data use32 class=data
    s1 dd 5892, 456, 33, 7, 245
    l equ ($-s1)/4
    arrayprint db "[s%d]=%d", 10, 13, 0

segment code use32 class=code
    
    start:
        mov esi, s1
        mov ebx, 0
        mov ecx, l
        print_loop:
            push ebx
            mov eax, 0
            lodsd
            push dword eax
            call MMCdigit
            pop ebx
            pushad
            
            push eax
            push ebx
            push dword arrayprint
            call [printf]
            add esp, 4*3
            
            popad
            inc ebx
        loop print_loop
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
