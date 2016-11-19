package com.angkorteam.mbaas.server;

import org.springframework.beans.BeansException;

/**
 * Created by socheat on 11/19/16.
 */
public class Spring {

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        throw new UnsupportedOperationException();
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        throw new UnsupportedOperationException();
    }
}
