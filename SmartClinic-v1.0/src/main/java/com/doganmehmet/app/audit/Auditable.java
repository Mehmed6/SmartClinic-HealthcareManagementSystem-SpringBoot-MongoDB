package com.doganmehmet.app.audit;

import com.doganmehmet.app.entity.UserProfile;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auditable {
    String action() default "";
    String entity() default  "";
    String description() default "";
}
