📋 Task Manager API (Secure Edition)

Status: Estável (Com Autenticação JWT e Multi-tenancy)

📖 Sobre o Projeto

Este projeto é uma API RESTful completa para gerenciamento de tarefas. Construída com arquitetura moderna, ela não apenas realiza o CRUD de entidades, mas também garante isolamento total de dados por usuário. Cada usuário cadastrado possui seu próprio ambiente seguro, gerenciando apenas os projetos e tarefas que lhe pertencem.

🚀 Tecnologias Utilizadas

Linguagem: Java 17

Framework: Spring Boot 3

Segurança: Spring Security & JWT (JSON Web Tokens)

Banco de Dados: PostgreSQL

Infraestrutura: Docker e Docker Compose

✨ Funcionalidades

Segurança Avançada: Cadastro e Autenticação de usuários gerando tokens JWT válidos por 24 horas.

Isolamento de Dados (Multi-tenancy): Usuários acessam e modificam exclusivamente seus próprios dados.

Gerenciamento Completo: CRUD completo para Categorias, Projetos e Tarefas.

Validações Rigorosas: Uso do padrão DTO (Data Transfer Object) para validar as entradas do usuário (ex: senhas fortes obrigatórias).

Tratamento de Erros: Global Exception Handler para respostas padronizadas em caso de conflitos de dados, itens não encontrados ou requisições inválidas.

🛠️ Como Executar

A aplicação está totalmente containerizada. Você só precisa do Docker e do Docker Compose.

Clone o repositório:

git clone https://github.com/siqueira77/task-manager-v2.git
cd task-manager-v2


Inicie a aplicação e o banco de dados:

docker-compose up --build


A API estará disponível em http://localhost:8080.

🧪 Como Testar e Usar a API

Todas as rotas do sistema (exceto a criação de conta e login) estão bloqueadas e exigem um Token JWT. Siga o fluxo abaixo:

1. Criar uma Conta (Registro)

POST /auth/register
(A senha deve conter pelo menos 6 caracteres, 1 número e 1 caractere especial).

{
  "username": "lucas.siqueira",
  "password": "Password@123"
}


2. Fazer Login para obter o Token

POST /auth/login

{
  "username": "lucas.siqueira",
  "password": "Password@123"
}


Guarde o token retornado na resposta desta requisição.

3. Acessar Rotas Protegidas

Para criar categorias, projetos ou tarefas, você deve adicionar o token recebido no Header (Cabeçalho) de todas as requisições seguintes:

Key: Authorization

Value: Bearer SEU_TOKEN_AQUI

Exemplo: Criar uma Categoria

POST /api/categories  (Lembre-se do Header Authorization)

{
  "name": "Faculdade"
}


Exemplo: Criar um Projeto

POST /api/projects

{
  "title": "Trabalho de Conclusão",
  "categoryId": 1
}


Exemplo: Criar uma Tarefa

POST /api/tasks

{
  "title": "Escrever documentação",
  "projectId": 1
}


🏗️ Estrutura e Relacionamentos

User: Entidade principal de segurança. É o dono (owner) de todas as outras entidades.

Category: Agrupa vários projetos temáticos.

Project: Pertence a uma categoria e agrupa tarefas específicas.

Task: Pertence a um projeto e possui um status booleano de conclusão (completed).