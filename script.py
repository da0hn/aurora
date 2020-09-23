import os
import sys

file_aurora = sys.argv[1]
recv_args = sys.argv[2:]
file_name = file_aurora.split(".")[0]
file_nasm = file_name + ".asm"

args = " "

for arg in recv_args:
    args += arg + " "

os.system("java -jar --enable-preview \"aurora.jar\" {} {}".format(file_aurora, args))
os.system("nasm -f elf {0}".format(file_nasm))
os.system("gcc -m32 {0}.o -o {0}".format(file_name))
os.system("{0}".format(file_name))
