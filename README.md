# Aurora Lang Compiler
Aplicação desenvolvida durante a disciplina de Compiladores do curso de Engenharia da Computação no
Instituto Federal de Ciências e Tecnologia

## TODO's
- [ ] Adaptar para última release do Java
- [ ] Reimplementar compilador utilizando Kotlin
- [ ] Implementar uma IDE para o compilador usando TornadoFX

## Funcionalidades implementadas
- [x] Léxico
- [X] Sintático
- [x] Semântico
- [x] Código intermediário
- [x] Código final

Compilar no windows via .bat
```bash
run_aurora --[flags] ${CAMINHO_DO_ARQUIVO}
```

Executar jar
```bash
java -jar aurora --[flags] ${CAMINHO_DO_ARQUIVO}
```

## Para compilar um arquivo .au em qualquer pasta do windows:

* Coloque o arquivo run_aurora.bat no path do sistema
```bash
run_aurora --[flags] %cd%{NOME_DO_ARQUIVO}
```

## flags:
> Flags ativam separadamente as funcionalidades do compilador
* --tokens
* --syntactic
* --semantic
* --symbols
* --final-code
* --readable
* --all




