package br.com.gfelix.app.entity;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Produtos {

    private String produto;
    private String categoria;
    private String codigo;
    private String valor;
    private String fornecedor;

}
