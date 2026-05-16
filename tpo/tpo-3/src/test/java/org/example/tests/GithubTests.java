package org.example.tests;

import org.example.pages.HomePage;
import org.example.pages.PullRequestsPage;
import org.example.pages.RepositoryPage;
import org.example.pages.SearchResultsPage;
import org.example.pages.SignUpPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.List;
import java.util.stream.Stream;

import org.openqa.selenium.WebElement;

public class GithubTests extends BaseTest {

    private static class ArgumentsProvider implements org.junit.jupiter.params.provider.ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(Arguments.of("chrome,firefox"));
        }
    }

    private void runInBrowsers(String browsers, Consumer<WebDriver> testAction) {
        String[] browserArray = browsers.split(",");
        java.util.List<CompletableFuture<Void>> futures = Arrays.stream(browserArray)
            .map(browser -> CompletableFuture.runAsync(() -> {
                WebDriver driver = createDriver(browser.trim());
                try {
                    testAction.accept(driver);
                } finally {
                    driver.quit();
                }
            })).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    @ParameterizedTest(name = "Проверка поиска по репозиториям: {0}")
    @ArgumentsSource(ArgumentsProvider.class)
    public void testSearchRepository(String browsers) {
        runInBrowsers(browsers, driver -> {
            HomePage homePage = new HomePage(driver);
            homePage.open();

            SearchResultsPage resultsPage = homePage.searchFor("Felijy/ITMO");

            Assertions.assertTrue(resultsPage.isSpecificResultFound("Felijy/ITMO"), "Должен быть найден репозиторий Felijy/ITMO");
        });
    }

    @ParameterizedTest(name = "Проверка репозитория и кнопки Download ZIP: {0}")
    @ArgumentsSource(ArgumentsProvider.class)
    public void testRepositoryDownloadZip(String browsers) {
        runInBrowsers(browsers, driver -> {
            RepositoryPage repoPage = new RepositoryPage(driver);
            repoPage.open("https://github.com/Felijy/ITMO");

            repoPage.clickCodeButton();

            Assertions.assertTrue(repoPage.isDownloadZipVisible(), "В выпадающем меню Code должна быть видна опция Download ZIP");
        });
    }

    @ParameterizedTest(name = "Переход на страницу регистрации: {0}")
    @ArgumentsSource(ArgumentsProvider.class)
    public void testNavigateToSignUp(String browsers) {
        runInBrowsers(browsers, driver -> {
            HomePage homePage = new HomePage(driver);
            homePage.open();

            SignUpPage signUpPage = homePage.clickSignUp();

            Assertions.assertTrue(signUpPage.isCreateAccountButtonVisible(), "Кнопка Create account должна быть видима на странице регистрации");
        });
    }

    @ParameterizedTest(name = "Переход на Open Source -> GitHub Sponsors: {0}")
    @ArgumentsSource(ArgumentsProvider.class)
    public void testNavigateToSponsors(String browsers) {
        runInBrowsers(browsers, driver -> {
            HomePage homePage = new HomePage(driver);
            homePage.open();

            boolean isNavigated = homePage.navigateToSponsors();
            Assertions.assertTrue(isNavigated, "Должен произойти успешный переход в раздел GitHub Sponsors");
            Assertions.assertTrue(driver.getCurrentUrl().contains("/sponsors"), "URL должен содержать /sponsors");
        });
    }

    @ParameterizedTest(name = "Проверка фильтра по языку программирования: {0}")
    @ArgumentsSource(ArgumentsProvider.class)
    public void testLanguageFilter(String browsers) {
        runInBrowsers(browsers, driver -> {
            HomePage homePage = new HomePage(driver);
            homePage.open();
            SearchResultsPage resultsPage = homePage.searchFor("linux");

            resultsPage.filterByLanguageC();

            List<WebElement> langs = resultsPage.getLanguageResults();
            Assertions.assertFalse(langs.isEmpty(), "Должны быть результаты с указанием языка C");
        });
    }

    @ParameterizedTest(name = "Проверка редиректа на sign in при клике на Star: {0}")
    @ArgumentsSource(ArgumentsProvider.class)
    public void testStarRedirect(String browsers) {
        runInBrowsers(browsers, driver -> {
            RepositoryPage repoPage = new RepositoryPage(driver);
            repoPage.open("https://github.com/Felijy/ITMO");

            repoPage.clickStar();

            Assertions.assertTrue(repoPage.isRedirectedToLogin(), "Должен быть редирект на страницу логина");
        });
    }

    @ParameterizedTest(name = "Проверка сортировки more stars: {0}")
    @ArgumentsSource(ArgumentsProvider.class)
    public void testSortMoreStars(String browsers) {
        runInBrowsers(browsers, driver -> {
            HomePage homePage = new HomePage(driver);
            homePage.open();
            SearchResultsPage resultsPage = homePage.searchFor("linux");

            resultsPage.sortByMostStars();

            List<WebElement> stars = resultsPage.getStarResults();
            Assertions.assertFalse(stars.isEmpty(), "Должны отображаться звезды репозиториев");

            if (stars.size() >= 2) {
                int c1 = Integer.parseInt(stars.get(0).getText().replaceAll("[^0-9]", ""));
                int c2 = Integer.parseInt(stars.get(1).getText().replaceAll("[^0-9]", ""));
                Assertions.assertTrue(c1 >= c2, "Количество звезд должно уменьшаться");
            }
        });
    }

    @ParameterizedTest(name = "Проверка сортировки в PR по most commented: {0}")
    @ArgumentsSource(ArgumentsProvider.class)
    public void testSortPullRequestsMostCommented(String browsers) {
        runInBrowsers(browsers, driver -> {
            PullRequestsPage prPage = new PullRequestsPage(driver);
            prPage.open("https://github.com/microsoft/vscode/pulls");

            prPage.sortByMostCommented();

            List<WebElement> comments = prPage.getCommentResults();
            Assertions.assertFalse(comments.isEmpty(), "Должно отображаться количество комментариев");

            if (comments.size() >= 2) {
                int c1 = Integer.parseInt(comments.get(0).getText().replaceAll("[^0-9]", ""));
                int c2 = Integer.parseInt(comments.get(1).getText().replaceAll("[^0-9]", ""));
                Assertions.assertTrue(c1 >= c2, "Количество комментариев должно уменьшаться");
            }
        });
    }
}
