package ru.innopolis.data;

public enum MenuURL {
  COURSE("https://stc.innopolis.university/#rec281327586"),
  BLOG("https://stc.innopolis.university/blog");
  private final String name;

  MenuURL(String name) {

    this.name = name;
  }

  public String getName() {

    return name;
  }
}
