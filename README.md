# Restdocs Sample

## 환경 설정
**1. build.gradle에 restdocs 관련 설정 추가**
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '2.7.7'
    id 'io.spring.dependency-management' version '1.0.15.RELEASE'
    id 'org.asciidoctor.convert' version '1.5.8' // (1)
}

...

ext {
    set('snippetsDir', file("build/generated-snippets"))
}

...

tasks.named('asciidoctor') {
    inputs.dir snippetsDir
    dependsOn test // (2)
}

...

bootJar {
    dependsOn asciidoctor // (3)
    copy {
        from "${asciidoctor.outputDir}" // (4)
        into 'BOOT-INF/classes/static/docs'
    }
}

...

dependencies {
    ...
    testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc' // (5)
}
```
(1) AsciiDoc 파일을 컨버팅하고 Build 폴더에 복사하기 위한 플러그인입니다.        
(2) gradle build 시 test -> asciidoctor 순으로 수행됩니다.          
(3) gradle build 시 asciidoctor -> bootJar 순으로 수행됩니다.             
(4) gradle build 시 ```./build/asciidoc/``` 에 html 파일이 생깁니다. 이것을 jar 안에 ```BOOT-INF/classes/static/docs``` 폴더에 복사가 됩니다.              
(5) mockmvc 를 restdocs 에 사용할수 있게 하는 라이브러리입니다.           

**2. 테스트 작성 시 공통 유틸**
```java
public interface ApiDocumentUtils {

    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                        modifyUris() // (1)
                                .scheme("https")
                                .host("docs.api.com")
                                .removePort(),
                        prettyPrint()); // (2)
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint()); // (3)
    }
}
```
(1) 문서상 uri 를 기본값인 http://localhost:8080 에서 https://docs.api.com 으로 변경하기 위해 사용합니다.             
(2) 문서의 request 을 예쁘게 출력하기 위해 사용합니다.              
(3) 문서의 response 를 예쁘게 출력하기 위해 사용합니다.

## 기본 작성법

## 