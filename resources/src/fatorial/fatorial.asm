section .data
    fmtin: db "%d", 0x0
    fmtout: db "%d", 0xA, 0x0

    str_0: db "Escreva um numero: ", 0xA, 0x0
    str_1: db "Fatorial: ", 0xA, 0x0

section .bss
    numero_0: resd 1
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

    push numero_0
    push dword fmtin
    call scanf
    add esp, 8

    mov eax, 1
    mov [fatorial_0], eax
_L1:
    mov eax, [numero_0]
    cmp eax, 1
    jle _L2

    mov eax, [fatorial_0]
    mov ebx, [numero_0]
    mul ebx
    mov [fatorial_0], eax
    mov eax, [numero_0]
    mov ebx, 1
    sub eax, ebx
    mov [numero_0], eax
    jmp _L1
_L2:
    push dword str_1
    call printf
    add esp, 4

    push dword [fatorial_0]
    push dword fmtout
    call printf
    add esp, 8

    mov esp, ebp
    pop ebp
    ret
