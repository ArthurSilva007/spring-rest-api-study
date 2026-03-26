# 📚 Spring Boot REST API - Produtos

[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.4-green?logo=spring)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue?logo=apache-maven)](https://maven.apache.org)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

> Uma API REST completa com **documentação detalhada linha por linha** para fins educacionais 🎓

---

## 📖 Documentação Rápida

| Documento | Descrição | Acesso |
|-----------|-----------|--------|
| **COMECE_AQUI.md** | ⭐ Início rápido | [`📖`](./COMECE_AQUI.md) |
| **INDICE.md** | Mapa completo de documentação | [`📖`](./INDICE.md) |
| **README.md** | Este arquivo - Documentação principal | [`📖`](./README.md) |
| **GUIA_RAPIDO.md** | Referência rápida e templates | [`📖`](./GUIA_RAPIDO.md) |
| **COMO_TESTAR.md** | Guia prático de testes | [`📖`](./COMO_TESTAR.md) |
| **TEMPLATE_NOVO_PROJETO.md** | Templates para novo projeto | [`📖`](./TEMPLATE_NOVO_PROJETO.md) |
| **RESUMO_FEITO.md** | Resumo do que foi implementado | [`📖`](./RESUMO_FEITO.md) |

---

## 📋 Índice do README

1. [Visão Geral](#visão-geral)
2. [Arquitetura](#arquitetura)
3. [Estrutura do Projeto](#estrutura-do-projeto)
4. [Dependências](#dependências)
5. [Configurações](#configurações)
6. [API REST - Endpoints](#api-rest---endpoints)
7. [Como Executar](#como-executar)
8. [Como Testar](#como-testar)
9. [Conceitos Importantes](#conceitos-importantes)
10. [Boas Práticas](#boas-práticas)

---

## 🎯 Visão Geral

Este é um **projeto de aprendizado** de Spring Boot que implementa uma **API REST completa** para gerenciar produtos com:

- ✅ 4 endpoints REST (CRUD)
- ✅ Código comentado
- ✅ Documentação completa
- ✅ Padrões profissionais
- ✅ Pronto para aprendizado

---

## 🚀 Endpoints

| Método | Endpoint | Descrição |
|--------|---------|----------|
| GET | `/v1/produtos` | Listar produtos |
| POST | `/v1/produtos` | Criar produto |
| PUT | `/v1/produtos/{id}` | Atualizar |
| DELETE | `/v1/produtos/{id}` | Deletar |

---

## ⚙️ Tecnologias

- Java 17
- Spring Boot 4.0.4
- Spring Web
- Spring Data JPA
- H2 Database
- Maven
- Swagger

---

## ▶️ Como Executar

```bash
mvn clean spring-boot:run
```

Acesse:

- API → http://localhost:8081/v1/produtos  
- Swagger → http://localhost:8081/swagger-ui.html  
- H2 → http://localhost:8081/h2-console  

---

## 🧪 Teste rápido (cURL)

```bash
curl http://localhost:8081/v1/produtos
```

---

## 🏗️ Arquitetura

```
Controller → Service → Repository → Database
```

---

## 💡 Boas práticas

- Usar DTO
- Separar camadas
- Tratar erros
- Validar dados
- Usar logs

---

## 📌 Próximos passos

- Segurança (JWT)
- Testes
- Banco real (MySQL/PostgreSQL)
- Docker
- CI/CD

---

## 🚀 Autor

Projeto para fins educacionais.

---

# spring-rest-api-study
