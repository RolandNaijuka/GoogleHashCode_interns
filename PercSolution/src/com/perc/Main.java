package com.perc;

import java.io.*;
import java.util.*;
public class Main {

  public static void main(String[] args) {
    try {
      File file = new File("../data/example.in");
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String data = scanner.nextLine();
        System.out.println(data);
        break;
      }
    } catch (Exception e) {
      
    } finally {
    
    }
  }
}
