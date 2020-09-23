section .data
    fmtin: db "%d", 0x0
    fmtout: db "%d", 0xA, 0x0

    str_0: db "Realizando conta ((2 * 2) + (6/2)) + 12 e salvando no fatorial...", 0xA, 0x0

section .bss
    num_0: resd 1
    fatorial_0: resd 1
    fatorial_0_0: resd 1
    fatorial_0_0_0: resd 1
    teste_0_0_1: resd 1
    teste_0_0_1_0: resd 1
    teste_0_0_1_1: resd 1
    dale_0_0_1: resd 1
    fatorial_0_0_2: resd 1
    num_0_1: resd 1

section .text
    global main
    extern printf
    extern scanf

main:
    push ebp
    mov ebp, esp

    mov eax, 3
    mov [num_0], eax
    mov eax, 7
    mov [fatorial_0], eax
    push fatorial_0
    push dword fmtin
    call scanf
    add esp, 8

_L1:
    mov eax, [num_0]
    cmp eax, 5
    jle _L2

    push fatorial_0_0
    push dword fmtin
    call scanf
    add esp, 8

    push dword str_0
    call printf
    add esp, 4

    mov eax, 2
    mov ebx, 2
    mul ebx
    mov ecx, eax
    mov eax, 6
    mov ebx, 2
    div ebx
    mov edx, eax
    mov eax, ecx
    mov ebx, edx
    add eax, ebx
    mov ecx, eax
    mov eax, ecx
    mov ebx, 12
    add eax, ebx
    mov [fatorial_0_0], eax
    mov eax, [num_0]
    cmp eax, 7
    jge _L3

    push fatorial_0_0_0
    push dword fmtin
    call scanf
    add esp, 8

    mov eax, 100
    mov ebx, [fatorial_0_0_0]
    add eax, ebx
    mov [fatorial_0_0_0], eax
    jmp _L4
_L3:
    mov eax, 9
    mov [teste_0_0_1], eax
    mov eax, [teste_0_0_1]
    cmp eax, 2
    jle _L5

    mov eax, 0
    mov [teste_0_0_1_0], eax
_L5:
    mov eax, [fatorial_0_0_0]
    cmp eax, 3
    jle _L6

    mov eax, 13
    mov [teste_0_0_1_1], eax
    push fatorial_0_0_0
    push dword fmtin
    call scanf
    add esp, 8

_L6:
    mov eax, 5
    mov ebx, [teste_0_0_1_1]
    div ebx
    mov [dale_0_0_1], eax
    push dale_0_0_1
    push dword fmtin
    call scanf
    add esp, 8

_L4:
    mov eax, [num_0]
    cmp eax, 4
    jle _L7

    mov eax, 5
    mov ebx, 1
    div ebx
    mov [fatorial_0_0_2], eax
    push fatorial_0_0_2
    push dword fmtin
    call scanf
    add esp, 8

_L7:
    jmp _L1
_L2:
    mov eax, [num_0]
    cmp eax, 5
    jle _L8

    push num_0_1
    push dword fmtin
    call scanf
    add esp, 8

_L8:
    push num_0_1
    push dword fmtin
    call scanf
    add esp, 8

    mov esp, ebp
    pop ebp
    ret
