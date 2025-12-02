package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RecruitmentPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By recruitmentMenu = By.xpath("//a[.//span[text()='Recruitment']");

    public RecruitmentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public void goToRecruitment() {
        wait.until(ExpectedConditions.elementToBeClickable(recruitmentMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Candidates') or contains(text(),'Vacancies')]")));
    }
}
