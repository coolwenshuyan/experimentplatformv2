package com.coolwen.experimentplatformv2.annotation;
import java.lang.annotation.*;

/**
 * @author Artell
 * @version 2020/12/30 10:20
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    String value() default "";
}
