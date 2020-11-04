package dev.daehoon.customparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class Solution {

    public String solution(String[] votes, int k) {

        Map<String, Integer> votesMap = getCountedMap(votes);

        int votesCount = getVotesCount(votesMap, k);

        Map<String, Integer> sortedMap = sorting(votesMap);

        return getResult(sortedMap, votesCount);
    }

    public Map<String, Integer> getCountedMap(String[] votes) {

        Map<String, Integer> votesMap = new TreeMap<>((s1, s2) -> s2.compareTo(s1));

        for (int i = 0; i < votes.length; i++) {
            Integer cnt = votesMap.getOrDefault(votes[i], 0);
            cnt++;
            votesMap.put(votes[i], cnt);
        }

        return votesMap;
    }

    public int getVotesCount(Map<String, Integer> votesMap, int k) {

        List<Integer> voteCounts = new ArrayList<>(votesMap.values());

        Collections.sort(voteCounts, Collections.reverseOrder());

        int sum = 0;

        for (int i = 0; i < k; i++) {
            sum = sum + voteCounts.get(i);
        }

        return sum;
    }

    public Map<String, Integer> sorting(Map<String, Integer> map) {

        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, (o1, o2) -> {

            int comparision = (o1.getValue() - o2.getValue()) * -1;
            return comparision == 0 ? o1.getKey().compareTo(o2.getKey()) : comparision;
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<>();

        for (Iterator<Map.Entry<String, Integer>> iter = list.iterator(); iter.hasNext(); ) {
            Map.Entry<String, Integer> entry = iter.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public String getResult(Map<String, Integer> map, int sum) {

        int p = 0;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            p = p + entry.getValue();

            if (p > sum) {
                return key;
            }
        }

        return null;

    }
}