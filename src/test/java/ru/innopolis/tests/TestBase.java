package ru.innopolis.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

public class TestBase {
  static Playwright playwright;
  static Browser browser;
  BrowserContext context;
  Page page;
  String testName;

  @BeforeAll
  static void launchBrowser() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch();
  }

  @AfterAll
  static void closeBrowser() {
    playwright.close();
  }
  @BeforeEach
  void createContextAndPage(TestInfo testInfo) {
    testName = testInfo.getDisplayName();
    context = browser.newContext();
    page = context.newPage();
    context.tracing().start(new Tracing.StartOptions()
        .setScreenshots(true)
        .setSnapshots(true)
        .setSources(true));
  }

  @AfterEach
  void closeContext() {
    context.tracing().stop(new Tracing.StopOptions()
        .setPath(Paths.get("trace_"+testName+".zip")));
    context.close();
  }
  static String baseUrl = "https://stc.innopolis.university/";
}
