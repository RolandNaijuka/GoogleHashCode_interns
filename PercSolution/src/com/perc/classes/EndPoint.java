package com.perc.classes;

import java.util.*;
import com.perc.classes.Video;
public class EndPoint {

  public int endPointId;
  public int dataCenterLatency;
  public HashMap<Integer, Integer> cacheLatency;
  public HashMap<Integer, Integer> videoRequests;

  public EndPoint(int endPointId, int dataCenterLatency) {
    this.endPointId = endPointId;
    this.dataCenterLatency = dataCenterLatency;
    this.cacheLatency = new HashMap<>();
    this.videoRequests = new HashMap<>();
  }

  public void addCacheLatency(int cacheServerId, int latency) {
    this.cacheLatency.put(cacheServerId, latency);
  }

  public void addVideoRequest(int videoId, int num_of_requests) {
    this.videoRequests.put(videoId, num_of_requests);
  }

  public int getRequests(int videoId) {
    if(!this.videoRequests.containsKey(videoId)) {
//      Does not contain the video
      return -1;
    }
    return this.videoRequests.get(videoId);
  }

  public int getCacheLatency(int cacheServerId) {
    if(!this.cacheLatency.containsKey(cacheServerId)) {
//      Does not contain the cache server
      return -1;
    }
    return this.cacheLatency.get(cacheServerId);
  }

  public int getCacheLatencySize() {
    return this.cacheLatency.size();
  }
  
}
