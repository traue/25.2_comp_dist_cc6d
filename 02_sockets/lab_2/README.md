# Mini chat em C com Socket

Projeto acadêmico de um pequeno chat multi-cliente em C que usa socket e selct


## Objetivos

 - O servidor aceita multiplas conexões usando select() e faz broadcast de mensagens
 - Cliente envia mensagens ao servidor via terminal por socket
 - o servidor aceita multiplas conexoes

## Estrutura

 - `chat_client.c`: cliente interativo
 - `chat_server.c`: servidor multi conexões
 - `Makefile`: builder do projeto (server, client, run-server, run-client)


## Compilação e execução

Para compilar e executar o proejto, siga as diretivas abaixo.

### Compilar

```bash
make            # compila tudo
make server     # compila o servidor
make client     # compila o client
```

### Executar

Servidor
```bash
make run-server
```

Cliente
```bash
make run-client
```