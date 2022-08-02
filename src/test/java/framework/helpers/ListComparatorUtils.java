package framework.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static framework.constants.BaseConstants.EARLIEST_DATE;
import static framework.constants.BaseConstants.EMPTY_STRING;


public class ListComparatorUtils<T> {
    public static boolean isSortedAscending(final List<String> list) {
        list.removeAll(Collections.singleton(EMPTY_STRING));
        List<String> orderedList = new ArrayList<>(list);
        Collections.sort(orderedList, String.CASE_INSENSITIVE_ORDER);
        return list.equals(orderedList);
    }

    public static boolean isSortedDescending(final List<String> list) {
        list.removeAll(Collections.singleton(EMPTY_STRING));
        List<String> orderedList = new ArrayList<>(list);
        Collections.sort(orderedList, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        return list.equals(orderedList);
    }

    public static boolean isDateSortedAscending(final List<LocalDateTime> list) {
        List<LocalDateTime> orderedList = new ArrayList<>(list);
        Collections.sort(orderedList);
        return list.equals(orderedList);
    }

    public static boolean isDateSortedDescending(final List<LocalDateTime> list) {
        List<LocalDateTime> orderedList = new ArrayList<>(list);
        Collections.sort(orderedList, Collections.reverseOrder());
        return list.equals(orderedList);
    }

    public static boolean isDateSortedAscending(final List<String> list, final String pattern) {
        list.removeAll(Collections.singleton(EMPTY_STRING));
        List<LocalDate> dates = list.stream()
            .map(date -> date.trim().isEmpty() ? EARLIEST_DATE : date)
            .map(date -> DateUtils.getDateFromString(date, pattern))
            .collect(Collectors.toList());
        List<LocalDate> orderedList = new ArrayList<>(dates);
        Collections.sort(orderedList);
        return dates.equals(orderedList);
    }

    public static boolean isDateSortedDescending(final List<String> list, final String pattern) {
        list.removeAll(Collections.singleton(EMPTY_STRING));
        List<LocalDate> dates = list.stream()
            .map(date -> date.trim().isEmpty() ? EARLIEST_DATE : date)
            .map(x -> DateUtils.getDateFromString(x, pattern))
            .collect(Collectors.toList());
        List<LocalDate> orderedList = new ArrayList<>(dates);
        Collections.sort(orderedList, Collections.reverseOrder());
        return dates.equals(orderedList);
    }

    public List<T> combineLists(final List<T> firstList, final List<T> secondList) {
        List<T> combinedList = Stream.of(firstList, secondList)
            .flatMap(item -> item.stream())
            .collect(Collectors.toList());
        return combinedList;
    }

    public static <T> boolean hasListDuplicateValues(final List<T> list) {
        Set<T> recordsWithoutDuplication = new HashSet<T>(list);
        return recordsWithoutDuplication.size() != list.size();
    }
}