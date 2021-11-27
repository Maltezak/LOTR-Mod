package lotr.common.entity;

public class LOTRInvasionStatus {
    private LOTREntityInvasionSpawner watchedInvasion;
    private int ticksSinceRelevance;

    public String getTitle() {
        return this.watchedInvasion.getInvasionType().invasionName();
    }

    public float[] getRGB() {
        return this.watchedInvasion.getInvasionType().invasionFaction.getFactionRGB_MinBrightness(0.45f);
    }

    public float getHealth() {
        return this.watchedInvasion.getInvasionHealthStatus();
    }

    public boolean isActive() {
        return this.watchedInvasion != null;
    }

    public void setWatchedInvasion(LOTREntityInvasionSpawner invasion) {
        this.watchedInvasion = invasion;
        this.ticksSinceRelevance = 0;
    }

    public void tick() {
        if (this.watchedInvasion != null) {
            if (this.watchedInvasion.isDead) {
                this.clear();
            } else {
                ++this.ticksSinceRelevance;
                if (this.ticksSinceRelevance >= 600) {
                    this.ticksSinceRelevance = 0;
                    this.clear();
                }
            }
        }
    }

    public void clear() {
        this.watchedInvasion = null;
        this.ticksSinceRelevance = 0;
    }
}

