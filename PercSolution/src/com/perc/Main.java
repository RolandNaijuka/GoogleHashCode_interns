package com.perc;

import java.io.*;
import java.util.*;
public class Main {

  public static void main(String[] args) {
    try {
      String path = new File("data/example.in").getAbsolutePath();
      File file = new File(path);
      Scanner scanner = new Scanner(file);
      String projectData = scanner.nextLine();
      String[] array = projectData.split(" ");
      int numVideo = Integer.parseInt(array[0]);
      int numEndPoints = Integer.parseInt(array[1]);
      int numRequests = Integer.parseInt(array[2]);
      int numCacheServers = Integer.parseInt(array[3]);
      int sizeCacheServer = Integer.parseInt(array[4]);
      
      String videos = scanner.nextLine();
      // Add video data here
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      
    }
  }
}
