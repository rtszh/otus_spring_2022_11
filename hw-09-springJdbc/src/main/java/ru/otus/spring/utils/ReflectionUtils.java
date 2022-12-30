package ru.otus.spring.utils;

import ru.otus.spring.exceptions.UnknownFieldException;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static Object getPrivateField(Field field, Object object) {
        field.setAccessible(true);

        Object result;

        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            throw new UnknownFieldException();
        }

        return result;
    }
}
