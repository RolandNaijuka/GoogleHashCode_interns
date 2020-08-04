package com.perc;

import java.io.*;
import java.util.*;
public class Main {

  public static void main(String[] args) {
    try {
      String path = new File("data/example.in").getAbsolutePath();
      File file = new File(path);
      Scanner scanner = new Scanner(file);
      int numVideo = scanner.nextInt();
      int numEndPoints = scanner.nextInt();
      int numRequests = scanner.nextInt();
      int numCacheServers = scanner.nextInt();
      int sizeCacheServer = scanner.nextInt();
      
      
      String videos = scanner.nextLine();
      // Add video data here
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      
    }
  }
}
