; A file name and a decimal number (on 16 bits) are given (the decimal number is in the unsigned interpretation). Create a file with the given name and write each digit composing the number on a different line to file.
bits 32 
global start        

extern exit, scanf, printf, fopen, fclose, fprintf        
import exit msvcrt.dll    
import scanf msvcrt.dll    
import printf msvcrt.dll              

import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll


segment data use32 class=data
    read1 db "The file name is ", 0
    read2 db "The number is ", 0
    
    file_name dd "%s", 0
    fname resb 20
    
    n dd 0
    number dd "%u", 0
    digit dd 0
    
    fd dd -1
    accmode db "w", 0
    formatp dd "%u  ", 10, 13, 0
    
segment code use32 class=code
    start:
        ; printf("The file name is ")
        push dword read1
        call [printf]
        add esp, 4
        
        ; scanf("%s, &fname)
        push dword fname
        push dword file_name
        call [scanf]
        add esp, 4*2
        
        ; fopen(fname, "w")
        push dword accmode
        push dword fname
        call [fopen]
        add esp, 4*2
        
        ; fd = EAX
        cmp eax, 0
        je endit
        mov [fd], eax
        
        ; printf("The number is ")
        push dword read2
        call [printf]
        add esp, 4
        
        ; scanf("%u", &n)
        push dword n
        push dword number
        call [scanf]
        add esp, 4*2
        
        digits:
            mov edx,0
            mov ax,[n]
            mov cx,10
            div cx
            mov [n],ax
            mov [digit],dx
            ; fprintf(fd, "%u", digit)
            push dword [digit]
            push formatp
            push dword [fd]
            call [fprintf]
            add esp,4*3
            mov ax, [n]
            cmp ax,0
            je endit
        
        jmp digits
        endit:
        
        ; fclose(fd)
        push dword [fd]
        call [fclose]
        add esp, 4
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
