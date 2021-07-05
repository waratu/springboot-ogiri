package jp.co.cybermissions.itspj.java.ogiri.controller;


import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.cybermissions.itspj.java.ogiri.model.AdminRepository;
import jp.co.cybermissions.itspj.java.ogiri.model.Login;
import jp.co.cybermissions.itspj.java.ogiri.model.PostRepository;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final PostRepository pRep;
    private final AdminRepository rep;
    
    @GetMapping("/login")
    public String handle(ModelMap modelMap) {
        modelMap.addAttribute("Login", new Login());
        return "auth/login";
    }

    // @PostMapping("/login")
    // public String postLogin(@Validated @ModelAttribute Login login, BindingResult br) {
    //     if (br.hasErrors()) {
    //         return "auth/login";
    //     }
    //     return "auth/top";
    // }
    

    
    @GetMapping("/top")
    public String success(ModelMap modelMap,Model model) {
        model.addAttribute("post", pRep.findAll(Sort.by(Sort.Direction.DESC,"id")));
        return "auth/top";
    }

    // @PostMapping("/top")
    // public String process(@Validated @ModelAttribute("admin") Admin admin, BindingResult result){
    //  if(result.hasErrors()){
    //      return "top";
    //  }
    // //  admin.setPassword(passwordEncoder.encode(admin.getPassword()));
    // //  if(admin.isAdmin()){
    // //      admin.setRole(Role.ADMIN.name());
    // //  }else{
    // //     admin.setRole(Role.ADMIN.name());
    // //  }
    // //  adminRepository.save(admin);
     
    //  return "redirect:/login?top";
    // }

   

    @GetMapping("/failure")
    public String failure(ModelMap modelMap) {
        modelMap.addAttribute("isError", true);
        modelMap.addAttribute("Login", new Login());
        return "auth/login";
    }

}
