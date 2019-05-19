package ru.icmit.vk;

/**
 * Created by Adele on 03/05/2019.
 */
public class Edge {
    public int groupIdA;
    public int groupIdB;
    public float affinity;
    public float Jakkar;
    public float BraunBlanquet;
    public float Overlap;

    public Edge(int groupIdA, int groupIdB, float affinity, float Jakkar, float BraunBlanquet, float Overlap) {
        this.groupIdA = groupIdA;
        this.groupIdB = groupIdB;
        this.affinity = affinity;
        this.Jakkar = Jakkar;
        this.BraunBlanquet = BraunBlanquet;
        this.Overlap = Overlap;
    }

    public int compareByAffinity(Edge other) {
        return Float.compare(this.affinity, other.affinity);
    }

    public int compareByJordan(Edge other) {
        return Float.compare(this.Jakkar, other.Jakkar);
    }

    public int compareByBraunBlanquet(Edge other) {
        return Float.compare(this.BraunBlanquet, other.BraunBlanquet);
    }

    public int compareByOverlap(Edge other) {
        return Float.compare(this.Overlap, other.Overlap);
    }

    public int compareByAffinityDecs(Edge other) {
        return -compareByAffinity(other);
    }

    public int compareByJordanDecs(Edge other) {
        return -compareByJordan(other);
    }

    public int compareByBraunBlanquetDecs(Edge other) {
        return -compareByJordan(other);
    }

    public int compareByOverlapDecs(Edge other) {
        return -compareByOverlap(other);
    }

    @Override
    public String toString() {
        return String.format("%d - %d: %f", groupIdA, groupIdB, affinity, Jakkar, BraunBlanquet, Overlap);
    }

    public String printEdges() {
        return String.format("\"source\":\"%s\",\"target\":\"%s\",\"Affinity\":\"%f\",\"Jakkar\":%f,\"BraunBlanquet\":%f,\"Overlap\":%f}", groupIdA, groupIdB, affinity, Jakkar, BraunBlanquet, Overlap);
    }

    public String printEdgesRandom() {
        return String.format("\"source\":\"%s\",\"target\":\"%s\",\"distance1\":\"%f\",\"distance\":%d}", groupIdA, groupIdB, Jakkar, getRandomArbitary(-1000, 1000));
    }

    public int getRandomArbitary(int min, int max) {
        int res = min + (int) (Math.random() * (max - min + 1));
        return res;
    }
}
