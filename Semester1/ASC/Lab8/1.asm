; Two numbers a and b are given. Compute the expression value: (a+b)*k, where k is a constant value defined in data segment. Display the expression   value (in base 10).

bits 32 
global start        

extern exit, scanf, printf        
import exit msvcrt.dll    
import scanf msvcrt.dll    
import printf msvcrt.dll              

segment data use32 class=data
    read db "The two numbers are: ", 10, 13, 0
    formats db "%d %d", 0
    formatp db "The value of the expression is %lld ", 10, 13, 0
    a dd 0
    b dd 0
    k dd 7
    
segment code use32 class=code
    start:
        ; printf("The two numbers are: \n")
        push dword read
        call [printf]
        add esp, 4
        
        ; scanf("%d %d", &a, &b)
        push dword b
        push dword a
        push dword formats
        call [scanf]
        add esp, 4*3
        
        mov eax, [a]
        add eax, [b]
        mul dword [k]
        
        ; printf("The value of the expression is %lld \n", edx:eax)
        push edx
        push eax
        push dword formatp
        call [printf]
        add esp, 4*2
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
