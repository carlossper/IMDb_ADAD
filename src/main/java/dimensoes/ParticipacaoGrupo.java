package dimensoes;

import factos.Bilheteira;
import interfaces.Query;

public class ParticipacaoGrupo implements Query {
	public static int participacaoGrupoID = 1;
	private final int id;
	
	private int participacaoID;
	private String funcao;
	
	public ParticipacaoGrupo(int participacaoID, String funcao) {
		this.setParticipacaoID(participacaoID);
		this.funcao = funcao;
		
		id = participacaoGrupoID++;
	}
	
	public int getParticipacaoID() {
		return participacaoID;
	}
	public void setParticipacaoID(int participacaoID) {
		this.participacaoID = participacaoID;
	}
	
	public int getID() {
		return id;
	}

	@Override
	public String getInsertQuery() {
		String query = "INSERT INTO PARTICIPACAOGRUPO(PARTICIPACAOGRUPO_ID, PARTICIPACAO_ID, FUNCAO) VALUES (" +
				this.id + "," + this.participacaoID + "," + "'" + this.funcao + "'" + ")";
		System.out.println(query);
		return query;
	}
	
	public String deleteAllFromTableQuery() {
		return "DELETE * FROM " + ParticipacaoGrupo.class.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParticipacaoGrupo other = (ParticipacaoGrupo) obj;
		if (funcao == null) {
			if (other.funcao != null)
				return false;
		} else if (!funcao.equals(other.funcao))
			return false;
		if (participacaoID != other.participacaoID)
			return false;
		return true;
	}

}
