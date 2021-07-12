package br.com.caelum.carangobom.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Brand {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String name;

	public Brand(String nome) {
		this(null, nome);
	}

	public Brand(Long id, String nome) {
		this.id = id;
		this.name = nome;
	}

}
