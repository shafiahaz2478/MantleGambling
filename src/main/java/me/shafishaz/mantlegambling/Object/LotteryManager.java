package me.shafishaz.mantlegambling.Object;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LotteryManager<UUID> {

    private class Entry {
        double accumulatedWeight;
        UUID object;
    }

    private List<Entry> entries = new ArrayList<Entry>();
    private double accumulatedWeight;
    private Random rand = new Random();

    public void addEntry(UUID object, double weight) {
        accumulatedWeight += weight;
        Entry entry = new Entry();
        entry.object = object;
        entry.accumulatedWeight = accumulatedWeight;
        entries.add(entry);
    }

    public UUID getRandom() {
        double r = rand.nextDouble() * accumulatedWeight;
        for (Entry entry: entries) {
            if (entry.accumulatedWeight >= r) {
                return entry.object;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

}
