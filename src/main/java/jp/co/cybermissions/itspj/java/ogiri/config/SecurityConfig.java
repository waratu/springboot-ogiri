package jp.co.cybermissions.itspj.java.ogiri.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

/**
 * Spring Securityの機能を有効にするための設定クラス
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  // DBのユーザー情報を参照するためのサービス
  private final UserDetailsService userDetailsService;

  /**
   * BCryptアルゴリズムでパスワードをハッシュ化するためのエンコーダ
   * 
   * ユーザー登録時にも使用する
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /** Web全体のセキュリティ設定 */
  @Override
  public void configure(WebSecurity web) throws Exception {
    // staticファイルはセキュリティ設定対象外としておく
    web.ignoring().antMatchers("/js/**", "/css/**", "/webjars/**");
  }

  /** HTTPリクエスト（URLごと）のセキュリティ設定 */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        ///////////////////////////////////////////////
        // 未ログインでもアクセス可能なURLを指定する
        // ログイン・登録画面
        .antMatchers("/login").permitAll()
        // 公開URL
        .antMatchers("/ogiri","/ogiri/new","/ogiri/{id}/eval","/ogiri/{id}/result","/top").permitAll()

        .antMatchers("/h2-console/**").permitAll()
        
        // 上記以外は認証が必要
        .anyRequest().authenticated().and()

        ///////////////////////////////////////////////
        // フォーム認証を有効にする
        .formLogin()
        // 自作ログイン画面のURL
        .loginPage("/login")
        // ログイン成功後に表示するURL
        .defaultSuccessUrl("/top").and()



        ///////////////////////////////////////////////
        // ログアウト
        .logout()
        // ログアウトのURL
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        // ログアウト成功後に表示するURL
        .logoutSuccessUrl("/ogiri");

        http.csrf().disable();
    http.headers().frameOptions().disable();


  }

  /** ログイン認証時のセキュリティ設定 */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // UserDetailsServiceを使用してDBからユーザーを参照する
    auth.userDetailsService(userDetailsService)
        // パスワードエンコーダ指定
        .passwordEncoder(passwordEncoder());
  }

}