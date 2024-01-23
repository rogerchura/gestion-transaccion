package com.bisa.cam.utils.lang;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class Annotations {
    final static Logger logger = LogManager.getLogger(Annotations.class);

    /**
     * This is a
     *
     * @param type
     * @param target
     * @param <A>
     * @return
     */
    public static <A extends Annotation> Optional<A> findAnnotationInType(Class type, Class<A> target) {

        return (Optional<A>) Arrays.asList(type.getAnnotations())
                .stream()
                .filter(annot -> target.isAssignableFrom(annot.getClass()))
                .findFirst();
    }

    /**
     * Finds annotation of a type if this exists a non empty optional is returned, empty optional otherwise
     * @param e
     * @param annotationType
     * @param <E>
     * @return
     */
    public static <E extends Enum, A extends Annotation> Optional<A> findAnnotationInEnum(E e, Class<? extends A> annotationType){
        try {
            String aname = e.name(); // Enum method to get name of presented enum constant
            Annotation[] annos = e.getClass().getField(aname).getAnnotations(); // Classic
            for (Annotation anno : annos) {
                if(annotationType.isAssignableFrom(anno.getClass()))
                    return (Optional<A>) Optional.ofNullable(anno);
            }
        } catch (NoSuchFieldException ex) {
        }
        return Optional.empty();
    }

    /**
     * find the annotation defined in a target class with a meta annotation associated with the resulting annotation
     * <p>
     * for example:
     *
     * @param targetObjectClass
     * @param metaAnnotationClass
     * @return
     * @AnntationA is annotated with @AnntationB, and ObjectA is annotation with @AnnotationA,
     * of(ObjectA.class, AnnotationB.class) returns AnnotationA.class
     */
    public static Class<? extends Annotation> findAnnotation(
            Class<?> targetObjectClass,
            Class<? extends Annotation> metaAnnotationClass) {

        //return null if the parameters are not valid
        if (targetObjectClass == null || metaAnnotationClass == null) {
            return null;
        }

        //the resulting annotation class
        Class<? extends Annotation> resultingAnnotation = null;

        // get all the annotations defined in the class level for
        // the managed bean objects
        Annotation[] annotations = targetObjectClass.getAnnotations();

        // check all the available annotations
        for (Annotation annotation : annotations) {
            // get the annotation's annotation to check whether it contains
            // the @MetaAnnotation annotation to define the annotation
            Annotation metaAnnotationInstance = annotation.annotationType().getAnnotation(metaAnnotationClass);

            if (metaAnnotationInstance != null) {
                resultingAnnotation = annotation.annotationType();
            }
        }

        //return the resuling annotation
        return resultingAnnotation;
    }

    /**
     * Fetches the annotated value which corresponds from given field or read method
     *
     * @param member the field used to determine the source of data
     * @return the annotation fetched from field or getter method. If annotation is not found, then returns null.
     */
    public static <A extends Annotation, M extends Member> A ofMember(M member, Class<A> annot) {

//        if (java.lang.reflect.Modifier.isStatic(member.getModifiers())) {
//            return null;
//        }

        if (Field.class.isAssignableFrom(member.getClass())) {
            if (((Field) member).getAnnotation(annot) != null) {
                return ((Field) member).getAnnotation(annot);
            }
            //try with read method
            try {
                Method readMethod = Reflections.getGetterMethod(((Field) member));
                if (readMethod.getAnnotation(annot) != null)
                    return readMethod.getAnnotation(annot);
            } catch (IntrospectionException e) {
            }
        } else if (Method.class.isAssignableFrom(member.getClass())) {
            if (((Method) member).getAnnotation(annot) != null)
                return ((Method) member).getAnnotation(annot);
        } else {
            logger.error("Member {} of type {} is not supported", member, member.getClass());
        }

        return null;
    }

    public static <A extends Annotation> boolean isMemberAnnotatedWith(Member member, Class<A> annot){

        member = Objects.requireNonNull(member, "Member should not be null");

        if(AccessibleObject.class.isAssignableFrom(member.getClass())){
            return ((AccessibleObject)member).isAnnotationPresent(annot);
        }

        logger.warn("Only requested two types of members FIELD or METHOD, the {} is not supported", member.getClass());
        return false;
    }

    public static <A extends Annotation> A find(Class<A> clazz, Method method){
        Method inspected = method;

        while(inspected!=null){
            A annotation = inspected.getAnnotation(clazz);
            if(annotation != null)return annotation;

            try {
                Class<?> declaringClass = inspected.getDeclaringClass();

                Class<?>[] interfaces = declaringClass.getInterfaces();
                for (Class<?> interf4ce : interfaces) {
                    inspected = interf4ce.getDeclaredMethod(method.getName(), method.getParameterTypes());
                    annotation = inspected.getAnnotation(clazz);
                    if(annotation != null)return annotation;
                }

                Class<?> superclass = declaringClass.getSuperclass();
                if(superclass==null){
                    inspected = null;
                    continue;
                }
                inspected = superclass.getDeclaredMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
            }
        }

        return null;
    }
}
