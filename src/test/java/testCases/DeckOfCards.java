package testCases;

import base.BaseTest;
import base.ConfigurationReader;
import io.restassured.http.ContentType;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Listeners({base.Listeners.class})
public class DeckOfCards extends BaseTest {

    @Test
    public void T01_ShuffleTheCards_Successful() {

        logger.info("T01 - Shuffle The Cards as Successful when the number of decks is 1");
        logger.info("T01 started");

        int deck_count = 1;
        String endPointShuffle = "api/deck/new/shuffle/?deck_count=" + deck_count + "";
        given().contentType(ContentType.JSON)
                .when()
                .get(ConfigurationReader.get("baseURI") + endPointShuffle)
                .then()
                .statusCode(200).assertThat().body("remaining", equalTo(52))
                .body("success", equalTo(true))
                .body("shuffled", equalTo(true))
                .log().all();

        logger.info("T01 finished");
    }

    @Test
    public void T02_ShuffleTheCards_MaxNumberExceeded() {

        logger.info("T02 - The cards could not be shuffled because the maximum number was exceeded");
        logger.info("T02 started");

        int deck_count = 21;
        String endPointShuffle = "api/deck/new/shuffle/?deck_count=" + deck_count + "";
        given().contentType(ContentType.JSON)
                .when()
                .get(ConfigurationReader.get("baseURI") + endPointShuffle)
                .then()
                .statusCode(200).assertThat()
                .body("success", equalTo(false))
                .body("error", equalTo("The max number of Decks is 20."))
                .log().all();

        logger.info("T02 finished");
    }

    @Test
    public void T03_DrawCard_Successful() {

        logger.info("T03 - Draw a Cards as Successful");
        logger.info("T03 started");

        int deck_count = 1;
        String endPointShuffle = "api/deck/new/shuffle/?deck_count=" + deck_count + "";
        String deck_id = given().contentType(ContentType.JSON)
                .when()
                .get(ConfigurationReader.get("baseURI") + endPointShuffle)
                .then()
                .statusCode(200).assertThat().body("remaining", equalTo(52))
                .body("success", equalTo(true))
                .body("shuffled", equalTo(true))
                .log().all().extract().path("deck_id");
        System.out.println(deck_id);

        int count = 1;
        String endPointDraw = "api/deck/" + deck_id + "/draw/?count=" + count + "";
        given().headers(
                        "Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get(ConfigurationReader.get("baseURI") + endPointDraw)
                .then()
                .statusCode(200).assertThat().body("success", equalTo(true))
                .body("remaining", equalTo(51))
                .log().all().extract().path("deck_id");

        logger.info("T03 finished");
    }

    @Test
    public void T04_DrawCard_MaxNumberExceeded() {

        logger.info("T04 - The card could not be draw because the maximum number was exceeded");
        logger.info("T04 started");

        int deck_count = 1;
        String endPointShuffle = "api/deck/new/shuffle/?deck_count=" + deck_count + "";
        String deck_id = given().contentType(ContentType.JSON)
                .when()
                .get(ConfigurationReader.get("baseURI") + endPointShuffle)
                .then()
                .statusCode(200).assertThat().body("remaining", equalTo(52))
                .body("success", equalTo(true))
                .body("shuffled", equalTo(true))
                .log().all().extract().path("deck_id");
        System.out.println(deck_id);

        int count = 53;
        String endPointDraw = "api/deck/" + deck_id + "/draw/?count=" + count + "";
        given().headers(
                        "Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get(ConfigurationReader.get("baseURI") + endPointDraw)
                .then()
                .statusCode(200).assertThat().body("success", equalTo(false))
                .body("remaining", equalTo(0))
                .body("error", equalTo("Not enough cards remaining to draw " + count + " additional"))
                .log().all();

        logger.info("T04 finished");
    }

}
