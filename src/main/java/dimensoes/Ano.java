package dimensoes;

import factos.Ratings;
import interfaces.Query;

public class Ano implements Query {
	public static int anoID = 1;
	private final int id;
	
	private int ano;
	
	public Ano(int ano) {
		this.ano = ano;
		
		this.id = anoID++;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public int getAno() {
		return ano;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO ANO (ANO_ID, ANO) VALUES (" +
				this.id + "," + this.ano + ")";
		System.out.println("Query Ratings: " + query);
		return query;
	}
	
	public static String deleteAllFromTableQuery() {
		return "DELETE * FROM " + Ano.class.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ano other = (Ano) obj;
		if (ano != other.ano)
			return false;
		return true;
	}
}
