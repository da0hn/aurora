; +-------------------------------------------------+
; | Assembly gerado utilizando Aurora Compiler v1.0 |
; | Autor: Gabriel Honda                            |
; | Data : 22/09/2020 20:38:46                      |
; +-------------------------------------------------+

section .data                                                                   ; Inicio da seção de constantes
    fmtin: db "%d", 0x0                                                         ; Formatador de input
    fmtout: db "%d", 0xA, 0x0                                                   ; Formatador de output

    str_0: db "Insira o número de notas:", 0xA, 0x0                             ; Declaração da string
    str_1: db "Insira a nota:", 0xA, 0x0                                        ; Declaração da string
    str_2: db "----------------------------", 0xA, 0x0                          ; Declaração da string
    str_3: db "Sua média é", 0xA, 0x0                                           ; Declaração da string
    str_4: db "----------------------------", 0xA, 0x0                          ; Declaração da string
    str_5: db "Você foi aprovado", 0xA, 0x0                                     ; Declaração da string
    str_6: db "Você foi reprovado", 0xA, 0x0                                    ; Declaração da string

section .bss                                                                    ; Inicio da seção de variáveis
    numeroNotas_0: resd 1                                                       ; Declaração da variável numeroNotas_0
    acumulador_0: resd 1                                                        ; Declaração da variável acumulador_0
    contador_0: resd 1                                                          ; Declaração da variável contador_0
    aux_0_0: resd 1                                                             ; Declaração da variável aux_0_0
    media_0: resd 1                                                             ; Declaração da variável media_0

section .text                                                                   ; Inicio da seção do código
    global main                                                                 ; Declaração do main
    extern printf                                                               ; Importação do printf
    extern scanf                                                                ; Importação do scanf

main:
    ; Preparação da pilha
    push ebp
    mov ebp, esp

    ; Escrever a string str_0 na saída
    push dword str_0
    call printf
    add esp, 4

    ; Ler a entrada do usuário para a variável numeroNotas_0
    push numeroNotas_0
    push dword fmtin
    call scanf
    add esp, 8

_L1:
    mov eax, [contador_0]
    cmp eax, [numeroNotas_0]
    jge _L2

    ; Escrever a string str_1 na saída
    push dword str_1
    call printf
    add esp, 4

    ; Ler a entrada do usuário para a variável aux_0_0
    push aux_0_0
    push dword fmtin
    call scanf
    add esp, 8

    mov eax, [acumulador_0]
    mov ebx, [aux_0_0]
    add eax, ebx
    mov [acumulador_0], eax
    mov eax, [contador_0]
    mov ebx, 1
    add eax, ebx
    mov [contador_0], eax
    jmp _L1
_L2:
    mov eax, [acumulador_0]
    mov ebx, [numeroNotas_0]
    xor edx, edx
    div ebx
    mov [media_0], eax
    ; Escrever a string str_2 na saída
    push dword str_2
    call printf
    add esp, 4

    ; Escrever a string str_3 na saída
    push dword str_3
    call printf
    add esp, 4

    ; Escrever a variável media_0 na saída
    push dword [media_0]
    push dword fmtout
    call printf
    add esp, 8

    ; Escrever a string str_4 na saída
    push dword str_4
    call printf
    add esp, 4

    mov eax, [media_0]
    cmp eax, 5
    jle _L3

    ; Escrever a string str_5 na saída
    push dword str_5
    call printf
    add esp, 4

    jmp _L4
_L3:
    ; Escrever a string str_6 na saída
    push dword str_6
    call printf
    add esp, 4

_L4:
    ; Término do programa
    mov esp, ebp
    pop ebp
    ret
