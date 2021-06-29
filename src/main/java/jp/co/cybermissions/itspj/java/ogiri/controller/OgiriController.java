package jp.co.cybermissions.itspj.java.ogiri.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.cybermissions.itspj.java.ogiri.model.Admin;
import jp.co.cybermissions.itspj.java.ogiri.model.Eval;
import jp.co.cybermissions.itspj.java.ogiri.model.EvalRepository;
import jp.co.cybermissions.itspj.java.ogiri.model.Login;
import jp.co.cybermissions.itspj.java.ogiri.model.Post;
import jp.co.cybermissions.itspj.java.ogiri.model.PostRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/ogiri")
@RequiredArgsConstructor
public class OgiriController {

    private final PostRepository pRep;
    private final EvalRepository eRep;

    @GetMapping("/new")
    public String register(@ModelAttribute Post post, Model model) {
        model.addAttribute("post");
        return "ogiri/new";
    }

    @PostMapping("")
    public String save(@RequestParam("file") MultipartFile file, @Validated @ModelAttribute Post post,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "ogiri/new";
        }
        if (file != null) {
            try {
                post.setImage(file.getBytes());
                post.setType(file.getContentType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pRep.save(post);
        return "redirect:/ogiri";
    }
    // pRep.save(post);
    // return "redirect:/ogiri";

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("post", pRep.findAll());
        return "ogiri/index";
    }

    @GetMapping("/{id}/eval")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("post", pRep.findById(id).get());
        return "ogiri/eval";
    }

    @PostMapping("/{id}/eval")
    public String eval(@PathVariable Integer id, @RequestParam(name = "radio") String choiceText, @ModelAttribute Eval eval, Model model) {
        Post post = pRep.findById(id).get();
        eval.setChoiceText(choiceText);
        eval.setPoint(1);
        eval.setPost(post);
        eRep.save(eval);

        return "redirect:/ogiri/" + id + "/result";
    }

    @GetMapping("/{id}/result")
    public String results(@PathVariable Integer id, Model model) {
        model.addAttribute("post", pRep.findById(id).get());
        final String  goodText="おもしろい", normalText="普通", badText="つまらない";
        int good = 0, normal = 0, bad = 0;
        for (Eval e : pRep.findById(id).get().getEval()) {
            switch (e.getChoiceText()) {
                case goodText:
                    good++;
                    break;
                case normalText:
                    normal++;
                    break;
                case badText:
                    bad++;
                    break;
            }
        }
        Map<String, Integer> result = new LinkedHashMap<>();
        result.put(goodText, good);
        result.put(normalText, normal);
        result.put(badText, bad);
        model.addAttribute("result", result);
        return "ogiri/result";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        pRep.deleteById(id);
        return "redirect:/ogiri";
    }


    @GetMapping("/login")
    public String handle(ModelMap modelMap) {
        modelMap.addAttribute("Login", new Login());
        return "auth/login";
    }

    
    @GetMapping("/top")
    public String success(ModelMap modelMap,Model model) {
        model.addAttribute("post", pRep.findAll());
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
