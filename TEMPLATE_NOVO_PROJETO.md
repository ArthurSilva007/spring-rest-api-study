# 🎯 Projeto Template - Como Estruturar Novos Projetos Spring Boot

Este arquivo mostra como estruturar um novo projeto Spring Boot seguindo as boas práticas aprendidas.

---

## 📋 Passo a Passo para Criar um Novo Projeto

### Passo 1: Gerar Projeto Base

Opção A: Via Site (Recomendado)
```
1. Acesse: https://start.spring.io/
2. Preencha:
   - Group: br.com.seuNome
   - Artifact: seu-projeto
   - Language: Java
   - Spring Boot: 4.0.4 (ou mais recente)
   - Java: 17
3. Selecione dependências:
   - Spring Web
   - Spring Data JPA
   - H2 Database (para teste)
4. Clique em "Generate"
5. Extraia o arquivo ZIP
6. Abra em sua IDE
```

Opção B: Via Maven
```bash
mvn archetype:generate ^
  -DgroupId=br.com.seuNome ^
  -DartifactId=seu-projeto ^
  -DarchetypeArtifactId=maven-archetype-quickstart ^
  -DinteractiveMode=false
```

---

## 🏗️ Estrutura de Pastas Padrão

```
seu-projeto/
│
├── src/main/java/br/com/seuNome/seuprojeto/
│   │
│   ├── SeuProjetoApplication.java
│   │   └─ Classe principal (@SpringBootApplication)
│   │
│   ├── controller/
│   │   ├── UserController.java
│   │   ├── ProductController.java
│   │   └── OrderController.java
│   │
│   ├── service/
│   │   ├── UserService.java
│   │   ├── ProductService.java
│   │   └── OrderService.java
│   │
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── ProductRepository.java
│   │   └── OrderRepository.java
│   │
│   ├── database/
│   │   └── model/
│   │       ├── UserEntity.java
│   │       ├── ProductEntity.java
│   │       └── OrderEntity.java
│   │
│   ├── dto/
│   │   ├── UserDto.java
│   │   ├── ProductDto.java
│   │   └── OrderDto.java
│   │
│   ├── exception/
│   │   ├── ResourceNotFoundException.java
│   │   ├── BadRequestException.java
│   │   └── UnauthorizedException.java
│   │
│   ├── handler/
│   │   └── GlobalExceptionHandler.java
│   │
│   ├── config/
│   │   └── WebConfig.java (se necessário)
│   │
│   └── utils/
│       ├── DateUtil.java
│       └── StringUtil.java
│
├── src/main/resources/
│   ├── application.yaml
│   ├── application-dev.yaml
│   ├── application-prod.yaml
│   └── static/
│       └── (CSS, JS, imagens)
│
├── src/test/java/
│   └── (testes aqui)
│
├── pom.xml
├── README.md
└── .gitignore
```

---

## 📝 Template de Classe Entity

```java
package br.com.seuNome.seuprojeto.database.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entity que representa [descrição]
 * Mapeada para tabela 'seu_entity' no banco de dados
 */
@Entity
@Table(name = "seu_entity")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeuEntity {

    /**
     * ID único (chave primária)
     * Gerado automaticamente pelo banco (IDENTITY)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Campo obrigatório de nome
     * Único na tabela
     */
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    /**
     * Campo de descrição
     * Pode ser nulo
     */
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    /**
     * Data de criação
     * Preenchida automaticamente
     */
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    /**
     * Data de atualização
     * Atualizada a cada mudança
     */
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    /**
     * Método chamado antes de salvar
     * Preenche datas automaticamente
     */
    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Método chamado antes de atualizar
     * Atualiza data de modificação
     */
    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
```

---

## 📦 Template de Classe DTO

```java
package br.com.seuNome.seuprojeto.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO para transferência de dados de SeuEntity
 * Usado em requisições POST e PUT
 * Não contém ID (gerado automaticamente pelo servidor)
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeuDto {

    /**
     * Nome do entity
     * Obrigatório, não pode ser branco
     */
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    /**
     * Descrição do entity
     * Opcional, mas com tamanho máximo
     */
    @Size(max = 500, message = "Descrição não pode ultrapassar 500 caracteres")
    private String descricao;

    /**
     * Preço (se aplicável)
     * Deve ser um valor positivo
     */
    @Positive(message = "Preço deve ser um valor positivo")
    @DecimalMin(value = "0.01", message = "Preço mínimo é 0.01")
    private BigDecimal preco;

    /**
     * Quantidade disponível
     * Deve ser zero ou positivo
     */
    @PositiveOrZero(message = "Quantidade não pode ser negativa")
    private Integer quantidade;

    /**
     * Email (se aplicável)
     * Deve ser um email válido
     */
    @Email(message = "Email deve ser válido")
    private String email;
}
```

---

## 🔍 Template de Classe Repository

```java
package br.com.seuNome.seuprojeto.repository;

import br.com.seuNome.seuprojeto.database.model.SeuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository para acesso a dados de SeuEntity
 * Estende JpaRepository que fornece métodos CRUD automáticos
 * Adicione métodos personalizados conforme necessário
 */
@Repository
public interface SeuRepository extends JpaRepository<SeuEntity, Long> {

    /**
     * Busca entity pelo nome
     * @param nome nome a buscar
     * @return Optional com entity se encontrado
     */
    Optional<SeuEntity> findByNome(String nome);

    /**
     * Busca todos os entities com nome contendo
     * @param nome parte do nome
     * @return lista de entities encontrados
     */
    List<SeuEntity> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca entities por data de criação
     * @param dataInicio data inicial
     * @param dataFim data final
     * @return lista de entities criados no período
     */
    @Query("SELECT s FROM SeuEntity s WHERE s.dataCriacao BETWEEN :dataInicio AND :dataFim")
    List<SeuEntity> findByDataCriacaoBetween(
        @Param("dataInicio") LocalDateTime dataInicio,
        @Param("dataFim") LocalDateTime dataFim
    );

    /**
     * Conta quantos entities existem com nome específico
     * @param nome nome a procurar
     * @return quantidade encontrada
     */
    Long countByNome(String nome);

    /**
     * Verifica se entity com nome existe
     * @param nome nome a verificar
     * @return true se existe, false caso contrário
     */
    boolean existsByNome(String nome);
}
```

---

## 💼 Template de Classe Service

```java
package br.com.seuNome.seuprojeto.service;

import br.com.seuNome.seuprojeto.database.model.SeuEntity;
import br.com.seuNome.seuprojeto.dto.SeuDto;
import br.com.seuNome.seuprojeto.exception.ResourceNotFoundException;
import br.com.seuNome.seuprojeto.repository.SeuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service que contém toda a lógica de negócio de SeuEntity
 * Atua como intermediário entre Controller e Repository
 */
@Service
@RequiredArgsConstructor
public class SeuService {

    /**
     * Injeção do Repository
     * Acessa dados no banco de dados
     */
    private final SeuRepository repository;

    /**
     * Busca todos os entities
     * @return lista com todos os entities
     */
    public List<SeuEntity> buscarTodos() {
        return repository.findAll();
    }

    /**
     * Busca entity por ID
     * @param id ID a buscar
     * @return entity encontrado
     * @throws ResourceNotFoundException se não encontrar
     */
    public SeuEntity buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity não encontrado com ID: " + id));
    }

    /**
     * Cria novo entity
     * @param dto DTO com dados do novo entity
     * @return entity criado
     */
    public SeuEntity criar(SeuDto dto) {
        // Validações de negócio
        if (repository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Entity com este nome já existe");
        }

        // Converte DTO para Entity
        SeuEntity entity = SeuEntity.builder()
            .nome(dto.getNome())
            .descricao(dto.getDescricao())
            .build();

        // Salva no banco
        return repository.save(entity);
    }

    /**
     * Atualiza entity existente
     * @param id ID do entity a atualizar
     * @param dto DTO com novos dados
     * @return entity atualizado
     * @throws ResourceNotFoundException se não encontrar
     */
    public SeuEntity atualizar(Long id, SeuDto dto) {
        SeuEntity entity = buscarPorId(id);

        // Atualiza campos
        if (dto.getNome() != null) {
            entity.setNome(dto.getNome());
        }
        if (dto.getDescricao() != null) {
            entity.setDescricao(dto.getDescricao());
        }

        // Salva alterações
        return repository.save(entity);
    }

    /**
     * Deleta entity por ID
     * @param id ID do entity a deletar
     * @throws ResourceNotFoundException se não encontrar
     */
    public void deletar(Long id) {
        SeuEntity entity = buscarPorId(id);
        repository.delete(entity);
    }
}
```

---

## 🎮 Template de Classe Controller

```java
package br.com.seuNome.seuprojeto.controller;

import br.com.seuNome.seuprojeto.database.model.SeuEntity;
import br.com.seuNome.seuprojeto.dto.SeuDto;
import br.com.seuNome.seuprojeto.service.SeuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST que mapeia endpoints HTTP para operações de SeuEntity
 * Todos os endpoints estão prefixados com /v1/seus
 */
@RestController
@RequestMapping("/v1/seus")
@RequiredArgsConstructor
public class SeuController {

    /**
     * Injeção do Service de negócio
     */
    private final SeuService service;

    /**
     * GET /v1/seus
     * Busca todos os entities
     * @return lista de todos os entities
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SeuEntity> buscarTodos() {
        return service.buscarTodos();
    }

    /**
     * GET /v1/seus/{id}
     * Busca um entity específico por ID
     * @param id ID do entity
     * @return entity encontrado
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SeuEntity buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    /**
     * POST /v1/seus
     * Cria novo entity
     * @param dto DTO com dados do novo entity
     * @return entity criado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeuEntity criar(@Valid @RequestBody SeuDto dto) {
        return service.criar(dto);
    }

    /**
     * PUT /v1/seus/{id}
     * Atualiza entity existente
     * @param id ID do entity a atualizar
     * @param dto DTO com novos dados
     * @return entity atualizado
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SeuEntity atualizar(@PathVariable Long id, @Valid @RequestBody SeuDto dto) {
        return service.atualizar(id, dto);
    }

    /**
     * DELETE /v1/seus/{id}
     * Deleta entity
     * @param id ID do entity a deletar
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
```

---

## ⚠️ Template de Classe Exception Handler

```java
package br.com.seuNome.seuprojeto.handler;

import br.com.seuNome.seuprojeto.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler global para tratamento de exceções
 * Captura exceções em qualquer controller e retorna resposta padronizada
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata ResourceNotFoundException
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error("Recurso não encontrado")
            .message(ex.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Trata erros de validação
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            erros.put(fieldName, errorMessage);
        });

        ErrorResponse error = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Erro de validação")
            .message("Campos inválidos")
            .validationErrors(erros)
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Trata exceções genéricas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        ErrorResponse error = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Erro interno do servidor")
            .message(ex.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * DTO de resposta de erro padronizada
     */
    @Getter
    @Setter
    static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private Map<String, String> validationErrors;

        // Builder (você pode usar Lombok @Builder também)
        public static ErrorResponse.ErrorResponseBuilder builder() {
            return new ErrorResponse.ErrorResponseBuilder();
        }

        public static class ErrorResponseBuilder {
            private LocalDateTime timestamp;
            private int status;
            private String error;
            private String message;
            private Map<String, String> validationErrors;

            public ErrorResponseBuilder timestamp(LocalDateTime timestamp) {
                this.timestamp = timestamp;
                return this;
            }

            public ErrorResponseBuilder status(int status) {
                this.status = status;
                return this;
            }

            public ErrorResponseBuilder error(String error) {
                this.error = error;
                return this;
            }

            public ErrorResponseBuilder message(String message) {
                this.message = message;
                return this;
            }

            public ErrorResponseBuilder validationErrors(Map<String, String> validationErrors) {
                this.validationErrors = validationErrors;
                return this;
            }

            public ErrorResponse build() {
                ErrorResponse response = new ErrorResponse();
                response.timestamp = this.timestamp;
                response.status = this.status;
                response.error = this.error;
                response.message = this.message;
                response.validationErrors = this.validationErrors;
                return response;
            }
        }
    }
}
```

---

## 📋 Template de application.yaml

```yaml
# ============================================
# CONFIGURAÇÕES PRINCIPAIS
# ============================================

spring:
  application:
    name: seu-projeto

  # ============================================
  # DATASOURCE (BANCO DE DADOS)
  # ============================================
  datasource:
    # Para H2 (desenvolvimento):
    url: jdbc:h2:mem:seu_projeto
    driverClassName: org.h2.Driver
    username:
    password:

    # Para MySQL (descomente e configure):
    # url: jdbc:mysql://localhost:3306/seu_projeto?useSSL=false&serverTimezone=UTC
    # driverClassName: com.mysql.cj.jdbc.Driver
    # username: root
    # password: sua_senha

    # Para PostgreSQL (descomente e configure):
    # url: jdbc:postgresql://localhost:5432/seu_projeto
    # driverClassName: org.postgresql.Driver
    # username: postgres
    # password: sua_senha

  # ============================================
  # JPA & HIBERNATE
  # ============================================
  jpa:
    hibernate:
      # update: cria/atualiza tabelas conforme necessário
      # validate: apenas valida schema
      # create: cria tudo (limpa dados ao iniciar)
      # create-drop: cria e deleta ao parar
      ddl-auto: update

    # Log de SQL (útil para debug)
    show-sql: false
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.H2Dialect
        # dialect: org.hibernate.dialect.MySQL8Dialect (para MySQL)
        # dialect: org.hibernate.dialect.PostgreSQL10Dialect (para PostgreSQL)

  # ============================================
  # H2 CONSOLE (APENAS DESENVOLVIMENTO)
  # ============================================
  h2:
    console:
      enabled: true
      path: /h2-console

# ============================================
# SERVIDOR (TOMCAT)
# ============================================
server:
  port: 8080
  servlet:
    context-path: /api  # Opcional: prefixo para todas as rotas

# ============================================
# LOGGING
# ============================================
logging:
  level:
    root: INFO
    org:
      springframework: INFO
      springframework.web: DEBUG
      hibernate: INFO
  format: "%d{HH:mm:ss} [%thread] %-5level %logger - %msg%n"
```

---

## 🧪 Checklist de Criação de Novo Projeto

- [ ] Criar estrutura de pastas
- [ ] Criar classes Entity com `@Entity @Getter @Setter @Builder`
- [ ] Criar DTOs correspondentes (sem ID)
- [ ] Criar Repositories estendendo `JpaRepository`
- [ ] Criar Services com lógica de negócio
- [ ] Criar Controllers com endpoints REST
- [ ] Adicionar validações (`@Valid @NotBlank` etc)
- [ ] Implementar GlobalExceptionHandler
- [ ] Configurar `application.yaml`
- [ ] Testar endpoints (GET, POST, PUT, DELETE)
- [ ] Adicionar logging
- [ ] Documentar com comentários
- [ ] Escrever testes unitários
- [ ] Criar README.md
- [ ] Configurar `.gitignore`

---

## 🚀 Comandos Maven Úteis

```bash
# Compilar
mvn clean compile

# Empacotar (gerar JAR)
mvn clean package

# Executar
mvn spring-boot:run

# Rodar testes
mvn test

# Limpar (remover target/)
mvn clean

# Ver dependências
mvn dependency:tree

# Criar relatório
mvn site
```

---

## 🔗 Recursos Adicionais

- Spring Boot Docs: https://spring.io/projects/spring-boot
- Spring Data JPA: https://spring.io/projects/spring-data-jpa
- Jakarta EE: https://jakarta.ee/
- Validação: https://hibernate.org/validator/

---

Você está pronto para criar seu próximo projeto Spring Boot! 🚀

Última atualização: 25/03/2026

