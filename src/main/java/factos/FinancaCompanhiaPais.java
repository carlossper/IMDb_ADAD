package factos;

import interfaces.Query;

/*
 * CREATE TABLE FilmePaisAno (
	pais_id number REFERENCES Pais(pais_id) ON DELETE CASCADE,
	dia_lancamento_id number REFERENCES DataSQL(data_id) ON DELETE CASCADE,
	filme_id number REFERENCES Filme(filme_id) ON DELETE CASCADE,
	receita_total number,
	orcamento_total number,
	balanco number
);
 */

public class FinancaCompanhiaPais implements Query {
	private int paisID;
	private int anoID;
	
	private long receita_total;
	private long orcamento_total;
	private long balanco;
	
	private String nome_companhia;

	public FinancaCompanhiaPais(int paisID, int anoID, long receita_total, long orcamento_total, String nome_companhia) {
		this.paisID = paisID;
		this.anoID = anoID;
		this.receita_total = receita_total;
		this.orcamento_total = orcamento_total;
		this.balanco = this.receita_total - this.orcamento_total;
		
		this.nome_companhia = nome_companhia;
	}
	
	public int getPaisID() {
		return paisID;
	}
	public void setPaisID(int paisID) {
		this.paisID = paisID;
	}

	public int getAnoID() {
		return anoID;
	}
	public void setAnoID(int anoID) {
		this.anoID = anoID;
	}

	public long getReceitaTotal() {
		return receita_total;
	}
	public void setReceitaTotal(long receita_total) {
		this.receita_total = receita_total;
	}

	public long getOrcamentoTotal() {
		return orcamento_total;
	}
	public void setOrcamentoTotal(long orcamento_total) {
		this.orcamento_total = orcamento_total;
	}

	public long getBalanco() {
		return balanco;
	}
	public void setBalanco(long balanco) {
		this.balanco = balanco;
	}
	public void updateFinanca(long orcamento, long receita) {
		this.receita_total += receita;
		this.orcamento_total += receita;
		
		this.balanco = this.receita_total - this.orcamento_total;
	}
	
	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO FINANCACOMPANHIAPAIS(DATA_ID,PAIS_ID,NOME_COMPANHIA,RECEITA_TOTAL,ORCAMENTO_TOTAL,BALANCO) VALUES (" +
				this.anoID + "," + this.paisID + "," + "'" + this.nome_companhia + "'" + "," + this.receita_total + "," + this.orcamento_total + "," + this.balanco + ")";
		System.out.println("Query FinancaCompanhiaPais: " + query);
		return query;
	}

	public static String deleteAllFromTableQuery() {
		return "DELETE * FROM " + FinancaCompanhiaPais.class.getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinancaCompanhiaPais other = (FinancaCompanhiaPais) obj;
		if (anoID != other.anoID)
			return false;
		if (nome_companhia == null) {
			if (other.nome_companhia != null)
				return false;
		} else if (!nome_companhia.equals(other.nome_companhia))
			return false;
		if (paisID != other.paisID)
			return false;
		return true;
	}
}
