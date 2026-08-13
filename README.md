# 📋 Task Manager API

> 🚧 **Status:** Em desenvolvimento (Refatoração e Atualização)

## 💻 Sobre o Projeto
Este projeto é uma API RESTful para gerenciamento de tarefas (Task Manager). Ele é a atualização e refatoração de um projeto que desenvolvi no passado, agora trazendo melhores práticas, código mais limpo e infraestrutura modernizada utilizando Docker.

O objetivo principal desta versão é demonstrar a evolução técnica e facilitar a execução em qualquer ambiente, sem a necessidade de configurações complexas.

## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Java 17
* **Framework:** Spring Boot
* **Banco de Dados:** PostgreSQL
* **Infraestrutura:** Docker e Docker Compose
* **Gerenciador de Dependências:** Maven

## ⚙️ Funcionalidades

**Implementadas (Até o momento):**
- [x] CRUD de Tarefas (Tasks)
- [x] CRUD de Projetos (Projects)
- [x] CRUD de Categorias (Categories)
- [x] CRUD de Comentários (Comments)
- [x] Tratamento global de exceções (Global Exception Handler)
- [x] Containerização com Docker (Aplicação + Banco de Dados)

## 🚀 Como Executar

A maior vantagem desta atualização é a facilidade de execução. Você não precisa ter o Java, Maven ou PostgreSQL instalados na sua máquina. Apenas o **Docker** e o **Docker Compose** são necessários.

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/siqueira77/task-manager-v2.git
   cd task-manager-v2
   ```

2. **Inicie a aplicação com o Docker:**
   ```bash
   docker-compose up --build
   ```

3. **Acesse a API:**
   A aplicação estará rodando na porta `8080`.

## 🧪 Como Testar as Funcionalidades

Você pode utilizar ferramentas como Postman, Insomnia ou até mesmo o cURL no terminal para realizar as requisições. A URL base é `http://localhost:8080/api`.

Como as entidades possuem relacionamentos, siga a ordem abaixo para testar o fluxo completo de criação:

### 1. Criar uma Categoria
**POST** `http://localhost:8080/api/categories`
```json
{
  "name": "Trabalho"
}
```

### 2. Criar um Projeto (Vinculado à Categoria ID 1)
**POST** `http://localhost:8080/api/projects`
```json
{
  "name": "Desenvolver API",
  "description": "Atualização do Task Manager para o portfólio",
  "category": {
    "id": 1
  }
}
```

### 3. Criar uma Tarefa (Vinculada ao Projeto ID 1)
**POST** `http://localhost:8080/api/tasks`
```json
{
  "title": "Configurar Docker",
  "description": "Criar Dockerfile e docker-compose.yml",
  "completed": false,
  "project": {
    "id": 1
  }
}
```

### 4. Adicionar um Comentário (Vinculado ao Projeto ID 1)
**POST** `http://localhost:8080/api/comments`
```json
{
  "text": "Não esquecer de adicionar o volume no PostgreSQL",
  "project": {
    "id": 1
  }
}
```

### Outras Operações (Exemplos)
* **Listar todos os projetos:** `GET http://localhost:8080/api/projects`
* **Buscar tarefa específica:** `GET http://localhost:8080/api/tasks/1`
* **Atualizar uma tarefa:** `PUT http://localhost:8080/api/tasks/1` *(Enviando o JSON atualizado no corpo)*
* **Deletar uma categoria:** `DELETE http://localhost:8080/api/categories/1`

## 📂 Estrutura de Dados (Modelagem)
* **Project:** Pertence a uma `Category` e contém várias `Tasks` e `Comments`.
* **Task:** Pertence a um `Project` e possui status de conclusão.
* **Category:** Agrupa vários `Projects`.
* **Comment:** Associado a um `Project`.
