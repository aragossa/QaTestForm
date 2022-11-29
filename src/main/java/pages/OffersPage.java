package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static application.WebDriverFactory.wdChrome;
import static application.appManager.cfg;
import static application.appManager.logger;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;


public class OffersPage {

  final WebDriver wd;

  public static WebDriverWait wait = new WebDriverWait(wdChrome, Duration.ofSeconds(20L));

  final String nameFieldValue = UUID.randomUUID().toString();
  final String keyFieldValue = UUID.randomUUID().toString();


  public OffersPage(WebDriver wd) {
    PageFactory.initElements(wd, this);
    this.wd = wdChrome;
  }

  @FindBy(xpath = "//span[contains(text(),'Menu')]")
  private WebElement showMenu;

  @FindBy(xpath = "//span[contains(text(),'Offers')]")
  private WebElement showOffers;

  @FindBy(xpath = "//span[contains(text(),'Add')]")
  private WebElement addButton;

  @FindBy(xpath = "//input[@id='mat-input-0']")
  private WebElement nameField;

  @FindBy(xpath = "//input[@id='mat-input-1']")
  private WebElement keyField;

  @FindBy(xpath = "//mat-card[3]//mat-form-field[1]")
  private WebElement getCategoryList;

  @FindBy(xpath = "//mat-card[3]//mat-form-field[1]//select")
  private WebElement getCategoryListSelect;

  @FindBy(xpath = "//mat-card[3]//mat-form-field[2]")
  private WebElement getCategoryNetworks;

  @FindBy(xpath = "//mat-option[@id='mat-option-0']")
  private WebElement getCategoryNetworksCheckbox;

  @FindBy(xpath = "//mat-card[3]//mat-form-field[3]")
  private WebElement getCategoryGroup;

  @FindBy(xpath = "//span[contains(text(),'Engineers')]")
  private WebElement getCategoryGroupCheckbox;

  @FindBy(xpath = "//span[contains(text(),'Add group')]")
  private WebElement addGroup;


  @FindBy(xpath = "//span[contains(text(),'Add segment')]")
  private WebElement addSegment;

  @FindBy(xpath = "//span[contains(text(),'Save')]")
  private WebElement saveButton;

  @FindBy(xpath = "//tbody[@role='rowgroup']")
  private WebElement rowGroup;

  @FindBy(xpath = "//button/span[contains(text(),'yes!')]")
  private WebElement yesButton;

  @FindBys(
          @FindBy(xpath = "//tbody[@role='rowgroup']/tr")
  )
  private List<WebElement> formTableRows;

  public OffersPage open() {
    wdChrome.get(cfg.url());
    logger.info(String.format("Successfully opened start page %s", cfg.url()));
    wait.until(visibilityOf(showMenu)).click();
    logger.info("Menu opened");
    wait.until(visibilityOf(showOffers)).click();
    logger.info("Offers showed");
    return this;
  }

  public OffersPage addOffer() {

    wait.until(visibilityOf(addButton)).click();
    logger.info("Offers showed");

    wait.until(visibilityOf(nameField)).sendKeys(nameFieldValue);
    wait.until(visibilityOf(keyField)).sendKeys(keyFieldValue);

    // Select Category
    wait.until(visibilityOf(getCategoryList)).click();
    wait.until(visibilityOf(getCategoryListSelect)).sendKeys(Keys.DOWN);
    wait.until(visibilityOf(getCategoryListSelect)).sendKeys(Keys.ENTER);

    // Select Network
    wait.until(visibilityOf(getCategoryNetworks)).click();
    wait.until(visibilityOf(getCategoryNetworksCheckbox)).click();
    wait.until(visibilityOf(getCategoryNetworksCheckbox)).sendKeys(Keys.ESCAPE);

    // Select Network
    wait.until(visibilityOf(getCategoryGroup)).click();
    wait.until(visibilityOf(getCategoryGroupCheckbox)).click();
    return this;
  }

  public OffersPage addGroup() {
    wait.until(visibilityOf(addGroup)).click();
    return this;
  }

  public OffersPage addSegment() {
    wait.until(visibilityOf(addSegment)).click();
    return this;
  }

  public OffersPage saveButton() {
    wait.until(visibilityOf(saveButton)).click();
    wait.until(visibilityOf(rowGroup));
    return this;
  }

  public OffersPage checkTableResults() {
    List<WebElement> tableRows = formTableRows;
    Boolean result = false;
    for (WebElement row : tableRows) {
      wait.until(visibilityOf(row));
      String currentRow = row.findElement(By.xpath("td[2]")).getText();
      if (currentRow.equals(nameFieldValue)) {
        result = true;
      }
    }
    logger.info(result);
    assert result.equals(true);
    return this;
  }

  public OffersPage deleteOffer() {
    wait.until(visibilityOf(rowGroup));
    List<WebElement> tableRowsBefore = formTableRows;
    int randomNum = new Random().nextInt(tableRowsBefore.size());
    logger.info(randomNum);
    logger.info(tableRowsBefore.size());
    tableRowsBefore.get(randomNum).findElement(By.xpath("td[4]/button[2]")).click();
    wait.until(visibilityOf(yesButton)).click();
    wd.navigate().refresh();
    wait.until(visibilityOf(rowGroup));
    List<WebElement> tableRowsAfter = formTableRows;
    logger.info(tableRowsBefore.size());
    logger.info(tableRowsAfter.size());
    assert tableRowsAfter.size() != tableRowsBefore.size();
    return this;
  }

  public OffersPage tearDown() {
    logger.info("Closing browser");
    wd.close();
    return this;
  }
}
