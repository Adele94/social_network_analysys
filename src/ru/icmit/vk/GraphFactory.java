package ru.icmit.vk;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Adele on 05/05/2019.
 */
public class GraphFactory {
    private final int allParticipantsSize;

    public GraphFactory(List<Group> groups) {
        this.allParticipantsSize = calculateAllParticipantsSize(groups);
    }

    public Edge createEdge(Group groupA, Group groupB) {
        return new Edge(
                groupA.getId(),
                groupB.getId(),
                calculateAffinity(groupA.getParticipants(), groupB.getParticipants(), allParticipantsSize),
                calculateJakkar(groupA.getParticipants(), groupB.getParticipants()),
                calculateBraunBlanquet(groupA.getParticipants(), groupB.getParticipants()),
                calculateOverlap(groupA.getParticipants(), groupB.getParticipants())
        );
    }

    private int calculateAllParticipantsSize(List<Group> groups) {
        Set<Integer> users = new HashSet<>();
        groups.forEach(group -> {
            users.addAll(group.getParticipants());
        });

        return users.size();
    }

    private static float calculateAffinity(
            Set<Integer> participantsA,
            Set<Integer> participantsB,
            int allParticipants) {
        int intersectionSize = intersection(participantsA, participantsB).size();
        return (float) intersectionSize * allParticipants / (participantsA.size() * participantsB.size());

    }

    private static float calculateJakkar(
            Set<Integer> participantsA,
            Set<Integer> participantsB) {
        int intersectionSize = intersection(participantsA, participantsB).size();
        int unionSize = union(participantsA, participantsB).size();
        return (float) intersectionSize / unionSize;

    }

    private static float calculateBraunBlanquet(
            Set<Integer> participantsA,
            Set<Integer> participantsB) {
        int intersectionSize = intersection(participantsA, participantsB).size();
        return (float) 2 * intersectionSize /
                (participantsA.size() + participantsB.size() + Math.abs(participantsA.size() - participantsB.size()));
    }

    private static float calculateOverlap(
            Set<Integer> participantsA,
            Set<Integer> participantsB) {
        int intersectionSize = intersection(participantsA, participantsB).size();
        return (float) 2 * intersectionSize /
                (participantsA.size() + participantsB.size() - Math.abs(participantsA.size() - participantsB.size()));
    }

    private static Set<Integer> intersection(Set<Integer> setA, Set<Integer> setB) {
        Set<Integer> tmp = new HashSet<>();
        setA.forEach(x -> {
            if (setB.contains(x)) {
                tmp.add(x);
            }
        });
        return tmp;
    }

    private static Set<Integer> union(Set<Integer> setA, Set<Integer> setB) {
        Set<Integer> tmp = new HashSet<>(setA);
        tmp.addAll(setB);
        return tmp;
    }
}
