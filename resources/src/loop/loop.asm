section .data
    fmtin: db "%d", 0x0
    fmtout: db "%d", 0xA, 0x0

    str_0: db "Entre com um valor: ", 0xA, 0x0
    str_1: db "Olar", 0xA, 0x0
    str_2: db "Fim do programa", 0xA, 0x0

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

_L1:
    mov eax, [num_0]
    cmp eax, 0
    jge _L2

    push dword str_1
    call printf
    add esp, 4

    push dword [num_0]
    push dword fmtout
    call printf
    add esp, 8

    mov eax, [num_0]
    mov ebx, 1
    sub eax, ebx
    mov [num_0], eax
    jmp _L1
_L2:
    push dword str_2
    call printf
    add esp, 4

    mov esp, ebp
    pop ebp
    ret
