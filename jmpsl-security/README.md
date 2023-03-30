# JMPSL Security

![](https://img.shields.io/badge/Made%20in-Java%2017%20with%20Gradle-1abc9c.svg)
&nbsp;&nbsp;
![](https://img.shields.io/badge/License-MIT-brown.svg)

[![][jmpsl security badge]][jmpsl security mvn]
&nbsp;&nbsp;
[![][jmpsl jitpack badge]][jmpsl jitpack]
<br>

[jmpsl jitpack]: https://jitpack.io/#pl.miloszgilga/jmpsl
[jmpsl jitpack badge]: https://img.shields.io/jitpack/version/pl.miloszgilga/jmpsl?color=gree&label=JMPSL%20JitPack

[jmpsl security badge]: https://img.shields.io/maven-central/v/pl.miloszgilga/jmpsl-security?label=JMPSL%20Security%20%28Central%29
[jmpsl security mvn]: https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-security

Provide classes which extending Spring Security (for most typical REST API scenarios).

Required artifacts: `jmpsl-core`. Corresponding versions.<br>
Required spring boot starter: `spring-boot-starter-security`. Min version: `3.0.2`

<a name="table-of-content"></a>
## Table of content
* [Installation](#installation)
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
    <artifactId>jmpsl-security</artifactId>
    <version>X.Y.Z_AA</version>
</dependency>
```

* for Gradle project with Groovy:
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'pl.miloszgilga:jmpsl-security:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("pl.miloszgilga:jmpsl-security:X.Y.Z_AA")
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
        <artifactId>jmpsl-security</artifactId>
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
    implementation 'pl.miloszgilga.jmpsl:jmpsl-security:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
  maven { url = 'https://jitpack.io' }
}

dependencies {
    implementation("pl.miloszgilga.jmpsl:jmpsl-security:X.Y.Z_AA")
}
```

where `X.Y.Z_AA` is the selected version.

For more info about installation script and integrate with other Java build project
technologies, go to [official Maven repository website](https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-core).


<a name="basic-usage"></a>
## Basic usage

1. Add Spring Boot Security starter:
* for Maven projects
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>3.X.Y</version>
</dependency>
```
* for Gradle with Groovy projects
```groovy
implementation 'org.springframework.boot:spring-boot-starter-security:3.X.Y'
```
* for Gradle with Kotlin projects
```kotlin
implementation("org.springframework.boot:spring-boot-starter-security:3.X.Y")
```

2. Apply following properties in `application.properties` or `application.yml`:
```properties
# OTA (One Time Access) token elapsed time in minutes. Property not required. By default it is 10 minutes
jmpsl.security.ota.length = 10

# password encoder strength (reccomended from 8 to 12). Property not required. By default it is 8 units
jmpsl.security.password-encoder-strength = 8

# JWT secret key (salt). Property required.
jmpsl.security.jwt.secret = fm92400mfomvnoifd1039cmoivmoeifmd01390d9fomfv

# JWT issuer (key signatory). Property required.
jmpsl.security.jwt.issuer = applicationName

# time (in minutes) after JWT is expired. Property not required. By default it is 5 minutes
jmpsl.security.jwt.expired-minutes = 5

# time (in days) after refresh token is expired. Property not required. By default it is 30 days
jmpsl.security.jwt.refresh-token-expired-days = 30

# enabled address in CORS policy (for more info check https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS). Property required.
jmpsl.security.cors.client = http://127.0.0.1:4200

# max CORS alive time in milis. Property not required. By default it is 3600 milis
jmpsl.security.cors.max-age = 3600
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
        // return here user password (hashable form data)
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
    // ...
}
```

4. Create service class for loading user to Spring Security Context:
```java
@Service
public class AuthUserDetailsService implements UserDetailsService {
    @Override
    public AuthUser<SimpleGrantedRole> loadUserByUsername(String identifier) {
        // identifier is getting by getAuthUsername() method from IAuthUserModel interface
        // get user from database and return user entity object
    }
}
```

5. Create standard `OncePerRequestFilter` filter for validating JWT and setting Spring Security Context. You can use
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

6. Create configurable class for Spring Security OAuth2 configuration (sample configurable class):
```java
@Configuration
public class SpringSecurityConfigurer {
    private final Environment env;
    
    private final AuthResolverForRest authResolverForRest;
    private final MiddlewareExceptionFilter middlewareExceptionFilter;
    private final AccessDeniedResolverForRest accessDeniedResolverForRest;

    // injected authentication filter from step 5
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
                .requestMatchers("/", "/error").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
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
