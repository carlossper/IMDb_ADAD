package factos;

import dimensoes.Ano;
import interfaces.Query;

public class AcumuladoProfissional implements Query {
	private int anoID;
	private String nome_profissional;
	
	private long receita_total, orcamento_total, balanco, media_balancofilmes;
	private int nr_filmes;
	
	public AcumuladoProfissional(int anoID, String nome_profissional, long orcamento, long receita) {
		this.anoID = anoID;
		this.nome_profissional = nome_profissional.replace("'"," ");
		this.receita_total = receita;
		this.orcamento_total = orcamento;
		this.balanco = this.receita_total - this.orcamento_total;
		
		this.nr_filmes = 1;
		
		this.media_balancofilmes = this.balanco / this.nr_filmes;
	}
	
	public int getAnoID() {
		return anoID;
	}

	public void setAnoID(int anoID) {
		this.anoID = anoID;
	}

	public String getNome_profissional() {
		return nome_profissional;
	}

	public void setNome_profissional(String nome_profissional) {
		this.nome_profissional = nome_profissional;
	}

	public long getReceita_total() {
		return receita_total;
	}

	public void setReceita_total(long receita_total) {
		this.receita_total = receita_total;
	}

	public long getOrcamento_total() {
		return orcamento_total;
	}

	public void setOrcamento_total(long orcamento_total) {
		this.orcamento_total = orcamento_total;
	}

	public long getBalanco() {
		return balanco;
	}

	public void setBalanco(long balanco) {
		this.balanco = balanco;
	}

	public long getMedia_balancofilmes() {
		return media_balancofilmes;
	}

	public void setMedia_balancofilmes(long media_balancofilmes) {
		this.media_balancofilmes = media_balancofilmes;
	}

	public int getNr_filmes() {
		return nr_filmes;
	}

	public void setNr_filmes(int nr_filmes) {
		this.nr_filmes = nr_filmes;
	}

	public void updateFinanca(long orcamento, long receita) {
		this.orcamento_total += orcamento;
		this.receita_total += receita;
		
		this.balanco = this.receita_total - this.orcamento_total;
		
		this.nr_filmes++;
		
		this.media_balancofilmes = this.balanco / this.nr_filmes;
	}
	
	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO ACUMULADOPROFISSIONAL (ANO_ID,NOME_PROFISSIONAL,RECEITA_TOTAL,ORCAMENTO_TOTAL,BALANCO,NUMERO_FILMES,MEDIA_BALANCOFILMES) VALUES (" +
				this.anoID + "," + "'" + this.nome_profissional + "'" + "," + this.receita_total + "," + this.orcamento_total + "," + this.balanco + "," + this.nr_filmes + "," + this.media_balancofilmes + ")";
		System.out.println("Query AcumuladoProfissional: " + query);
		return query;
	}
	
	public static String deleteAllFromTableQuery() {
		return "TRUNCATE TABLE " + AcumuladoProfissional.class.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcumuladoProfissional other = (AcumuladoProfissional) obj;
		if (anoID != other.anoID)
			return false;
		if (nome_profissional == null) {
			if (other.nome_profissional != null)
				return false;
		} else if (!nome_profissional.equals(other.nome_profissional))
			return false;
		return true;
	}

}
