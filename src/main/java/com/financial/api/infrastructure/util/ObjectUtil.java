package com.financial.api.infrastructure.util;

import java.lang.reflect.Field;

public class ObjectUtil {

    public static <T> T copy(T source, T target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("source and target are null");
        }

        for (final Field field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return target;
    }
}
