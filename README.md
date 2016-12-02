# ras-ulghul
라스-얼굴 프로젝트 [OpenFace](https://cmusatyalab.github.io/openface/) 기반으로 학습한 분류 모델에 대한 웹데모 프로젝트입니다.

자바진영의 spring-boot 프레임워크를 사용하여 만들었습니다.

관련 내용은 [Popit: #엑소사랑하자 – OpenFace로 우리 오빠들 얼굴 인식하기](http://www.popit.kr/openface-exo-member-face-recognition/)에서 확인할 수 있습니다.


## 설치하기
우선 프로젝트를 로컬로 clone합니다.
<pre><code>$ git clone https://github.com/socurites/ras-ulghul.git</code></pre>

웹 데모를 실행하기 전에 OpenFace 설치 위치, 학습된 모델 위치, 테스트할 이미지를 업로드할 위치 등을 변경합니다.
<pre><code>$ vi {RAS_ULGHUL_CLONED_DIRECTORY}/src/main/java/com/socurites/rasulghul/web/controller/RasUlGhulWebController.java</code></pre>

```java
@Controller
public class RasUlGhulWebController {
  // OpenFace가 설치된 디렉토리
  private static final String OPEN_FACE_DIR = "/home/ubuntu/OpenFace/openface/";
  // 학습된 모델이 위치한 디렉토리의 prefix
  private static final String MODEL_DIR = "/home/ubuntu/artist_faces/model/generated-embeddings-";
  // 테스트할 이미지를 업로드할 위치
  // 반드시 {RAS_ULGHUL_CLONED_DIRECTORY}/src/main/webapp/resources/upload/로 설정한다.
  private static final String UPLOAD_DIR = "/home/ubuntu/git/ras-ulghul/src/main/webapp/resources/upload/";</pre></code>

...

  @RequestMapping(value = "/infer", method = RequestMethod.GET)
  @ResponseBody
  public String infer(@RequestParam("fileName") String fileName, @RequestParam("artist") String artist) throws Exception {
    // 학습된 모델 위치의 마지막 이름(artist)는 파라미터로 받는다.
    String command = OPEN_FACE_DIR + "demos/classifier.py infer " + MODEL_DIR + artist + "/classifier.pkl " + UPLOAD_DIR + fileName + " --multi";

    // classifier.py를 실행한다.
    Process p = Runtime.getRuntime().exec(command);

```


## 실행하기
spring-boot 웹 데모를 실행합니다. 기본으로 사용하는 포트는 8080입니다.
<pre><code>$ mvn clean spring-boot:run
[INFO] Attaching agents: []

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.1.4.RELEASE)

2016-12-02 10:58:06.259  INFO 31323 --- [           main] c.s.r.web.RasUlGhulWebApplication        : Starting RasUlGhulWebApplication on island with PID 31323 (/home/socurites/Downloads/ras-ulghul/target/classes started by socurites in /home/socurites/Downloads/ras-ulghul)
2016-12-02 10:58:06.307  INFO 31323 --- [           main] ationConfigEmbeddedWebApplicationContext : Refreshing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@6cc7b4de: startup date [Fri Dec 02 10:58:06 KST 2016]; root of context hierarchy
2016-12-02 10:58:06.851  INFO 31323 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Overriding bean definition for bean 'beanNameViewResolver': replacing [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration$WhitelabelErrorViewConfiguration; factoryMethodName=beanNameViewResolver; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [org/springframework/boot/autoconfigure/web/ErrorMvcAutoConfiguration$WhitelabelErrorViewConfiguration.class]] with [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration$WebMvcAutoConfigurationAdapter; factoryMethodName=beanNameViewResolver; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [org/springframework/boot/autoconfigure/web/WebMvcAutoConfiguration$WebMvcAutoConfigurationAdapter.class]]
2016-12-02 10:58:07.837  INFO 31323 --- [           main] .t.TomcatEmbeddedServletContainerFactory : Server initialized with port: 8080</code></pre>


http://localhost:8080으로 접속하여 테스트할 수 있습니다.

현재 학습한 아티스트 모델은 엑소(exo), 트와이스(twice)입니다. 이미지 업로드 전 테스트할 아티스트 모델을 클릭하여 선택한 후 업로드해야 정상적으로 동작합니다.

