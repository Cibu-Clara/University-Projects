; Two byte strings S1 and S2 of the same length are given. Obtain the string D where each element contains the minimum of the corresponding elements from S1 and S2.
; Example: 
; S1: 1, 3, 6, 2, 3, 7
; S2: 6, 3, 8, 1, 2, 5
; D: 1, 3, 6, 1, 2, 5
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 1, 3, 6, 2, 3, 7 ; declare the string s1 of bytes
    l equ $-s1 ; compute the length of the string in l
    s2 db 6, 3, 8, 1, 2, 5 ; declare the string s2 of bytes
    d times l db 0 ; reserve l bytes for the destination string and initialize it

; our code starts here
segment code use32 class=code
    start:
        mov ecx, l ; we put the length l in ECX in order to make the loop
        mov esi, 0 ; our index
        jecxz end_loop ; check if ecx is 0
        myloop:
                mov al, [s1+esi] ; puts each element in AL
                mov bl, [s2+esi] ; puts each corresponding element in BL
                cmp al, bl ; sets some flags
                jae else_clause ; jumps to else_clause in case AL>=BL (above or equal)
                        ; AL < BL
                        mov [d+esi], al ; puts the minimum in the destination string
                        jmp end_if ; if this case is executed, it jumps over else_clause
                    else_clause:
                        ; AL >= BL
                        mov [d+esi], bl ; puts the minimum in the destination string
        end_if:
        inc esi ; increments the index
        loop myloop ; decrement ecx, jnz
        end_loop:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
