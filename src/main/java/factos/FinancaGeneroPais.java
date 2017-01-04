package factos;

import interfaces.Query;

public class FinancaGeneroPais implements Query {
	private int generoID;
	private int anoID;
	private int paisID;
	
	private long receita_total;
	private long orcamento_total;
	private long balanco;
	
	public FinancaGeneroPais(int generoID, int anoID, int paisID, long receita_total, long orcamento_total) {
		this.generoID = generoID;
		this.anoID = anoID;
		this.paisID = paisID;
		this.receita_total = receita_total;
		this.orcamento_total = orcamento_total;
		this.balanco = this.receita_total - this.orcamento_total;
	}

	public int getGeneroID() {
		return generoID;
	}
	public void setPaisID(int generoID) {
		this.generoID = generoID;
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
		String query = "INSERT INTO FINANCAGENEROPAIS(GENERO_ID,ANO_ID,PAIS_ID,RECEITA_TOTAL,ORCAMENTO_TOTAL,BALANCO) VALUES (" +
				this.generoID + "," + this.anoID + "," + this.paisID + "," + this.receita_total + "," + this.orcamento_total + "," + this.balanco + ")";
		System.out.println("Query FinancaGeneroPais: " + query);
		return query;
	}

	public static String deleteAllFromTableQuery() {
		return "TRUNCATE TABLE " + FinancaGeneroPais.class.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinancaGeneroPais other = (FinancaGeneroPais) obj;
		if (anoID != other.anoID)
			return false;
		if (generoID != other.generoID)
			return false;
		if (paisID != other.paisID)
			return false;
		return true;
	}

}
