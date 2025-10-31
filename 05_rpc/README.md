# Atividade de Pesquisa: RPC, gRPC e `.proto`

**Disciplina:** Computação Distribuída  
**Tema:** Chamadas de Procedimento Remoto (RPC)  


## Objetivo  
Explorar o paradigma RPC e suas implementações modernas, entendendo como sistemas distribuídos podem se comunicar de forma eficiente sem depender exclusivamente de REST. Investigar o formato `.proto` e sua relação com o gRPC.

## Contexto  
Grande parte das APIs com as quais você já interagiu até agora provavelmente usava REST. Mas REST é apenas uma das formas de construir sistemas que conversam. No subsolo das arquiteturas distribuídas, existe uma tradição poderosa: **RPC (Remote Procedure Call)**.

Essa atividade é seu bilhete para explorar:

- O que é RPC e por que existe
- Como ele se compara com REST
- Por que gigantes como Google e Netflix utilizam **gRPC**
- O papel dos arquivos `.proto` como contratos de comunicação

Prepare-se para viajar além do JSON.

## Tarefas  

### 1. Pesquisa Conceitual  
Explique com suas palavras:

- O que é RPC (Remote Procedure Call)
- Diferença conceitual entre RPC e REST
- O que é o **gRPC**
- Por que o gRPC foi criado e quais problemas resolve
- O que é **Protocol Buffers (Protobuf)** e sua função
- Relação entre arquivos `.proto` e a comunicação RPC no gRPC

Inclua pelo menos uma analogia para cada conceito (se fosse um ambiente de restaurante, um jogo online, um bairro inteligente — divirta-se).

### 2. Comparações Fundamentadas  
Preencha uma tabela com comparações entre:

| Tema | RPC | REST |
|------|-----|------|
| Modelo de comunicação | | |
| Formato de dados | | |
| Performance | | |
| Acoplamento entre cliente e servidor | | |
| Facilidades/dificuldades | | |
| Casos de uso comuns | | |

E outra entre:

| Tema | Protobuf | JSON |
|------|----------|------|
| Estrutura | | |
| Desempenho | | |
| Legibilidade humana | | |
| Casos de uso | | |

### 3. Leitura de um Arquivo `.proto`  
Escolha um arquivo `.proto` na internet (dica: documentação oficial do gRPC ou GitHub) e:

- Cole o conteúdo no relatório
- Explique cada parte com comentários (o que são mensagens, campos, tipos, serviços, métodos)
- Faça um pequeno desenho/diagrama explicando o fluxo “cliente → servidor → resposta”

### 4. Estudo de Caso  
Escolha um cenário real, como:

- Google
- Netflix
- Kubernetes
- Discord
- Mercado Livre

E responda:

- Como o gRPC (ou outro RPC) é usado nesse contexto?
- Por que faz sentido ali?
- Quais vantagens ele traz?
- Existe uso combinado com REST?

### 5. Reflexão Crítica  
Responda:

- Vale aprender RPC na era REST? Justifique.
- Existem situações onde REST ainda é preferível? Quais?
- Qual o principal desafio ao adotar RPC/gRPC em uma empresa com equipe iniciante?

## Entrega  
- Individual
- Formato: PDF
- Extensão sugerida: 2 a 4 páginas  
- Cite as fontes utilizadas  
- Diagramas, exemplos e metáforas são bem-vindos (a imaginação é parte da ciência)

## Critérios de Avaliação  

| Critério | Peso |
|---------|------|
| Clareza conceitual | ⭐⭐⭐⭐ |
| Profundidade da pesquisa | ⭐⭐⭐⭐ |
| Análise crítica | ⭐⭐⭐ |
| Exemplos e analogias | ⭐⭐⭐ |
| Referências citadas | ⭐⭐ |

---

> RPC é quase como pedir um feitiço para um mago distante, você não vê o que acontece lá, só torce para a magia vir pronta e funcionar. Pesquisa bem e descobre como esse grimório digital é escrito, aluno-mago.

Boa atividade!