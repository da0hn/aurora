; constantes
section .data
    SYS_EXIT equ 1
    SYS_WRITE equ 4
    STDIN equ 0
    STDOUT equ 1
    ; 0xA -> '\n', 0x0 -> '\0'
    msg1 db 'Hello, programmers: ', 0xA, 0x0
    len1 equ $-msg1

    msg2 db 'Welcome to the world of,', 0xA, 0x0
    len2 equ $ - msg2

    msg3 db 'Linux assembly programming! ', 0xA, 0x0
    len3 equ $- msg3
    ; usado para printar variaveis
    fmt db '%d', 0xA, 0x0
; variaveis
section .bss


; código
section .text
    global main
    extern printf

main:
    ; prepara pilha (obrigatório)
    push ebp
    mov ebp, esp

    push dword msg1
    call printf
    add esp, 4

    push dword msg2
    call printf
    add esp, 4

    push dword msg3
    call printf
    add esp, 4

    ; prepara a pilha para terminar o programa (obrigatório)
    mov esp, ebp
    pop ebp
    ret


