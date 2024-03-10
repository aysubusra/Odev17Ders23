import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BookStoreAPITest {
    @Test
    public void testAddBookToCollection() {
        // API'nin base URL'si
        baseURI = "https://demoqa.com/BookStore/v1/Books";

        // Geçerli bir kullanıcı kimliği (userId) ve geçerli bir ISBN numarası (isbn)
        String userId = "123456";
        String isbn = "9781449337711"; // Örnek ISBN numarası

        // JSON verisini oluşturun
        JSONObject requestBody = new JSONObject();
        requestBody.put("userId", userId);

        JSONArray collectionOfIsbns = new JSONArray();
        JSONObject isbnObject = new JSONObject();
        isbnObject.put("isbn", isbn);
        collectionOfIsbns.put(isbnObject);

        requestBody.put("collectionOfIsbns", collectionOfIsbns);

        given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                body(requestBody.toString()). // JSON verisini string olarak gönderin
                when().
                post("/Books").
                then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("success", is(true)). // Başarılı bir yanıt dönüp dönmediğini kontrol etmek için
                body("message", equalTo("Books successfully added to your collection.")); // Yanıtın mesajını kontrol etmek için
    }
}
