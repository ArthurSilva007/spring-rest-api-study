# Guia de Referência Rápida - Spring Boot Produtos

## 📌 Referência Rápida de Anotações

### Anotações Spring Framework

```java
@SpringBootApplication      // Marca classe principal da aplicação
@RestController             // Controller que retorna JSON/XML
@Controller                 // Controller que retorna views HTML
@Service                    // Marca classe de serviço/negócio
@Repository                 // Marca classe de acesso a dados
@Component                  // Marca classe genérica do Spring
@Configuration              // Marca classe de configuração
@Bean                       // Define um bean gerenciado pelo Spring
```

### Anotações de Mapeamento de Requisições

```java
@RequestMapping("/caminho")  // Mapeia uma rota base
@GetMapping                  // Mapeia requisições GET (ler)
@PostMapping                 // Mapeia requisições POST (criar)
@PutMapping                  // Mapeia requisições PUT (atualizar)
@DeleteMapping               // Mapeia requisições DELETE (remover)
@PatchMapping                // Mapeia requisições PATCH (atualizar parcial)
```

### Anotações de Parâmetros

```java
@RequestBody                 // Mapeia corpo JSON para objeto Java
@PathVariable Integer id     // Extrai variável da URL (/v1/produtos/1)
@RequestParam String nome    // Extrai parâmetro query (?nome=valor)
@RequestHeader String token  // Extrai valor de header
```

### Anotações de Status HTTP

```java
@ResponseStatus(HttpStatus.OK)       // 200 - Sucesso geral
@ResponseStatus(HttpStatus.CREATED)  // 201 - Recurso criado
@ResponseStatus(HttpStatus.NO_CONTENT) // 204 - Sem conteúdo
@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 - Requisição inválida
@ResponseStatus(HttpStatus.NOT_FOUND)   // 404 - Não encontrado
```

### Anotações Lombok

```java
@Getter              // Gera getters para todos os atributos
@Setter              // Gera setters para todos os atributos
@NoArgsConstructor   // Gera construtor sem argumentos
@AllArgsConstructor  // Gera construtor com todos os atributos
@RequiredArgsConstructor // Gera construtor com atributos final
@Builder             // Implementa padrão Builder
@ToString            // Gera método toString()
@EqualsAndHashCode   // Gera equals() e hashCode()
@Data                // Combina @Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
```

---

## 📊 Códigos de Status HTTP

| Código | Nome | Uso |
|--------|------|-----|
| 200 | OK | Requisição bem-sucedida, retorna dados |
| 201 | CREATED | Novo recurso criado com sucesso |
| 204 | NO_CONTENT | Sucesso, sem dados para retornar |
| 400 | BAD_REQUEST | Requisição inválida (erro do cliente) |
| 401 | UNAUTHORIZED | Autenticação necessária |
| 403 | FORBIDDEN | Acesso negado |
| 404 | NOT_FOUND | Recurso não encontrado |
| 409 | CONFLICT | Conflito nos dados |
| 500 | INTERNAL_SERVER_ERROR | Erro no servidor |

---

## 🔍 Verbos HTTP vs Operações

| Método | Operação | Idempotente* | Usa Body |
|--------|----------|--------------|----------|
| GET | Ler | Sim | Não |
| POST | Criar | Não | Sim |
| PUT | Atualizar (completo) | Sim | Sim |
| DELETE | Deletar | Sim | Não |
| PATCH | Atualizar (parcial) | Não | Sim |

*Idempotente = múltiplas chamadas têm mesmo efeito que uma única chamada

---

## 💾 Estrutura de Resposta JSON

### Sucesso (GET todos)
```json
[
  {
    "id": 1,
    "name": "Notebook",
    "quantidade": 10,
    "preco": 2000
  }
]
```

### Sucesso (Criar/Atualizar)
```json
{
  "id": 1,
  "name": "Notebook",
  "quantidade": 10,
  "preco": 2000
}
```

### Sucesso (Deletar)
```
204 No Content (sem body)
```

### Erro
```json
{
  "timestamp": "2024-03-25T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Campo obrigatório faltando",
  "path": "/v1/produtos"
}
```

---

## 🔄 Fluxo da Requisição no Spring Boot

```
1. CLIENTE HTTP
   ↓
2. DispatcherServlet (Spring recebe requisição)
   ↓
3. Handler Mapping (procura qual @Controller/@RestController)
   ↓
4. @RestController/ProdutoController
   ↓
5. @GetMapping/@PostMapping (qual método chamar)
   ↓
6. ProdutoController.metodo()
   ↓
7. @Service/ProdutoService (lógica de negócio)
   ↓
8. Repository/Banco de Dados (acessa dados)
   ↓
9. Retorna dados
   ↓
10. Content Negotiation (converte para JSON)
    ↓
11. RESPOSTA HTTP ao CLIENTE
```

---

## 📝 Templates de Código

### Criar um novo Controller

```java
package br.com.silva.spring_boot_stud.controller;

import br.com.silva.spring_boot_stud.dto.MinhaDto;
import br.com.silva.spring_boot_stud.service.MinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/minhas-entidades")
@RequiredArgsConstructor
public class MinhaController {

    private final MinhaService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MinhaEntidade> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MinhaEntidade create(@RequestBody MinhaDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MinhaEntidade update(@PathVariable Integer id, @RequestBody MinhaDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
```

### Criar um novo Service

```java
package br.com.silva.spring_boot_stud.service;

import br.com.silva.spring_boot_stud.dto.MinhaDto;
import br.com.silva.spring_boot_stud.database.model.MinhaEntidade;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MinhaService {

    private static final List<MinhaEntidade> DADOS = new ArrayList<>();

    // Métodos CRUD aqui
}
```

### Criar um novo Entity

```java
package br.com.silva.spring_boot_stud.database.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinhaEntidade {

    private Integer id;
    private String nome;
    // adicione outros atributos
}
```

### Criar um novo DTO

```java
package br.com.silva.spring_boot_stud.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinhaDto {

    private String nome;
    // adicione outros atributos (sem id)
}
```

---

## 🚀 Comandos Maven Úteis

```bash
# Compilar o projeto
mvn clean compile

# Empacotar para JAR/WAR
mvn clean package

# Executar testes
mvn test

# Executar a aplicação
mvn spring-boot:run

# Ver dependências
mvn dependency:tree

# Atualizar dependências
mvn versions:update-properties

# Limpar arquivos compilados
mvn clean
```

---

## 🔌 Exemplo: Testando com cURL

```bash
# GET - Listar todos
curl http://localhost:8081/v1/produtos

# GET - Com filtro (se implementado)
curl "http://localhost:8081/v1/produtos?nome=Notebook"

# POST - Criar novo
curl -X POST http://localhost:8081/v1/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Monitor",
    "quantidade": 3,
    "preco": 800
  }'

# PUT - Atualizar
curl -X PUT http://localhost:8081/v1/produtos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Monitor Gamer",
    "quantidade": 2,
    "preco": 1200
  }'

# DELETE - Deletar
curl -X DELETE http://localhost:8081/v1/produtos/1

# Com saída formatada (requer jq)
curl http://localhost:8081/v1/produtos | jq '.'
```

---

## 📚 Estrutura Padrão de um Projeto Spring Boot

```
projeto/
│
├── src/
│   ├── main/
│   │   ├── java/br/com/empresa/projeto/
│   │   │   ├── ProjetoApplication.java       (CLASSE PRINCIPAL)
│   │   │   │
│   │   │   ├── controller/                   (API ENDPOINTS)
│   │   │   │   ├── UserController.java
│   │   │   │   └── ProductController.java
│   │   │   │
│   │   │   ├── service/                      (LÓGICA DE NEGÓCIO)
│   │   │   │   ├── UserService.java
│   │   │   │   └── ProductService.java
│   │   │   │
│   │   │   ├── repository/                   (ACESSO A DADOS)
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── ProductRepository.java
│   │   │   │
│   │   │   ├── database/model/               (ENTITIES JPA)
│   │   │   │   ├── UserEntity.java
│   │   │   │   └── ProductEntity.java
│   │   │   │
│   │   │   ├── dto/                          (DADOS PARA CLIENTE)
│   │   │   │   ├── UserDto.java
│   │   │   │   └── ProductDto.java
│   │   │   │
│   │   │   ├── exception/                    (EXCEÇÕES CUSTOMIZADAS)
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   │
│   │   │   ├── handler/                      (TRATAMENTO DE ERROS)
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │
│   │   │   ├── config/                       (CONFIGURAÇÕES)
│   │   │   │   └── SecurityConfig.java
│   │   │   │
│   │   │   └── utils/                        (UTILITÁRIOS)
│   │   │       └── DateUtil.java
│   │   │
│   │   └── resources/
│   │       ├── application.yaml              (CONFIGURAÇÕES PRINCIPAIS)
│   │       ├── application-dev.yaml          (CONFIG DESENVOLVIMENTO)
│   │       ├── application-prod.yaml         (CONFIG PRODUÇÃO)
│   │       ├── static/                       (CSS, JS, imagens)
│   │       └── templates/                    (HTML - se usar Thymeleaf)
│   │
│   └── test/
│       └── java/br/com/empresa/projeto/
│           ├── controller/
│           ├── service/
│           └── repository/
│
├── pom.xml                                   (DEPENDÊNCIAS)
├── README.md                                 (DOCUMENTAÇÃO)
└── .gitignore                                (GIT IGNORE)
```

---

## 🎯 Checklist para Novo Projeto Spring Boot

- [ ] Criar classe `ProjetoApplication` com `@SpringBootApplication`
- [ ] Configurar `application.yaml` (porta, banco, logging)
- [ ] Criar estrutura de pastas (controller, service, etc)
- [ ] Criar Entity com `@Getter @Setter @Builder` etc
- [ ] Criar DTO correspondente (sem ID)
- [ ] Criar Repository estendendo `JpaRepository`
- [ ] Criar Service com lógica de negócio
- [ ] Criar Controller com endpoints CRUD
- [ ] Testar endpoints via Swagger UI ou cURL
- [ ] Adicionar validações (@Valid, @NotNull, etc)
- [ ] Implementar tratamento de erros (GlobalExceptionHandler)
- [ ] Documentar código com comentários
- [ ] Escrever testes unitários

---

## 🔗 Links Importantes

| Recurso | URL |
|---------|-----|
| Spring Boot Docs | https://spring.io/projects/spring-boot |
| Spring Data JPA | https://spring.io/projects/spring-data-jpa |
| Lombok | https://projectlombok.org/ |
| Swagger/OpenAPI | https://swagger.io/ |
| Jakarta EE | https://jakarta.ee/ |
| Maven | https://maven.apache.org/ |

---

## 💡 Dicas Importantes

### 1. Sempre use `equals()` para comparar
```java
// ❌ Errado
if (produto.getId() == id) { }

// ✅ Correto
if (produto.getId().equals(id)) { }
```

### 2. Use Optional para evitar NullPointerException
```java
// ❌ Perigoso
return PRODUTOS.stream().findFirst().get();

// ✅ Seguro
return PRODUTOS.stream().findFirst().orElseThrow();
```

### 3. Crie cópias de listas para evitar modificações externas
```java
// ❌ Expõe lista original
return PRODUTOS;

// ✅ Retorna cópia segura
return new ArrayList<>(PRODUTOS);
```

### 4. Use constantes para valores fixos
```java
// ❌ Usar valores mágicos
if (status == 1) { }

// ✅ Usar constantes
private static final Integer ATIVO = 1;
if (status == ATIVO) { }
```

### 5. Nomeie variáveis claramente
```java
// ❌ Confuso
Integer x = 100;
String s = "produto";

// ✅ Claro
Integer quantidade = 100;
String nomeProduto = "produto";
```

---

Última atualização: 25/03/2026

