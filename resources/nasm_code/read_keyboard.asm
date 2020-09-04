section .data                               ;data segment
    userMsg db 'Please enter a number: '    ;ask the user to enter a number
    lenUserMsg equ $-userMsg                ;the length of the message
    dispMsg db 'You have entered: '         ;display message
    lenDispMsg equ $-dispMsg                ;the length of display message

section .bss                                ;uninitialized data
    num resb 5

section .text                               ;code segment
    global _start

_start:                                     ;user prompt
    mov eax, 4                              ; sys_write
    mov ebx, 1                              ; int
    mov ecx, userMsg                        ;print 'please enter a number: '
    mov edx, lenUserMsg                     ;length
    int 80h                                 ;call the relevant interrupt

    ;read and store the user input
    mov eax, 3                              ;sys_open
    mov ebx, 2                              ;struct pt_regs
    mov ecx, num
    mov edx, 5                              ;5 bytes (numeric, 1 for sign) of that information
    int 80h

    ;output the message 'the entered number is: '
    mov eax, 4
    mov ebx, 1
    mov ecx, dispMsg
    mov edx, lenDispMsg
    int 80h

    ;output the number entered
    mov eax, 4                              ;sys_open
    mov ebx, 1                              ;int
    mov ecx, num
    mov edx, 5                              ;
    int 80h                                 ;call the relevant interrupt

    ;exit code
    mov eax, 1                              ;sys_exit
    mov ebx, 0
    int 80h