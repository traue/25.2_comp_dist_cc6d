# Projeto Prático: Sistema de Votação Distribuído em Tempo Real

**Disciplina:** Computação Distribuída  
**Tecnologias sugeridas:** C (sockets TCP + threads), opcional MPI
**Tema:** Comunicação em rede, concorrência, sincronização, consistência

---

## Objetivo

Desenvolver um sistema distribuído de votação em tempo real com múltiplos clientes conectados a um servidor. Cada cliente vota uma única vez e pode solicitar o placar parcial. O servidor deve lidar com concorrência, falhas simples e encerramento da eleição via comando administrativo.

---

## Arquitetura Geral

- **Servidor**
  - Gerencia conexões via socket TCP
  - Recebe e contabiliza votos
  - Garante voto único por `VOTER_ID`
  - Fornece placar parcial e final
  - Encerra votação sob comando admin

- **Clientes**
  - Conectam ao servidor e informam `VOTER_ID`
  - Listam opções
  - Registram voto
  - Consultam placar
  - Encerram sessão

Opcional:
- **Workers MPI**
  - Distribuem a contagem e realizam agregação final

---

## Requisitos Funcionais

### Servidor deve:
- Aceitar **mínimo 20 clientes simultâneos**
- Manter lista configurável de opções (mínimo 3)
- Garantir **voto único** por `VOTER_ID`
- Responder com placar parcial
- Encerrar eleição por comando `ADMIN CLOSE`
- Registrar log (`eleicao.log`)
- Gerar `resultado_final.txt`

### Cliente deve:
- Informar `VOTER_ID`
- Ver opções
- Enviar 1 voto
- Consultar placar
- Encerrar conexão

---

## Protocolo de Comunicação

### Cliente → Servidor
```
HELLO <VOTER_ID>
LIST
VOTE <OPCAO>
SCORE
BYE
```

### Servidor → Cliente

```
WELCOME <VOTER_ID>
OPTIONS <k> <op1> ... <opk>
OK VOTED <OPCAO>
ERR DUPLICATE
ERR INVALID_OPTION
SCORE <k> <op1>:<count1> ...
CLOSED FINAL <k> <op1>:<count1> ...
ERR CLOSED
BYE
```

### Comando administrativo

```
ADMIN CLOSE
```

---

## Requisitos Técnicos

- Linguagem C
- Sockets TCP (modo texto, linha a linha)
- Threads (`pthread`)
- Exclusão mútua com `mutex`
- Log de eventos em arquivo
- Tolerância a falhas simples (cliente cair após votar → voto válido)

Opcional:
- MPI para contagem distribuída

---

## Arquivos esperados

| Arquivo | Descrição |
|--------|----------|
| `server.c` | Servidor da eleição |
| `client.c` | Cliente votante |
| `eleicao.log` | Log da execução |
| `resultado_final.txt` | Placar final |
| `README.md` | Instruções de compilação/execução |
| `relatorio.pdf` ou `relatorio.md` | Relatório técnico |

<br>

⚠️ **Atenção:** Enviar um ZIP com o código-fonte. O PDF deve ir FORA do arquivo zip (no Moodle)


---

## Casos de Teste Mínimos

| Cenário | Esperado |
|--------|----------|
| Voto único | 2º voto → `ERR DUPLICATE` |
| Opção inválida | `ERR INVALID_OPTION` |
| 20 clientes simultâneos | Funciona sem travar |
| Cliente cai antes de votar | Voto não contado |
| Admin encerra votação | Após `ADMIN CLOSE` → rejeitar `VOTE` |
| Consulta após encerramento | Retornar placar final |

---

## Bônus (opcional)

- MPI para contagem paralela
- Cache de placar
- Exportar CSV/JSON
- Interface ncurses
- Snapshot do estado
- Autenticação por lista de eleitores

---

## Avaliação

| Critério | Peso |
|---------|------|
| Corretude funcional | 40% |
| Concorrência e robustez | 25% |
| Qualidade do código e logs | 15% |
| Relatório + testes | 10% |
| Bônus | até +10% |

---

## Dicas

- Comece pelo servidor aceitando múltiplos clientes
- Implemente protocolo linha a linha
- Proteja incrementos e mapa de votos com mutex
- Teste com múltiplos terminais

---

Boa implementação!