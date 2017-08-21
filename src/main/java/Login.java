import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login {
	
	// INITIAL TMWEB REQUEST
	
	/**
		curl 'https://tmweb.troopmaster.com/Login/Index?website'
		-H 'DNT: 1'
		-H 'Accept-Encoding: gzip, deflate, br'
		-H 'Accept-Language: en-US,en;q=0.8'
		-H 'Upgrade-Insecure-Requests: 1'
		-H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36'
		-H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng;q=0.8'
		-H 'Referer: http://tmweb.troopmaster.com/Login/Index?website'
		-H 'Cookie: __utma=79578065.1002456651.1476986116.1476986116.1477080833.2; TroopMasterWebSiteID=202574; ASP.NET_SessionId=zv0i41i2kw3r5lygax44hlyn'
		-H 'Connection: keep-alive'
		--compressed
	**/
	
	// TMWEB LOGIN REQUEST - POST - https://tmweb.troopmaster.com/Login/Login
	
	/**
		curl 'https://tmweb.troopmaster.com/Login/Login'
		-H 'Cookie: __utma=79578065.1002456651.1476986116.1476986116.1477080833.2; TroopMasterWebSiteID=202574; ASP.NET_SessionId=zv0i41i2kw3r5lygax44hlyn'
		-H 'Origin: https://tmweb.troopmaster.com'
		-H 'Accept-Encoding: gzip, deflate, br'
		-H 'Accept-Language: en-US,en;q=0.8'
		-H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36'
		-H 'Content-Type: application/json' -H 'Accept: '
		-H 'Referer: https://tmweb.troopmaster.com/Login/Index?website'
		-H 'X-Requested-With: XMLHttpRequest'
		-H 'Connection: keep-alive'
		-H 'DNT: 1'
		--data-binary '{"UserID":"FirstName.LastName","Password":"password"}'
		--compressed
	**/
	
	// TMWEB GET ALL SCOUTS (ScoutManagement) - GET - https://tmweb.troopmaster.com/ScoutManagement/index
	
	/**
		curl 'https://tmweb.troopmaster.com/ScoutManagement/index'
		-H 'DNT: 1'
		-H 'Accept-Encoding: gzip, deflate, br'
		-H 'Accept-Language: en-US,en;q=0.8'
		-H 'Upgrade-Insecure-Requests: 1'
		-H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36'
		-H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng;q=0.8'
		-H 'Referer: https://tmweb.troopmaster.com/ScoutManagement/index'
		-H 'Cookie: __utma=79578065.1002456651.1476986116.1476986116.1477080833.2; TroopMasterWebSiteID=202574; ASP.NET_SessionId=zv0i41i2kw3r5lygax44hlyn; TMUser=1=Matthew.Atkins&2=20257449d94cbcbccd455996f36658e6e5372d; HomeTroopMasterWebSiteID=202574; .FormsAuth=6737EE102B2BE9861352DBBCC0EB81D1C1DC845451ACA6F99BF8A1992697244B248F83881988BBC93FC8E5CCD385E82003BFD543E2F1E0811B585364CC52FF3BD2F63C4DAFF12D0FCFDE9304F1CA0F4F12511FCD1042472989836E6B4B62EC036390998FE027BCB6136370562C558831'
		-H 'Connection: keep-alive'
		--compressed
	**/	

	// In the response of the ScoutManagement - find the DIV element with the ID of "grid"
			
	public static void main(String[] args) {
		
		HttpURLConnection connection = null;
		
		try {
			URL login = new URL("https://tmweb.troopmaster.com/Login/Login");
			connection = (HttpURLConnection) login.openConnection();
			connection.setRequestMethod("POST");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
