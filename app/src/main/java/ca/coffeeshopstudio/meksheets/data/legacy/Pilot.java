package ca.coffeeshopstudio.meksheets.data.legacy;

public class Pilot {
    private int piloting = 5;
    private int gunnery = 4;
    private int hits;

    public int getPiloting() {
        return piloting;
    }

    public void setPiloting(int piloting) {
        this.piloting = piloting;
    }

    public int getGunnery() {
        return gunnery;
    }

    public void setGunnery(int gunnery) {
        this.gunnery = gunnery;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }
}
