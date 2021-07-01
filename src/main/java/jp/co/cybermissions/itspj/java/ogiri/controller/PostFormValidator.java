package jp.co.cybermissions.itspj.java.ogiri.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import jp.co.cybermissions.itspj.java.ogiri.model.PostForm;


@Component
public class PostFormValidator implements Validator{
  
    @Override
    public boolean supports(Class<?> clazz){
        return PostForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        PostForm form = (PostForm) target;
        MultipartFile file = form.getFile();
        if (file == null || file.getOriginalFilename().isEmpty()){
            errors.rejectValue("file", "PostFormValidator.file", "ファイルが存在しません");
        }
    }

}
