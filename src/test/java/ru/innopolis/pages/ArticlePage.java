package ru.innopolis.pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ru.innopolis.utils.DataExtractor;

public class ArticlePage {

  private final Page page;
  private final Locator blogButton;
  private final Locator modalWindowTitle;
  private final Locator modalWindowButton;

  public ArticlePage(Page page) {
    this.page = page;
    this.blogButton = page.getByText("БЛОГ", new Page.GetByTextOptions().setExact(true));
    this.modalWindowTitle = page.locator("css=.tn-atom").getByText("Подождите, не уходите!");
    this.modalWindowButton = page.locator("css=.tn-atom").getByText("перейти");

  }

  public void checkArticlePage(DataExtractor dataExtractor) {
    assertAll(
        "Проверяем наличие лейбла Блог и название статьи",
        () -> assertThat(blogButton).isVisible(),
        () -> assertThat(page.getByText(dataExtractor.getArticleName())).isVisible()
    );
  }

  public ArticlePage moveCursorUp() {
    page.mouse().up();
    return this;
  }

  public void checkModalWindow() {
    assertAll(
        "Проверяем заголовок модального окна и наличие кнопки \"Перейти\"",
        () -> assertThat(modalWindowTitle).isVisible(),
        () -> assertThat(modalWindowButton).isVisible()
    );
  }
}
