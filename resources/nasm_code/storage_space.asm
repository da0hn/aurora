section .data
    choice: db 'y', 0                           ;1 byte
    fmt: db '%d', 10, 0

section .text
    global main                           ;must be declared for linker (gcc)
    extern printf
main:
    ;prepara a pilha
    push ebp
    mov ebp, esp

    push dword choice
    call printf
    add esp, 4

    ; ?????
    mov esp, ebp
    pop ebp
    ret