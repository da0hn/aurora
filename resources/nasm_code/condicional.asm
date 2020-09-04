section .data

    str1: db "Insira um numero: ", 10, 0
    str2: db "Número maior que 100", 10, 0
    str3: db "Número menor que 100", 10, 0
    str4: db "Número igual a 100", 10, 0

    fmtin: db "%d", 0
    fmtout: db "%d", 10, 0

section .bss
    num: resd 1

section .text
    global main
    extern printf
    extern scanf
main:
    push ebp
    mov ebp, esp

    ;print
_loop:
    push dword str1
    call printf
    add esp, 4

    ;scanf num
    push num
    push fmtin
    call scanf
    add esp, 8


    mov eax, [num]
    cmp eax, 100
    jl _menor
    je _igual

    push dword str2
    call printf
    add esp, 4
    jmp _fim
_menor:
    push dword str3
    call printf
    add esp, 4
    jmp _fim
_igual:
    push dword str4
    call printf
    add esp, 4
_fim:
;    mov eax, [num]
    cmp eax, 5
    jne _loop

    mov esp, ebp
    pop ebp
    ret