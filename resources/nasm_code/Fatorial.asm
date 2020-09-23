section .bss
numero_0: resd 1
fatorial_0: resd 1

section .data
fmtin: db "%d", 0
fmtout: db "%d", 10, 0
string_1: db "Escreva um numero: ", 0
string_2: db "Fatorial: ", 0

section .text
global main
extern printf
extern scanf

main:
push ebp
mov ebp, esp
push dword string_1
call printf
add esp, 4
push numero_0
push fmtin
call scanf
add esp, 8
mov eax, 1
mov [fatorial_0], eax
_L1:
mov eax, [numero_0]
cmp eax, 1
jle _L2
mov eax, [fatorial_0]
mov ebx, [numero_0]
mul ebx
mov [fatorial_0], eax
mov eax, [numero_0]
mov ebx, 1
sub eax, ebx
mov [numero_0], eax
jmp _L1
_L2:
push dword string_2
call printf
add esp, 4
push dword [fatorial_0]
push dword fmtout
call printf
add esp, 8
mov esp, ebp
pop ebp
ret