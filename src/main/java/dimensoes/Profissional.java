package dimensoes;

import interfaces.Query;

/*
 * CREATE TABLE Profissional (
	profissional_id number NOT NULL PRIMARY KEY,
	nome_profissional varchar2(50) NOT NULL
);
 */

public class Profissional implements Query {
	public static int profissionalID = 1;
	private final int id;
	
	private String nome;
	
	public Profissional() {
		this.id = profissionalID++;
	}
	
	public Profissional(String nome) {
		this.nome = nome;
		
		this.id = profissionalID++;
	}
	
	public int getProfissionalID() {
		return profissionalID;
	}
	public void setProfissionalID(int profissionalID) {
		Profissional.profissionalID = profissionalID;
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
	
	public boolean equals(Profissional prof) {
		if(prof.getNome() == this.getNome()) return true;
		else return false;
	}

	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO PROFISSIONAL (PROFISSIONAL_ID, NOME_PROFISSIONAL) VALUES (" +
				this.id + "," + "'" + this.nome + "'" + ")";
		System.out.println("Query Profissional: " + query);
		return query;
	}

	public String deleteAllFromTableQuery() {
		return "DELETE * FROM " + Profissional.class.getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profissional other = (Profissional) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
