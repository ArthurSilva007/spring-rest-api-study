package br.com.silva.spring_boot_stud.dto;

// Importações das anotações Lombok para geração automática de código
import lombok.AllArgsConstructor;  // Gera construtor com todos os atributos como parâmetros
import lombok.Builder;             // Gera padrão Builder para construção fluida do objeto
import lombok.Getter;              // Gera métodos getters para todos os atributos
import lombok.NoArgsConstructor;   // Gera construtor sem parâmetros
import lombok.Setter;              // Gera métodos setters para todos os atributos
import lombok.ToString;            // Gera método toString customizado

// Importação do BigDecimal para trabalhar com valores monetários com precisão
import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) para transferência de dados de Produto
 * DTOs são usados para trafegar dados entre a API e o cliente (requisições/respostas HTTP)
 * Note que este DTO NÃO possui o atributo 'id' pois é gerado automaticamente pelo servidor
 */
// @Getter: Gera automaticamente métodos get para todos os atributos da classe
@Getter
// @Setter: Gera automaticamente métodos set para todos os atributos da classe
@Setter
// @ToString: Gera automaticamente método toString() com todos os atributos
@ToString
// @NoArgsConstructor: Gera construtor vazio (necessário para Jackson deserializar JSON)
@NoArgsConstructor
// @AllArgsConstructor: Gera construtor com parâmetro para cada atributo
@AllArgsConstructor
// @Builder: Gera padrão Builder para construção fluida (ex: ProdutoDto.builder().name("Notebook").build())
@Builder
public class ProdutoDto {

    // Nome/descrição do produto que será enviado na requisição HTTP (ex: "Notebook")
    private String name;
    
    // Quantidade de produtos a serem adicionados/atualizados
    private Integer quantidade;
    
    // Preço do produto em formato BigDecimal (melhor para valores monetários que double/float)
    private BigDecimal preco;

}