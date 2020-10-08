package com.wct.config;



import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@SuppressWarnings("all")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //认证 可以用内存认证和数据库
    // 认证  用户、密码（加密处理PasswordEncoder）、角色
    // springboot security5.0+增加了很多的加密方法~
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1")
                .and()
                .withUser("wuchengtao").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2");
    }

    @Override
    //授权
    protected void configure(HttpSecurity http) throws Exception {

        // 请求授权 / 首位请求所有人  /leve11下的所有请求 vip1角色
        // 角色的请求路径对应的视图显示可以在前端判断
        http.authorizeRequests().antMatchers("/").permitAll()
             .antMatchers("/level1/**").hasRole("vip1")
             .antMatchers("/level2/**").hasRole("vip2")
             .antMatchers("/level3/**").hasRole("vip3");
        //没有权限不够的角色请求会跳转到登录页面
        //开启登录页面 /login

        //将默认的页面变为我们自定义的页面 /toLogin  前端路径与loginPage的参数相同可以
        // 如果路径参数不同用loginProcessingUrl()
        // 当前端登录的用户和密码的name值与默认(username,password)的不符合时用loginProcessingUrl()
        http.formLogin().loginPage("/toLogin").loginProcessingUrl("/login");

        //开启注销  /logout 注销成功会跳到 /login
        //注销成功给他跳到首页 /
        http.logout().logoutSuccessUrl("/");

        //spring中默认开启了网站的跨站防护（get方式不安全）
        //关闭防护
        http.csrf().disable();

        // 开启记住我的功能
        http.rememberMe().rememberMeParameter("rem");

    }
}
