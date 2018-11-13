package com.example.liuwen.two.EventBus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/08/09 17:19
 * desc   :
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindEventBus {

}
