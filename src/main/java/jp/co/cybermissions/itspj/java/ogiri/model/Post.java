package jp.co.cybermissions.itspj.java.ogiri.model;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Post")
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @NotBlank
    @Size(max = 20)
    private String  nickname;

    @NotNull
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate date; 
    
 /** 画像データ */
    @Lob
    private byte[] image;
  /** 画像データ形式 */
    private String type;

    @NotBlank
    @Size(max=20)
    private String title;
    

    @OneToMany(mappedBy = "post",cascade = {CascadeType.ALL})
    private List<Eval> eval;


      /**
   * 画像データをHTMLのimageタグで表示可能な形式で取得する
   * 
   * @return BASE64形式文字列
   * @throws Exception
   */
  public String getImageSource() throws Exception {
    StringBuffer sb = new StringBuffer();
    if (image.length > 0) {
      sb.append("data:");
      sb.append(type == null ? "image/png" : type);
      sb.append(";base64,");
      sb.append(Base64.getEncoder().encodeToString(image));
    }
    return sb.toString();
  }


      public void setStatus(int scBadRequest) {
      }
}
