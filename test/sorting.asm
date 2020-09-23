section .data
    fmtin: db "%d", 0x0
    fmtout: db "%d", 0xA, 0x0

    str_0: db "Escreva o primeiro número:", 0xA, 0x0
    str_1: db "Escreva o segundo número:", 0xA, 0x0
    str_2: db "Escreva o terceiro número:", 0xA, 0x0
    str_3: db "--------------------------------", 0xA, 0x0
    str_4: db "Inteiros do menor para o maior", 0xA, 0x0
    str_5: db "--------------------------------", 0xA, 0x0
    str_6: db "Valor: ", 0xA, 0x0
    str_7: db "FIM DO PROGRAMA", 0xA, 0x0

section .bss
    a_0: resd 1
    b_0: resd 1
    c_0: resd 1
    aux_0: resd 1
    limite_0: resd 1

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

    push a_0
    push dword fmtin
    call scanf
    add esp, 8

    push dword str_1
    call printf
    add esp, 4

    push b_0
    push dword fmtin
    call scanf
    add esp, 8

    push dword str_2
    call printf
    add esp, 4

    push c_0
    push dword fmtin
    call scanf
    add esp, 8

    mov eax, [a_0]
    cmp eax, [b_0]
    jle _L1

    mov eax, [a_0]
    mov [aux_0], eax
    mov eax, [b_0]
    mov [a_0], eax
    mov eax, [aux_0]
    mov [b_0], eax
_L1:
    mov eax, [a_0]
    cmp eax, [c_0]
    jle _L2

    mov eax, [a_0]
    mov [aux_0], eax
    mov eax, [c_0]
    mov [a_0], eax
    mov eax, [aux_0]
    mov [c_0], eax
_L2:
    mov eax, [b_0]
    cmp eax, [c_0]
    jle _L3

    mov eax, [b_0]
    mov [aux_0], eax
    mov eax, [c_0]
    mov [b_0], eax
    mov eax, [aux_0]
    mov [c_0], eax
_L3:
    push dword str_3
    call printf
    add esp, 4

    push dword str_4
    call printf
    add esp, 4

    push dword [a_0]
    push dword fmtout
    call printf
    add esp, 8

    push dword [b_0]
    push dword fmtout
    call printf
    add esp, 8

    push dword [c_0]
    push dword fmtout
    call printf
    add esp, 8

    push dword str_5
    call printf
    add esp, 4

    mov eax, [a_0]
    mov [aux_0], eax
    mov eax, [c_0]
    mov ebx, 1
    add eax, ebx
    mov [limite_0], eax
_L4:
    mov eax, [aux_0]
    cmp eax, [limite_0]
    jge _L5

    push dword str_6
    call printf
    add esp, 4

    push dword [aux_0]
    push dword fmtout
    call printf
    add esp, 8

    mov eax, [aux_0]
    mov ebx, 1
    add eax, ebx
    mov [aux_0], eax
    jmp _L4
_L5:
    push dword str_7
    call printf
    add esp, 4

    mov esp, ebp
    pop ebp
    ret
