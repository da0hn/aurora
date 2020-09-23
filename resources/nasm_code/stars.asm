section .text
    global _start       ;must be declared for linker (gcc)

section .data
    msg db 'displaying 9 stars', 0xa    ; a message
    len equ $ - msg                     ; length of message
    s2 times 9 db '*'

_start:                 ;tell linker entry point
;write a message on output
    mov edx, len        ;message length
    mov ecx, msg        ;message to write
    mov ebx, 1          ;file descriptor (stdout)
    mov eax, 4          ;system call number (sys_write)
    int 0x80            ;call kernel
;write a message on output
    mov edx, 9          ;message length
    mov ecx, s2         ;message to write
    mov ebx, 1          ;file descriptor (stdout)
    mov eax, 4          ;system call number (sys_write)
    int 0x80            ;call kernel
;system call exit
    mov eax, 1          ;system call number (sys_exit)
    int 0x80            ;call kernel