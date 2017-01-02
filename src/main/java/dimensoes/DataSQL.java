package dimensoes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import interfaces.Query;

/*
 * CREATE TABLE DataSQL (
	data_id number NOT NULL PRIMARY KEY,
	dia number NOT NULL,
	mes number NOT NULL,
	ano number NOT NULL,
	nome_mes varchar2(20) NOT NULL,
	datasql date NOT NULL,
	dia_semana varchar2(20) NOT NULL
);
 */
public class DataSQL implements Query {
	public static int dataID = 1;
	private final int id;
	
	private int dia;
	private int ano;
	
	private String nomeMes;
	private String diaSemana;

	public DataSQL(int dia, String nomeMes, int ano, String diaSemana) {
		this.dia = dia;
		this.ano = ano;
		this.nomeMes = nomeMes;
		this.diaSemana = diaSemana;
		
		id = dataID++;
	}
	
	public int getDataID() {
		return dataID;
	}
	public void setDataID(int dataID) {
		DataSQL.dataID = dataID;
	}

	public int getID() {
		return id;
	}
	
	public int getDia() {
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	
	public String getNomeMes() {
		return nomeMes;
	}
	public void setNomeMes(String nomeMes) {
		this.nomeMes = nomeMes;
	}

	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
	
	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO DATA (DATA_ID, DIA, MES, ANO, DATASQL, DIA_SEMANA) VALUES (" +
				this.id + "," + this.dia + "," + "'" + this.nomeMes + "," + "," + this.ano + "," + "null" + "," + "'" + this.diaSemana + "'" + ")";
		System.out.println("Query DataSQL: " + query);
		return query;
	}

	public static String deleteAllFromTableQuery() {
		return "DELETE * FROM " + DataSQL.class.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataSQL other = (DataSQL) obj;
		if (ano != other.ano)
			return false;
		if (dia != other.dia)
			return false;
		if (diaSemana == null) {
			if (other.diaSemana != null)
				return false;
		} else if (!diaSemana.equals(other.diaSemana))
			return false;
		if (nomeMes == null) {
			if (other.nomeMes != null)
				return false;
		} else if (!nomeMes.equals(other.nomeMes))
			return false;
		return true;
	}
}
