package factos;

import interfaces.Query;

/*
 * CREATE TABLE Ratings (
	valor float(5) NOT NULL,
	contagem number NOT NULL,
	dia_lancamento_id number REFERENCES DataSQL(data_id) ON DELETE CASCADE,
  filme_id number REFERENCES Filme(filme_id) ON DELETE CASCADE
);
 */

public class Ratings implements Query {
	// Foreign Keys
	private int datasqlID;
	private int filmeID;
	
	private double valor;
	private int contagem;
	
	public Ratings(int datasqlID, int filmeID, double valor, int contagem) {
		this.setDatasqlID(datasqlID);
		this.setFilmeID(filmeID);
		this.valor = valor;
		this.setContagem(contagem);
	}	

	public int getFilmeID() {
		return filmeID;
	}
	public void setFilmeID(int filmeID) {
		this.filmeID = filmeID;
	}

	public int getDatasqlID() {
		return datasqlID;
	}
	public void setDatasqlID(int datasqlID) {
		this.datasqlID = datasqlID;
	}

	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getContagem() {
		return contagem;
	}
	public void setContagem(int contagem) {
		this.contagem = contagem;
	}

	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO RATINGS (DATA_ID, FILME_ID, VALOR, CONTAGEM) VALUES (" +
				this.datasqlID + "," + this.filmeID + "," + this.valor + "," + this.contagem + ")";
		System.out.println("Query Ratings: " + query);
		return query;
	}
	
	public static String deleteAllFromTableQuery() {
		return "TRUNCATE TABLE " + Ratings.class.getName();
	}
}
