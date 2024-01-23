package com.bisa.cam.utils.lang;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Strings {
    /**
     * @param string
     * @param charCount
     * @return
     * @throws NullPointerException
     */
    public static Collection<String> tokenize(String string, int charCount) throws NullPointerException {
        string = Objects.requireNonNull(string, "Need to pass a non null string");

        Collection<String> splits = new LinkedList<>();
        AtomicInteger counted = new AtomicInteger(0);

        while (string.length() - counted.get() > 0) {
            int end = Math.min(string.length() - counted.get(), charCount);
            splits.add(string.substring(counted.get(), counted.addAndGet(end)));
        }

        return splits;
    }

    /**
     * @param object should not be NULL, because {@link NullPointerException} will be thrown
     * @return
     * @throws NullPointerException
     */
    public static boolean isString(Object object) throws NullPointerException {
        return String.class.isAssignableFrom(object.getClass());
    }

    /**
     * @deprecated use {@link #arrange(String, Object...)} method instead
     */
    @Deprecated
    public static String format(String string, Object... values) {
        return arrange(string, values);
    }

    /**
     * Simply builds a String with the given values
     *
     * @param string
     * @param values
     */
    public static String arrange(String string, Object... values) {
        Pattern pattern = Pattern.compile("\\{\\}", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);

        StringBuilder builder = new StringBuilder();
        AtomicInteger index = new AtomicInteger(0);

        Queue queue = Arrays.asList(values).stream().map(item -> Optional.ofNullable(item).orElse("null")).collect(Collectors.toCollection(ArrayDeque::new));

        while (matcher.find()) {
            builder.append(string, index.getAndSet(matcher.start() + 2), matcher.start());
            builder.append(queue.poll());
        }
        builder.append(string, index.get(), string.length());

        return builder.toString();
    }

    public static String camelCase(String str) {
        return camelCase(str, "\\s+");
    }

    public static String camelCase(String str, String regexSeparator) {
        return (str == null || str.isEmpty()) ? "" : Arrays.stream(str.trim().split(regexSeparator))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining());
    }
}
