section .data
    count dw 0
    value db 15
section .bss


section .text
    global main
    extern printf
main:
    inc [count]
    dec [value]

    mov ebx, count
    inc word [ebx]              ; 2 byte

    mov esi, value
    dec byte [esi]              ; 1 byte

