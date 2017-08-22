import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import io.javalin.Javalin;

public class Main {

	private static final String LOGIN_URI = "https://tmweb.troopmaster.com/Login/Login";
	private static final String SCOUTS_URI = "https://tmweb.troopmaster.com/ScoutManagement/index";
	private static Client client = ClientBuilder.newClient();
		
    // Main function that starts the web app
    public static void main(String[] args) {
        Javalin app = Javalin.create()
            .port(getHerokuAssignedPort())
            .enableStaticFiles("/public")
            .get("/refresh", (req, res) -> res.body(doLoginAttempt()));
            //.post("/", (req, res) -> res.body(addPlaceName(req.bodyAsClass(Place.class))))
            //.delete("/*", (req, res) -> res.body(removePlaceName(req.path().substring(1)) ? "deleted" : "not found"));
    }

    // Helper function to get the port Heroku wants us to use
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000;
    }
    
    private static String doLoginAttempt() {
    	String output = "";
		
    	Response response = getLogin(System.getenv("TM_UNIT"), System.getenv("TM_USER"), System.getenv("TM_PASS"));
		output += "Status: " + response.getStatus();
		for (Entry<String, NewCookie> entry  : response.getCookies().entrySet()) {
			output += entry.getKey() + "=" + entry.getValue();
		}
		
		//Response response2 = getScouts();
		
		return output;//+ response2.getStatus() + response2.readEntity(String.class);
    }
    
	public static Response getLogin(String unit, String user, String pass) {
		return client
				.target(LOGIN_URI)
				.request(MediaType.APPLICATION_JSON)
				.header("Cookie", "TroopMasterWebSiteID=" + unit + ";")
				.post(Entity.entity("{\"UserID\":\"" + user + "\",\"Password\":\"" + pass + "\"}", MediaType.APPLICATION_JSON));
	}
	
	public static Response getScouts() {
		return client.target(SCOUTS_URI).request(MediaType.TEXT_HTML).get();
	}


    /* Commenting out the PlaceName stuff for now - DELETE later
    
    // Search through the place names and return a list of matches
    private static List<Place> findPlaceNames(String query) {
        List<Place> matches = new ArrayList<Place>();
        for (Entry<String, Place> entry : places.entrySet()) {
            if (entry.getKey().toLowerCase().contains(query.toLowerCase())) {
                matches.add(entry.getValue());
            }
        }
        return matches;
    }

    // Add a given place to the data set
    private static String addPlaceName(Place place) {
        places.put(place.name, place);
        return "added";
    }

    // Deletes a entry from the list of places if it exists
    private static boolean removePlaceName(String key) {
        if (places.remove(key) != null) {
            return true;
        } else {
            return false;
        }
    }

    // Simple data structure to hold place information
    private static class Place {
        public String name;
        public Double lat;
        public Double lon;

        public Place() {

        }

        public Place(String name, Double lat, Double lon) {
            this.name = name;
            this.lat = lat;
            this.lon = lon;
        }
    }
    
    */
}
