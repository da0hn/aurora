section .data
    fmtin: db "%d", 0x0
    fmtout: db "%d", 0xA, 0x0

    str_0: db "Entre com um numero inteiro para ser testado: ", 0xA, 0x0
    str_1: db "O valor inserido eh maior do que 7", 0xA, 0x0
    str_2: db "O valor inserido eh menor que 7", 0xA, 0x0

section .bss
    num_0: resd 1

section .text
    global main
    extern printf
    extern scanf

main:
    push ebp
    mov ebp, esp

    push dword str_0
    call printf
    add esp, 4

    push num_0
    push dword fmtin
    call scanf
    add esp, 8

    mov eax, [num_0]
    cmp eax, 7
    jle _L1

    push dword str_1
    call printf
    add esp, 4

    jmp _L2
_L1:
    mov eax, [num_0]
    cmp eax, 7
    jge _L3

    push dword str_2
    call printf
    add esp, 4

_L3:
_L2:
    mov esp, ebp
    pop ebp
    ret
