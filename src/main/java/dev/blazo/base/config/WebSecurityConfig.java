package dev.blazo.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import dev.blazo.base.utils.AuthorityName;

/**
 * Clase de configuración de Spring Security.
 * 
 * Esta clase de configuración es esencial para habilitar y configurar Spring
 * Security.
 * 
 * Proporciona la información necesaria para autenticar y autorizar a los
 * usuarios, permitiendo así proteger los recursos y garantizar la seguridad de
 * la aplicación web. También define cómo se almacenan y gestionan las
 * credenciales de usuario, lo que es fundamental para el control de acceso y la
 * protección de datos sensibles.
 * 
 * Al proporcionar un método userDetailsService() que define cómo se autentican
 * los usuarios (en memoria en este caso) y qué roles tienen. Spring Security
 * utiliza esta configuración para controlar el acceso y la protección de
 * recursos en la aplicación.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Los métodos anotados con @Bean en una clase de configuración de Spring, como
     * WebSecurityConfig, indican que estos métodos deben ser administrados por el
     * contenedor de Spring y que los objetos que retornan deben estar disponibles
     * para su uso en la aplicación.
     */

    /**
     ** Proporciona el objeto que Spring Security utiliza como administrador de
     ** credenciales de usuario.
     * 
     ** Este objeto se utiliza para obtener información sobre el usuario, como
     ** nombre de usuario, contraseña y roles en cualquier lugar de la aplicación.
     * 
     * @return Una instancia de InMemoryUserDetailsManager que implementa
     *         UserDetailsService.
     */
    // @Bean
    // public UserDetailsService userDetailsService() {
    // /**
    // * Provisión de Usuarios en Memoria
    // *
    // ** Ventajas:
    // * - Es simple de configurar y útil para desarrollo y pruebas.
    // * - Útil para aplicaciones pequeñas o prototipos.
    // *
    // ** Limitaciones:
    // * - No es escalable ni adecuado para aplicaciones en producción con una gran
    // * cantidad de usuarios.
    // *
    // * - Los usuarios se almacenan en la memoria y se pierden cuando la aplicación
    // * se reinicia.
    // */
    // // UserDetails user =
    // // User.withUsername("blazo").password("12345").authorities("read").build();

    // /**
    // * Devolvemos una instancia del objeto que se encarga de manejar los datos de
    // * los usuarios en memoria.
    // */
    // // return new InMemoryUserDetailsManager(user);

    // return new SecurityUserDetailsService();
    // }

    /**
     * Objeto encargado de manejar contraseñas
     * 
     * 
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        /**
         * NoOpPasswordEncoder (No Operation Password Encoder) es una implementación de
         * PasswordEncoder proporcionada por Spring Security que en realidad no cifra ni
         * verifica contraseñas de ningún modo.
         * 
         * Su función principal es proporcionar una contraseña "en texto plano" o "sin
         * cifrar" para que Spring Security pueda compararla directamente con la
         * contraseña proporcionada por el usuario sin aplicar ningún algoritmo de
         * cifrado.
         * 
         * ! Esta implementación es útil para fines de desarrollo y pruebas, pero no
         * ! debe utilizarse en aplicaciones en producción.
         */

        // return NoOpPasswordEncoder.getInstance();

        // Codificador de contraseña BCrypt
        // return new BCryptPasswordEncoder();

        // Codificador de contraseña delegado, puede manejar varios algoritmos de
        // codificación
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /***
     * Configuración de la Cadena de Filtros
     * Sobrescribiremos el comportamiento predeterminado de la cadena de filtros de
     * Spring Security al intentar acceder a recursos de nuestra aplicación.
     * 
     * Spring Security para establecer niveles de autorización cuenta con dos temas
     ** 1. Definir el recurso a proteger -> Matcher
     * - AnyRequest -> Cualquier petición será protegida
     * - RequestMatcher -> Definir un patrón de URL para proteger
     * - AntMatcher -> Definir un patrón de URL para proteger
     * - RequestMatcher HttpMethod -> Definir un patrón de URL para proteger y
     * un método HTTP
     * 
     ** 2. Definir las reglas de autorización -> Filter
     * - PermitAll -> Permite el acceso a todos los usuarios
     * - DenyAll -> Deniega el acceso a todos los usuarios
     * - Authenticated -> Permite el acceso a usuarios autenticados
     * - HasRole -> Permite el acceso a usuarios con un rol específico
     * - HasAuthority -> Permite el acceso a usuarios con una autoridad específica
     * - Access (SpEL) Spring Expression Language -> Permite el acceso a usuarios
     * que cumplan con una expresión lógica.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /**
         * Para sobrescribir el comportamiento predeterminado de la cadena de filtros
         * debemos utilizar authorizeHttpRequests() y definir las reglas de autorización
         * 
         * En la cadena de filtro primero ocurre la autenticación y luego la
         * autorización.
         * 
         * Por lo que si intentamos acceder a un recurso protegido con un usuario que no
         * existe, se nos devolverá un 401 Unauthorized. Debido a que no puede
         * autenticar el usuario.
         * 
         */
        http.httpBasic(withDefaults())
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/admin/**").hasAuthority(AuthorityName.ADMIN.name())
                                .requestMatchers("/deny/**").denyAll()
                                .anyRequest().authenticated());

        return http.build();
    }
}
