package com.perc;

import com.perc.classes.CacheServer;
import com.perc.classes.DataCenter;
import com.perc.classes.EndPoint;
import com.perc.classes.Video;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
  private static final String SEPARATOR = "#";

  public static void main(String[] args) {
//    use the pathname as "data/in" while running on terminal
//   use the pathname as "src/com/perc/data/in" while running in IntelliJ IDEA or eclipse
    String folderPath = new File("src/com/perc/data/in").getAbsolutePath();
    File folder = new File(folderPath);
    File[] listOfFiles = folder.listFiles();

    for (File file : listOfFiles) {
      if (file.isFile()) {
        try {
          Scanner scanner = new Scanner(file);
          int numVideo = scanner.nextInt();
          int numEndPoints = scanner.nextInt();
          int numRequests = scanner.nextInt();
          int numCacheServers = scanner.nextInt();
          int sizeCacheServer = scanner.nextInt();
//      Create all cache servers
          ArrayList<CacheServer> cacheServers = new ArrayList<>();
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
            while (numOfConnectedCaches > 0) {
              int cacheServerId = scanner.nextInt();
              int latency = scanner.nextInt();
              endPoint.addCacheLatency(cacheServerId, latency);
              numOfConnectedCaches--;
            }
            endPoints.add(endPoint);
          }
//      Add all the requests
          HashMap<String, Integer> videoRequests = new HashMap<>();
          int tempNumRequests = numRequests;
          while (tempNumRequests > 0) {
            int videoId = scanner.nextInt();
            int endPointId = scanner.nextInt();
            int num_of_requests = scanner.nextInt();

//        Add video requests to a map which is gonna be sorted later
            String videoId_endPointId = String.format("%d%s%d", videoId, SEPARATOR, endPointId);
            videoRequests.put(videoId_endPointId, num_of_requests);

            endPoints.get(endPointId).addVideoRequest(videoId, num_of_requests);
            tempNumRequests--;
          }

          scanner.close();


          DataCenter dataCenter = new DataCenter();
//      minimizeLatency(endPoints, cacheServers, videos, dataCenter);
          minimizeLatencies(videoRequests, endPoints, cacheServers, videos, dataCenter);

          List<CacheServer> filteredCacheServers = cacheServers
                  .stream()
                  .filter(cacheServer -> !cacheServer.videos.isEmpty())
                  .collect(Collectors.toList());
//          Get the input file name without the .in extension
          String fileName = file.getName().split("\\.")[0];

          String pathOut = new File("src/com/perc/data/out").getAbsolutePath();
//          Create the folder  if it does not exist
          File outputFolder = new File(pathOut);
          //noinspection ResultOfMethodCallIgnored
          outputFolder.mkdir();
          pathOut += File.separator + fileName + ".out";
          File output = new File(pathOut);

          FileWriter fileWriter = new FileWriter(output);
          fileWriter.write(filteredCacheServers.size() + "\n");

          for (CacheServer cacheServer : filteredCacheServers) {
            fileWriter.write(cacheServer.toString() + "\n");
          }

          fileWriter.close();

        } catch (Exception e) {
          System.err.println(e.toString());
        }
      }
    }
  }

  private static HashMap<String, Integer> sortMapDesc(HashMap<String, Integer> videoRequestsUnSorted) {
    HashMap<String, Integer> videoRequestsSorted = new HashMap<>();
    videoRequestsUnSorted.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEachOrdered(x -> videoRequestsSorted.put(x.getKey(), x.getValue()));
    return videoRequestsSorted;
  }

  private static void minimizeLatencies(
          HashMap<String, Integer> videoRequestsUnSorted,
          ArrayList<EndPoint> endPoints,
          ArrayList<CacheServer>  cacheServers,
          ArrayList<Video> videos,
          DataCenter dataCenter
  ) {
    HashMap<String, Integer> videoRequestsSorted = sortMapDesc(videoRequestsUnSorted);
    for(String key: videoRequestsSorted.keySet()) {
      String[] ids = key.split(SEPARATOR);
      int videoId = Integer.parseInt(ids[0]);
      int endPointId = Integer.parseInt(ids[1]);

      HashMap<Integer, Integer> cacheLatenciesUnsorted = endPoints.get(endPointId).cacheLatency;
      //      Sorted in ascending order of latencies
      HashMap<Integer, Integer> cacheLatencies = new HashMap<>();
      cacheLatenciesUnsorted.entrySet()
              .stream()
              .sorted(Map.Entry.comparingByValue())
              .forEachOrdered(x -> cacheLatencies.put(x.getKey(), x.getValue()));

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
