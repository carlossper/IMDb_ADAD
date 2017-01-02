package dimensoes;

import interfaces.Query;

/*
 * CREATE TABLE Filme (
	filme_id number NOT NULL PRIMARY KEY,
	nome_filme varchar2(50) NOT NULL, 
	duracao number NOT NULL,
	ano number NOT NULL,
	estado varchar2(20) NOT NULL,
	popularidade float(5) NOT NULL,	
	orcamento number,
	receita number
);
 */

public class Filme implements Query {
	private static int filmeID = 1;
	private final int id;
	
	private String nome;
	private int duracao;
	private int ano;
	
	private String estado;
	private double popularidade;
	
	private int or�amento;
	private int receitas;
	
	public Filme(String nome, int duracao, int ano, String estado, double popularidade, int or�amento, int receitas) {
		this.nome = nome;
		this.duracao = duracao;
		this.ano = ano;
		this.estado = estado;
		this.popularidade = popularidade;
		this.or�amento = or�amento;
		this.receitas = receitas;
		
		id = filmeID++;
		
	}
	
	public int getFilmeID() {
		return filmeID;
	}
	public void setFilmeID(int filmeID) {
		Filme.filmeID = filmeID;
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
	
	public int getDuracao() {
		return duracao;
	}
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public int getOr�amento() {
		return or�amento;
	}
	public void setOr�amento(int or�amento) {
		this.or�amento = or�amento;
	}
	
	public int getReceitas() {
		return receitas;
	}
	public void setReceitas(int receitas) {
		this.receitas = receitas;
	}

	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO FILME (FILME_ID, NOME_FILME, DURACAO, ANO, ESTADO, POPULARIDADE, ORCAMENTO, RECEITA) VALUES (" + 
				id + "," + "'" + this.nome + "'" + "," + this.duracao  + "," + this.ano + "," + "'" + this.estado + "'" + "," + this.popularidade + "," + this.or�amento +"," + this.receitas + ")";
		System.out.println("Query Filme: " + query);
		return query;
	}

	public static String deleteAllFromTableQuery() {
		return "DELETE * FROM " + Filme.class.getName();
	}
}
