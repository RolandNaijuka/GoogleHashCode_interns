package com.perc.classes;

import java.util.*;

public class CacheServer {
  // ID
  // MaxStorage
  // List of videos
  // getters
    public int cacheServerId;
    public int maxStorage;
    public HashSet<Integer> videos;
    public CacheServer(int cacheServerId, int maxStorage) {
        this.cacheServerId = cacheServerId;
        this.maxStorage = maxStorage;
        this.videos  = new HashSet<>();
    }

    public void addVideo(int videoId) {
        this.videos.add(videoId);
    }

    public void addAllVideos(ArrayList<Integer> videos) {
        this.videos.addAll(videos);
    }

    public boolean containsVideo(int videoId) {
        return this.videos.contains(videoId);
    }

    @Override
    public String toString() {
        String str = this.cacheServerId+"";
        StringBuilder sbr = new StringBuilder(str);
        for (int videoId: this.videos) {
            sbr.append(" ");
            sbr.append(videoId);
        }
        return sbr.toString();
    }


}
