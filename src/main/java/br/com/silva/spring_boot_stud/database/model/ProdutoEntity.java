package br.com.silva.spring_boot_stud.database.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoEntity {

    private Integer id;
    private String name;
    private Integer quantidade;
    private BigDecimal preco;

}