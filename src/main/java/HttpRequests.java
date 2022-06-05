import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class HttpRequests {


    public static Response get(RestAssured baseURI, String endpoint) {
        return given().get(baseURI + endpoint);
    }


}
