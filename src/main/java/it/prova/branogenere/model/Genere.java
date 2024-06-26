package it.prova.branogenere.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "genere")

public class Genere {

	@ManyToMany(mappedBy = "generi", fetch = FetchType.LAZY)
	private Set<Brano> brani = new HashSet<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descrizione")
	private String descrizione;

	public Genere() {

	}

	public Genere(Long id, String descrizione) {
		this.id = id;
		this.descrizione = descrizione;
	}

	public Genere(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Brano> getBrani() {
		return brani;
	}

	@Override
	public String toString() {
		return "Genere [id=" + id + ", descrizione=" + descrizione + "]";
	}

}