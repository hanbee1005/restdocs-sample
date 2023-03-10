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
[Spring Restdocs 문서](https://docs.spring.io/spring-restdocs/docs/1.0.0.BUILD-SNAPSHOT/reference/html5/#documenting-your-api)
### Snippet 구조
- 일반 String, Number
  - 기본 String 은 type 을 JsonFieldType.STRING 으로 지정하면 되고
  - 숫자 타입(int, long 등)은 JsonFieldType.NUMBER 으로 지정하면 됩니다.
  ```
  fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 아이디"),
  fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
  fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
  fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
  ```
- Object
  - 객체 내 각 필드들을 .을 찍어서 이어 붙여 표시합니다.
  ```
  fieldWithPath("personal").type(JsonFieldType.OBJECT).description("개인정보"),
  fieldWithPath("personal.phoneNumber").type(JsonFieldType.STRING).description("전화번호"),
  fieldWithPath("personal.email").type(JsonFieldType.STRING).description("이메일")
  ```
- Array
  - List 내 값이 일반 Integer, String 등이라고 한다면 JsonFieldType.ARRAY 하나만 사용하면 되고
  - List 내 값이 또 다른 객체라면 객체 내 값들의 필드 이름과 타입을 차례대로 지정하면 됩니다.
  ```
  fieldWithPath("hobby").type(JsonFieldType.ARRAY).description("취미"),
  
  fieldWithPath("address").type(JsonFieldType.ARRAY).description("주소"),
  fieldWithPath("address[].type").type(JsonFieldType.STRING).description("주소 타입"),
  fieldWithPath("address[].sido").type(JsonFieldType.STRING).description("시도명"),
  fieldWithPath("address[].sigungu").type(JsonFieldType.STRING).description("시군구명"),
  fieldWithPath("address[].road").type(JsonFieldType.STRING).description("도로명")
  ```

### Path Variable 설정
***org.springframework.restdocs.request.RequestDocumentation.pathParameters;*** 사용
```
this.mockMvc.perform(get("/locations/{latitude}/{longitude}", 51.5072, 0.1275)) 
    .andExpect(status().isOk())
    .andDo(document("locations", pathParameters( 
            parameterWithName("latitude").description("The location's latitude"), 
            parameterWithName("longitude").description("The location's longitude") 
    )));
```

### Query Param 설정
***org.springframework.restdocs.request.RequestDocumentation.requestParameters;*** 사용
```
this.mockMvc.perform(get("/users?page=2&per_page=100")) 
    .andExpect(status().isOk())
    .andDo(document("users", requestParameters( 
            parameterWithName("page").description("The page to retrieve"), 
            parameterWithName("per_page").description("Entries per page") 
    )));
```

### Request Body 설정
***org.springframework.restdocs.request.RequestDocumentation.requestParameters;*** 사용
```
this.mockMvc.perform(post("/users").param("username", "Tester")) 
    .andExpect(status().isCreated())
    .andDo(document("create-user", requestParameters(
            parameterWithName("username").description("The user's username")
    )));
```

### Response Body 설정
***org.springframework.restdocs.payload.PayloadDocumentation.responseFields;*** 사용
```
this.mockMvc.perform(get("/user/5").accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andDo(document("index", responseFields( 
            fieldWithPath("contact").description("The user's contact details"), 
            fieldWithPath("contact.email").description("The user's email address"))));
```

## 개선 사항
- 필수값 여부
  - ```.optional()```을 붙여서 필수값  여부를 지정할 수 있고 이를 표시하기 위한 스니펫 커스터마이징을 해야합니다.
  - request-fields 의 스니펫을 customizing 하려면 ```src/test/resources/org/springframework/restdocs/templates``` 경로에 ```request-fields.snippet``` 파일을 추가하면 됩니다.
    ```
    |===
    |Name|Type|Optional|Description
    
    {{#fields}}
    |{{#tableCellContent}}`+{{path}}+`{{/tableCellContent}}
    |{{#tableCellContent}}`+{{type}}+`{{/tableCellContent}}
    |{{#tableCellContent}}`+{{optional}}+`{{/tableCellContent}}
    |{{#tableCellContent}}{{description}}{{/tableCellContent}}
    
    {{/fields}}
    
    |===
    ```
- 입력 포맷 지정
  - attribute 로 사용될 key, value 를 static 으로 지정합니다. (자주 쓰는 경우)
    ```
    import org.springframework.restdocs.snippet.Attributes;
    import static org.springframework.restdocs.snippet.Attributes.key;

    public interface DocumentFormatGenerator {
    
        static Attributes.Attribute getDateFormat() { // (2)
            return key("format").value("yyyy-MM-dd");
        }
    }
    ```
  - attributes() 속성을 추가합니다.
    ```
    fieldWithPath("birthdate").type(JsonFieldType.STRING).description("생년월일").attributes(getDateFormat())
    ```
  - 이후 이것이 표시될 snippet 을 수정합니다.
    ```
    |===
    |Name|Type|Description|Optional|Format
    
    {{#fields}}
    |{{#tableCellContent}}`+{{path}}+`{{/tableCellContent}}
    |{{#tableCellContent}}`+{{type}}+`{{/tableCellContent}}
    |{{#tableCellContent}}{{description}}{{/tableCellContent}}
    |{{#tableCellContent}}{{optional}}{{/tableCellContent}}
    |{{#tableCellContent}}{{#format}}{{.}}{{/format}}{{/tableCellContent}}
    
    {{/fields}}
    
    |===
    ```
- 입력 코드 표기
  - enum list 를 반환하는 controller 를 test 패키지에 생성한 뒤 이를 호출하는 테스트를 작성합니다.
    ```
    @WebMvcTest(EnumViewController.class)
    @AutoConfigureRestDocs
    public class CommonDocumentationTest {
    @Autowired
    private MockMvc mockMvc;
    
        @Test
        @DisplayName("enum 목록 조회")
        public void enums() throws Exception {
            // given
            this.mockMvc.perform(get("/codes").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(document("common",
                            customResponseFields("enum-response", beneathPath("genders").withSubsectionId("genders"), // (2)
                                    attributes(key("title").value("성별")),
                                    enumConvertFieldDescriptor(Gender.values())
                            ),
                            customResponseFields("enum-response", beneathPath("address").withSubsectionId("address"), // (2)
                                    attributes(key("title").value("주소")),
                                    enumConvertFieldDescriptor(Address.values())
                            )
                    ));
        }
    
        private FieldDescriptor[] enumConvertFieldDescriptor(EnumType[] enumTypes) {
            return Arrays.stream(enumTypes)
                    .map(enumType -> fieldWithPath(enumType.getCode()).description(enumType.getDesc()))
                    .toArray(FieldDescriptor[]::new);
        }
    
        public static CustomResponseFieldsSnippet customResponseFields(String type,
                                                                       PayloadSubsectionExtractor<?> subsectionExtractor,
                                                                       Map<String, Object> attributes, FieldDescriptor... descriptors) {
            return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
                    , true);
        }
    }
    ```
  - response-fields 스니펫을 이미 선언했기에 모든 응답필드는 해당 response-fields 를 사용하게 됩니다.
    그렇게 되면 불필요한 포맷과 필수 여부가 테이블에 나타나서 보기가 안좋습니다.
    그래서 특정 이름의 스니펫을 사용할수 있게 CustomResponseFieldsSnippet 를 만들었습니다.
    ```
    public class CustomResponseFieldsSnippet extends AbstractFieldsSnippet {

        public CustomResponseFieldsSnippet(String type, PayloadSubsectionExtractor<?> subsectionExtractor,
                                           List<FieldDescriptor> descriptors, Map<String, Object> attributes,
                                           boolean ignoreUndocumentedFields) {
            super(type, descriptors, attributes, ignoreUndocumentedFields,
                    subsectionExtractor);
        }
    
        @Override
        protected MediaType getContentType(Operation operation) {
            return operation.getResponse().getHeaders().getContentType();
        }
    
        @Override
        protected byte[] getContent(Operation operation) throws IOException {
            return operation.getResponse().getContent();
        }
    }
    ```
    enum-response-fields.snippet
    ```
    {{title}}
    |===
    |코드|코드명
  
    {{#fields}}
    |{{#tableCellContent}}`+{{path}}+`{{/tableCellContent}}
    |{{#tableCellContent}}{{description}}{{/tableCellContent}}
  
    {{/fields}}
    |===
    ```
- 공통 포맷 지정
  - 공통 포맷만 반환하는 컨트롤러를 만들고 이에 대한 테스트를 작성합니다.
    ```
    resultActions.andExpect(status().isOk())
                .andDo(document(documentPath,
                        customResponseFields("common-response", null,
                                attributes(key("title").value("공통 응답")),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지"),
                                subsectionWithPath("data").description("데이터"))))
                .andDo(print());
    ```
  - 이후 공통 포맷이 쓰이는 api에 다음과 같이 추가를 해줍니다.
    ```
    resultActions.andExpect(status().isOk())
                .andDo(document("members/find-by-id",
                        ...
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
    ...
    ```
## 참고
- https://techblog.woowahan.com/2597/
- https://techblog.woowahan.com/2678/
- https://github.com/hojinDev/restdocs-sample
- https://htmlpreview.github.io/?https://github.com/hojinDev/restdocs-sample/blob/master/html/step3.html
- [개인정리](https://developerbee.tistory.com/250)
