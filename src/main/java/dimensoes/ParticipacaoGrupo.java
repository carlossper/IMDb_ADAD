package dimensoes;

import interfaces.Query;

public class ParticipacaoGrupo implements Query {
	private final int id;
	
	private int participacaoID;
	
	public ParticipacaoGrupo(int id, int participacaoID) {
		this.setParticipacaoID(participacaoID);
		this.id = id;
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
		String query = "INSERT INTO PARTICIPACAOGRUPO(PARTICIPACAOGRUPO_ID, PARTICIPACAO_ID) VALUES (" +
				this.id + "," + this.participacaoID + ")";
		System.out.println(query);
		return query;
	}
	
	public String deleteAllFromTableQuery() {
		return "TRUNCATE TABLE " + ParticipacaoGrupo.class.getName();
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
		if (id != other.id)
			return false;
		if (participacaoID != other.participacaoID)
			return false;
		return true;
	}
	
}
