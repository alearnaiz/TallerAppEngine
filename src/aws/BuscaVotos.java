package aws;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modelo.Busca;
import modelo.Grupo;
import palabras.CuentaPalabras;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class BuscaVotos {
	
	public static ArrayList<Grupo> encuentraVotos(Busca[] artistas){
		ArrayList<Grupo> salida = new ArrayList<Grupo>();
		for(int i = 0; i < artistas.length; i++){
		//---------------------Cuenta-----------------------------//
		
        String delimitadores = " .,:;-!¡?¿(){}[]";
        List<String> buenas = Arrays.asList("bueno","buen","gusta","genial","estupendo","nice","genial","maravilloso","maravillosa",
        		"buena","precioso","preciosa");
        List<String> malas = Arrays.asList("malo","mala","feo","fea","horrible","terrible","inutil","feisimo","feisima",
        		"nefasta","nefasto","odio");
        
        CuentaPalabras cp = new CuentaPalabras();
        CuentaPalabras cpm = new CuentaPalabras();
		
		//---------------------Twitter API -----------------------//
		
		// Incluir numero de seguidores en Twitter
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("ZolgAGXPPpJUDHzfUlAvcg")
		  .setOAuthConsumerSecret("6yXC0Gxckx0JoL99nfouFUcVDzckmkCCNLgihoHx1A")
		  .setOAuthAccessToken("747376760-3Lh5CFhBQ6xdznG5kuqMCbS43bpHxIVyKNexXu1Z")
		  .setOAuthAccessTokenSecret("jBxXkm5OKrvH8yEtOuGZYpGKeHTXLtILRlbLssFBIlU");
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter t = tf.getInstance();
			Query query = new Query(artistas[i].getNombre()).count(100000).lang("es");
		    QueryResult result = null;
			try {
				result = t.search(query);
				
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			
			ArrayList<String> tweets = new ArrayList<String>();
			
		    for (Status status : result.getTweets()) {
		    	tweets.add(status.getText());
		    }
		    for(String tweet : tweets){
		    	cp.agregarLínea(tweet);
		    	cpm.agregarLínea(tweet);
		    }
		    
		    
            int positivos = cp.resolver(delimitadores, buenas);
            int negativos = cpm.resolver(delimitadores, malas);
            Grupo g = new Grupo();
            g.setNombre(artistas[i].getNombre());
            g.setVotosPositivos(positivos);
            g.setVotosNegativos(negativos);
            salida.add(g);
		}
		return salida;
	}
}
	

