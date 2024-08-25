package ru.innopolis.pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainPage {

  private final Page page;
  private final Locator mainHeader;
  private final Locator subHeader;
  private final Locator logo;
  private final Locator menuItems;
  private final Locator courseCard;
  private final Locator courseMenuSection;
  private final Locator needHelpButton;
  private final Locator needConsultationTitle;
  private final Locator needConsultationSubTitle;
  private final Locator submitButton;

  public MainPage(Page page) {
    this.page = page;
    this.mainHeader = page.getByRole(AriaRole.HEADING, new GetByRoleOptions().setName(
        "ЦЕНТР СПЕЦИАЛИЗИРОВАННОЙ ИТ-ПОДГОТОВКИ УНИВЕРСИТЕТА ИННОПОЛИС"));
    this.subHeader = page.getByText(
        "Твой путь к работе над крутыми проектами в ведущих ИТ-компаниях");
    this.logo = page.locator("css=.t456__imglogomobile");
    this.menuItems = page.locator("css=.t-menu__list .t-menu__link-item");
    this.courseCard = page.locator("css=.t-store__card__wrap_txt-and-btns");
    this.courseMenuSection = page.locator("css=.t395__title.t-name.t-name_xs");
    this.needHelpButton = page.getByText("Не могу выбрать курс, нужна помощь");
    this.needConsultationTitle = page.getByText("Нужна консультация!");
    this.needConsultationSubTitle = page.getByText("Связаться с менеджером");
    this.submitButton = page.getByRole(AriaRole.BUTTON).getByText("Отправить заявку ");
  }

  public MainPage navigate(String url) {
    page.navigate(url);
    return this;
  }

  public MainPage checkHeaderAndSubHeaderText() {
    assertAll(
        "Проверяем заголовок, подзаголовок и кнопку модального окна",
        () -> assertThat(mainHeader).isVisible(),
        () -> assertThat(subHeader).isVisible()
    );
    return this;
  }

  public void checkLogo() {
    assertThat(logo).hasAttribute("src",
        "https://static.tildacdn.com/tild3632-3861-4263-b462-316336656166/Group_31.svg");
  }

  public void checkMenuList(List<String> menuItemsList) {
    List<String> menuNames = new ArrayList<>();

    List<ElementHandle> elements = menuItems.elementHandles();
    elements.forEach(object -> {
      String menuName = object.textContent();
      menuNames.add(menuName);
    });

    Collections.sort(menuItemsList);
    Collections.sort(menuNames);
    assertTrue(menuItemsList.equals(menuNames));
  }

  public MainPage clickMenuItemForMainPageNavigation(String menuItem) {
    menuItems.getByText(menuItem).click();
    return this;
  }

  public BlogPage navigateToBlogPage(BrowserContext context) {
    Page newPage = context.waitForPage(() -> {
      menuItems.getByText("Блог").click();
    });
    BlogPage blogPage = new BlogPage(newPage);
    return blogPage;
  }

  public void checkStateAfterNavigation(String menuItem, String menuUrl) {
    assertAll(
        "Проверяем, что url при клике на пункт меню меняется, пункт меню выделен, а карточки курсов попадают во viewport",
        () -> assertEquals(page.url(), menuUrl),
        () -> assertThat(menuItems.getByText(menuItem)).hasClass("t-menu__link-item t-active"),
        () -> assertThat(courseCard.first()).isInViewport()
    );
  }

  public MainPage clickCourseMenuSection(String courseMenuSectionName) {
    courseMenuSection.getByText(courseMenuSectionName).click();
    return this;
  }

  public void checkCourseMenuSectionIsSelected(String courseMenuSectionName) {
    Locator sectionName = page.getByText(courseMenuSectionName);
    assertThat(courseMenuSection.filter(new Locator.FilterOptions().setHas(
        sectionName))).hasAttribute("aria-selected", "true");
  }

  public MainPage clickNeedHelpButton() {
    needHelpButton.click();
    return this;
  }

  public void checkNeedHelpModalWindow() {
    assertAll(
        "Проверяем заголовок, подзаголовок и кнопку модального окна",
        () -> assertThat(needConsultationTitle).isVisible(),
        () -> assertThat(needConsultationSubTitle).isVisible(),
        () -> assertThat(submitButton).isVisible()
    );
  }

}
