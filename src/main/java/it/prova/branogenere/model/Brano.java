package it.prova.branogenere.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "brano")

public class Brano {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "titolo")
	private String titolo;
	@Column(name = "autore")
	private String autore;
	@Column(name = "data_pubblicazione")
	private LocalDate dataPubblicazione;

	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "brano_genere", joinColumns = @JoinColumn(name = "id_brano"), inverseJoinColumns = @JoinColumn(name = "id_genere"))
	private Set<Genere> generi = new HashSet<>();

	public Brano() {

	}

	public Brano(Long id, String titolo, String autore, LocalDate dataPubblicazione) {
		this.id = id;
		this.titolo = titolo;
		this.autore = autore;
		this.dataPubblicazione = dataPubblicazione;
	}

	public Brano(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public LocalDate getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(LocalDate dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
	
	
	public Set<Genere> getGeneri(){
		return generi;
	}

	@Override
	public String toString() {
		return "Brano [id=" + id + ", titolo=" + titolo + ", autore=" + autore + ", dataPubblicazione="
				+ dataPubblicazione + "\n Generi associati=" + generi + "]";
	}

}