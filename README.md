# Projeto Spring Boot Produtos - Documentação Completa

## 📋 Índice
1. [Visão Geral do Projeto](#visão-geral-do-projeto)
2. [Arquitetura e Padrões](#arquitetura-e-padrões)
3. [Estrutura do Projeto](#estrutura-do-projeto)
4. [Dependências e Bibliotecas](#dependências-e-bibliotecas)
5. [Configurações](#configurações)
6. [Endpoints da API](#endpoints-da-api)
7. [Executar o Projeto](#executar-o-projeto)
8. [Testando a API](#testando-a-api)
9. [Conceitos Importantes](#conceitos-importantes)
10. [Dicas para Aplicar em Outros Projetos](#dicas-para-aplicar-em-outros-projetos)

---

## 🎯 Visão Geral do Projeto

Este é um **projeto de aprendizado** de Spring Boot que implementa uma **API REST** para gerenciar produtos. A aplicação permite:

- **Listar** todos os produtos (GET)
- **Criar** novos produtos (POST)
- **Atualizar** produtos existentes (PUT)
- **Deletar** produtos (DELETE)

### Tecnologias Utilizadas
- **Java 17**: Linguagem de programação
- **Spring Boot 4.0.4**: Framework web
- **Spring Data JPA**: Para acesso a banco de dados
- **Lombok**: Para reduzir código repetitivo
- **H2 Database**: Banco de dados em memória (para testes)
- **Maven**: Gerenciador de dependências

---

## 🏗️ Arquitetura e Padrões

### Padrão de Camadas (Layered Architecture)

Este projeto segue o padrão de **arquitetura em camadas**, que é a estrutura mais comum em aplicações Spring Boot:

```
┌─────────────────────────────────────────┐
│   CAMADA DE APRESENTAÇÃO (Controller)   │ ← Recebe requisições HTTP
├─────────────────────────────────────────┤
│   CAMADA DE NEGÓCIO (Service)           │ ← Lógica de negócio
├─────────────────────────────────────────┤
│   CAMADA DE DADOS (Repository/Entity)   │ ← Acesso ao banco de dados
├─────────────────────────────────────────┤
│   CAMADA DE ENTIDADES (Entity/Model)    │ ← Representação dos dados
└─────────────────────────────────────────┘
```

### Padrões Utilizados

#### 1. **MVC (Model-View-Controller)**
- **Model**: ProdutoEntity (representa os dados)
- **View**: JSON (retornado pela API)
- **Controller**: ProdutoController (recebe e processa requisições)

#### 2. **DTO (Data Transfer Object)**
- ProdutoDto é usado para transferir dados entre cliente e servidor
- Separa a estrutura interna da aplicação da estrutura da API

#### 3. **Service Layer (Camada de Serviço)**
- ProdutoService encapsula toda a lógica de negócio
- Controllers delegam operações para o Service
- Favorece reutilização de código e testes

#### 4. **Injeção de Dependência**
- Spring gerencia a criação e injeção de objetos automaticamente
- Exemplo: `@RequiredArgsConstructor` injeta o ProdutoService no Controller

---

## 📁 Estrutura do Projeto

```
spring-boot-stud/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/silva/spring_boot_stud/
│   │   │       │
│   │   │       ├── SpringBootStudApplication.java
│   │   │       │   └─ Classe principal que inicia a aplicação
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   └── ProdutoController.java
│   │   │       │       └─ Mapeia endpoints HTTP para operações de produtos
│   │   │       │
│   │   │       ├── service/
│   │   │       │   └── ProdutoService.java
│   │   │       │       └─ Contém a lógica de negócio de produtos
│   │   │       │
│   │   │       ├── database/
│   │   │       │   └── model/
│   │   │       │       └── ProdutoEntity.java
│   │   │       │           └─ Entidade JPA que representa um Produto
│   │   │       │
│   │   │       └── dto/
│   │   │           └── ProdutoDto.java
│   │   │               └─ DTO para transferência de dados HTTP
│   │   │
│   │   └── resources/
│   │       ├── application.yaml
│   │       │   └─ Configurações da aplicação (porta, banco de dados, etc)
│   │       ├── static/
│   │       │   └─ Arquivos estáticos (CSS, JS, imagens)
│   │       └── templates/
│   │           └─ Templates HTML (não usado neste projeto)
│   │
│   └── test/
│       └── java/
│           └── SpringBootStudApplicationTests.java
│               └─ Testes unitários (não implementados neste projeto)
│
├── pom.xml
│   └─ Arquivo de configuração do Maven (dependências)
│
└── README.md
    └─ Este arquivo de documentação
```

---

## 📦 Dependências e Bibliotecas

### Dependências Principais (pom.xml)

#### 1. **Spring Boot Starter Web MVC**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>
```
- Fornece suporte para criar APIs REST
- Inclui o servidor Tomcat embutido
- Permite usar anotações como @RestController, @GetMapping, etc

#### 2. **Spring Boot Data JPA**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
- Simplifica acesso a banco de dados
- Fornece abstração sobre SQL
- Usa Hibernate como implementação padrão

#### 3. **Lombok**
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```
- **Problema que resolve**: Java requer muito código repetitivo (getters, setters, construtores)
- **Solução**: Gera esse código automaticamente em tempo de compilação
- **Anotações usadas**:
  - `@Getter`: Gera métodos get
  - `@Setter`: Gera métodos set
  - `@NoArgsConstructor`: Gera construtor vazio
  - `@AllArgsConstructor`: Gera construtor com todos os atributos
  - `@Builder`: Implementa padrão Builder para construção fluida
  - `@ToString`: Gera método toString()

#### 4. **H2 Database**
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```
- Banco de dados em memória (RAM)
- **Vantagem**: Não precisa instalar banco de dados externo
- **Desvantagem**: Dados são perdidos quando aplicação reinicia
- **Uso**: Ideal para testes e prototipagem
- **Console**: Acessível em http://localhost:8081/h2-console

#### 5. **SpringDoc OpenAPI**
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>3.0.2</version>
</dependency>
```
- Gera documentação automática da API
- **Swagger UI**: Interface visual para testar endpoints
- **Acessível em**: http://localhost:8081/swagger-ui.html

---

## ⚙️ Configurações

### arquivo: `src/main/resources/application.yaml`

Este arquivo controla como a aplicação é executada:

```yaml
spring:
  application:
    name: spring-boot-stud
    # Nome da aplicação (usado internamente)

  datasource:
    url: jdbc:h2:mem:testdb
    # Conecta a um banco H2 em memória
    # "mem:testdb" significa banco em memória com nome "testdb"

  jpa:
    hibernate:
      ddl-auto: update
      # Auto-cria/atualiza tabelas conforme necessário
      # Opções: none, validate, update, create, create-drop

server:
  port: 8081
  # Porta onde a API está disponível
  # Acesse em: http://localhost:8081
```

### Como Alterar a Porta?

Se você quer trocar a porta de `8081` para outra (ex: `8080`):

1. Abra `src/main/resources/application.yaml`
2. Altere a linha:
   ```yaml
   server:
     port: 8080  # Mudou de 8081 para 8080
   ```
3. Reinicie a aplicação
4. API estará em: http://localhost:8080

---

## 🔌 Endpoints da API

### Base URL
```
http://localhost:8081/v1/produtos
```

### 1. **Listar Todos os Produtos**

```
GET /v1/produtos
```

**Descrição**: Retorna uma lista com todos os produtos cadastrados

**Status HTTP**: 200 (OK)

**Exemplo de Resposta**:
```json
[
  {
    "id": 1,
    "name": "Notebook",
    "quantidade": 10,
    "preco": 2000
  },
  {
    "id": 2,
    "name": "iphone",
    "quantidade": 10,
    "preco": 7000
  }
]
```

---

### 2. **Criar Novo Produto**

```
POST /v1/produtos
Content-Type: application/json
```

**Descrição**: Cria um novo produto e atribui um ID automaticamente

**Status HTTP**: 201 (CREATED)

**Corpo da Requisição** (não inclua `id`):
```json
{
  "name": "Teclado",
  "quantidade": 5,
  "preco": 150
}
```

**Resposta** (inclui o `id` gerado):
```json
{
  "id": 4,
  "name": "Teclado",
  "quantidade": 5,
  "preco": 150
}
```

---

### 3. **Atualizar Produto**

```
PUT /v1/produtos/{id}
Content-Type: application/json
```

**Descrição**: Atualiza um produto existente pelo ID

**Status HTTP**: 201 (CREATED)

**Parâmetros**:
- `{id}`: ID do produto a atualizar (ex: 1, 2, 3)

**Corpo da Requisição**:
```json
{
  "name": "Notebook Gamer",
  "quantidade": 5,
  "preco": 3500
}
```

**Resposta**:
```json
{
  "id": 1,
  "name": "Notebook Gamer",
  "quantidade": 5,
  "preco": 3500
}
```

**Exemplo Completo**:
```
PUT http://localhost:8081/v1/produtos/1
Content-Type: application/json

{
  "name": "Notebook Gamer",
  "quantidade": 5,
  "preco": 3500
}
```

---

### 4. **Deletar Produto**

```
DELETE /v1/produtos/{id}
```

**Descrição**: Remove um produto do sistema

**Status HTTP**: 204 (NO CONTENT)

**Parâmetros**:
- `{id}`: ID do produto a deletar

**Resposta**: Nenhum corpo (apenas status 204)

**Exemplo Completo**:
```
DELETE http://localhost:8081/v1/produtos/1
```

---

## 🚀 Executar o Projeto

### Pré-requisitos
- Java 17+ instalado
- Maven instalado
- IDE (IntelliJ IDEA, VS Code ou Eclipse recomendado)

### Opção 1: Executar via Maven

```bash
# Navegar até a pasta do projeto
cd C:\Users\ANDERSON\Downloads\spring-boot-stud\spring-boot-stud

# Compilar e executar
mvn clean spring-boot:run
```

### Opção 2: Executar via IDE

1. Abra o projeto na IDE (IntelliJ IDEA recomendado)
2. Clique com botão direito em `SpringBootStudApplication.java`
3. Selecione "Run" ou pressione Shift+F10

### Sinais de Sucesso

Quando a aplicação inicia com sucesso, você verá no console:
```
Started SpringBootStudApplication in X.XXX seconds
```

E poderá acessar:
- **API**: http://localhost:8081/v1/produtos
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **H2 Console**: http://localhost:8081/h2-console

---

## 🧪 Testando a API

### Opção 1: Swagger UI (Recomendado)

1. Acesse: http://localhost:8081/swagger-ui.html
2. Veja todos os endpoints documentados
3. Teste diretamente pela interface

### Opção 2: Postman ou Insomnia

1. Crie uma nova requisição
2. Configure conforme exemplo abaixo
3. Teste cada endpoint

**Exemplo GET**:
```
URL: http://localhost:8081/v1/produtos
Método: GET
```

**Exemplo POST**:
```
URL: http://localhost:8081/v1/produtos
Método: POST
Headers: Content-Type: application/json
Body:
{
  "name": "Monitor",
  "quantidade": 3,
  "preco": 800
}
```

### Opção 3: cURL (Linha de Comando)

```bash
# GET - Listar todos
curl http://localhost:8081/v1/produtos

# POST - Criar novo
curl -X POST http://localhost:8081/v1/produtos \
  -H "Content-Type: application/json" \
  -d '{"name":"Monitor","quantidade":3,"preco":800}'

# PUT - Atualizar
curl -X PUT http://localhost:8081/v1/produtos/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Monitor Gamer","quantidade":2,"preco":1200}'

# DELETE - Deletar
curl -X DELETE http://localhost:8081/v1/produtos/1
```

---

## 📚 Conceitos Importantes

### 1. REST API

**O que é?**
- REST = Representational State Transfer
- Padrão arquitetural para criar APIs via HTTP
- Usa métodos HTTP: GET, POST, PUT, DELETE

**Por que usar?**
- Simples e padronizado
- Funciona com qualquer tecnologia
- Fácil de testar e debugar

**Métodos HTTP**:
| Método | Operação | Status Sucesso |
|--------|----------|----------------|
| GET | Ler dados | 200 OK |
| POST | Criar dados | 201 CREATED |
| PUT | Atualizar dados | 200 OK ou 201 CREATED |
| DELETE | Deletar dados | 204 NO CONTENT |

---

### 2. Spring Boot

**O que é?**
- Framework que facilita criação de aplicações Spring
- Traz configuração automática (auto-configuration)
- Inclui servidor web embutido (Tomcat)

**Vantagens**:
- Reduz código repetitivo
- Inicia rápido
- Fácil integração com outras bibliotecas

**Anotações principais**:
- `@SpringBootApplication`: Marca classe principal
- `@RestController`: Marca controller REST
- `@Service`: Marca classe de serviço
- `@Autowired` ou `@RequiredArgsConstructor`: Injeta dependências

---

### 3. JPA (Java Persistence API)

**O que é?**
- Interface padrão para acesso a banco de dados em Java
- Transforma objetos Java em registros de banco de dados

**Hibernate**: Implementação mais popular de JPA
- Mapeia atributos Java para colunas de banco
- Gera SQL automaticamente

**Entity**:
```java
@Entity // Marca classe como tabela no banco
public class ProdutoEntity {
    @Id // Chave primária
    private Integer id;

    private String name; // Vira coluna "name" na tabela
}
```

---

### 4. DTO (Data Transfer Object)

**Problema**: Expor a Entity diretamente na API pode causar:
- Exposição de dados sensíveis
- Dificuldade em manter compatibilidade

**Solução**: Usar DTO
- Classe separada para transferência de dados
- Pode ter estrutura diferente da Entity
- Exemplo: ProdutoDto não tem `id` (gerado automaticamente)

**Fluxo**:
```
Cliente HTTP
    ↓
ProdutoDto (JSON)
    ↓
ProdutoController
    ↓
ProdutoService (converte para Entity)
    ↓
ProdutoEntity
    ↓
Banco de Dados
```

---

### 5. Injeção de Dependência

**Problema**: Criar objetos manualmente é trabalhoso
```java
// Sem injeção (errado)
ProdutoService service = new ProdutoService();
ProdutoController controller = new ProdutoController(service);
```

**Solução**: Deixar Spring gerenciar
```java
// Com injeção (correto)
@RestController
public class ProdutoController {
    private final ProdutoService produtoService; // Spring injeta automaticamente
}
```

**Como funciona**:
1. Spring detecta classes com `@Service`, `@Controller`, etc
2. Cria instâncias automaticamente
3. Injeta onde necessário (`@Autowired` ou construtor)

---

### 6. Streams e Lambda (Java 8+)

Este projeto usa programação funcional em vários lugares:

**Stream API** - Processa coleções de forma funcional:
```java
// Encontrar produto com ID específico
PRODUTOS.stream()
    .filter(p -> p.getId().equals(id))  // Lambda: filtra por ID
    .findFirst()                          // Pega primeiro resultado
    .orElseThrow(() -> new RuntimeException("Não encontrado"));
```

**Lambda** - Função anônima compacta:
```java
// Sem lambda (antigo)
PRODUTOS.removeIf(new Predicate<ProdutoEntity>() {
    public boolean test(ProdutoEntity p) {
        return p.getId().equals(id);
    }
});

// Com lambda (moderno)
PRODUTOS.removeIf(p -> p.getId().equals(id));
```

---

### 7. Builders (Padrão de Design)

**Problema**: Construtores com muitos parâmetros ficam confusos
```java
// Difícil ler e lembrar ordem
new ProdutoEntity(1, "Notebook", 10, new BigDecimal(2000));
```

**Solução**: Padrão Builder (gerado por Lombok)
```java
// Fácil ler e entender
ProdutoEntity.builder()
    .id(1)
    .name("Notebook")
    .quantidade(10)
    .preco(new BigDecimal(2000))
    .build();
```

---

## 💡 Dicas para Aplicar em Outros Projetos

### 1. Estrutura de Pastas

Sempre organize seu projeto assim:
```
src/main/java/
├── controller/    (mapeia requisições HTTP)
├── service/       (lógica de negócio)
├── database/
│   ├── model/     (entities JPA)
│   └── repository/ (acesso a dados)
├── dto/          (transferência de dados)
└── exception/    (tratamento de erros)
```

### 2. Separação de Responsabilidades

```
Controller → Recebe requisição HTTP → retorna resposta
Service    → Executa lógica de negócio → validações
Repository → Acessa banco de dados → CRUD operations
Entity    → Representa dados
DTO       → Transfere dados ao cliente
```

### 3. Sempre Use DTOs

```java
// ❌ Errado: Expor Entity diretamente
@PostMapping
public ProdutoEntity create(@RequestBody ProdutoEntity entity) {
    return repository.save(entity);
}

// ✅ Correto: Usar DTO
@PostMapping
public ProdutoEntity create(@RequestBody ProdutoDto dto) {
    ProdutoEntity entity = mapper.toEntity(dto);
    return service.create(entity);
}
```

### 4. Tratamento de Erros

Crie uma classe `ExceptionHandler`:
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(e.getMessage()));
    }
}
```

### 5. Validação de Dados

Use `@Valid` e `javax.validation`:
```java
@PostMapping
public ProdutoEntity create(@Valid @RequestBody ProdutoDto dto) {
    return service.create(dto);
}

public class ProdutoDto {
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    private BigDecimal preco;
}
```

### 6. Banco de Dados Real

Para trocar H2 por MySQL/PostgreSQL:

**1. Adicione ao pom.xml**:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
```

**2. Configure em application.yaml**:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/meu_banco
    username: root
    password: minhasenha
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    database-platform: org.hibernate.dialect.MySQL8Dialect
```

### 7. Repositories (Acesso a Dados)

Para banco de dados real, crie repositories:
```java
@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {
    List<ProdutoEntity> findByName(String name);
    List<ProdutoEntity> findByPrecoBetween(BigDecimal min, BigDecimal max);
}
```

Depois use na service:
```java
@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repository;

    public List<ProdutoEntity> findAll() {
        return repository.findAll();
    }
}
```

### 8. Testes Unitários

Crie testes para garantir qualidade:
```java
@SpringBootTest
class ProdutoServiceTest {
    @MockBean
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    @Test
    void testFindAll() {
        List<ProdutoEntity> result = service.findAll();
        assertNotNull(result);
    }
}
```

### 9. Segurança

Adicione Spring Security:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

E proteja endpoints:
```java
@RestController
@RequestMapping("/v1/produtos")
public class ProdutoController {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<ProdutoEntity> findAll() {
        return service.findAll();
    }
}
```

### 10. Logging

Use logs em vez de System.out:
```java
private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

public List<ProdutoEntity> findAll() {
    logger.info("Buscando todos os produtos");
    List<ProdutoEntity> produtos = repository.findAll();
    logger.debug("Total de produtos: {}", produtos.size());
    return produtos;
}
```

---

## 🔗 Recursos Úteis

- **Spring Boot Docs**: https://spring.io/projects/spring-boot
- **Spring Data JPA**: https://spring.io/projects/spring-data-jpa
- **Lombok**: https://projectlombok.org/
- **Swagger/OpenAPI**: https://swagger.io/
- **RESTful API Guide**: https://restfulapi.net/

---

## 📝 Resumo do que foi Feito

### ✅ Arquivos Comentados
- [x] SpringBootStudApplication.java
- [x] ProdutoEntity.java
- [x] ProdutoDto.java
- [x] ProdutoService.java
- [x] ProdutoController.java
- [x] application.yaml

### ✅ Documentação
- [x] README.md completo
- [x] Explicação de cada conceito
- [x] Exemplos de uso
- [x] Dicas para outros projetos

### ✅ Funcionalidades Implementadas
- [x] API REST CRUD (Create, Read, Update, Delete)
- [x] 4 endpoints funcionais
- [x] Dados em memória (H2 em memory)
- [x] Swagger UI para testes
- [x] Padrão de camadas (Controller → Service → Entity)

---

## 🎓 Próximos Passos para Aprendizado

1. **Implementar Repository**: Trocar ArrayList por banco de dados real
2. **Adicionar Validações**: @Valid, @NotNull, @Positive
3. **Tratamento de Erros**: GlobalExceptionHandler
4. **Autenticação**: Spring Security
5. **Testes**: JUnit 5 e Mockito
6. **Logging**: SLF4J e Logback
7. **Docker**: Containerizar a aplicação
8. **CI/CD**: GitHub Actions ou Jenkins

---

## 📞 Dúvidas?

Revise os comentários em cada arquivo Java para entender:
- O que cada anotação faz
- Por que cada padrão foi usado
- Como alterar e estender o código

Boa sorte com seus próximos projetos! 🚀

#   s p r i n g - r e s t - a p i - s t u d y  
 