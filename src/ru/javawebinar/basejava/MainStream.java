package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{9, 8}));
        System.out.println(minValue(new int[]{1, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{3, 2, 3, 3, 2, 1}));
        System.out.println(minValue(new int[]{9, 2, 3, 3, 2, 5, 1, 5}));

        System.out.println(oddOrEven(Arrays.asList(9, 8)));
        System.out.println(oddOrEven(Arrays.asList(3, 2, 3, 3, 2, 1)));
        System.out.println(oddOrEven(Arrays.asList(9, 2, 3, 3, 2, 5, 1, 5)));

        System.out.println(oddCount(new int[]{9, 2, 4, 3, 2, 5, 1, 5}));  // odd - 5
        System.out.println(evenCount(new int[]{9, 2, 4, 3, 2, 5, 1, 5}));  // even - 3

    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (left, right) -> 10 * left + right);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        System.out.println(integers.stream().reduce(0, Integer::sum));
        Map<Boolean, List<Integer>> integersMap = integers.stream()
                .collect(Collectors.partitioningBy(p -> p % 2 != 0, Collectors.toList()));
        return integersMap.get(integersMap.get(true).size() % 2 == 0);
    }

    private static int oddCount(int[] integers) {
        return (int) Arrays.stream(integers).filter((p) -> p % 2 != 0).count();
    }

    private static int evenCount(int[] integers) {
        return (int) Arrays.stream(integers).filter((p) -> p % 2 == 0).count();
    }
}