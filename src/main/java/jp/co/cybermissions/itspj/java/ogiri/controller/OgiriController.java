package jp.co.cybermissions.itspj.java.ogiri.controller;


import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.cybermissions.itspj.java.ogiri.model.Eval;
import jp.co.cybermissions.itspj.java.ogiri.model.EvalRepository;
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
    public String register(@ModelAttribute Post post) {
        return "ogiri/new";
    }

    
   @PostMapping("")
  public String save(HttpServletResponse response,@RequestParam("file") MultipartFile file, @Validated @ModelAttribute Post post,
      BindingResult result) {
        if (file.isEmpty()) {
            post.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "ogiri/new";
          }
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
    

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("posts", pRep.findAll());
        return "ogiri/index";
    }

    @GetMapping("/{id}/eval")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("post", pRep.findById(id).get());
        return "ogiri/eval";
    }

    @PostMapping("/{id}/eval")
    public String eval(@PathVariable Long id, @RequestParam(name = "radio") String choiceText, @ModelAttribute Eval eval, Model model) {
        Post post = pRep.findById(id).get();
        eval.setChoiceText(choiceText);
        eval.setPoint(1);
        eval.setPost(post);
        eRep.save(eval);

        return "redirect:/ogiri/" + id + "/result";
    }

    @GetMapping("/{id}/result")
    public String results(@PathVariable Long id, Model model) {
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

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        pRep.deleteById(id);
        return "redirect:/top";
    }


}
