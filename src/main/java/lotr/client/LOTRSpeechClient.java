package lotr.client;

import java.util.*;

import lotr.common.entity.npc.LOTREntityNPC;

public class LOTRSpeechClient {
    private static Map<UUID, TimedSpeech> npcSpeeches = new HashMap<>();
    private static int DISPLAY_TIME = 200;

    public static void update() {
        HashMap<UUID, TimedSpeech> newMap = new HashMap<>();
        for(Map.Entry<UUID, TimedSpeech> e : npcSpeeches.entrySet()) {
            UUID key = e.getKey();
            TimedSpeech speech = e.getValue();
            speech.time--;
            if(speech.time <= 0) continue;
            newMap.put(key, speech);
        }
        npcSpeeches = newMap;
    }

    public static void receiveSpeech(LOTREntityNPC npc, String speech) {
        npcSpeeches.put(npc.getUniqueID(), new TimedSpeech(speech, DISPLAY_TIME));
    }

    public static void removeSpeech(LOTREntityNPC npc) {
        npcSpeeches.remove(npc.getUniqueID());
    }

    public static TimedSpeech getSpeechFor(LOTREntityNPC npc) {
        UUID key = npc.getUniqueID();
        if(npcSpeeches.containsKey(key)) {
            return npcSpeeches.get(key);
        }
        return null;
    }

    public static boolean hasSpeech(LOTREntityNPC npc) {
        return LOTRSpeechClient.getSpeechFor(npc) != null;
    }

    public static void clearAll() {
        npcSpeeches.clear();
    }

    public static class TimedSpeech {
        private String speech;
        private int time;

        private TimedSpeech(String s, int i) {
            this.speech = s;
            this.time = i;
        }

        public String getSpeech() {
            return this.speech;
        }

        public float getAge() {
            return (float) this.time / (float) DISPLAY_TIME;
        }
    }

}
