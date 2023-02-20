; A string of bytes 'input' is given together with two additional strings of N bytes each, 'src' and 'dst'. Obtain a new string of bytes called 'output' from the 'input' string, by replacing all the bytes with the value src[i] with the new value dst[i], for i=1..N.
; Example:
; input: 1, 5, 6, 3, 2, 3, 1
; src: 9, 1, 4, 5, 2
; dst: 7, 4, 1, 8, 6
; output: 4, 8, 6, 3, 6, 3, 4
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    input db 1, 5, 6, 3, 2, 3, 1 ; declare the string input of bytes
    l equ $-input ; compute the length of the string in l
    src db 9, 1, 4, 5, 2 ; declare the string src of bytes
    N equ $-src ; compute the length of the string in N
    dst db 7, 4, 1, 8, 6 ; declare the string dst of bytes
    output times l db 0 ; reserve l bytes for the destination string and initialize it
    
; our code starts here
segment code use32 class=code
    start:
        mov esi, input ; puts the address of input string into <DS:ESI>
        mov edi, output ; puts the address of output string into <ES:EDI>
        cld ; parse the string from left to right(DF=0) 
        mov ecx, l ; we put the length l in ECX in order to make the loop
        mov ebx, N ; we put the length of strings src and dst in ebx
        jecxz end_loop ; check if ECX is 0
        input_loop:
            push ecx ; we push ECX in order to use CL
            lodsb ; we load the current byte from the input string in AL + increment ESI
            mov cl, al ; CL <- AL
            push esi ; we push ESI in order to use it at src 
            mov esi, src ; puts the address of src string into <DS:ESI>
            mov edx, 0 ; we index the length of dst string in EDX
            src_loop:
                lodsb ; we load the current byte from the src string in AL + increment ESI
                cmp al, cl 
                jne else_clause ; jumps if not equal
                        mov al, [dst+edx] ; we put in AL the current element of dst
                        stosb ; puts AL in output string + decrement EDI
                        jmp skip ; we skip the else clause
                    else_clause:
                        inc edx ; increments the index of dst
                
                cmp edx, ebx
                jne src_loop ; repeats the loop if we still have elements in src/dst
                mov al, cl ; AL <- CL
                stosb ; we put the element from the input string in the output string
                skip:
                pop esi 
                pop ecx
        loop input_loop
        end_loop:
   
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
