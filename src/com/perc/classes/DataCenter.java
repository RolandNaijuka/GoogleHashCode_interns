package com.perc.classes;

import java.util.ArrayList;

public class DataCenter {

    public ArrayList<Integer> videos;

    public DataCenter(){
        this.videos = new ArrayList<>();
    }

    public void addVideo(int videoId) {
        this.videos.add(videoId);
    }

    public void addAllVideos(ArrayList<Integer> videos) {
        this.videos.addAll(videos);
    }

    public ArrayList<Integer> getVideos() {
        return this.videos;
    }
}
