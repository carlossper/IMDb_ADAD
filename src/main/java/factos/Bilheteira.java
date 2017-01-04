package factos;

import dimensoes.Participacao;
import interfaces.Query;

/*
 * CREATE TABLE Bilheteira (
	participacao_id number REFERENCES Participacao(participacao_id) ON DELETE CASCADE,
	orcamento number,
	receita number
);
 */

public class Bilheteira implements Query {
	private int participacaogrupoID;
	private int filmeID;
	
	private long orcamento;
	private long receita;
	
	public Bilheteira(int participacaogrupoID, int filmeID, long orcamento, long receita) {
		this.participacaogrupoID = participacaogrupoID;
		this.filmeID = filmeID;
		
		this.orcamento = orcamento;
		this.receita = receita;
	}

	public int getParticipacaoGrupoID() {
		return participacaogrupoID;
	}
	public void setParticipacaoID(int participacaogrupoID) {
		this.participacaogrupoID = participacaogrupoID;
	}
	
	public long getOrcamento() {
		return orcamento;
	}
	public void setOrcamento(long orcamento) {
		this.orcamento = orcamento;
	}
	
	public long getReceita() {
		return receita;
	}
	public void setReceita(long receita) {
		this.receita = receita;
	}

	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO BILHETEIRA (PARTICIPACAOGRUPO_ID, FILME_ID, ORCAMENTO, RECEITA) VALUES (" + 
				this.participacaogrupoID + "," + this.filmeID + "," + this.orcamento  + "," + this.receita + ")";
		System.out.println("Query Bilheteira: " + query);
		return query;
	}
	
	public String deleteAllFromTableQuery() {
		return "TRUNCATE TABLE " + Bilheteira.class.getName();
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bilheteira other = (Bilheteira) obj;
		if (orcamento != other.orcamento)
			return false;
		if (participacaogrupoID != other.participacaogrupoID)
			return false;
		if (receita != other.receita)
			return false;
		return true;
	}

}
