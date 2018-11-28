package cn.com.suosi.controller;

import cn.com.suosi.domain.LearnResource;
import cn.com.suosi.service.LearnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/")
public class LearnController {

    @Autowired
    private LearnService learnService;

    /**
     * 添加教程
     */
    @GetMapping(value = "/add")
    public String addResource(String author, String title, String url) {
        learnService.add(new LearnResource(author, title, url));
        return "redirect:tolearn";
    }

    /**
     * 修改教程页面
     */
    @GetMapping(value = "/toupdatelearn")
    public String toupdate(String id, Model model) {
        LearnResource learnResource = new LearnResource();
        learnResource.setId(Long.valueOf(id));
        LearnResource learn = learnService.findById(learnResource);
        model.addAttribute("learn", learn);
        return "updatelearn";
    }

    /**
     * 修改教程
     */
    @PostMapping(value = "/updatelearn")
    public String updateResource(String id, String author, String title, String url) {
        learnService.update(new LearnResource(Long.valueOf(id), author, title, url));
        return "redirect:tolearn";
    }

    /**
     * 删除教程
     */
    @GetMapping(value = "/deletelearn")
    public String deleteResource(String id) {
        LearnResource learnResource = new LearnResource();
        learnResource.setId(Long.valueOf(id));
        learnService.deleteById(learnResource);
        return "redirect:tolearn";
    }

    /**
     * 查询教程
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public LearnResource findResource(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    /**
     * 教程列表页面
     */
    @GetMapping(value = "/tolearn")
    public String findAll(@RequestParam(required = true, defaultValue = "1") Integer pageNum,
                          @RequestParam(required = true, defaultValue = "5") Integer pageSize,
                          Model model) {
        PageHelper.startPage(pageNum, pageSize);
        List<LearnResource> learnList = learnService.findAll();
        PageInfo<LearnResource> pageInfo = new PageInfo<>(learnList);
        model.addAttribute("page", pageInfo);
        model.addAttribute("learnList", learnList);
        return "learn";
    }
}
