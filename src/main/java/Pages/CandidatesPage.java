package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CandidatesPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CandidatesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToCandidatePage() {
        WebElement recruitMenu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Recruitment']")));
        recruitMenu.click();
        WebElement candidatesLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'viewCandidates') or text()='Candidates']")));
        candidatesLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(.,'Candidates') or contains(.,'Candidate')]")));
    }

    public void addCandidate(String firstName, String lastName, String email, String contact) {
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[text()=' Add ']] | //button[contains(.,'Add')]")));
        addBtn.click();

        // Wait for form
        WebElement fn = wait.until(ExpectedConditions.elementToBeClickable(By.name("firstName")));
        fn.clear();
        fn.sendKeys(firstName);

        WebElement ln = driver.findElement(By.name("lastName"));
        ln.clear();
        ln.sendKeys(lastName);

        // Email & contact might have specific selectors
        try {
            WebElement emailInput = driver.findElement(By.xpath("//input[@type='email' or @name='email']"));
            emailInput.clear();
            emailInput.sendKeys(email);
        } catch (NoSuchElementException ignored) {}

        try {
            WebElement contactInput = driver.findElement(By.xpath("//input[@name='contactNumber' or @name='mobile']"));
            contactInput.clear();
            contactInput.sendKeys(contact);
        } catch (NoSuchElementException ignored) {}

        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[text()=' Save ']] | //button[contains(.,'Save')]")));
        saveBtn.click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'oxd-toast')]")),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(.,'Candidates') or contains(.,'Candidate')]"))
        ));
    }

    public boolean searchCandidates(String name) {
        goToCandidatePage();

        WebElement candidateInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[text()='Candidate']//parent::div/following-sibling::div//input | //input[@placeholder='Type for hints...']")));
        candidateInput.clear();
        candidateInput.sendKeys(name);

        WebElement searchBtn = driver.findElement(By.xpath("//button[.//span[text()=' Search ']] | //button[contains(.,'Search')]"));
        searchBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'oxd-table-body')]")));

        List<WebElement> rows = driver.findElements(By.xpath("//div[contains(@class,'oxd-table-body')]//div[contains(@class,'oxd-table-card')]"));
        for (WebElement row : rows) {
            if (row.getText().contains(name)) return true;
        }
        return false;
    }
}
