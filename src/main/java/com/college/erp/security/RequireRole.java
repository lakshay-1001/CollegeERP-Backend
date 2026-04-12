package com.college.erp.security;

import com.college.erp.entity.enums.Role;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRole {
    Role[] value();
}
