package com.college.erp.security;

import com.college.erp.entity.enums.UserStatus;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireStatus {
    UserStatus[] value();
}
