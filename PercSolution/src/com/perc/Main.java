package com.perc;

import com.perc.classes.CacheServer;
import com.perc.classes.EndPoint;
import com.perc.classes.Video;

import java.io.*;
import java.util.*;
public class Main {

  public static void main(String[] args) {
    try {
//      use the pathname as "data/example.in" while running on terminal
//      use the pathname as "src/com/perc/data/example.in" while running in IntelliJ IDEA or eclipse
      String path = new File("src/com/perc/data/example.in").getAbsolutePath();
      File file = new File(path);
      Scanner scanner = new Scanner(file);
      int numVideo = scanner.nextInt();
      int numEndPoints = scanner.nextInt();
      int numRequests = scanner.nextInt();
      int numCacheServers = scanner.nextInt();
      int sizeCacheServer = scanner.nextInt();
//      Create all cache servers
      ArrayList<CacheServer>  cacheServers =  new ArrayList<>();
      for (int cacheServerId = 0; cacheServerId < numCacheServers; cacheServerId++) {
        cacheServers.add(new CacheServer(cacheServerId, sizeCacheServer));
      }
//      Add all videos
      ArrayList<Video> videos = new ArrayList<>();
      for (int videoId = 0; videoId < numVideo; videoId++) {
        int videoSize = scanner.nextInt();
        videos.add(new Video(videoId, videoSize));
      }
//      Add all endpoints and their connected cache servers
      ArrayList<EndPoint> endPoints = new ArrayList<>();
      for (int endPointId = 0; endPointId < numEndPoints; endPointId++) {
        int dataCenterLatency = scanner.nextInt();
        int numOfConnectedCaches = scanner.nextInt();
        EndPoint endPoint = new EndPoint(endPointId, dataCenterLatency);
        while(numOfConnectedCaches > 0) {
          int cacheServerId = scanner.nextInt();
          int latency = scanner.nextInt();
          endPoint.addCacheLatency(cacheServerId, latency);
          numOfConnectedCaches--;
        }
        endPoints.add(endPoint);
      }
//      Add all the requests
      while (numRequests > 0)  {
      int videoId = scanner.nextInt();
      int endPointId = scanner.nextInt();
      int num_of_requests = scanner.nextInt();
      endPoints.get(endPointId).addVideoRequest(videoId, num_of_requests);
        numRequests --;
      }




      scanner.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
