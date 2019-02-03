package com.example.demo6;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Pelajar {
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

	
	@Column(unique = true)
	@NotEmpty(message = "Tidak boleh kosong")
	private String nama;
	
	private String jurusan;
	private int nilaiUn;
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	protected Pelajar() {
	}
	

	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getJurusan() {
		return jurusan;
	}

	public void setJurusan(String jurusan) {
		this.jurusan = jurusan;
	}

	public int getNilaiUn() {
		return nilaiUn;
	}

	public void setNilaiUn(int nilaiUn) {
		this.nilaiUn = nilaiUn;
	}

}
