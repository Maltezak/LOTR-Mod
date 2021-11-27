package lotr.client.sound;

import java.util.*;

import lotr.common.world.biome.LOTRMusicRegion;

public class LOTRRegionTrackPool {
    private final LOTRMusicRegion region;
    private List<LOTRMusicTrack> trackList = new ArrayList<>();

    public LOTRRegionTrackPool(LOTRMusicRegion r, String s) {
        this.region = r;
    }

    public void addTrack(LOTRMusicTrack track) {
        this.trackList.add(track);
    }

    public boolean isEmpty() {
        return this.trackList.isEmpty();
    }

    public LOTRMusicTrack getRandomTrack(Random rand, LOTRTrackSorter.Filter filter) {
        List<LOTRMusicTrack> sortedTracks = LOTRTrackSorter.sortTracks(this.trackList, filter);
        double totalWeight = 0.0;
        for(LOTRMusicTrack track : sortedTracks) {
            double weight2 = track.getRegionInfo(this.region).getWeight();
            totalWeight += weight2;
        }
        double randWeight = rand.nextDouble();
        randWeight *= totalWeight;
        Iterator<LOTRMusicTrack> it = sortedTracks.iterator();
        LOTRMusicTrack track = null;
        do {
            if(it.hasNext()) continue;
            return track;
        }
        while((randWeight -= ((track = it.next()).getRegionInfo(this.region).getWeight())) >= 0.0);
        return track;
    }
}
