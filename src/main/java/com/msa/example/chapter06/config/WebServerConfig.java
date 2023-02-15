package com.msa.example.chapter06.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.example.chapter06.controller.resolver.ClientInfoArgumentResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;
import java.util.Locale;

@Configuration
public class WebServerConfig implements WebMvcConfigurer {
    /*
    * 사용자 요청의 URI, 파라미터, HTTP 메소드를 바탕으로 어떤 핸들러 메소드를 매핑할 지 결정하는 설정 메소드
    * */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        /*
        * PathMatchConfigurer.setUseTrailingSlashMatch()
        * : 끝이 /로 끝나는 요청 처리 방법 선언
        *   파라미터로 true가 전달될 경우 /로 끝나는 URI과 /로 끝나지 않는 URI를 동일한 핸들러 메소드 매핑(기본값)
        *   Ex) '/v2/hotels' == '/v2/hotels/'
        * */
        configurer.setUseTrailingSlashMatch(true)
            /*
            * PathMatchConfigurer.addPathPrefix()
            * : 머리말과 머리말을 설정할 대상을 파라미터로 받음
            *   아래 코드는 @RestController나 @Controller 어노테이션이 사용된 클래스의 핸들러 메소드 URI에 '/v2'를 머리말로 붙인다는 의미
            * */
            .addPathPrefix("/v2", HandlerTypePredicate.forAnnotation(RestController.class, Controller.class));

        /*
        * 기타 메소드
        * setPathMatcher(): PathMacher 객체를 파라미터로 받아 path 설정값과 사용자 URI를 매핑함
        * setUrlPathHelper(): UrlPathHelper 객체를 파라미터로 받아 @PathVariable 값을 처리함
        * */
    }

    /*
    * 콘텐츠 협상 설정 메소드
    *
    * 콘텐츠 협상: 클라이언트가 URL로 특정 리소스 요구 시 서버에서 가장 적절한 프레젠테이션을 제공하는 방법
    * */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // 콘텐츠 협상을 사용하기 위한 URI 파라미터 이름 설정
        configurer.parameterName("contentType")
            // Accept 헤더를 사용한 콘텐츠 협상 기능을 사용하지 않도록 설정
            .ignoreAcceptHeader(true)
            // 콘텐츠 협상 과정에서 적합한 값을 찾기 못하면 기본값 application/json으로 설정
            .defaultContentType(MediaType.APPLICATION_JSON)
            // URI 파라미터 contentType 값이 json이면 application.json으로 간주
            .mediaType("json", MediaType.APPLICATION_JSON)
            // URI 파라미터 contentType 값이 xml이면 application.xml으로 간주
            .mediaType("xml", MediaType.APPLICATION_XML);
        /*
        * /hotels?contentType=json으로 요청 시 JSON 문서로 응답하고
        * /hotels?contentType=xml로 요청 시 XML 문서로 응답
        * */
    }

    /*
    * 비동기 서블릿 사용을 위한 설정 메소드
    * */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        // 스레드 풀 스레드 이름 머리말 설정
        taskExecutor.setThreadNamePrefix("Async-Executor");
        // 스레드 풀의 기본 스레드 갯수 설정
        taskExecutor.setCorePoolSize(50);
        // 스레드 풀의 최대 스레드 갯수 설정
        taskExecutor.setMaxPoolSize(100);
        // 스레드 풀의 대기열 크기 설정
        taskExecutor.setQueueCapacity(300);
        // ThreadPoolTaskExecutor 스레드 풀 초기화
        taskExecutor.initialize();

        // 생성된 스레드풀을 설정
        configurer.setTaskExecutor(taskExecutor);
        // 비동기 처리 타임아웃 설정
        configurer.setDefaultTimeout(10_000L);
    }

    /*
    * 잘못된 경로의 페이지를 요청했을 때 기본 서블릿 경로("/")로 이동시켜주는 설정
    * */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /*
    * 웹 애플리케이션의 resources 폴더에 저장된 정적 파일의 디렉토리 구조와 사용자가 요청한 디렉토리 구조가 다를 때 경로를 매핑해주는 설정 메소드
    * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/");
    }

    /*
    * 출처가 다른 리소스를 공유할 수 있는 CORS 설정을 위한 메소드
    * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 리소스에 대해 CORS 적용
        registry.addMapping("/**")
            // 파라미터에 입력한 URI만 출처로 허용
            .allowedOrigins("www.springtour.io")
            // 파라미터로 입력한 HTTP 메소드만 허용
            .allowedMethods("GET", "POST", "PUT")
            // 파라미터로 입력한 헤더만 허용
            .allowedHeaders("*")
            // 파라미터로 입력한 시간(초)만큼 정책이 유효함
            .maxAge(24 * 60 * 60);
    }

    /*
    * 사용자 요청과 어떤 뷰를 매핑할지 설정하는 메소드
    * */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 사용자가 요청한 URI가 "/"일 때 처리하는 뷰 이름은 "main"으로 지정
        registry.addViewController("/").setViewName("main");
    }

    /*
    * ClientInfoArgumentResolver를 추가하는 메소드
    * */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ClientInfoArgumentResolver());
    }

    /*
    * 직렬화 과정에서 NON_EMPTY로 설정한 ObjectMapper 객체를 만드는 메소드
    * @Bean, @Primary 어노테이션을 사용했기 때문에 최우선적으로 스프링 빈이 생성됨
    * */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return objectMapper;
    }

    /*
     * 헤더 값에 따라 JSON과 XML로 응답하도록 설정하는 메소드
     * HttpMessageConverter 생성자에 ObjectMapper를 주입하지 않아도 objectMapper() 메소드에서 생성된 스프링 빈이 HttpMessageConverter에서 사용됨
     * */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // JSON을 처리하는 MappingJackson2HttpMessageConverter 객체 생성해 추가
        converters.add(new MappingJackson2HttpMessageConverter());
        // XML을 처리하는 MappingJackson2XmlHttpMessageConverter 객체 생성해 추가
        converters.add(new MappingJackson2XmlHttpMessageConverter());
    }

    /*
    * 인코딩 처리용 메소드
    * */
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> defaultCharacterEncodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();

        // 기본 문자셋을 UTF-8로 설정
        encodingFilter.setEncoding("utf-8");
        // 요청 메시지와 응답 메시지 모두 설정된 문자셋으로 인코딩함
        encodingFilter.setForceEncoding(true);

        FilterRegistrationBean<CharacterEncodingFilter> filterBean = new FilterRegistrationBean<>();
        // FilterRegistrationBean에 CharacterEncodingFilter 서블릿 필터 객체를 세팅
        filterBean.setFilter(encodingFilter);
        // 초기 파라미터 설정
        // 파라미터 이름과 값을 넣으면 Filter의 init() 메소드 파라미터인 FilterConfig 객체에서 사용 가능
        filterBean.addInitParameter("paramName", "paramValue");
        // 필터를 적용할 URL 패턴 설정
        filterBean.addUrlPatterns("*");
        // 두 개 이상의 서블릿 필터를 설정할 떄 실행 순서 설정
        // 값이 작을수록 먼저 실행됨
        filterBean.setOrder(1);
        return filterBean;
    }

    /*

    @Bean(value = "localeResolver")
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(Locale.KOREAN);
        return acceptHeaderLocaleResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("locale");
        registry.addInterceptor(localeChangeInterceptor)
            .excludePathPatterns("/favicon.ico")
            .addPathPatterns("/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new HotelRoomNumberConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ClientInfoArgumentResolver());
    }*/
}
