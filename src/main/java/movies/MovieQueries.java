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

import dimensoes.Ano;
import dimensoes.DataSQL;
import dimensoes.Filme;
import dimensoes.Genero;
import dimensoes.Pais;
import dimensoes.Participacao;
import dimensoes.ParticipacaoGrupo;
import factos.AcumuladoProfissional;
import factos.Bilheteira;
import factos.FinancaCompanhiaPais;
import factos.FinancaGeneroPais;
import factos.Ratings;
import retrofit2.Call;

public class MovieQueries {
	private static OracleConnection oracleConnection;
	private static MoviesService moviesService;
	
	private static List<Filme> filmes = new ArrayList<Filme>();
	private static List<Pais> paises = new ArrayList<Pais>();
	private static List<Genero> generos = new ArrayList<Genero>();
	
	private static List<Participacao> participacoes = new ArrayList<Participacao>();
	private static List<ParticipacaoGrupo> partsGrupo  = new ArrayList<ParticipacaoGrupo>();
	
	private static List<Bilheteira> bilheteiras = new ArrayList<Bilheteira>();
	
	private static List<DataSQL> datas = new ArrayList<DataSQL>();
	
	private static List<Ratings> ratings = new ArrayList<Ratings>();
	
	private static List<FinancaCompanhiaPais> fcp = new ArrayList<FinancaCompanhiaPais>();
	private static List<FinancaGeneroPais> fgp = new ArrayList<FinancaGeneroPais>();
	
	private static List<Ano> anos = new ArrayList<Ano>();
	
	private static List<AcumuladoProfissional> acumulados = new ArrayList<AcumuladoProfissional>();
	
	private static final int NUMBER_OF_PAGES = 1;
	
	public static void main(String[] args) throws Exception {	
		Tmdb tmdb = new Tmdb("0b97edab69a564ad5ce4847142659d80");
		moviesService = tmdb.moviesService();
		
		getDataFromTMDBApi(); 
		
		for(Ano ano : anos) 
			ano.getInsertQuery();
		
		for(DataSQL data : datas)
			data.getInsertQuery();
		
		for(Filme filme : filmes) 
			filme.getInsertQuery();

		for(Pais pais : paises) 
			pais.getInsertQuery();
		
		for(Genero genero : generos) 
			genero.getInsertQuery();
		
		for(Participacao part : participacoes)
			part.getInsertQuery();
		
		for(Bilheteira bilh : bilheteiras) 
			bilh.getInsertQuery();
		
		for(ParticipacaoGrupo partGrupo : partsGrupo) 
			partGrupo.getInsertQuery();

		for(Ratings rating : ratings) 
			rating.getInsertQuery();
		
		for(FinancaCompanhiaPais fcpObject : fcp) 
			fcpObject.getInsertQuery();
		
		for(FinancaGeneroPais fgpObject : fgp)
			fgpObject.getInsertQuery();

		
		for(AcumuladoProfissional acumulado : acumulados) 
			acumulado.getInsertQuery();
		
		saveDataToOracle();
	}

	private static void saveDataToOracle() throws Exception, SQLException {
		oracleConnection = new OracleConnection();
		
		// execute queries
		/*for(Ano ano : anos) 
			oracleConnection.executeQuery(ano.getInsertQuery());
		
		for(DataSQL data : datas)
			oracleConnection.executeQuery(data.getInsertQuery());*/
		
		/*for(Filme filme : filmes)
			oracleConnection.executeQuery(filme.getInsertQuery());
		
		for(Pais pais : paises) 
			oracleConnection.executeQuery(pais.getInsertQuery());
		
		for(Genero genero : generos) 
			oracleConnection.executeQuery(genero.getInsertQuery());*/
		
		for(Participacao part : participacoes)
			oracleConnection.executeQuery(part.getInsertQuery());
		
		for(Bilheteira bilh : bilheteiras) 
			oracleConnection.executeQuery(bilh.getInsertQuery());
		
		/*for(ParticipacaoGrupo partGrupo : partsGrupo) 
			oracleConnection.executeQuery(partGrupo.getInsertQuery());
		
		for(Ratings rating : ratings) 
			oracleConnection.executeQuery(rating.getInsertQuery());
		
		for(FinancaCompanhiaPais fcpObject : fcp) 
			oracleConnection.executeQuery(fcpObject.getInsertQuery());
		
		for(FinancaGeneroPais fgpObject : fgp)
			oracleConnection.executeQuery(fgpObject.getInsertQuery());	*/	
		
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
	
		Pais pais;
		Genero genero;
		
		Participacao participacao;
		ParticipacaoGrupo partGrupo;
		
		Bilheteira bilheteira;
		
		DataSQL data;
		Ano ano_;
		
		Ratings rating;
		
		FinancaCompanhiaPais fcpObject;
		FinancaGeneroPais fgpObject;
		
		AcumuladoProfissional acumulado;
		
		int participacaogrupo_id = 1;
		
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
        	
        	ano_ = new Ano(ano);
        	int anoID = ano_.getID();
        	for(int i = 0; i<anos.size(); i++) {
        		if(anos.get(i).equals(ano_)) {
        			Ano.anoID--;
        			anoID = anos.get(i).getID();
        			found = true;
        			break;
        		}
        	}
        	
        	if(!found) 
        		anos.add(ano_);
        	else 
        		found = false;
			
			System.out.println("Release date: " + dataString);
			System.out.println("Duracao: " + duracao);
			
			filme = new Filme(nome,duracao,ano,estado,popularidade,orcamento,receitas);
			//filme.getInsertQuery();
			filmes.add(filme);
			
			rating = new Ratings(dataID,filme.getID(),mov.vote_average,mov.vote_count);
			ratings.add(rating);
	        
			Call<Credits> callMovieCredits = moviesService.credits(id);
	        Credits credits = callMovieCredits.execute().body();
	        
	        castList = credits.cast;
	        
	        for(CastMember person : castList) {	        	      	
	        	participacao = new Participacao(person.name,"Actor");
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
	        	
	        	partGrupo = new ParticipacaoGrupo(participacaogrupo_id, partID);
	        	partsGrupo.add(partGrupo);
	        
	        	acumulado = new AcumuladoProfissional(anoID, person.name, orcamento, receitas);
            	for(int i = 0; i<acumulados.size(); i++) {
            		if(acumulados.get(i).equals(acumulado)) {
            			acumulados.get(i).updateFinanca(orcamento,receitas);
            			found = true;
            			break;
            		}
            	}
            	
            	if(!found) 
            		acumulados.add(acumulado);
            	else 
            		found = false;	        	
	        }
	        
	        crewList = credits.crew;
	        
	        for(CrewMember person : crewList) {	        	
	        	participacao = new Participacao(person.name,person.job);
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
	        	
	        	partGrupo = new ParticipacaoGrupo(participacaogrupo_id,partID);
        		partsGrupo.add(partGrupo);

	        	acumulado = new AcumuladoProfissional(anoID, person.name, orcamento, receitas);
            	for(int i = 0; i<acumulados.size(); i++) {
            		if(acumulados.get(i).equals(acumulado)) {
            			acumulados.get(i).updateFinanca(orcamento,receitas);
            			found = true;
            			break;
            		}
            	}
            	
            	if(!found) 
            		acumulados.add(acumulado);
            	else 
            		found = false;
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
	        	
	        	fgpObject = new FinancaGeneroPais(generoID,anoID,filme.getID(),mov.budget,mov.revenue);

	        	for(int i=0; i<fgp.size(); i++) {
	        		if(fgp.get(i).equals(fgpObject)) {
	        			fgp.get(i).updateFinanca(orcamento,receitas);
	        			found = true;
	        			break;
	        		}
	        	}
	        	
	        	if(!found)
	        		fgp.add(fgpObject);
	        	else
	        		found = false;
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
		        	fcpObject = new FinancaCompanhiaPais(paisID,anoID,mov.budget,mov.revenue,company.name);

		        	for(int i=0; i<fcp.size(); i++) {
		        		if(fcp.get(i).equals(fcpObject)) {
		        			fcp.get(i).updateFinanca(orcamento,receitas);
		        			found = true;
		        			break;
		        		}
		        	}
		        	
		        	if(!found)
		        		fcp.add(fcpObject);
		        	else
		        		found = false;
		        }
	        }
        	bilheteira = new Bilheteira(participacaogrupo_id, orcamento, receitas);
        	
        	bilheteiras.add(bilheteira);
    		participacaogrupo_id++;
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