package com.springboot.logistics.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity // anotação que define essa classe representa uma entidade que está relacionada
		// a uma tabela no banco de dados @Table(name = "tb_cliente") se a anotação
		// @Table com a propriedade name não for adicionada, será entendido que o nome
		// da tabela no banco é o mesmo nome da classe (cliente neste caso)
public class Cliente {

	@EqualsAndHashCode.Include
	@Id // anotação que define a chave primária (PK) da entidade
	@GeneratedValue(strategy = GenerationType.IDENTITY) // estratégia de geração da PK para usar a forma nativa do banco
														// de dados, que no caso do MySQL é auto_increment
	private Long id;

	// @Column(name = "nome") funciona exatamente como a anotação @Table só que para
	// as colunas da tabela (nesse caso também não é utilizada por que a coluna
	// possui exatamente o mesmo nome do atributo na classe)
	@NotBlank
	@Size(max = 60)
	private String nome;

	@NotBlank
	@Email
	@Size(max = 255)
	private String email;

	@NotBlank
	@Size(max = 20)
	@Column(name = "fone")
	private String telefone;

}
