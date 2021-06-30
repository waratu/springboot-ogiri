package jp.co.cybermissions.itspj.java.ogiri.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.cybermissions.itspj.java.ogiri.model.Admin;
import jp.co.cybermissions.itspj.java.ogiri.model.AdminRepository;
import lombok.RequiredArgsConstructor;

    
/**
 * Spring Securityで使用するユーザー情報（UserDetails）を取得するためのサービスクラス
 *
 * DBに登録されているユーザー情報をUserDetailsに変換している
 *
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  /** DBのユーザー情報にアクセスするためのリポジトリ */
  private final AdminRepository rep;

  /** ユーザー名からユーザー情報を取得する */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // DB検索
    Admin admin = rep.findByUsername(username);
    if (admin == null) {
      // 見つからない場合は例外をスロー
      throw new UsernameNotFoundException(username + " not found.");
    }
    // UserDetailsオブジェクトを作成
    // カスタム実装クラスを使用
    return createUserDetails(admin);
  }

  private UserDetails createUserDetails(Admin admin){
  // ユーザー名
  String username = admin.getUsername();
  // パスワード
  String password = admin.getPassword();
  // 権限
  Set<GrantedAuthority> authSet = new HashSet<>();
  

  return new org.springframework.security.core.userdetails.User(username, password, authSet);
  }
}