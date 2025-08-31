package com.learn.newsfeed.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnvironmentResolve {
    private static final Pattern ENV_PATTERN = Pattern.compile("\\$\\{([^:}]+)(?::([^}]*))?}");
    public static String resolve(String input) {
        Matcher matcher = ENV_PATTERN.matcher(input);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String varName = matcher.group(1);
            String defaultValue = matcher.group(2);
            String resolved = System.getenv(varName);

            if (resolved == null) {
                resolved = defaultValue != null ? defaultValue : "";
            }
            matcher.appendReplacement(result, Matcher.quoteReplacement(resolved));
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
