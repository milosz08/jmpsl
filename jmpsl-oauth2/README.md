# JMPSL OAuth2
![](https://jitpack.io/v/pl.miloszgilga/jmpsl.svg)
&nbsp;&nbsp;
![Maven Central](https://maven-badges.herokuapp.com/maven-central/pl.miloszgilga/jmpsl-oauth2/badge.svg)
&nbsp;&nbsp;
![Generic badge](https://img.shields.io/badge/License-MIT-brown.svg)
&nbsp;&nbsp;
![Generic badge](https://img.shields.io/badge/Made%20in-Java%2017%20with%20Gradle-1abc9c.svg)


Provide classes for OAuth2 Spring Security authorization via stateless REST endpoints. At this moment, support following
OAuth2 authentication providers:
* Facebook
* Google
* Github
* LinkedIn

Required artifacts: `jmpsl-core`, `jmpsl-security`. Corresponding versions.<br>
Required spring boot starter: `spring-boot-starter-oauth2-client`. Min version: `3.0.2`

<a name="table-of-content"></a>
## Table of content
* [Installation](#installation)
* [Data flow](#data-flow)
* [Basic usage](#basic-usage)
* [Author](#author)
* [License](#license)

<a name="installation"></a>
## Installation
### Stable releases, from Maven Central

* for Maven project:
```xml
<dependency>
    <groupId>pl.miloszgilga</groupId>
    <artifactId>jmpsl-oauth2</artifactId>
    <version>X.Y.Z_AA</version>
</dependency>
```

* for Gradle project with Groovy:
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'pl.miloszgilga:jmpsl-oauth2:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("pl.miloszgilga:jmpsl-oauth2:X.Y.Z_AA")
}
```

### Snapshot releases, from JitPack repository

* for Maven project:
```xml
<repositories>
    <repository>
        <id>jitpack</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    ...
    <dependency>
        <groupId>pl.miloszgilga.jmpsl</groupId>
        <artifactId>jmpsl-oauth2</artifactId>
        <version>X.Y.Z_AA</version>
    </dependency>
    ...
</dependencies>
```

* for Gradle project with Groovy:
```groovy
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'pl.miloszgilga.jmpsl:jmpsl-oauth2:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
  maven { url = 'https://jitpack.io' }
}

dependencies {
    implementation("pl.miloszgilga.jmpsl:jmpsl-oauth2:X.Y.Z_AA")
}
```

where `X.Y.Z_AA` is the selected version.

For more info about installation script and integrate with other Java build project
technologies, go to [official Maven repository website](https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-core).


<a name="data-flow"></a>
## Data flow
1. Client make request for address `http://127.0.0.1:4200/oauth2/authorization/[supplier]?base_uri=[base]&after_login_uri=[login]&after_signup_uri=[signup]`, where
    * `[supplier]` is the supplier name (facebook, google)
    * `[base]` is the base frontend application URL, ex. `http://127.0.0.1:4200`
    * `[login]` is the redirect address suffix after successfully login new user, ex. `auth/login`
    * `[signup]` is the redirect address suffix after successfully register, ex. `auth/after-register`
2. For login scenario after successfully authenticated, server make redirect to `http://127.0.0.1:4200/auth/login?token=[rawToken]`,
   where `[rawToken]` is the generated JWT from API (similarly, the data flow will be represented by the registration action).
3. On some authentication exception, server make redirect to `http://127.0.0.1:4200/auth/[action]?error=[message]`, where
    * `[action]` is the one of the suffix, ex. `login` or `after-register`
    * `[message]` is the specific error message


<a name="basic-usage"></a>
## Basic usage

1. Add Spring Boot OAuth2 Client starter:
* for Maven projects
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
    <version>3.X.Y</version>
</dependency>
```
* for Gradle with Groovy projects
```groovy
implementation 'org.springframework.boot:spring-boot-starter-oauth2-client:3.X.Y'
```
* for Gradle with Kotlin projects
```kotlin
implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.X.Y")
```

2. Apply following properties in `application.properties` or `application.yml` (example for facebook and google providers):
```properties
# standard Spring Boot OAuth2 starter properties, requireds
security.oauth2.client.registration.google.clientId = <insert here google client id>
security.oauth2.client.registration.google.clientSecret = <insert here google client key>
security.oauth2.client.registration.facebook.clientId = <insert here facebook client id>
security.oauth2.client.registration.facebook.clientSecret = <insert here facebook client key>

# true, if OAuth2 is active, false if is not active. WARN! If OAuth2 is active, in the absence of any below the required
# property, program will immediately terminate with an error
jmpsl.security.oauth2-active = true

# time (in minutes) after which cookie from external OAuth2 authentication server is expired. Property not required; by
# default is 3 minutes
jmpsl.oauth2.cookie-expired-minutes = 3

# list of all supported suppliers by application. Available options: facebook, google, github, linkedin. Property required.
jmpsl.oauth2.available-suppliers = google,facebook

# redirect URLs after successfull or failure authentication via OAuth2. Separated by comma. Property required.
jmpsl.oauth2.redirect-uris = http://localhost:4200/oauth2/redirect
```

3. Create entity class represents User database model and implements `IAuthUserModel` interface:
```java
@Entity
public class UserEntity implements Serializable, IAuthUserModel<SimpleGrantedRole> { 
    @Serial private static final long serialVersionUID = 1L;

    // standard hibernate columns, getters, setters and non arguments constructor
    
    @Override
    public String getAuthUsername() {
        // return here user unique identifier (user name or email address)
    }
    
    @Override
    public String getAuthPassword() {
        // ONLY FOR LOCAL ACCOUNTS: return user password, otherwise (if your authnetication method
        // is only realized by OAuth2, return null)
    }
  
    @Override
    public Set<String> getAuthRoles() {
        // return user roles as Set of strings, ex. USER, ADMIN, OWNER
        return SimpleGrantedRole.getSetCollection(); // return default roles
    }

    @Override
    public boolean isAccountEnabled() {
        // return true, if account is enabled. NOTE! By default, returns false.
        return true;
    }
}
```

4. Create service class, which implementing `IOAuth2LoaderService` interface:
```java
@Service
public class OAuth2Service implements IOAuth2LoaderService<SimpleGrantedRole> {
    @Override
    public OAuth2UserExtender<SimpleGrantedRole> registrationProcessingFactory(OAuth2RegistrationDataDto registrationData) {
        // persist or update OAuth2 user details and return fabricated user using fabricateUser()
        // method from OAuth2Util class
    }
}
```

5. Create service class which generating JWT for OAuth2 purposes:
```java
@Service
public class JwtGenerator implements IOAuth2TokenGenerator {
    @Override
    public String generateToken(Authentication auth) {
        // generate JWT based principals from Authentication class and return raw token
    }
}
```

6. Create service class for loading user to Spring Security Context:
```java
@Service
public class AuthUserDetailsService implements UserDetailsService {
    @Override
    public OAuth2UserExtender<SimpleGrantedRole> loadUserByUsername(String identifier) {
        // identifier is getting by getAuthUsername() method from IAuthUserModel interface
        // get user from database and return fabricated user using fabricateUser() method from OAuth2Util class
    }
}
```

7. Create standard `OncePerRequestFilter` filter for validating JWT and setting Spring Security Context. You can use
   default pre-defined `AbstractJwtRequestFilter` from `JMPSL Security` module or create own custom filter:
```java
@Component
public class JwtAuthenticationFilter extends AbstractJwtRequestFilter {

    // extract and validate user by functional expression. Take token from request and return user identifier or null
    private final Function<String, String> validateFunctor = token -> { 
        /* extracted user details from JWT */
    };
    
    public JwtAuthenticationFilter(JwtService jwtService, AuthUserDetailsService details) {
        super(jwtService, details, validateFunctor);
    }
}
```

8. Create configurable class for Spring Security OAuth2 configuration (sample configurable class):
```java
@Configuration
public class SpringSecurityConfigurer {
    private final Environment env;

    private final OAuth2OnFailureResolver oAuth2OnFailureResolver;
    private final MiddlewareExceptionFilter middlewareExceptionFilter;
    private final CookieOAuth2ReqRepository cookieOAuth2ReqRepository;
  
    private final AuthResolverForRest authResolverForRest;
    private final AccessDeniedResolverForRest accessDeniedResolverForRest;

    // injected service from step 4
    private final OAuth2Service oAuth2Service;
    // injected JWT generator from step 5
    private final JwtGenerator jwtGenerator;
    // injected authentication filter from step 7
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    // constructor for fields injection

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(options -> options.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(middlewareExceptionFilter, LogoutFilter.class)
            .formLogin().disable()
            .httpBasic().disable()
            .csrf().disable()
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authResolverForRest)
                .accessDeniedHandler(accessDeniedResolverForRest)
            )
            .authorizeHttpRequests(auth -> auth
                // insert here requestMatchers
                .requestMatchers("/", "/error", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login -> oauth2Login
                .authorizationEndpoint(p2p -> p2p.authorizationRequestRepository(cookieOAuth2ReqRepository))
                .redirectionEndpoint()
                .and()
                .userInfoEndpoint(info -> info
                    .oidcUserService(new AppOidcUserService(oAuth2Service))
                    .userService(new AppOAuth2UserService(env, oAuth2Service))
                )
                .tokenEndpoint(p2p -> p2p.accessTokenResponseClient(OAuth2Util.auth2AccessTokenResponseClient()))
                .successHandler(oAuth2OnSuccessfulResolver())
                .failureHandler(oAuth2OnFailureResolver)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
  
    @Bean
    public OAuth2OnSuccessfulResolver oAuth2OnSuccessfulResolver() {
        return new OAuth2OnSuccessfulResolver(env, jwtGenerator);
    }
}
```


<a name="author"></a>
## Author
Created by Miłosz Gilga. If you have any questions about this application, send message:
[personal@miloszgilga.pl](mailto:personal@miloszgilga.pl).


<a name="license"></a>
## License
This application is on MIT License.
