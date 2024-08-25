package ru.innopolis.utils;

import com.github.javafaker.Faker;
import java.util.Locale;

public  class RandomGenerator {
  static Faker faker = new Faker(new Locale("ru"));
  public static int getRandomInt(int from, int to){
    return faker.number().numberBetween(from, to);
  }
}
