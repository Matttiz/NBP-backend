package nbp.pl.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import nbp.pl.model.Rate;
import org.apache.http.HttpStatus;

import java.util.List;

import static org.junit.Assert.assertEquals;


@Slf4j
public class ExchangeRatesSteps {
    private static List<Rate> cachedRates = null;

    @Given("Pobrano kursy walut")
    public void getCurrency() {
        if (cachedRates == null) {
            Response response = RestAssured
                    .given()
                    .baseUri("http://api.nbp.pl")
                    .basePath("/api/exchangerates/tables/A")
                    .when()
                    .get();

            log.info("Kod odpowiedzi z API NBP: " + response.getStatusCode());
            assertEquals(HttpStatus.SC_OK, response.getStatusCode());
            List<Rate> rates = response.jsonPath().getList("[0].rates", Rate.class);
            log.info("Pobrano " + rates.size() + " kursów.");
            cachedRates = rates;
        }
    }

    @When("Wyświetl kurs dla waluty o kodzie: {string}")
    public void displayExchangeRateForCurrencyCode(String code) {
        Rate found = cachedRates.stream()
                .filter(rate -> rate.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);

        if (found != null) {
            log.info("Kurs dla waluty " + code + " wynosi: " + found.getMid());
        } else {
            log.info("Nie znaleziono waluty o kodzie " + code);
        }
    }

    @When("Wyświetl kurs dla waluty o nazwie: {string}")
    public void displayExchangeRateForCurrencyName(String currencyName) {
        Rate found = cachedRates.stream()
                .filter(rate -> rate.getCurrency().equalsIgnoreCase(currencyName))
                .findFirst()
                .orElse(null);
        if (found != null) {
            log.info("Kurs dla waluty " + currencyName + " wynosi: " + found.getMid());
        } else {
            log.info("Nie znaleziono waluty o nazwie " + currencyName);
        }
    }

    @When("Wyświetl waluty o kursie powyżej: {double}")
    public void displayCurrenciesWithRateAbove(Double threshold) {
        List<Rate> above = cachedRates.stream()
                .filter(rate -> rate.getMid() > threshold)
                .toList();
        if (!above.isEmpty()) {
            log.info("Waluty o kursie powyżej " + threshold + ":");
            above.forEach(rate ->
                    System.out.println(rate.getCurrency() + " (" + rate.getCode() + "): " + rate.getMid())
            );
        } else {
            log.info("Brak walut o kursie powyżej " + threshold);
        }
    }

    @Then("Wyświetl waluty o kursie poniżej: {double}")
    public void displayCurrenciesWithRateBelow(Double threshold) {
        List<Rate> below = cachedRates.stream()
                .filter(rate -> rate.getMid() < threshold)
                .toList();

        if (!below.isEmpty()) {
            System.out.println("Waluty o kursie poniżej " + threshold + ":");
            below.forEach(rate ->
                    System.out.println(rate.getCurrency() + " (" + rate.getCode() + "): " + rate.getMid())
            );
        } else {
            log.info("Brak walut o kursie poniżej " + threshold);
        }
    }
}