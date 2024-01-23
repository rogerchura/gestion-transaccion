package com.bisa.cam.utils.lang;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Reflections {
    static final Logger LOGGER = LogManager.getLogger(Reflections.class);

    static final String IS_PREFIX = "is";
    static final String GET_PREFIX = "get";
    static final String SET_PREFIX = "set";

    /**
     * Retrieves the values from a {@link Enum} type
     * @param enumClass
     * @param <E>
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @see <a href="https://www.logicbig.com/how-to/code-snippets/jcode-reflection-values-field-in-enum.html">Reflection to fecth enum values</a>
     */
    public static <E extends Enum> E[] getEnumValues(Class<E> enumClass) {
        try {
            Field f = enumClass.getDeclaredField("$VALUES");
            f.setAccessible(true);
            Object o = f.get(null);
            return (E[]) o;
        }catch(NoSuchFieldException e){
            LOGGER.warn("returning NULL by default", e);
            return null;
        }catch(IllegalAccessException e){
            LOGGER.warn("returning NULL by default", e);
            return null;
        }
    }

    /**
     * This method retrieves all declared fields including those which belong to
     * the super classes.
     *
     * @param type
     * @return
     * @deprecated use {@link #getAllFields(Class)} or {@link #getFields(Class)} instead
     */
    @Deprecated
    public static List<Field> getDeclaredFields(Class<?> type) {
        List<Field> fields = new ArrayList();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * Returns the fields from type and super class inclusive
     * @param type
     * @return
     */
    public static Set<Field> getAllFields(Class<?> type) {
        return getAllFields(type, (field)->true);
    }

    public static Set<Field> getAllFields(Class<?> type, Predicate<Field> predicate) {
        Set<Field> fields = new HashSet<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields.stream().filter(predicate).collect(Collectors.toSet());
    }

    /**
     *Returns the fields from given class only
     * @param type
     * @return
     */
    public static List<Method> getFields(Class<?> type){
        return Arrays.asList(type.getDeclaredMethods());
    }

    /**
     * Returns the method declared within given class
     * @param type
     * @return
     */
    public static List<Method> getMethods(Class<?> type){
        return Arrays.asList(type.getMethods());
    }

    /**
     * @param provider
     * @param field
     * @param <T>
     * @return
     * @throws ReflectionException
     */
    public static <T> T fetch(Object provider, Field field) throws ReflectionException {
        AtomicBoolean accesible = new AtomicBoolean(field.isAccessible());
        try {
            field.setAccessible(true);
            return (T) field.get(provider);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        } finally {
            field.setAccessible(accesible.get());
        }
    }

    /**
     * @param assignable
     * @param value
     * @param field
     * @param <T>
     * @throws ReflectionException
     */
    public static <T> void assign(Object assignable, T value, Field field) throws ReflectionException {
        AtomicBoolean accesible = new AtomicBoolean(field.isAccessible());
        try {
            field.setAccessible(true);
            field.set(assignable, value);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        } finally {
            field.setAccessible(accesible.get());
        }
    }

    /**
     * Retrieves all the Property descriptors of a type
     *
     * @param type
     * @return
     */
    public static Iterable<PropertyDescriptor> inspectType(final Class<?> type) {
        return new Iterable<PropertyDescriptor>() {

            public Iterator<PropertyDescriptor> iterator() {
                try {
                    return Arrays.asList(Introspector.getBeanInfo(type).getPropertyDescriptors()).iterator();
                } catch (IntrospectionException e) {
                    //throw new IllegalStateException(of("Unable to inspect given type: '").add(type).add("'").build(), e);
                    LOGGER.debug("Unable to inspect given type: '" + type.getName() + "'", e);
                    return Collections.emptyIterator();
                }
            }
        };
    }

    /**
     * @param field field
     * @return
     * @throws IntrospectionException
     */
    public static Method getGetterMethod(Field field) throws IntrospectionException {
        return getGetterMethod(field.getName(), field.getDeclaringClass());
    }

    /**
     * returns the GET or IS prefixed method for given field name. If  method does not exist, returns null;
     *
     * @param fieldName
     * @param ownerType
     * @return
     * @throws IntrospectionException
     */
    public static Method getGetterMethod(String fieldName, Class ownerType) throws IntrospectionException {
//        return new PropertyDescriptor(fieldName, ownerType).getReadMethod();
        try {
            return ownerType.getMethod(GET_PREFIX + NameGenerator.capitalize(fieldName));
        } catch (NoSuchMethodException e) {
            try {
                return ownerType.getMethod(IS_PREFIX + NameGenerator.capitalize(fieldName));
            } catch (NoSuchMethodException e1) {
                throw new IntrospectionException(e.getMessage());
            }
        }
    }

    /**
     * A method to build a possible set method name
     *
     * @param name the name to be used for getter
     * @param type the type to be used for getter
     * @return
     */
    public static String getSetterMethodName(String name, Class type) {
        assert name != null && type != null;

        return SET_PREFIX + NameGenerator.capitalize(name);
    }

    /**
     * Retrieves the field setter method, if and only if write methdod is public and is void type.
     * {@link PropertyDescriptor} class validates this as follows: if (!writeMethod.getReturnType().equals(void.class)) {
     *
     * @param field
     * @return
     * @throws IntrospectionException
     */
    public static Method getSetterMethod(Field field) throws IntrospectionException {
        return getGetterMethod(field.getName(), field.getDeclaringClass());
    }

    /**
     * Retrieves the field setter method, if and only if write methdod is public and is void type.
     * {@link PropertyDescriptor} class validates this as follows: if (!writeMethod.getReturnType().equals(void.class)) {
     *
     * @param fname     field name
     * @param ownerType field declaring class
     * @return
     * @throws IntrospectionException
     */
    public static Method getSetterMethod(String fname, Class ownerType) throws IntrospectionException {
        //return new PropertyDescriptor(fname, ownerType).getWriteMethod();
        try {
            return ownerType.getMethod(SET_PREFIX + NameGenerator.capitalize(fname));
        } catch (NoSuchMethodException e) {
            throw new IntrospectionException(e.getMessage());
        }
    }

    /**
     * A method to build a possible get/is  method name, up to the given type
     *
     * @param name
     * @param type
     * @return
     */
    public static String getGetterMethodName(String name, Class type) {
        //return getSetterMethod(fname, ftype).value();

        assert name != null && type != null;

        if (type == boolean.class || type == null) {
            return IS_PREFIX + NameGenerator.capitalize(name);
        } else {
            return GET_PREFIX + NameGenerator.capitalize(name);
        }
    }

    /**
     * @param fname
     * @param ftype
     * @return
     * @deprecated use by {@link #getGetterMethodName(String, Class)}
     */
    @Deprecated
    public static String getGetterName(String fname, Class ftype) {
        String getterName = ftype.isAssignableFrom(Boolean.class) || ftype.isAssignableFrom(boolean.class) ? "is" : "get";
        getterName += fname.substring(0, 1).toUpperCase();
        getterName += fname.substring(1);
        return getterName;
    }

    /**
     * @param fieldName
     * @return
     * @deprecated replaced by {@link #getSetterMethodName(String, Class)}
     */
    @Deprecated
    public static String getSetterName(String fieldName) {
        String setterName = "set";
        setterName += fieldName.substring(0, 1).toUpperCase();
        setterName += fieldName.substring(1);
        return setterName;
    }

    /**
     * Returns all the generic types found in a class declaration
     * @param clazz
     * @return
     */
    public static Set<Class<?>> getGenericTypesOf(Class clazz) {
        Set<Class<?>> genericTypes = new LinkedHashSet<>();
        Type[] actualTypeArguments = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
        for (Type actualTypeArgument : actualTypeArguments) {
            genericTypes.add((Class<?>) actualTypeArguments[0]);
        }
        return genericTypes;
    }

    public static <T> Class<T> getFirstTypeOfMethod(Method method) {
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (int i = 0; i < genericParameterTypes.length; i++) {
            if (genericParameterTypes[i] instanceof ParameterizedType) {
//                Type[] parameters = ((ParameterizedType) genericParameterTypes[i]).getActualTypeArguments();
                return (Class<T>) ((ParameterizedType) genericParameterTypes[i]).getActualTypeArguments()[0];
            }
        }
        //todo those are two methods for reflections
        return null;
    }
}