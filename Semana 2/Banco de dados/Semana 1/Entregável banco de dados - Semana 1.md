### 1. Entidades

- **ALUNO** 

- **PLANO\_ASSINATURA** 

- **INSTRUTORES** 

- **CARTÃO\_DE\_ACESSO**


### 2. Atributos e chaves primárias

**ALUNO**

- `id\_aluno (PK)` 

- nome 

- CPF 

- telefone 

- email 

**PLANO\_ASSINATURA**

- `id\_plano (PK)` 

- nome\_plano 

- valor 

- duração 

**INSTRUTOR**

- `id\_instrutor (PK)` 

- nome 

- CPF 

- telefone 

- especialidade

**CARTÃO\_DE\_ACESSO**

- `id\_cartao` **(PK)** 

- `codigo\_cartao` 

- `data\_emissao` 

- `status`


### 3. Relacionamentos e cardinalidades

#### **PLANO\_ASSINATURA — ALUNO**

**1:N**

Um plano pode ter vários alunos, enquanto cada aluno possui um plano.

```
`PLANO\_ASSINATURA 1 ───── N ALUNO`
```

#### **INSTRUTOR — ALUNO**

**N:N**

Um instrutor pode acompanhar vários alunos, e um aluno pode ter vários instrutores.

```
`INSTRUTOR N ───── N ALUNO`
```

#### **ALUNO — CARTÃO\_DE\_ACESSO**

**1:1**

Como você pediu um exemplo de **1:1** mais relacionado diretamente à academia:

- Um aluno possui um cartão de acesso. 

- Um cartão de acesso pertence a um único aluno. 

```
`ALUNO 1 ───── 1 CARTÃO\_DE\_ACESSO`
```

Então, você já tem exemplos dos três tipos principais de cardinalidade:

- **1:1** → Aluno e Cartão de Acesso 

- **1:N** → Plano de Assinatura e Alunos 

- **N:N** → Instrutores e Alunos

