package com.qinchy.agent;

import net.bytebuddy.asm.Advice;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class MyAdvice {

    // 方法执行前
    @Advice.OnMethodEnter
    public static void enter(@Advice.This Object obj, @Advice.AllArguments Object[] allArguments, @Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        System.out.println("OnMethodEnter -> " + className + "." + methodName);
    }

    // 方法执行后, inline = true 必须使用默认, 否则会出错
    @Advice.OnMethodExit
    public static void exit(@Advice.This Object obj, @Advice.AllArguments Object[] allArguments,
                            @Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        System.out.println("OnMethodExit -> " + className + "." + methodName);
        System.out.println("OnMethodExit -> " + obj + "." + allArguments);
        for (Object argument : allArguments) {
            System.out.println(argument.getClass());
            if (argument instanceof HttpServletResponse) {
                HttpServletResponse response = (HttpServletResponse) argument;
                response.setHeader("t_id", UUID.randomUUID().toString());
                response.setHeader("t_c_n", className);
                response.setHeader("t_m_n", methodName);
                response.setHeader("DevGroup", "Dev");
            }
        }
    }
}
