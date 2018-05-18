package com.springproject.gab_photography.Controller;

import com.springproject.gab_photography.Dao.ImageRepository;
import com.springproject.gab_photography.Service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class GridController {


    private Logger logger = LoggerFactory.getLogger(GridController.class);


    @Autowired
    ImageRepository ir;

    @Autowired
    EmailService emailService;


    @GetMapping("/images")
    public ModelAndView showGrid() {
        ModelAndView mv = new ModelAndView("imageGrid");
        mv.getModel().put("imageList", ir.findAll());

        return mv;
    }

    @GetMapping("/webpage")
    public ModelAndView webpage() {
        ModelAndView mv = new ModelAndView("webPage");
        mv.getModel().put("imageList", ir.findAll());
        return mv;
    }


    @RequestMapping("/loginForm")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("loginForm");
        return mv;
    }


    @PostMapping("webpage/mail")
    public ModelAndView mailform(@RequestParam(name = "user") String user,  @RequestParam(name = "email") String email, @RequestParam(name = "msg") String msg ){

        try {
            String message = user + ": " + msg + "\n\n" + "from: " + email;
            emailService.sendEmail(user,email,message);
        }catch (MailException e){
            logger.info(e.getMessage());
        }
        ModelAndView mv = new ModelAndView("webPage");
        mv.getModel().put("imageList", ir.findAll());
        return mv;
    }


}
