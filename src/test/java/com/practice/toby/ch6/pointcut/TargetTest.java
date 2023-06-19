package com.practice.toby.ch6.pointcut;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import static org.junit.jupiter.api.Assertions.*;

class TargetTest {

    @Test
    @DisplayName("포인트 컷 테스트")
    public void pointcutTest() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public int com.practice.toby.ch6.pointcut.Target.minus(int, int) throws java.lang.RuntimeException)");
        Assertions.assertThat(pointcut.getClassFilter().matches(Target.class)
                && pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null)).isTrue();
    }
}