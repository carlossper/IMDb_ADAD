package movies;

import java.io.IOException;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.CastMember;
import com.uwetrottmann.tmdb2.entities.Credits;
import com.uwetrottmann.tmdb2.entities.CrewMember;
import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.entities.ProductionCompany;
import com.uwetrottmann.tmdb2.entities.ProductionCountry;
import com.uwetrottmann.tmdb2.services.MoviesService;

import dimensoes.DataSQL;
import dimensoes.Filme;
import dimensoes.Genero;
import dimensoes.Pais;
import dimensoes.Participacao;
import dimensoes.ParticipacaoGrupo;
import dimensoes.Profissional;
import factos.Bilheteira;
import factos.FinancaCompanhiaPais;
import factos.FinancaGeneroPais;
import factos.Ratings;
import retrofit2.Call;

public class MovieQueries {
	private static OracleConnection oracleConnection;
	private static MoviesService moviesService;
	
	private static List<Filme> filmes = new ArrayList<Filme>();
	private static List<Profissional> profissionais = new ArrayList<Profissional>();
	private static List<Pais> paises = new ArrayList<Pais>();
	private static List<Genero> generos = new ArrayList<Genero>();
	
	private static List<Participacao> participacoes = new ArrayList<Participacao>();
	private static List<ParticipacaoGrupo> partsGrupo  = new ArrayList<ParticipacaoGrupo>();
	
	private static List<Bilheteira> bilheteiras = new ArrayList<Bilheteira>();
	
	private static List<DataSQL> datas = new ArrayList<DataSQL>();
	
	private static List<Ratings> ratings = new ArrayList<Ratings>();
	
	private static List<FinancaCompanhiaPais> fcp = new ArrayList<FinancaCompanhiaPais>();
	private static List<FinancaGeneroPais> fgp = new ArrayList<FinancaGeneroPais>();
	
	private static final int NUMBER_OF_PAGES = 1;
	
	public static void main(String[] args) throws Exception {	
		Tmdb tmdb = new Tmdb("0b97edab69a564ad5ce4847142659d80");
		moviesService = tmdb.moviesService();
		
		getDataFromTMDBApi(); 
		
		for(Filme filme : filmes) 
			filme.getInsertQuery();
		
		for(Profissional prof : profissionais)
			prof.getInsertQuery();
		
		for(Pais pais : paises) 
			pais.getInsertQuery();
		
		for(Genero genero : generos) 
			genero.getInsertQuery();
		
		for(Participacao part : participacoes)
			part.getInsertQuery();
		
		for(ParticipacaoGrupo partGrupo : partsGrupo) 
			partGrupo.getInsertQuery();
		
		for(Bilheteira bilh : bilheteiras) 
			bilh.getInsertQuery();
		
		for(Ratings rating : ratings) 
			rating.getInsertQuery();
		
		for(FinancaCompanhiaPais fcpObject : fcp) 
			fcpObject.getInsertQuery();
		
		for(FinancaGeneroPais fgpObject : fgp)
			fgpObject.getInsertQuery();
		
		saveDataToOracle();
	}

	private static void saveDataToOracle() throws Exception, SQLException {
		oracleConnection = new OracleConnection();
		
		// execute queries
		
		for(Filme filme : filmes)
			oracleConnection.executeQuery(filme.getInsertQuery());
		
		for(Profissional prof : profissionais)
			oracleConnection.executeQuery(prof.getInsertQuery());
		
		for(Pais pais : paises) 
			oracleConnection.executeQuery(pais.getInsertQuery());
		
		for(Genero genero : generos) 
			oracleConnection.executeQuery(genero.getInsertQuery());
		
		for(Participacao part : participacoes)
			oracleConnection.executeQuery(part.getInsertQuery());
		
		for(ParticipacaoGrupo partGrupo : partsGrupo) 
			oracleConnection.executeQuery(partGrupo.getInsertQuery());
		
		for(Bilheteira bilh : bilheteiras) 
			oracleConnection.executeQuery(bilh.getInsertQuery());
		
		for(Ratings rating : ratings) 
			oracleConnection.executeQuery(rating.getInsertQuery());
		
		for(FinancaCompanhiaPais fcpObject : fcp) 
			oracleConnection.executeQuery(fcpObject.getInsertQuery());
		
		for(FinancaGeneroPais fgpObject : fgp)
			oracleConnection.executeQuery(fgpObject.getInsertQuery());		
		
		oracleConnection.closeConnection();
	}
	
	private static void getDataFromResultsPage(MovieResultsPage page) throws InterruptedException, IOException {
		List<Movie> movies = page.results;
		
		Filme filme;
		List<CastMember> castList;
		List<CrewMember> crewList;
		List<ProductionCountry> countries;
		List<Genre> genres;
		List<ProductionCompany> companies;
		
		Profissional profissional;
		Pais pais;
		Genero genero;
		
		Participacao participacao;
		ParticipacaoGrupo partGrupo;
		
		Bilheteira bilheteira;
		
		DataSQL data;
		Ratings rating;
		
		FinancaCompanhiaPais fcpObject;
		FinancaGeneroPais fgpObject;
		
		for(Movie movie : movies) {
			castList = new ArrayList<CastMember>();
			crewList = new ArrayList<CrewMember>();
			
			int id = movie.id;
			System.out.println("Movie ID: " + id);
	        
	        Call<Movie> callMovieMovies = moviesService.summary(id,"en",null);
	        Movie mov = callMovieMovies.execute().body();
	        
			String nome, estado;
			double popularidade;
			int duracao, ano, orcamento, receitas;			
			String dataString, nomeMes, diaSemana;
			int dia;
			
			String[] dataArray;
			
			nome = mov.original_title;
			estado = mov.status.value;
			orcamento = mov.budget;
			receitas = mov.revenue;
			popularidade = mov.popularity;
			duracao = mov.runtime;
			
			dataString = mov.release_date.toString();
			dataArray = dataString.split(" ");
			
			ano = Integer.parseInt(dataArray[5]);
			nomeMes = dataArray[1];
			diaSemana = dataArray[0];
			dia = Integer.parseInt(dataArray[2]);
			
			data = new DataSQL(dia,nomeMes,ano,diaSemana);
			int dataID = data.getID();
			
			boolean found = false;
			
        	for(int i = 0; i<datas.size(); i++) {
        		if(datas.get(i).equals(data)) {
        			DataSQL.dataID--;
        			dataID = datas.get(i).getID();
        			found = true;
        			break;
        		}
        	}
        	
        	if(!found) 
        		datas.add(data);
        	else 
        		found = false;
			
			System.out.println("Release date: " + dataString);
			
			filme = new Filme(nome,duracao,ano,estado,popularidade,orcamento,receitas);
			//filme.getInsertQuery();
			filmes.add(filme);
			
			rating = new Ratings(dataID,filme.getID(),mov.vote_average,mov.vote_count);
			ratings.add(rating);
	        
			Call<Credits> callMovieCredits = moviesService.credits(id);
	        Credits credits = callMovieCredits.execute().body();
	        
	        castList = credits.cast;
	        
	        for(CastMember person : castList) {	        	
	        	profissional = new Profissional(person.name);
	        	if(!profissionais.contains(profissional))
	        		profissionais.add(profissional);
	        	else
	        		Profissional.profissionalID--;
	        	
	        	found = false;
	        	
	        	participacao = new Participacao(person.name);
	        	int partID = participacao.getID();
	        	for(int i = 0; i<participacoes.size(); i++) {
	        		if(participacoes.get(i).equals(participacao)) {
	        			Participacao.participacaoID--;
	        			partID = participacoes.get(i).getID();
	        			found = true;
	        			break;
	        		}
	        	}
	        	
	        	if(!found) 
	        		participacoes.add(participacao);
	        	else 
	        		found = false;
	        	
	        	partGrupo = new ParticipacaoGrupo(partID,"Actor");
	        	int partGrupoID = partGrupo.getID();
	        	for(int i = 0; i<partsGrupo.size(); i++) {
	        		if(partsGrupo.get(i).equals(partGrupo)) {
	        			ParticipacaoGrupo.participacaoGrupoID--;
	        			partGrupoID = partsGrupo.get(i).getID();
	        			found = true;
	        			break;
	        		}
	        	}
	        	
	        	if(!found) 
	        		partsGrupo.add(partGrupo);
	        	else 
	        		found = false;
	        	
	        	bilheteira = new Bilheteira(partGrupoID, orcamento, receitas);
	        	
	        	if(!bilheteiras.contains(bilheteira))
	        		bilheteiras.add(bilheteira);
	        	
	        }
	        
	        crewList = credits.crew;
	        
	        for(CrewMember person : crewList) {	        	
	        	profissional = new Profissional(person.name);
	        	if(!profissionais.contains(profissional)) 
	        		profissionais.add(profissional);
	        	else 
	        		Profissional.profissionalID--;
	        	
	        	found = false;
	        	
	        	participacao = new Participacao(person.name);
	        	int partID = participacao.getID();
	        	for(int i = 0; i<participacoes.size(); i++) {
	        		if(participacoes.get(i).equals(participacao)) {
	        			Participacao.participacaoID--;
	        			partID = participacoes.get(i).getID();
	        			found = true;
	        			break;
	        		}
	        	}
	        	
	        	if(!found) 
	        		participacoes.add(participacao);
	        	else 
	        		found = false;
	        	
	        	partGrupo = new ParticipacaoGrupo(partID,person.job);
	        	int partGrupoID = partGrupo.getID();
	        	for(int i = 0; i<partsGrupo.size(); i++) {
	        		if(partsGrupo.get(i).equals(partGrupo)) {
	        			ParticipacaoGrupo.participacaoGrupoID--;
	        			partGrupoID = partsGrupo.get(i).getID();
	        			found = true;
	        			break;
	        		}
	        	}
	        	
	        	if(!found) 
	        		partsGrupo.add(partGrupo);
	        	else 
	        		found = false;
	        	
	        	bilheteira = new Bilheteira(partGrupoID, orcamento, receitas);
	        	
	        	if(!bilheteiras.contains(bilheteira))
	        		bilheteiras.add(bilheteira);
	        }
	        
	        countries = mov.production_countries;
	        int paisID;
	        
	        int generoID;
	        genres = mov.genres;
        	
	        for(Genre genre : genres) {
	        	genero = new Genero(genre.name);
	        	generoID = genero.getID();
	        	for(int i=0; i<generos.size(); i++) {
	        		if(generos.get(i).equals(genero)) {
	        			Genero.generoID--;
	        			generoID = generos.get(i).getID();
	        			found = true;
	        			break;
	        		}
	        	}
	        	
	        	if(!found)
	        		generos.add(genero);
	        	
	        	found = false;
	        	
	        	fgpObject = new FinancaGeneroPais(generoID,dataID,filme.getID(),mov.budget,mov.revenue);

	        	if(!fgp.contains(fgpObject))
	        		fgp.add(fgpObject);
	        }
	        
	        for(ProductionCountry country : countries) {
	        	pais = new Pais(country.name);
	        	
	        	if(!paises.contains(pais))
	        		paises.add(pais);
	        	else
	        		Pais.paisID--;
	        	
	           	pais = new Pais(country.name);
	        	paisID = pais.getID();
	        	for(int i=0; i<paises.size(); i++) {
	        		if(paises.get(i).equals(pais)) {
	        			Pais.paisID--;
	        			paisID = paises.get(i).getID();
	        			found = true;
	        			break;
	        		}
	        	}
	        	
	        	if(!found)
	        		paises.add(pais);
	        	
	        	found = false;
	        		
		        companies = mov.production_companies;
		        
		        for(ProductionCompany company : companies) {
		        	fcpObject = new FinancaCompanhiaPais(paisID,dataID,mov.budget,mov.revenue,company.name);

		        	if(!fcp.contains(fcpObject))
		        		fcp.add(fcpObject);
		        }
	        }
	
	        Thread.sleep(500);
		}
	}

	private static void getDataFromTMDBApi() throws IOException, InterruptedException {
		for(int i=1; i<NUMBER_OF_PAGES + 1; i++) {
			Call<MovieResultsPage> call = moviesService.popular(i, "en");
			MovieResultsPage page = call.execute().body();
			
			getDataFromResultsPage(page);
		}		
	}
}