import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.logging.LoggingFeature;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.javalin.Javalin;

public class Main {

	private static int PORT = 7000;

	private static String TM_UNIT = "";
	private static String TM_USER = "";
	private static String TM_PASS = "";

	private static String LOGIN_URI = "https://tmweb.troopmaster.com/Login/Login";
	private static String SCOUTS_URI = "https://tmweb.troopmaster.com/ScoutManagement/index";
	private static String ADULTS_URI = "https://tmweb.troopmaster.com/AdultManagement/index";
	private static String ACTIVITIES_URI = "https://tmweb.troopmaster.com/ActivityManagement/index";

	private static Client client = null;

	private static Map<String, String> cookies = new HashMap<String, String>();
	
	private static Map<String, Scout> scouts = new HashMap<String, Scout>();
	private static Map<String, Adult> adults = new HashMap<String, Adult>();
	private static Map<String, Activity> activities = new HashMap<String, Activity>();

	public static void main(String[] args) {
		initLocalEnvironment();
		//initHerokuEnvironment();

		Logger logger = Logger.getLogger(Main.class.getName());
		LoggingFeature feature = new LoggingFeature(logger, Level.INFO, null, null);
		client = ClientBuilder.newBuilder().register(feature).build();

		// startJavalin();
		
		performLogin();
		parseScouts();
		parseAdults();
		parseActivities();
		
		printScouts();
		printLeaders();
		printCamping();
	}

	private static void initHerokuEnvironment() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			PORT = Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		TM_UNIT = System.getenv("TM_UNIT");
		TM_USER = System.getenv("TM_USER");
		TM_PASS = System.getenv("TM_PASS");
		cookies.put("TroopMasterWebSiteID", TM_UNIT);
	}

	private static void initLocalEnvironment() {
		TM_UNIT = "202574";
		TM_USER = "";
		TM_PASS = "";
		cookies.put("TroopMasterWebSiteID", TM_UNIT);
	}

	private static void startJavalin() {
		Javalin app = Javalin.create()
				.port(PORT)
				.enableStaticFiles("/public");
				//.get("/login", (req, res) -> res.body("Status: "  + performLogin()))
				//.get("/scouts", (req, res) -> res.body(parseScouts()));
	}

	private static int performLogin() {
		Response response = login(TM_UNIT, TM_USER, TM_PASS);

		for (Entry<String, NewCookie> entry : response.getCookies().entrySet()) {
			cookies.put(entry.getValue().getName(), entry.getValue().getValue());
		}

		return response.getStatus();
	}

	private static void parseScouts() {
		Response response = scouts();
		Document html = Jsoup.parse(response.readEntity(String.class));
		Elements rows = html.select("#grid tbody tr");
		for (Element tr : rows) {
			Scout scout = new Scout();
			String[] name = tr.child(1).text().split(", ");
			scout.setFirstName(name[1]);
			scout.setLastName(name[0]);
			scout.setPatrol(tr.child(2).text());
			scout.setRank(tr.child(3).text());
			scout.setPosition(tr.child(4).text());
			scout.setId(tr.child(5).text());
			scouts.put(scout.getId(), scout);
		}
	}

	private static void parseAdults() {
		Response response = adults();
		Document html = Jsoup.parse(response.readEntity(String.class));
		Elements rows = html.select("#grid tbody tr");
		for (Element tr : rows) {
			Adult adult = new Adult();
			String[] name = tr.child(1).text().split(", ");
			adult.setFirstName(name[1]);
			adult.setLastName(name[0]);
			adult.setLeader(tr.child(2).text().equals("Yes"));
			adult.setTrained(tr.child(3).text().equals("Yes"));
			adult.setPosition(tr.child(4).text());
			adult.setId(tr.child(5).text());
			adults.put(adult.getId(), adult);
		}
	}
	
	private static void parseActivities() {
		Response response = activities();
		Document html = Jsoup.parse(response.readEntity(String.class));
		Elements rows = html.select("#gridActivity tbody tr");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");
		for (Element tr : rows) {
			Activity activity = new Activity();
			activity.setLevel(tr.child(0).text());
			activity.setType(tr.child(1).text());
			activity.setTitle(tr.child(2).text());
			activity.setStart(LocalDate.parse(tr.child(3).text(), formatter));
			activity.setEnd(LocalDate.parse(tr.child(4).text(), formatter));
			activity.setLocation(tr.child(5).text());
			activity.setId(tr.child(7).text());
			activities.put(activity.getId(), activity);
		}		
	}

	public static Response login(String unit, String user, String pass) {
		return client
				.target(LOGIN_URI)
				.request(MediaType.APPLICATION_JSON)
				.header("Cookie", getCookies())
				.post(Entity.entity("{\"UserID\":\"" + user + "\",\"Password\":\"" + pass + "\"}", MediaType.APPLICATION_JSON));
	}

	public static Response scouts() {
		return client
				.target(SCOUTS_URI)
				.request(MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML, MediaType.APPLICATION_XML)
				.header("Cookie", getCookies())
				.get();
	}
	
	public static Response adults() {
		return client
				.target(ADULTS_URI)
				.request(MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML, MediaType.APPLICATION_XML)
				.header("Cookie", getCookies())
				.get();		
	}
	
	public static Response activities() {
		return client
				.target(ACTIVITIES_URI)
				.request(MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML, MediaType.APPLICATION_XML)
				.header("Cookie", getCookies())
				.get();
	}
	
	public static String getCookies() {
		String output = "";
		for (String key : cookies.keySet()) {
			output += key + "=" + cookies.get(key) + ";";
		}
		return output;
	}
	
	public static void printScouts() {
		for (String key : scouts.keySet()) {
			Scout scout = scouts.get(key);
			System.out.println(scout.getFirstName() + " " + scout.getLastName() + " [" + scout.getRank() + "] - " + scout.getPatrol());
		}
	}

	public static void printLeaders() {
		for (String key : adults.keySet()) {
			Adult adult = adults.get(key);
			if (adult.isLeader()) {
				System.out.println(adult.getFirstName() + " " + adult.getLastName() + " [" + adult.getPosition() + "]");
			}
		}
	}
	
	public static void printCamping() {
		for (String key : activities.keySet()) {
			Activity activity = activities.get(key);
			if (activity.getType().equals("Camping")) {
				System.out.println(activity.getTitle() + " [" + activity.getType() + "]");
				System.out.println(activity.getStart() + " - " + activity.getEnd());
				System.out.println(activity.getLocation());
			}
		}		
	}
	
}
