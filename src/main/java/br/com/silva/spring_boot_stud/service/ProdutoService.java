package br.com.silva.spring_boot_stud.service;

// Importações necessárias para esta service
import br.com.silva.spring_boot_stud.database.model.ProdutoEntity;  // Entidade que representa um Produto
import br.com.silva.spring_boot_stud.dto.ProdutoDto;               // DTO para transferência de dados
import br.com.silva.spring_boot_stud.exception.NotFoundException;
import org.springframework.stereotype.Service;                       // Anotação que marca como componente de negócio

// Importações para manipulação de dados numéricos e coleções
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service (Serviço) de Produtos
 * Esta classe contém toda a lógica de negócio relacionada a produtos
 * Services atuam como intermediários entre Controllers (requisições) e o banco de dados
 */
// Anotação que marca esta classe como um serviço Spring gerenciado pelo container
@Service
public class ProdutoService {

    /**
     * Lista estática que simula um banco de dados em memória
     * Em uma aplicação real, seria utilizado um verdadeiro banco de dados (MySQL, PostgreSQL, etc)
     * 'private static final' significa que é privada (não acessível de fora), estática (compartilhada por todas instâncias) e imutável (referência não pode mudar)
     */
    private static final List<ProdutoEntity> PRODUTOS = new ArrayList<>();

    /**
     * Bloco static (inicializador estático)
     * É executado uma única vez quando a classe é carregada
     * Aqui populamos a lista com dados de exemplo para testes
     */
    static {
        // Adiciona primeiro produto: Notebook com preço de 2000 reais e quantidade 10
        PRODUTOS.add(ProdutoEntity.builder()
                 .id(1)                                    // ID único do produto
                 .name("Notebook")                         // Nome do produto
                 .preco(new BigDecimal(2000))             // Preço usando BigDecimal para precisão monetária
                 .quantidade(10)                          // Quantidade em estoque
                 .build());                               // Constrói o objeto com o padrão Builder

        // Adiciona segundo produto: iPhone com preço de 7000 reais e quantidade 10
        PRODUTOS.add(ProdutoEntity.builder()
                .id(2)
                .name("iphone")
                .preco(new BigDecimal(7000))
                .quantidade(10)
                .build());

        // Adiciona terceiro produto: Mouse com preço de 500 reais e quantidade 10
        PRODUTOS.add(ProdutoEntity.builder()
                .id(3)
                .name("Mouse")
                .preco(new BigDecimal(500))
                .quantidade(10)
                .build());
    }

    /**
     * Método que busca TODOS os produtos
     * @return Lista com todos os produtos cadastrados
     */
    public List<ProdutoEntity> findAll() {
        // Retorna uma nova ArrayList (cópia) para evitar modificações externas na lista original
        return new ArrayList<>(PRODUTOS);
    }

    /**
     * Método que cria um novo produto
     * Recebe um DTO (dados do cliente) e cria a Entidade no banco de dados
     * @param produtoDto DTO contendo os dados do novo produto (name, quantidade, preco)
     * @return A ProdutoEntity criada com o ID gerado automaticamente
     */
    public ProdutoEntity createProdutos(ProdutoDto produtoDto) {

        /**
         * Lógica para gerar o novo ID:
         * 1. PRODUTOS.stream() converte a lista em um stream (sequência de dados processável)
         * 2. .mapToInt(ProdutoEntity::getId) extrai o ID de cada produto (referência de método)
         * 3. .max() encontra o maior ID
         * 4. .orElse(0) retorna 0 se não houver produtos
         * 5. + 1 incrementa o ID para gerar um novo identificador único
         */
        Integer identificador = PRODUTOS.stream()
                .mapToInt(ProdutoEntity::getId)
                .max()
                .orElse(0) + 1;

        /**
         * Cria um novo ProdutoEntity com os dados fornecidos no DTO
         * Usa o padrão Builder para construção clara e legível
         */
        ProdutoEntity novoProduto = ProdutoEntity.builder()
                .id(identificador)                    // Atribui o ID gerado automaticamente
                .name(produtoDto.getName())           // Copia o nome do DTO para a Entidade
                .preco(produtoDto.getPreco())         // Copia o preço do DTO para a Entidade
                .quantidade(produtoDto.getQuantidade())  // Copia a quantidade do DTO para a Entidade
                .build();                             // Constrói o objeto
        
        // Adiciona o novo produto à lista de produtos
        PRODUTOS.add(novoProduto);
        
        // Retorna o produto criado (geralmente a resposta HTTP contém o objeto criado)
        return novoProduto;
    }

    /**
     * Método que atualiza um produto existente
     * @param produtoDto DTO contendo os novos dados do produto
     * @param id ID do produto a ser atualizado
     * @return A ProdutoEntity atualizada
     * @throws RuntimeException se o produto não for encontrado
     */
    public ProdutoEntity atualizaProdutos(ProdutoDto produtoDto, Integer id) throws NotFoundException {
        /**
         * Busca o produto existente:
         * 1. PRODUTOS.stream() converte a lista em stream
         * 2. .filter(produto -> produto.getId().equals(id)) filtra apenas o produto com o ID especificado
         * 3. .findFirst() pega o primeiro (e único) resultado
         * 4. .orElseThrow() lança exceção se não encontrar
         */
        ProdutoEntity produtoExistente = PRODUTOS.stream()
                .filter(produto -> produto.getId().equals(id))  // Comparação do ID com equals (mais seguro que ==)
                .findFirst()                                     // Encontra o primeiro resultado
                .orElseThrow(() -> new NotFoundException ("Produto não encontrado"));  // Lança erro se não encontrar

        // Atualiza o nome do produto com o valor do DTO
        produtoExistente.setName(produtoDto.getName());
        
        // Atualiza o preço do produto com o valor do DTO
        produtoExistente.setPreco(produtoDto.getPreco());
        
        // Atualiza a quantidade do produto com o valor do DTO
        produtoExistente.setQuantidade(produtoDto.getQuantidade());

        // Retorna o produto atualizado
        return produtoExistente;
    }

    /**
     * Método que remove um produto pelo ID
     * @param id ID do produto a ser removido
     */
    public void removerProdutos(Integer id) {
        /**
         * Remove o produto da lista:
         * PRODUTOS.removeIf() remove todos os elementos que atendem a condição
         * produto -> produto.getId().equals(id) define a condição: remover se o ID bater
         */
        PRODUTOS.removeIf(produto -> produto.getId().equals(id));
    }
}
