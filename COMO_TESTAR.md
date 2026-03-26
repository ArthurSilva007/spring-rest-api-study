# 🧪 Guia Prático de Testes - API de Produtos

## Como Testar a API de Produtos

Este documento mostra passo-a-passo como testar cada endpoint da API.

---

## 📌 Informação Importante

**Antes de testar**, certifique-se de que a aplicação está rodando:

```bash
mvn spring-boot:run
```

Ou execute diretamente da IDE. Você deve ver a mensagem:
```
Started SpringBootStudApplication in X.XXX seconds
```

---

## 🌐 Opção 1: Swagger UI (RECOMENDADO)

### O que é Swagger UI?
Interface visual interativa para testar APIs REST sem código.

### Como Acessar:
1. Abra o navegador
2. Acesse: **http://localhost:8081/swagger-ui.html**
3. Veja todos os endpoints documentados
4. Teste cada um clicando em "Try it out"

### Vantagens:
✅ Interface visual amigável
✅ Documentação automática
✅ Testa sem ferramentas externas
✅ Vê exemplos de Request e Response
✅ Gera código automaticamente

---

## 🔧 Opção 2: Postman ou Insomnia

### Instalação:
- **Postman**: https://www.postman.com/downloads/
- **Insomnia**: https://insomnia.rest/

### Passo a Passo para cada Endpoint:

### 1️⃣ GET - Listar Todos os Produtos

```
Método: GET
URL: http://localhost:8081/v1/produtos
Headers: (nenhum necessário)
Body: (vazio)
```

**Resposta Esperada (200 OK)**:
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
  },
  {
    "id": 3,
    "name": "Mouse",
    "quantidade": 10,
    "preco": 500
  }
]
```

---

### 2️⃣ POST - Criar Novo Produto

```
Método: POST
URL: http://localhost:8081/v1/produtos
Headers: Content-Type: application/json
Body: (veja abaixo)
```

**Body (JSON)**:
```json
{
  "name": "Monitor LG 24 polegadas",
  "quantidade": 5,
  "preco": 800
}
```

**Resposta Esperada (201 CREATED)**:
```json
{
  "id": 4,
  "name": "Monitor LG 24 polegadas",
  "quantidade": 5,
  "preco": 800
}
```

**⚠️ Nota**: Observe que:
- O `id` é gerado automaticamente (não envie na requisição)
- O `id` retorna na resposta
- Status HTTP é 201 (CREATED), não 200

---

### 3️⃣ PUT - Atualizar Produto Existente

```
Método: PUT
URL: http://localhost:8081/v1/produtos/1
Headers: Content-Type: application/json
Body: (veja abaixo)
```

**Body (JSON)** - Novo dados:
```json
{
  "name": "Notebook Gamer i9",
  "quantidade": 7,
  "preco": 5000
}
```

**Resposta Esperada (201 CREATED)**:
```json
{
  "id": 1,
  "name": "Notebook Gamer i9",
  "quantidade": 7,
  "preco": 5000
}
```

**⚠️ Nota**:
- Não mande `id` no body (está na URL: `/v1/produtos/1`)
- Apenas envie os campos que quer atualizar
- ID não muda (sempre será 1)

---

### 4️⃣ DELETE - Deletar Produto

```
Método: DELETE
URL: http://localhost:8081/v1/produtos/1
Headers: (nenhum necessário)
Body: (vazio)
```

**Resposta Esperada (204 NO CONTENT)**:
- Sem corpo de resposta
- Apenas status 204

**Para verificar se funcionou**:
- Faça um GET em `/v1/produtos`
- O produto com ID 1 não deve estar mais lá

---

## 💻 Opção 3: cURL (Linha de Comando)

### O que é cURL?
Ferramenta para fazer requisições HTTP via terminal/PowerShell.

### Passo a Passo:

#### 1️⃣ GET - Listar Todos

```powershell
curl http://localhost:8081/v1/produtos
```

#### 2️⃣ POST - Criar Novo

```powershell
curl -X POST http://localhost:8081/v1/produtos `
  -H "Content-Type: application/json" `
  -d '{
    "name": "Teclado Mecânico",
    "quantidade": 8,
    "preco": 450
  }'
```

#### 3️⃣ PUT - Atualizar

```powershell
curl -X PUT http://localhost:8081/v1/produtos/1 `
  -H "Content-Type: application/json" `
  -d '{
    "name": "Notebook Atualizado",
    "quantidade": 5,
    "preco": 3000
  }'
```

#### 4️⃣ DELETE - Deletar

```powershell
curl -X DELETE http://localhost:8081/v1/produtos/1
```

### Com Formatação Bonita (PowerShell):

Se instalar jq (https://stedolan.github.io/jq/):

```powershell
curl http://localhost:8081/v1/produtos | jq '.'
```

---

## 🧪 Sequência Completa de Testes

### Teste 1: Fluxo Completo (CRUD)

```
1. GET /v1/produtos
   └─> Vê produtos iniciais (Notebook, iPhone, Mouse)

2. POST /v1/produtos
   Body: { "name": "Monitor", "quantidade": 3, "preco": 800 }
   └─> Recebe ID 4

3. GET /v1/produtos
   └─> Agora tem 4 produtos (Monitor foi adicionado)

4. PUT /v1/produtos/4
   Body: { "name": "Monitor Samsung 4K", "quantidade": 2, "preco": 1200 }
   └─> Produto atualizado

5. DELETE /v1/produtos/4
   └─> Sucesso (204 No Content)

6. GET /v1/produtos
   └─> Monitor foi removido (3 produtos novamente)
```

---

## 🔴 Teste 2: Tratamento de Erros

### Tentar atualizar produto inexistente

```
PUT /v1/produtos/999
Body: { "name": "Teste", "quantidade": 1, "preco": 100 }
```

**Resposta Esperada**:
```
Erro 500 Internal Server Error
Mensagem: "Produto não encontrado"
```

Este é um erro esperado da aplicação.

---

## 📊 Checklist de Testes

### ✅ GET /v1/produtos
- [ ] Retorna lista vazia ou com produtos
- [ ] Status 200 OK
- [ ] Formato JSON correto
- [ ] Campos: id, name, quantidade, preco

### ✅ POST /v1/produtos
- [ ] Cria novo produto
- [ ] Retorna ID gerado automaticamente
- [ ] Status 201 CREATED
- [ ] ID novo é diferente dos anteriores

### ✅ PUT /v1/produtos/{id}
- [ ] Atualiza produto existente
- [ ] ID não muda
- [ ] Novos valores aparecem
- [ ] Status 201 CREATED
- [ ] Erro ao tentar atualizar ID inexistente

### ✅ DELETE /v1/produtos/{id}
- [ ] Remove produto
- [ ] Status 204 NO CONTENT
- [ ] Produto não aparece mais em GET
- [ ] Não retorna nenhum corpo

---

## 🔍 Verificando a Saúde da API

### 1. Verificar Porta
```powershell
netstat -ano | findstr :8081
```

Se retornar uma linha, porta está em uso (API rodando).

### 2. Testar Conectividade
```powershell
curl -v http://localhost:8081/v1/produtos
```

Se vir `< HTTP/1.1 200 OK`, conexão funciona.

### 3. Acessar Console H2
```
http://localhost:8081/h2-console
```

- JDBC URL: `jdbc:h2:mem:testdb`
- User: (deixe vazio)
- Password: (deixe vazio)

---

## 📝 Exemplos com Variações

### Exemplo 1: Produto com Preço Decimal

```json
{
  "name": "Webcam HD",
  "quantidade": 2,
  "preco": 199.99
}
```

✅ Funciona com BigDecimal

### Exemplo 2: Quantidade Negativa (Não Recomendado)

```json
{
  "name": "Produto Teste",
  "quantidade": -5,
  "preco": 100
}
```

⚠️ A API aceita (pois não há validação)
💡 Em produção, adicionar `@Positive` ou similar

### Exemplo 3: Nome Vazio

```json
{
  "name": "",
  "quantidade": 10,
  "preco": 500
}
```

⚠️ A API aceita (sem validação)
💡 Em produção, adicionar `@NotBlank`

---

## 🎯 Observações Importantes

### 1. Os dados são perdidos ao reiniciar
- A lista é carregada em memória (ArrayList)
- Ao parar a aplicação, dados são perdidos
- Só retêm para testes (sem persistência)

### 2. Não há autenticação
- Qualquer um pode acessar os endpoints
- Em produção, adicionar Spring Security

### 3. IDs são gerados sequencialmente
- Novo ID = (maior ID atual) + 1
- Se deletar ID 2, próximo novo será 4 (não 2)
- Esperado em ArrayList

### 4. Não há limite de requisições
- Em produção, adicionar rate limiting
- Sem proteção contra DoS (negação de serviço)

---

## 🐛 Troubleshooting

### Problema: Porta 8081 já está em uso

**Solução 1**: Parar aplicação que usa porta 8081

```powershell
# Encontrar processo na porta
netstat -ano | findstr :8081

# Matar processo (substitua PID)
taskkill /PID 1234 /F
```

**Solução 2**: Trocar porta em `application.yaml`

```yaml
server:
  port: 8082  # Mudou de 8081
```

---

### Problema: 404 Not Found

**Verificar**:
- URL está correta? (sem typos)
- Aplicação está rodando?
- Endpoint está implementado?

```powershell
# Teste URL básica
curl http://localhost:8081/v1/produtos
```

---

### Problema: 500 Internal Server Error ao atualizar/deletar

**Causa**: Produto com ID não existe

**Solução**:
1. Faça GET para ver IDs disponíveis
2. Use um ID que existe
3. Ou implemente validação melhor

---

### Problema: JSON inválido

**Erro**: 400 Bad Request

**Solução**:
- Verifique sintaxe JSON (colchetes, vírgulas, aspas)
- Use validador: https://jsonlint.com/
- Verifique tipos de dados (string vs number)

```json
// ❌ Errado (quantidade é string)
{ "name": "Produto", "quantidade": "10", "preco": 100 }

// ✅ Correto (quantidade é número)
{ "name": "Produto", "quantidade": 10, "preco": 100 }
```

---

## 📺 Visualização em Tempo Real

### H2 Console (Banco de Dados)

1. Acesse: http://localhost:8081/h2-console
2. Deixe padrão (testdb)
3. Execute SQL para ver dados

```sql
SELECT * FROM PRODUTO_ENTITY;
```

**Nota**: Neste projeto, H2 em memória não tem tabelas (usa ArrayList)

---

## 🎬 Vídeo Resumido dos Testes

Se fosse um vídeo, teria estes passos:

```
[0:00] Iniciar aplicação (mvn spring-boot:run)
[0:15] Abrir Swagger UI (http://localhost:8081/swagger-ui.html)
[0:30] Clicar em GET /v1/produtos
[0:45] Testar GET (ver 3 produtos iniciais)
[1:00] Clicar em POST /v1/produtos
[1:15] Adicionar novo produto no body
[1:30] Executar POST (ver status 201, novo produto com ID)
[1:45] Clicar em PUT /v1/produtos/{id}
[2:00] Atualizar dados do produto
[2:15] Executar PUT (ver dados atualizados)
[2:30] Clicar em DELETE /v1/produtos/{id}
[2:45] Executar DELETE (ver status 204)
[3:00] Fazer GET novamente (ver produto deletado)
[3:15] Conclusão: CRUD funciona perfeitamente!
```

---

## 🏆 Resumo

| Endpoint | Método | URL | Status |
|----------|--------|-----|--------|
| Listar | GET | /v1/produtos | 200 |
| Criar | POST | /v1/produtos | 201 |
| Atualizar | PUT | /v1/produtos/{id} | 201 |
| Deletar | DELETE | /v1/produtos/{id} | 204 |

Todos os endpoints **devem estar funcionando** após implementar os comentários!

---

Criado: 25/03/2026
Versão: 1.0

