package cn.com.suosi.controller;

import cn.com.suosi.domain.User;
import cn.com.suosi.service.UserService;
import cn.com.suosi.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录界面
     * */
    @RequestMapping(value = "/tologin")
    public String login(){
        return "login";
    }

    /**
     * 注册界面
     * */
    @RequestMapping(value = "/toregister")
    public String register(){
        return "register";
    }

    /**
     * 登录操作
     * */
    @GetMapping(value = "/login")
    public String login(String username, String password, Model model, RedirectAttributes attributes){
        ArrayList<String> message = new ArrayList<>();

        //登录验证
        if (StringUtils.isBlank(username)) {
            message.add("用户名不能为空，请重新输入");
            attributes.addFlashAttribute("message", message);
            return "redirect:/tologin";
        }else if (StringUtil.haveNull(username)){
            message.add("用户名不能含有空格，请重新输入");
            attributes.addFlashAttribute("message", message);
            return "redirect:/tologin";
        }else {
            User user = new User();
            user.setUsername(username);
            User user1 = userService.getUser(user);
            String md5password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (user1 != null && user1.getPassword().equals(md5password)){
                model.addAttribute("user",user1);
                message.add("登录成功");
                model.addAttribute("message", message);
                return "index";
            }else {
                message.add("账号密码错误，请重新输入");
                attributes.addFlashAttribute("message", message);
                return "redirect:tologin";
            }
        }
    }

    /**
     * 注册操作
     * */
    @GetMapping(value = "/register")
    public String register(String username, String password, String echopassword,
                           String realname, String phonenumber, String birthday, Model model, RedirectAttributes attributes){
        ArrayList<String> message = new ArrayList<>();

        //注册验证
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(realname)
                || StringUtils.isBlank(phonenumber) || StringUtils.isBlank(birthday)) {
            message.add("信息填写不完整，请重新输入");
            attributes.addFlashAttribute("message", message);
            return "redirect:toregister";
        } else {
            User user = new User();
            user.setUsername(username);
            User user1 = userService.getUser(user);
            if (null != user1){
                message.add("用户已存在，请重新注册");
                attributes.addFlashAttribute("message", message);
                return "redirect:toregister";
            } else if (!password.equals(echopassword)) {
                message.add("密码确认错误，请重新输入");
                attributes.addFlashAttribute("message", message);
                return "redirect:toregister";
            } else{
                String md5password = DigestUtils.md5DigestAsHex(password.getBytes());
                DateFormat formatter = new SimpleDateFormat("YY-MM-DD");
                Date date = null;
                try {
                    date = formatter.parse(birthday);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                User user2 = new User(username,md5password,realname,phonenumber,date);
                userService.add(user2);
                User user3 = userService.getUser(user2);
                model.addAttribute("user",user3);
                message.add("注册成功，你已登录");
                model.addAttribute("message", message);
                return "index";
            }
        }
    }

    /**
     * 登出操作
     * */
    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest request,RedirectAttributes attributes) {
        ArrayList<String> message = new ArrayList<>();
        HttpSession session = request.getSession(false);
        if (null != session) {
            session.removeAttribute("user");
            message.add("退出成功");
            attributes.addFlashAttribute("message", message);
        } else {
            message.add("连接已断开，请重新登录");
            attributes.addFlashAttribute("message", message);
        }
        return "redirect:tologin";
    }

    /**
     * 个人信息管理
     * */
    @GetMapping(value = "/toupdate")
    public String toupdate(String id,Model model){
        User user = userService.getUser(Long.valueOf(id));
        model.addAttribute("user",user);
        return "updateuser";
    }

    /**
     * 修改个人信息
     * */
    @GetMapping(value = "/updateuser")
    public String updateuser(String id,String username, String realname, String phonenumber, String birthday,
                             Model model){
        DateFormat formatter = new SimpleDateFormat("YY-MM-DD");
        Date date = null;
        try {
            date = formatter.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User user = new User(Long.valueOf(id),username,realname,phonenumber,date);
        userService.update(user);
        User user1 = userService.getUser(Long.valueOf(id));
        model.addAttribute("user",user1);
        return "index";
    }

    /**
     * 修改密码页面
     * */
    @GetMapping(value = "/toupdatepw")
    public String toupdatepw(String id,Model model){
        User user = new User();
        user.setId(Long.valueOf(id));
        model.addAttribute("user",user);
        return "updatepw";
    }

    /**
     * 修改密码
     * */
    @GetMapping(value = "/updatepw")
    public String updatepw(String id, String oldpassword, String newpassword, String echonewpassword, RedirectAttributes attributes){
        ArrayList<String> message = new ArrayList<>();
        User user = userService.getUser(Long.valueOf(id));

        if (!user.getPassword().equals(oldpassword)){
            message.add("旧密码输入错误，请重新输入");
            attributes.addFlashAttribute("message",message);
            attributes.addAttribute("id",id);
            return "redirect:toupdatepw";
        }else if (!newpassword.equals(echonewpassword)){
            message.add("确认新密码输入错误，请重新输入");
            attributes.addFlashAttribute("message",message);
            attributes.addAttribute("id",id);
            return "redirect:toupdatepw";
        }else {
            user.setPassword(newpassword);
            userService.update(user);
            message.add("密码修改成功");
            attributes.addFlashAttribute("message",message);
            return "redirect:tologin";
        }
    }
}
