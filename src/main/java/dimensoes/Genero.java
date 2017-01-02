package dimensoes;

import interfaces.Query;

/*
 * CREATE TABLE Genero (
	genero_id number NOT NULL PRIMARY KEY,
	nome varchar2(20) NOT NULL
);
 */

public class Genero implements Query {
	public static int generoID = 1;
	private final int id;
	
	private String nome;
	
	public Genero(String nome) {
		this.nome = nome;
		
		this.id = generoID++;
	}
	
	public void setGeneroID(int id) {
		Genero.generoID = id;
	}
	public int getGeneroID() {
		return generoID;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO GENERO (GENERO_ID, NOME) VALUES (" +
				this.id + "," + "'" + this.nome + "'" + ")";
		System.out.println("Query Genero: " + query);
		return query;
	}
	
	public static String deleteAllFromTableQuery() {
		return "DELETE * FROM " + Genero.class.getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genero other = (Genero) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
