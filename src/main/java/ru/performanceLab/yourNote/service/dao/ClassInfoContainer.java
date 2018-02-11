package ru.performanceLab.yourNote.service.dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassInfoContainer {
    private Class classObj;
    private List<Method> methods = new ArrayList<>();

    public Class getClassObj() {
        return classObj;
    }

    public void setClassObj(Class classObj) {
        this.classObj = classObj;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void addMethod(Method method) {
        this.methods.add(method);
    }
}
