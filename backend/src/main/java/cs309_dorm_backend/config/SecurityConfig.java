package cs309_dorm_backend.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


// 定义了/login的声明，就会覆盖默认的(假设开启认证开关的话，否则默认的就不存在)。
//使用 UserDetailsService 和密码编码器进行身份验证。这意味着在进行身份验证时，输入的密码将自动使用密码编码器进行加密，并与数据库中存储的加密密码进行比较。
@Configuration //声明当前类是一个配置类，相当于一个Spring配置的xml文件
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //@EnableWebSecurity声明开启认证！
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final UserDetailsService userDetailsService;

    @Getter
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
    //将自定义的 UserDetailsService 和密码编码器配置到 AuthenticationManagerBuilder 中，以便在进行身份验证时使用它们。

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll() // permitAll()方法表示该请求任何人都可以访问
                .anyRequest().authenticated()
                .and() // and()方法表示结束当前标签，返回上一级标签
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout() //添加退出登录支持。当使用WebSecurityConfigurerAdapter时，这将自动应用。默认情况是，访问URL”/ logout”，使HTTP Session无效来清除用户，清除已配置的任何#rememberMe()身份验证，清除SecurityContextHolder，然后重定向到”/login?success”
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }

}
