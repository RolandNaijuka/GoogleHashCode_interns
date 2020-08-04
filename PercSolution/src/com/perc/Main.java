package com.perc;

import com.perc.classes.CacheServer;
import com.perc.classes.DataCenter;
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
      int tempNumRequests = numRequests;
      while (tempNumRequests > 0)  {
      int videoId = scanner.nextInt();
      int endPointId = scanner.nextInt();
      int num_of_requests = scanner.nextInt();
      endPoints.get(endPointId).addVideoRequest(videoId, num_of_requests);
        tempNumRequests --;
      }
      DataCenter dataCenter = new DataCenter();
      minimizeLatency(endPoints, cacheServers, videos, dataCenter);

      for (CacheServer server: cacheServers) {
//        Print the list of cache server
        System.out.println();
      }


      scanner.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

// Boiler plate for the algorithm
  public static void minimizeLatency(ArrayList<EndPoint> endPoints, ArrayList<CacheServer>  cacheServers, ArrayList<Video> videos, DataCenter dataCenter) {
    for (EndPoint endPoint: endPoints) {
      HashMap<Integer, Integer> videoRequestsUnsorted = endPoint.videoRequests;
      HashMap<Integer, Integer> cacheLatenciesUnsorted = endPoint.cacheLatency;

//      Sorted in descending order of video requests
      HashMap<Integer, Integer> videoRequests = new HashMap<>();
      videoRequestsUnsorted.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
              .forEachOrdered(x -> videoRequests.put(x.getKey(), x.getValue()));
//      Sorted in ascending order of latencies
      HashMap<Integer, Integer> cacheLatencies = new HashMap<>();
      cacheLatenciesUnsorted.entrySet()
              .stream()
              .sorted(Map.Entry.comparingByValue())
              .forEachOrdered(x -> cacheLatencies.put(x.getKey(), x.getValue()));


      for(int videoId: videoRequests.keySet()) {
        int num_of_video_requests = videoRequests.get(videoId);
        int videoSize = videos.get(videoId).videoSize;
        boolean videoAdded = false;
        for (int cacheId: cacheLatencies.keySet()) {
          CacheServer cacheServer = cacheServers.get(cacheId);
          if (videoSize <= cacheServer.maxStorage && !cacheServer.containsVideo(videoId)) {
            cacheServer.maxStorage -= videoSize;
            cacheServer.addVideo(videoId);
            videoAdded = true;
            break;
          }
        }
        if (!videoAdded) {
          dataCenter.addVideo(videoId);
        }
      }
    }
  }
}
