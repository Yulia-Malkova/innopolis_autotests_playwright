package ru.innopolis.pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import ru.innopolis.utils.DataExtractor;
import ru.innopolis.utils.RandomGenerator;

public class BlogPage {

  private final Page page;
  private final Locator mainHeader;
  private final Locator subHeader;
  private final Locator subscribeButton;
  private final Locator nameField;
  private final Locator phoneNumberField;
  private final Locator emailField;
  private final Locator notificationReceptionButton;
  private final Locator articleCard;

  public BlogPage(Page page) {
    this.page = page;
    this.mainHeader = page.getByRole(AriaRole.HEADING, new GetByRoleOptions().setName(
        "Блог STC"));
    this.subHeader = page.getByText(
        "Рассказываем об ИТ-профессиях и технологиях, делимся опытом экспертов, радуемся историям успеха наших выпускников курсов");
    this.subscribeButton = page.getByText("Подписаться на новости");
    this.nameField = page.locator("css=[aria-label='name']");
    this.phoneNumberField = page.getByRole(AriaRole.TEXTBOX,
        new Page.GetByRoleOptions().setName("(999) 999-99-99"));
    this.emailField = page.getByPlaceholder("Ваш E-mail");
    this.notificationReceptionButton = page.getByRole(AriaRole.BUTTON)
        .getByText("Получать уведомления");
    this.articleCard = page.locator("css=.t-card__link");
  }

  public void checkStateAfterNavigation(String menuUrl) {
    assertAll(
        "Проверяем, что url при клике на пункт меню меняется, пункт меню выделен, а карточки курсов попадают во viewport",
        () -> assertEquals(page.url(), menuUrl),
        () -> assertThat(mainHeader).isVisible(),
        () -> assertThat(subHeader).isVisible(),
        () -> assertThat(subscribeButton).isVisible(),
        () -> assertThat(subscribeButton).hasAttribute("href", "#signup")
    );
  }

  public BlogPage clickSubscribeButton() {
    subscribeButton.click();
    return this;
  }

  public void checkNotificationSubscriptionSection() {
    assertAll(
        "Проверяем, что отображается форма для подписки и кнопка \"Получать уведомления\"",
        () -> assertThat(nameField).isVisible(),
        () -> assertThat(phoneNumberField).isVisible(),
        () -> assertThat(emailField).isVisible(),
        () -> assertThat(notificationReceptionButton).isVisible()
    );
  }

  @Step("Кликнуть на случайную статью")
  public ArticlePage clickArticleCard(BrowserContext context, DataExtractor dataExtractor) {
    int randomArticleCardNumber = RandomGenerator.getRandomInt(1, 10);
    dataExtractor.setArticleName(articleCard.nth(randomArticleCardNumber).textContent());
    Page newPage = context.waitForPage(() -> {
      articleCard.nth(randomArticleCardNumber).click();
    });
    ArticlePage articlePage = new ArticlePage(newPage);
    return articlePage;

  }

}
