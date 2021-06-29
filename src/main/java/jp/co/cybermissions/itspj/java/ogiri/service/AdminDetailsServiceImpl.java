// package jp.co.cybermissions.itspj.java.ogiri.service;

// import org.springframework.stereotype.Service;

// import jp.co.cybermissions.itspj.java.ogiri.model.Admin;
// import jp.co.cybermissions.itspj.java.ogiri.model.AdminRepository;
// import lombok.RequiredArgsConstructor;



// @Service
// @RequiredArgsConstructor
// public class AdminDetailsServiceImpl implements AdminDetailsService {

//   /** DBのユーザー情報にアクセスするためのリポジトリ */
//   private final AdminRepository rep;

//   /** ユーザー名からユーザー情報を取得する */
//   @Override
//   public AdminDetails loadAdminByAdminname(String adminname) throws AdminnameNotFoundException {
//     // DB検索
//     Admin admin = rep.findByAdminname(adminname);
//     if (user == null) {
//       // 見つからない場合は例外をスロー
//       throw new AdminnameNotFoundException(adminname + " not found.");
//     }
//     // UserDetailsオブジェクトを作成
//     // カスタム実装クラスを使用
//     return new AdminDetailsImpl(admin);
//   }
// }

