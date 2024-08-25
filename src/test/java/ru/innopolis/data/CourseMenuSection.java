package ru.innopolis.data;

public enum CourseMenuSection {
  SCHOOL("Школьникам"),
  PROFS("Педагогам"),
  QA("Тестирование"),
  DEV("Разработка"),
  ANALYSIS("Аналитика"),
  HR("HR"),
  MANAGEMENT("Управление"),
  ALL("Все программы");

  private final String name;
  CourseMenuSection(String name) {

    this.name = name;
  }

  public String getName() {

    return name;
  }
}
