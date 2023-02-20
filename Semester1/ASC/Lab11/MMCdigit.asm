bits 32 

global MMCdigit        

; MMCdigit(int): int
;                - 1 parameter N:integer
;                - calculates the digit in the hundreds place of N
;                - return value in EAX: integer 
;                - uses/modifies eax, ebx, edx 

MMCdigit:   
    mov eax, [esp+4]
    cdq
    mov ebx, 100
    idiv ebx ; quotient in eax
    cmp eax, 0
    jz else_clause
            cdq 
            mov ebx, 10
            idiv ebx ; remainder in edx
            mov eax, edx
            jmp end_if
       else_clause:
            mov edx, 0
            mov eax, edx
       end_if:
    ret 4    ; call exit to terminate the program
