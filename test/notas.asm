section .data
    fmtin: db "%d", 0x0
    fmtout: db "%d", 0xA, 0x0

    str_0: db "Insira o número de notas: ", 0xA, 0x0
    str_1: db "Insira a nota:", 0xA, 0x0
    str_2: db "----------------------------", 0xA, 0x0
    str_3: db "Sua média é", 0xA, 0x0
    str_4: db "----------------------------", 0xA, 0x0
    str_5: db "Você foi aprovado", 0xA, 0x0
    str_6: db "Você foi reprovado", 0xA, 0x0

section .bss
    numeroNotas_0: resd 1
    acumulador_0: resd 1
    contador_0: resd 1
    aux_0_8: resd 1
    media_0: resd 1

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

    push numeroNotas_0
    push dword fmtin
    call scanf
    add esp, 8

_L1:
    mov eax, [contador_0]
    cmp eax, 1
    jle _L2

    jmp _L1
_L2:
_L3:
    mov eax, 1
    cmp eax, [contador_0]
    jge _L4

    jmp _L3
_L4:
_L5:
    mov eax, [acumulador_0]
    cmp eax, [contador_0]
    jge _L6

    jmp _L5
_L6:
_L7:
    mov eax, 3
    cmp eax, 1
    jge _L8

    jmp _L7
_L8:
    mov eax, [contador_0]
    cmp eax, 1
    jle _L9

_L9:
    mov eax, 1
    cmp eax, [contador_0]
    jge _L10

_L10:
    mov eax, [acumulador_0]
    cmp eax, [contador_0]
    jge _L11

_L11:
    mov eax, 2
    cmp eax, 1
    jle _L12

_L12:
_L13:
    mov eax, [contador_0]
    cmp eax, [numeroNotas_0]
    jge _L14

    push dword str_1
    call printf
    add esp, 4

    push aux_0_8
    push dword fmtin
    call scanf
    add esp, 8

    mov eax, [acumulador_0]
    mov ebx, [aux_0_8]
    add eax, ebx
    mov [acumulador_0], eax
    mov eax, [contador_0]
    mov ebx, 1
    add eax, ebx
    mov [contador_0], eax
    jmp _L13
_L14:
    mov eax, [acumulador_0]
    mov ebx, [numeroNotas_0]
    xor edx, edx
    div ebx
    mov [media_0], eax
    push dword str_2
    call printf
    add esp, 4

    push dword str_3
    call printf
    add esp, 4

    push dword [media_0]
    push dword fmtout
    call printf
    add esp, 8

    push dword str_4
    call printf
    add esp, 4

    mov eax, [media_0]
    cmp eax, 5
    jle _L15

    push dword str_5
    call printf
    add esp, 4

    jmp _L16
_L15:
    push dword str_6
    call printf
    add esp, 4

_L16:
    mov esp, ebp
    pop ebp
    ret
