package dimensoes;

import interfaces.Query;

/*
 * CREATE TABLE Pais (
	pais_id number NOT NULL PRIMARY KEY,
	nome_pais varchar2(20) NOT NULL
);
 */

public class Pais implements Query {
	public static int paisID = 1;
	private final int id;
	
	private String nome;
	
	public Pais(String nome) {
		this.nome = nome;
		
		this.id = paisID++;
	}
	
	public int getPaisID() {
		return paisID;
	}
	public void setPaisID(int paisID) {
		Pais.paisID = paisID;
	}
	
	public int getID() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO PAIS (PAIS_ID, NOME_PAIS) VALUES (" +
				this.id + "," + "'" + this.nome + "'" + ")";
		System.out.println("Query Pais: " + query);
		return query;
	}
	
	public static String deleteAllFromTableQuery() {
		return "DELETE * FROM " + Pais.class.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
