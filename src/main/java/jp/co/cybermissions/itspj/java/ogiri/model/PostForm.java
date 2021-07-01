package jp.co.cybermissions.itspj.java.ogiri.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {
    @NotBlank
    @Size(max= 20)
    private String title;

    @NotBlank
    @Size(max = 20)
    private String nickname;
    
    @NotNull
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate date;
    
    private MultipartFile file;
    
}
