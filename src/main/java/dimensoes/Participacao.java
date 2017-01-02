package dimensoes;

import interfaces.Query;

/*
 * CREATE TABLE Participacao (
	participacao_id number NOT NULL PRIMARY KEY,
	filme_id number REFERENCES Filme(filme_id) ON DELETE CASCADE,
	funcao varchar2(30) NOT NULL,
	nome_profissional varchar2(30) NOT NULL
);
 */

public class Participacao implements Query {
	public static int participacaoID = 1;
	private final int id;

	private String nome_profissional;
	
	public Participacao(String nome_profissional) {
		this.nome_profissional = nome_profissional;
		
		id = participacaoID++;
	}

	public int getParticipaoID() {
		return participacaoID;
	}
	public void setParticipacaoID(int participacaoID) {
		Participacao.participacaoID = participacaoID;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO PARTICIPACAO (PARTICIPACAO_ID, NOME_PROFISSIONAL) VALUES (" +
				this.id  + "," + "'" + this.nome_profissional + "'" + ")";
		System.out.println("Query Participacao: " + query);
		return query;
	}

	public String deleteAllFromTableQuery() {
		return "DELETE * FROM " + Participacao.class.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participacao other = (Participacao) obj;
		if (nome_profissional == null) {
			if (other.nome_profissional != null)
				return false;
		} else if (!nome_profissional.equals(other.nome_profissional))
			return false;
		return true;
	}
}
