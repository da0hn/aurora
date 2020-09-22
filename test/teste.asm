section .data
    fmtin: db "%d", 0x0
    fmtout: db "%d", 0xA, 0x0


section .bss
    foo_0: resd 1
    bar_0: resd 1

section .text
    global main
    extern printf
    extern scanf

main:
    push ebp
    mov ebp, esp

    push foo
    push dword fmtin
    call scanf
    add esp, 8

    mov eax, 3
    mov ebx, [foo_0]
    div ebx
    mov [bar_0], eax
    mov esp, ebp
    pop ebp
    ret
