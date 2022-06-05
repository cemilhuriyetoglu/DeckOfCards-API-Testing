import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class RequestSpec {


    public String AccessToken(String id , String secret)
    {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        // RestAssured.baseURI=apiUrl;
        Response request = RestAssured.given()
                .config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs("x-www-form-urlencoded",
                                        ContentType.URLENC)))
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", id)
                .formParam("client_secret", secret)
                .post("/v1/auth/token");
        String responsestring=request.asString();
        io.restassured.path.json.JsonPath js=new io.restassured.path.json.JsonPath(responsestring);
        String token=js.get("access_token");

        return token;
    }

    public String sipayPFToken(String id , String secret)
    {
        RestAssured.baseURI="https://provisioning.sipay.com.tr";
        Response request = RestAssured.given()
                .config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs("x-www-form-urlencoded",
                                        ContentType.URLENC)))
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("grant_type", "client_credentials")
                .formParam("app_id", id)
                .formParam("app_secret", secret)
                .post("/ccpayment/api/token");
        String responsestring=request.asString();
        io.restassured.path.json.JsonPath js=new io.restassured.path.json.JsonPath(responsestring);
        String token=js.get("data.token");

        return token;
    }
}


