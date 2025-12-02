package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class VacanciesPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public VacanciesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToVacancyPage() {
        // Assumes you are logged in and on main menu
        // Click Recruitment -> Vacancies
        WebElement recruitMenu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Recruitment']")));
        recruitMenu.click();
        WebElement vacanciesLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'viewJobVacancyList') or text()='Vacancies']")));
        vacanciesLink.click();

        // Wait until Vacancies page header visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(.,'Vacancies') or contains(.,'Vacancy')]")));
    }

    public void addVacancy(String vacancyName, String hiringManager) {
        // Click Add button
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[text()=' Add ']] | //button[.=' Add '] | //button[contains(.,'Add')]")));
        addBtn.click();

        // Wait for name input (job vacancy name field) — selector may differ by version
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@name='vacancyName' or @name='name' or contains(@placeholder,'Vacancy')]")));
        nameInput.clear();
        nameInput.sendKeys(vacancyName);

        // Hiring manager input (type for hints)
        WebElement managerInput = null;
        try {
            managerInput = driver.findElement(By.xpath("//input[@placeholder='Type for hints...' or contains(@class,'oxd-input')][1]"));
        } catch (NoSuchElementException e) {
            // fallback
            managerInput = driver.findElement(By.xpath("//input[contains(@class,'oxd-autocomplete-input')]"));
        }
        managerInput.clear();
        managerInput.sendKeys(hiringManager);
        // Sometimes suggestions appear — press ENTER to accept
        managerInput.sendKeys(Keys.ENTER);

        // Save
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[text()=' Save ']] | //button[contains(.,'Save')]")));
        saveBtn.click();

        // Wait for save to complete — either vacancy list visible or success toast
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'oxd-toast')]")),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(.,'Vacancies') or contains(.,'Vacancy')]"))
        ));
    }

    public boolean searchVacancy(String vacancyName) {

        goToVacancyPage();


        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Type for hints...' or @name='vacancyName' or contains(@placeholder,'Vacancy')]")));
        searchInput.clear();
        searchInput.sendKeys(vacancyName);
        WebElement searchBtn = driver.findElement(By.xpath("//button[.//span[text()=' Search ']] | //button[contains(.,'Search')]"));
        searchBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'oxd-table-body')]")));

        List<WebElement> rows = driver.findElements(By.xpath("//div[contains(@class,'oxd-table-body')]//div[contains(@class,'oxd-table-card')]"));
        for (WebElement row : rows) {
            if (row.getText().contains(vacancyName)) return true;
        }
        return false;
    }
}
