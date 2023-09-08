package com.qinchy.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class HttpAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        try {
            // debug 需要暂停, 方便启动时 debug
            Thread.sleep(5000);
        } catch (Exception ignore) {
        }

        new AgentBuilder.Default().type(nameStartsWith("com.qinchy.springcloudnacosdubbo.consumer.controller")).transform((builder, typeDescription, classLoader, module) -> {
            builder = builder.visit(Advice.to(MyAdvice.class).on(isMethod().and(not(isConstructor())).and(not(isStatic())).and(not(isSetter())).and(not(isGetter())).and(not(isToString())).and(any()).and(not(nameStartsWith("main")))));
            return builder;
        }).installOn(inst);
    }

}
