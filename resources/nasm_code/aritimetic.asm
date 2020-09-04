section .data

    msg1: db 'Enter a digit: ', 10, 0
    msg2: db 'Please enter a second digit: ', 10, 0
    msg3: db 'The sum is: ', 10, 0

    fmtout: db '%d', 10, 0
    fmtin:  db '%d', 0

section .bss
    num1: resd 1
    num2: resd 1
    res:  resd 1

section .text
    global main
    extern printf
    extern scanf

main:
    push ebp
    mov ebp, esp

    push dword msg1
    call printf
    add esp, 4

    push num1
    push fmtin
    call scanf
    add esp, 8

    push dword msg2
    call printf
    add esp, 4

    push num2
    push fmtin
    call scanf
    add esp, 8

    mov eax, [num1]
    mov ebx, [num2]
    add eax, ebx
    mov [res], eax

    push dword msg3
    call printf
    add esp, 4

    push dword [res]
    push dword fmtout
    call printf
    add esp, 8

    mov esp, ebp
    pop ebp
    ret