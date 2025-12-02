package Base;

import Base.BaseTest;
import Pages.LoginPage;
import Pages.VacanciesPage;
import Pages.CandidatesPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RecruitmentTest extends BaseTest {

    @Test
     public void verifyAddAndSearchVacancyAndCandidate() {

        LoginPage login = new LoginPage(driver);
        login.login("Admin", "admin123");

        VacanciesPage vacancy = new VacanciesPage(driver);
        vacancy.goToVacancyPage();;
        vacancy.addVacancy("Junior QA Engineer", "Ranga Ankhuri");
        vacancy.addVacancy("Software QA Engineer", "Ranga Ankhuri");

        Assert.assertTrue(
                vacancy.searchVacancy("Junior QA Engineer"),
                "Junior QA Engineer vacancy not found!"
        );

        Assert.assertTrue(
                vacancy.searchVacancy("Software QA Engineer"),
                "Software QA Engineer vacancy not found!"
        );

        // Candidate Page Test
        CandidatesPage candidate = new CandidatesPage(driver);
        candidate.goToCandidatePage();
        candidate.addCandidate("Bindu Kumari", "Neupane", "binduneupane@gmail.com", "9876543");
        candidate.addCandidate("Isha Kumari", "Neupane", "ishaneupane@gmail.com", "98745678");

        Assert.assertTrue(
                candidate.searchCandidates("Bindu"),
                "Candidate Bindu not found!"
        );

        Assert.assertTrue(
                candidate.searchCandidates("Isha"),
                "Candidate Isha not found!"
        );
    }
}

