package br.com.silva.spring_boot_stud.controller;

// Importações necessárias para o Controller
import br.com.silva.spring_boot_stud.database.model.ProdutoEntity;  // Entidade de Produto
import br.com.silva.spring_boot_stud.dto.ProdutoDto;               // DTO para transferência de dados
import br.com.silva.spring_boot_stud.exception.NotFoundException;
import br.com.silva.spring_boot_stud.service.ProdutoService;       // Service com lógica de negócio
import lombok.RequiredArgsConstructor;                             // Gera construtor com dependências Lombok

// Importações de anotações Spring Web MVC para criar APIs REST
import org.springframework.http.HttpStatus;       // Códigos de status HTTP (200, 201, 204, etc)
import org.springframework.web.bind.annotation.DeleteMapping;    // Anotação para requisições DELETE
import org.springframework.web.bind.annotation.GetMapping;       // Anotação para requisições GET
import org.springframework.web.bind.annotation.PathVariable;     // Anotação para variáveis na URL
import org.springframework.web.bind.annotation.PostMapping;      // Anotação para requisições POST
import org.springframework.web.bind.annotation.PutMapping;       // Anotação para requisições PUT
import org.springframework.web.bind.annotation.RequestBody;      // Anotação para o corpo da requisição
import org.springframework.web.bind.annotation.RequestMapping;   // Anotação para rota base do controller
import org.springframework.web.bind.annotation.ResponseStatus;   // Anotação para definir status HTTP da resposta
import org.springframework.web.bind.annotation.RestController;   // Anotação que marca como Controller REST

import java.util.List;

/**
 * Controller REST responsável por gerenciar requisições HTTP relacionadas a Produtos
 * Controllers recebem requisições HTTP, as processam através de Services e retornam respostas
 */
// @RestController: Marca esta classe como um Controller REST (retorna JSON por padrão)
@RestController
// @RequestMapping("/v1/produtos"): Define a rota base para todos os endpoints deste controller
// Todos os endpoints abaixo serão acesso em /v1/produtos
@RequestMapping("/v1/produtos")
// @RequiredArgsConstructor: Anotação Lombok que gera construtor com os atributos final
// Permite injeção de dependência automática (ProdutoService é injetado aqui)
@RequiredArgsConstructor
public class ProdutoController {

    /**
     * Injeção de dependência do ProdutoService
     * 'final' significa que não pode ser alterado após inicialização
     * Spring injeta automaticamente uma instância de ProdutoService
     */
    private final ProdutoService produtoService;

    /**
     * ENDPOINT GET: Busca todos os produtos
     * URL: GET /v1/produtos
     * Retorna: Lista de todos os produtos cadastrados com status HTTP 200 (OK)
     * @return Lista contendo todos os ProdutoEntity cadastrados
     */
    // @GetMapping: Mapeia requisições GET para este método (sem sufixo significa rota raiz)
    @GetMapping
    // @ResponseStatus(HttpStatus.OK): Define que a resposta terá status HTTP 200 (OK)
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoEntity> findAll() {
        // Chama o método findAll() da Service para buscar todos os produtos
        return produtoService.findAll();
    }

    /**
     * ENDPOINT POST: Cria um novo produto
     * URL: POST /v1/produtos
     * Corpo da requisição: JSON com dados do produto (name, quantidade, preco)
     * Retorna: O produto criado com ID gerado automaticamente e status HTTP 201 (CREATED)
     * @param produtoDto DTO com os dados enviados na requisição
     * @return ProdutoEntity criada com ID gerado
     */
    // @PostMapping: Mapeia requisições POST para este método (cria novos recursos)
    @PostMapping
    // @ResponseStatus(HttpStatus.CREATED): Define que a resposta terá status HTTP 201 (CREATED)
    // Este status indica que um novo recurso foi criado com sucesso
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoEntity createdProduto(
        // @RequestBody: Indica que o JSON do corpo da requisição será desserializado para ProdutoDto
        @RequestBody ProdutoDto produtoDto) {
        // Chama o método createProdutos() da Service para criar novo produto
        return produtoService.createProdutos(produtoDto);
    }

    /**
     * ENDPOINT PUT: Atualiza um produto existente
     * URL: PUT /v1/produtos/{id}
     * Corpo da requisição: JSON com novos dados do produto (name, quantidade, preco)
     * Retorna: O produto atualizado e status HTTP 201 (CREATED)
     * @param id ID do produto a ser atualizado (extraído da URL)
     * @param produtoDto DTO com os novos dados do produto
     * @return ProdutoEntity atualizada
     */
    // @PutMapping("/{id}"): Mapeia requisições PUT com um ID na URL
    // O {id} é uma variável que será extraída da URL
    @PutMapping("/{id}")
    // @ResponseStatus(HttpStatus.CREATED): Status HTTP 201 para indicar atualização bem-sucedida
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoEntity updatedProduto(
        // @PathVariable Integer id: Extrai o valor de {id} da URL e converte para Integer
        @PathVariable Integer id,
        // @RequestBody: JSON do corpo da requisição é desserializado para ProdutoDto
        @RequestBody ProdutoDto produtoDto) throws NotFoundException {
        // Chama o método atualizaProdutos() da Service passando o DTO e o ID
        return produtoService.atualizaProdutos(produtoDto, id);
    }

    /**
     * ENDPOINT DELETE: Remove um produto pelo ID
     * URL: DELETE /v1/produtos/{id}
     * Retorna: Status HTTP 204 (NO CONTENT) indicando sucesso, sem corpo de resposta
     * @param id ID do produto a ser removido (extraído da URL)
     */
    // @DeleteMapping("/{id}"): Mapeia requisições DELETE com um ID na URL
    @DeleteMapping("/{id}")
    // @ResponseStatus(HttpStatus.NO_CONTENT): Status HTTP 204
    // 204 indica que a requisição foi bem-sucedida mas não há conteúdo para retornar
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProduto(
        // @PathVariable Integer id: Extrai o valor de {id} da URL e converte para Integer
        @PathVariable Integer id) {
        // Chama o método removerProdutos() da Service para remover o produto
        produtoService.removerProdutos(id);
    }

}
