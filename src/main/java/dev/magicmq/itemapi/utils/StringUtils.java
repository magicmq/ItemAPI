package dev.magicmq.itemapi.utils;

import java.util.regex.Matcher;

public class StringUtils {

    private static final java.util.regex.Pattern timePattern = java.util.regex.Pattern.compile("\\G([0-9]+)(seconds|minutes|hours|days|sec|min|hour|day|s|m|h|d)");

    public static int parseTime(String input) {
        input = input.toLowerCase();

        int ticks = 0;
        Matcher matcher = timePattern.matcher(input);
        while (matcher.find()) {
            int num = Integer.parseInt(matcher.group(1));
            String dur = matcher.group(2);
            if (dur.equals("seconds") || dur.equals("sec") || dur.equals("s")) {
                ticks += num * 20;
            } else if (dur.equals("minutes") || dur.equals("min") || dur.equals("m")) {
                ticks += num * (20 * 60);
            } else if (dur.equals("hours") || dur.equals("hour") || dur.equals("h")) {
                ticks += num * (20 * 3600);
            } else if (dur.equals("days") || dur.equals("day") || dur.equals("d")) {
                ticks += num * (20 * 86400);
            }
        }

        return ticks;
    }

    public static String formatTicks(int ticks) {
        return (ticks * 20L) + "s";
    }
}
