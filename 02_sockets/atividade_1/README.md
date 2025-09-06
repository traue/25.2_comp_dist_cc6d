# Atividade: Calculadora Cliente–Servidor com Sockets em C

## Objetivo
Implementar uma aplicação **cliente–servidor** em C, usando **sockets TCP (IPv4)**, na qual o cliente envia **operações matemáticas** e o servidor **executa** e **retorna o resultado**. A atividade reforça conceitos de redes, protocolos baseados em texto, *parsing* robusto, tratamento de erros, organização do código e automação de build com **Makefile**.

---

## Descrição Geral
Você deverá implementar:
- **Servidor (`server`)**: aceita conexões TCP; recebe mensagens de requisição, **separa tokens** e executa **as quatro operações básicas** (+, −, ×, ÷); devolve o resultado ao cliente.
- **Cliente (`client`)**: conecta ao servidor, envia a operação digitada pelo usuário (linha por linha) e imprime a resposta.

O servidor deve processar **uma requisição por linha** e responder **uma linha** por requisição. A conexão permanece aberta até o cliente enviar `QUIT` (ou ocorrer erro).

> **Porta padrão**: `5050`. Permita alterá-la via linha de comando (ex.: `./server 6000`).

---

## Especificação do Protocolo (texto simples)
Formato **obrigatório (prefixo)** para requisições:
```
OP A B\n
```
Onde:
- `OP ∈ {ADD, SUB, MUL, DIV}`
- `A, B` são números reais no formato decimal com ponto (ex.: `2`, `-3.5`, `10.0`).

Formato **da resposta** do servidor:
```
OK R\n
```
ou, em caso de erro:
```
ERR <COD> <mensagem>\n
```
- Códigos sugeridos: `EINV` (entrada inválida), `EZDV` (divisão por zero), `ESRV` (erro interno).

### Exemplos
Requisição → Resposta
```
ADD 10 2\n      ->  OK 12\n
SUB 7  9\n      ->  OK -2\n
MUL -3 3.5\n    ->  OK -10.5\n
DIV 5  0\n      ->  ERR EZDV divisao_por_zero\n
```

> **Bônus opcional**: aceitar também a forma infixa **(não obrigatória)**: `A <op> B\n` com `<op> ∈ {+, -, *, /}`. Ex.: `10 + 2\n`.

---

## Requisitos Mínimos
1. **Servidor TCP (IPv4)** funcional na porta indicada; pode ser **single-process/single-thread** (um cliente por vez).
2. **Cliente** de terminal que lê linhas do **stdin**, envia ao servidor e imprime a resposta.
3. **Parsing** robusto (validar quantidade de tokens, tipos numéricos e operação).
4. **Tratamento de erros** (entrada inválida e divisão por zero).
5. **Formatação** do resultado com **ponto decimal** (não depender do *locale*). Sugestão: `printf("%.6f\n", valor)` para `R`.
6. **Makefile** com *targets* `all`, `server`, `client`, `clean`.
7. **README.md** explicando execução, exemplos e decisões de projeto.

### Requisitos Recomendados
- Logs simples no servidor (conexões, requisições, erros).
- Encerramento limpo ao receber `SIGINT` (Ctrl+C).
- Parametrização de endereço/porta no cliente (`./client 127.0.0.1 5050`).

### Bônus (até +10%)
- **Concorrência**: atender múltiplos clientes (via `fork`, *threads* ou `select/poll`).
- **Testes automatizados** (scripts que disparam casos de teste e comparam saídas).
- **Protocolo estendido** (ex.: aceitar forma infixa, mensagens `HELP`, `VERSION`).

---

## Estrutura Sugerida do Projeto
```
/src
  client.c
  server.c
/include
  proto.h
/Makefile
/README.md
/tests (opcional)
```
- Separe funções de **parsing**, **cálculo** e **I/O de socket**.
- Mantenha cabeçalhos em `/include` e implemente em `/src`.

---

## Exemplos de Uso
Servidor (porta padrão 5050):
```bash
./server
```
Cliente:
```bash
./client 127.0.0.1 5050
ADD 10 2
OK 12
DIV 5 0
ERR EZDV divisao_por_zero
QUIT
```

---

## Entrega
Escolha **uma** das opções:
1. **PDF** único contendo **todo o código-fonte**, **Makefile** e **README** (com trechos de código bem formatados), **OU**
2. **Link de um repositório** com o projeto completo (**README**, fontes e **Makefile**).

Inclua no README:
- Como compilar e executar (exemplos de comandos).
- Formato do protocolo e exemplos.
- Decisões de projeto e limitações conhecidas.
- Como rodar testes (se houver).

---

## Critérios de Avaliação
- **Funcionalidade e conformidade ao protocolo** – 40%
- **Robustez e tratamento de erros** – 20%
- **Organização do código e Makefile** – 15%
- **Documentação (README e clareza)** – 15%
- **Testes e/ou Bônus** – 10%