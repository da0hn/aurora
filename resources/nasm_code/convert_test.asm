section .data
    fmtout: db "%d", 0xA, 0x0
    fmtin: db "%d", 0x0

    str1: db "variavel menor que 1", 10, 0
    str2: db "variavel menor que 3", 10, 0

section .bss
    constante_0: resd 1
    variavel_0: resd 1

section .text
    global main
    extern printf
    extern scanf
main:
    push ebp
    mov ebp, esp

    mov eax, 3
    mov [constante_0], eax

    mov eax, 3
    mov ebx, [constante_0]
    mul ebx

    mov ebx, 2
    add eax, ebx
    mov [variavel_0], eax

    mov eax, [variavel_0]
    cmp eax, 1
    jge _L1

    push dword str1
    call printf
    add esp, 4

    jmp _L2

_L1:
    mov eax, [variavel_0]
    cmp eax, 3
    jle _L3

    push dword str2
    call printf
    add esp, 4

_L3:
_L2:
    mov esp, ebp
    pop ebp
    ret


