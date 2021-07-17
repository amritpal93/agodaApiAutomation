package agoda.apiTestSuite;

import agoda.core.ConfigDetails;
import agoda.core.UrlProvider;
import agoda.resources.EndPoints;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;

public class ApiTest extends ConfigDetails {

String log4jConfPath =System.getProperty("user.dir")+"/log4j.properties";

	public static Logger log4j ;

	@BeforeSuite
	public void initialize_ConfigVariables(){
		System.out.println("the path for log4j is"+log4jConfPath);
		PropertyConfigurator.configure(log4jConfPath);
		log4j = Logger.getLogger("devpinoyLogger");
		try {
			FileInputStream input = new FileInputStream("Config.properties");
			Properties	prop = new Properties();
			prop.load(input);


			env = prop.getProperty("Environment");
			log4j.debug(env);

			baseUrl = prop.getProperty("baseUrl");
			log4j.debug(baseUrl);

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}



	@BeforeMethod
	public void getBaseConfig() {
		UrlProvider.Load_Base_Url();
	}

	@Test
	public void TC_001_VerifyAllFamousQoutesAuthor() {
		List<HashMap<String, String>> searchItems = get(EndPoints.getfamousQoutesAuthor("famous-quotes", "Confucius")).body().jsonPath().get("results");
		for(HashMap<String,String> authorTilte :searchItems){
			Assert.assertTrue(true, String.valueOf(authorTilte.get("author").equalsIgnoreCase("Confucius")));
		}
	}
	@Test
	public void TC_002_VerifyAllFamousQoutesPage() {
		List<HashMap<String, ArrayList<String>>> searchItems = get(EndPoints.getFamousQoutesPage("friendship", "2")).body().jsonPath().get("results");
		for (HashMap<String, ArrayList<String>> size : searchItems) {
			ArrayList title = size.get("tags");
			 String expected= (String) title.stream().map(Object::toString).collect(Collectors.joining(","));
			 Assert.assertTrue(true, String.valueOf(expected.contains("friendship")));

		}}


}
