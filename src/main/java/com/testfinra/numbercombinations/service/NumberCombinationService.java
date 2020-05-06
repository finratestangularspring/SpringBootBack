package com.testfinra.numbercombinations.service;

import com.testfinra.numbercombinations.domain.NumberCombination;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NumberCombinationService {

    public Mono<NumberCombination> getNumberCombinations(Map<String, String> pathVars) {

        return Mono.just(combineNumber(pathVars));
    }


    /***
     * Combines all the possible numbers for a given phone number.
     *
     * @param pathVars
     * @return a list of all the possible combinations
     */
    // TO-DO Ask what kind of "Alpha numeric" combination it refers to?
    // The same number in different position?
    // The letters A-Z the numbers represent?
    // The phone number formatted like +1 (234) 567-8900 ?
    // The numbers plus the letters they represent?
    // Repeated digits? non-duplicated?
    private NumberCombination combineNumber(Map<String, String> pathVars) {
        String phoneNumber = pathVars.get("id").trim();
        String index = pathVars.get("index").trim();

        Long number;
        int idx;
        Set<String> set = new TreeSet<>();

        try {
            if (phoneNumber.length() >= 7) {
                number = Math.abs(Long.parseLong(phoneNumber));

            } else {
                number = 0L;
            }

            idx = Math.abs(Integer.parseInt(index));

        } catch (Exception e) {
            number = 0L;
            idx = 0;
            System.out.println("Something went wrong in the parsing");
        }


        if (number > 0 && idx < phoneNumber.length() - 1) {

            set = combine(phoneNumber);
        }

        List<String> list = set.stream()
                .collect(Collectors.toList());

        if (list.size() > (idx + 1) * 1000) {
            list = list.subList(idx * 1000, (idx + 1) * 1000);
        } else if (list.size() > (idx) * 1000) {
            list = list.subList(idx * 1000, list.size());
        } else {
            list = new ArrayList<>();
        }

        NumberCombination nc = new NumberCombination("" + set.size(), list);

        return nc;
    }


    /***
     * TO-DO store the combinations in a DB, so all the memory is not consumed with big numbers.
     * also, only once the combination will be calculated, the other times we just query the DB.
     *
     * @param phoneNumber
     * @return Set<String>
     */
    private static Set<String> combine(String phoneNumber)
    {
        Set<String> set = new TreeSet<>();

        if (phoneNumber.length() == 1) {
            set.add(phoneNumber);
        } else {
            for (int i = 0; i < phoneNumber.length(); i++) {

                String before = phoneNumber.substring(0, i);
                String after = phoneNumber.substring(i + 1);
                String pending = before + after;

                for (String s : combine(pending)) {
                    set.add(phoneNumber.charAt(i) + s);
                }
            }
        }

        return set;
    }
}
