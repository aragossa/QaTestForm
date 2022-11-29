import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import pages.OffersPage;

import static application.WebDriverFactory.wdChrome;


public class QATestForm {

    public static OffersPage offersPage = new OffersPage(wdChrome);

    @Test
    public void addOffers1()  {
        offersPage.open();
        offersPage.addOffer();
        offersPage.addGroup();
        offersPage.saveButton();
        offersPage.checkTableResults();
    }


    @Test
    public void addOffers2()  {
        offersPage.open();
        offersPage.addOffer();
        offersPage.addSegment();
        offersPage.saveButton();
        offersPage.checkTableResults();
    }

    @Test
    public void deleteOffers() {
        offersPage.open();
        offersPage.deleteOffer();

    }

    @AfterAll
    public static void tearDown() {
        offersPage.tearDown();
    }
}
