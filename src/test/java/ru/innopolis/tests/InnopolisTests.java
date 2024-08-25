package ru.innopolis.tests;

import static ru.innopolis.data.MenuItems.menuItems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.innopolis.data.CourseMenuSection;
import ru.innopolis.data.MenuURL;
import ru.innopolis.pages.MainPage;
import ru.innopolis.utils.DataExtractor;

public class InnopolisTests extends TestBase {

  @Test
  @DisplayName("Проверка заголовка и подзаголовка на главной странице")
  void checkMainPageTitle() {
    MainPage mainPage = new MainPage(page);
    mainPage
        .navigate(baseUrl)
        .checkHeaderAndSubHeaderText();
  }

  @Test
  @DisplayName("Проверка наличия логотипа университета на главной странице")
  void checkMainPageLogo() {
    MainPage mainPage = new MainPage(page);
    mainPage
        .navigate(baseUrl)
        .checkLogo();
  }

  @Test
  @DisplayName("Проверка пунктов меню на главной странице")
  void checkMenuItemsName() {
    MainPage mainPage = new MainPage(page);
    mainPage
        .navigate(baseUrl)
        .checkMenuList(menuItems);
  }

  @Test
  @DisplayName("Переход в раздел \"Курсы\" на главной странице")
  void navigateToCoursesSection() {
    String menuItem = "Курсы";
    String menuUrl = "https://stc.innopolis.university/#rec281327586";

    MainPage mainPage = new MainPage(page);
    mainPage
        .navigate(baseUrl)
        .clickMenuItemForMainPageNavigation(menuItem)
        .checkStateAfterNavigation(menuItem, menuUrl);
  }

  @ParameterizedTest(name = "Переход в раздел {0} курсов через меню")
  @EnumSource(CourseMenuSection.class)
  void navigateThroughMenuOfCoursesSection(CourseMenuSection c) {
    String menuItem = "Курсы";

    MainPage mainPage = new MainPage(page);
    mainPage
        .navigate(baseUrl)
        .clickMenuItemForMainPageNavigation(menuItem)
        .clickCourseMenuSection(c.getName())
        .checkCourseMenuSectionIsSelected(c.getName());
  }

  @Test
  @DisplayName("Открытие модального окна \"Нужна помощь\"")
  void openNeedHelpModalWindow() {
    String menuItem = "Курсы";

    MainPage mainPage = new MainPage(page);
    mainPage
        .navigate(baseUrl)
        .clickMenuItemForMainPageNavigation(menuItem)
        .clickNeedHelpButton()
        .checkNeedHelpModalWindow();
  }

  @Test
  @DisplayName("Переход на страницу \"Блог\" с главной страницы")
  void openCoursePage() {
    MainPage mainPage = new MainPage(page);
    mainPage
        .navigate(baseUrl)
        .navigateToBlogPage(context)
        .checkStateAfterNavigation(MenuURL.BLOG.getName());
  }

  @Test
  @DisplayName("Открыть секцию для подписки на уведомления через страницу \"Блог\"")
  void subscribeViaBlogPage() {

    MainPage mainPage = new MainPage(page);
    mainPage
        .navigate(baseUrl)
        .navigateToBlogPage(context)
        .clickSubscribeButton()
        .checkNotificationSubscriptionSection();
  }

  @Test
  @DisplayName("Открыть статью через страницу \"Блог\"")
  void openBlogArticle() {
    DataExtractor dataExtractor = new DataExtractor();

    MainPage mainPage = new MainPage(page);
    mainPage
        .navigate(baseUrl)
        .navigateToBlogPage(context)
        .clickArticleCard(context, dataExtractor)
        .checkArticlePage(dataExtractor);
  }

  @Test
  @DisplayName("При попытке уйти со страницы статьи открывается модальное окно \"Пожалуйста, не уходите\"")
  void makeAppearModalWindowWhileTryToClosePage() {
    DataExtractor dataExtractor = new DataExtractor();

    MainPage mainPage = new MainPage(page);
    mainPage
        .navigate(baseUrl)
        .navigateToBlogPage(context)
        .clickArticleCard(context, dataExtractor)
        .moveCursorUp()
        .checkModalWindow();
  }
}
