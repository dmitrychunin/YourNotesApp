package ru.performanceLab.yourNote;

import java.lang.reflect.Method;
import java.util.List;

public class Utils {
    public static boolean isMethodOverriden(Method implementation, Method interfaceAbstract) {
        if (!implementation.getName().equals(interfaceAbstract.getName())) {
            return false;
        }
        return equalParamTypes(implementation.getParameterTypes(), interfaceAbstract.getParameterTypes());
    }
    public static boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        if (params1.length != params2.length) {
            return false;
        }
        for (int i = 0; i < params1.length; i++) {
            if (params1[i] != params2[i])
                return false;
        }
        return true;
    }
    public static boolean isOneOfTheMethodsOverriden(List<Method> methods, Method method) {
        for (Method item: methods) {
            if (isMethodOverriden(item, method)) {
                return true;
            }
        }
        return false;
    }
}
