import os
import sys

file_nasm = sys.argv[1]
file_name = file_nasm.split(".")[0]

os.system("nasm -f elf {0}".format(file_nasm))
os.system("gcc -m32 {0}.o -o {0}".format(file_name))
# os.system("ld -m elf_i386 -s -o {0} {0}.o".format(file_name))
os.system("./{0}".format(file_name))