package com.example.restdocssample.common.annotation;

import com.example.restdocssample.codes.service.model.BaseCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Java enum class 를 http response 로 전달합니다. /codes API 를 사용하여 코드를 전달 받을 수 있습니다.
 * 전제 조건
 *   1. 특정 패키지 (CodeQueryService.CODE_PACKAGE 참고)
 *   2. CodeClass 어노테이션
 *   3. static factory method 제공
 *
 * 사용 방법
 *   1. CommonCode 를 구현한 enum class 정의
 *   2. BaseCode 또는 상속하는 class 정의
 *   3. static factory method 정의
 *   4. enum class 에 CodeClass 어노테이션을 추가
 *   5. 2번, 3번에서 작성한 내용을 CodeClass의 value, methodName 에 세팅
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeClass {
    Class value() default BaseCode.class;
    String methodName() default "of";
}
