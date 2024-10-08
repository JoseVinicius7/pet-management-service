# Pet Management Service

Microserviço para gerenciamento de pets e agendamentos de serviços (banho, tosa, etc.) em pet shops.

## Tecnologias

- **Java 21**
- **Spring Boot 3**
- **PostgreSQL**
- **Swagger** para documentação
- **JWT** para autenticação
- **JUnit 5** para testes
- **AWS** para deploy

## Funcionalidades

- **CRUD de Pets**: Criar, listar, atualizar e excluir pets.
- **CRUD de Agendamentos**: Criar, listar, atualizar e cancelar agendamentos.

## Endpoints Principais

### Pets

- `GET /api/v1/pets` - Listar todos os pets
- `POST /api/v1/pets` - Criar um novo pet
- `PUT /api/v1/pets/{id}` - Atualizar um pet existente
- `DELETE /api/v1/pets/{id}` - Excluir um pet

### Agendamentos

- `GET /api/v1/appointments` - Listar todos os agendamentos
- `POST /api/v1/appointments` - Criar um novo agendamento
- `PUT /api/v1/appointments/{id}` - Atualizar um agendamento existente
- `DELETE /api/v1/appointments/{id}` - Cancelar um agendamento

## Configuração Local

Clone o repositório:

```bash
git clone https://github.com/seu-usuario/pet-management-service.git
