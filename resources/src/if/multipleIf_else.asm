section .data
    fmtin: db "%d", 0x0
    fmtout: db "%d", 0xA, 0x0

    str_0: db "Entre com um numero: ", 0xA, 0x0
    str_1: db "O Valor eh maior que 2: ", 0xA, 0x0
    str_2: db "O valor eh menor 2", 0xA, 0x0
    str_3: db "O valor eh maior que 1", 0xA, 0x0
    str_4: db "O valor eh menor ou igual a 1", 0xA, 0x0
    str_5: db "Fim do programa", 0xA, 0x0

section .bss
    num_0: resd 1
    fatorial_0: resd 1

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
    cmp eax, 2
    jle _L1

    push dword str_1
    call printf
    add esp, 4

    push dword [num_0]
    push dword fmtout
    call printf
    add esp, 8

    jmp _L2
_L1:
    push dword str_2
    call printf
    add esp, 4

    mov eax, [num_0]
    cmp eax, 1
    jle _L3

    push dword str_3
    call printf
    add esp, 4

    jmp _L4
_L3:
    push dword str_4
    call printf
    add esp, 4

_L4:
_L2:
    push dword str_5
    call printf
    add esp, 4

    mov esp, ebp
    pop ebp
    ret
