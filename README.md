# learncenter

### 功能描述
本项目采用spring boot + mybatis + thymeleaf模板引擎
* 实现了简单的登录、注册、退出功能
* 个人信息管理、教程信息管理模块
* 个人信息管理包括修改个人信息、修改密码功能
* 教程信息管理包括教程信息的添加、删除、修改、查询功能

### 开发环境
* Intellij IDEA
* JDK 1.8
* MYSQL 5.7
* MAVEN 3.5
* Spring Boot 2.0

### 项目结构

### 测试DEMO
1.基本环境配置
* 在MYSQL中创建数据库learncenter,运行learncenter\src\main\resources\resource.sql文件，创建数据表users和learnresources
* 修改数据库配置，在learncenter/src/main/resources/application.properties文件中修改相应的数据源配置

2.运行项目
* 右键运行工程包中Spring Boot应用启动类的main函数，然后在浏览器访问：localhost:8080/center，进入登录页面

3.代码分析
* 实体类User
```J
/**
 * 用户信息
 */

@Data
@Table(name = "users")
public class User {
    /**
     * id，用户ID
     */
    @Id
    private Long id;
    /**
     * username，用户名
     */
    private String username;
    /**
     * password，密码
     */
    private String password;
    /**
     * realname，真实姓名
     */
    private String realname;
    /**
     * phonenumber，手机号码
     */
    private String phonenumber;
    /**
     * birthday，出生日期
     */
    private Date birthday;
    /**
     * logintime，注册时间
     */
    private Date logintime;
    /**
     * ip，登陆IP
     */
    private String ip;

    public User() {
    }

    public User(Long id, String username, String realname, String phonenumber, Date birthday) {
        this.id = id;
        this.username = username;
        this.realname = realname;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
    }

    public User(String username, String password, String realname, String phonenumber, Date birthday) {
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
    }

}
```
* 实体类LearnResource
```Java
/**
 * 教程信息
 */

@Data
@Table(name = "learnresources")
public class LearnResource {
    /**
     * id，ID
     */
    @Id
    private Long id;
    /**
     * author，作者
     */
    private String author;
    /**
     * title，教程名称
     */
    private String title;
    /**
     * url，教程地址链接
     */
    private String url;
    /**
     * createdby，添加人
     */
    private String createdby;
    /**
     * created，添加时间
     */
    private Date created;
    /**
     * updatedby，修改人
     */
    private String updatedby;
    /**
     * updated，修改时间
     */
    private Date updated;
    /**
     * userid，用户ID
     */
    private Long userid;

    public LearnResource() {
    }

    public LearnResource(String authore, String title, String url) {
        this.author = authore;
        this.title = title;
        this.url = url;
    }

    public LearnResource(Long id, String authore, String title, String url) {
        this.id = id;
        this.author = authore;
        this.title = title;
        this.url = url;
    }

}

```
* 个人信息管理模块的控制类LoginController
```Java
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录界面
     */
    @RequestMapping(value = "/tologin")
    public String login() {
        return "login";
    }

    /**
     * 注册界面
     */
    @RequestMapping(value = "/toregister")
    public String register() {
        return "register";
    }

    /**
     * 登录操作
     */
    @GetMapping(value = "/login")
    public String login(String username, String password, Model model, RedirectAttributes attributes) {
        ArrayList<String> message = new ArrayList<>();

        //登录验证
        //判断用户输入是否正确
        if (StringUtils.isBlank(username)) {
            message.add("用户名不能为空，请重新输入");
            attributes.addFlashAttribute("message", message);
            return "redirect:/tologin";
        } else if (StringUtil.haveNull(username)) {
            message.add("用户名不能含有空格，请重新输入");
            attributes.addFlashAttribute("message", message);
            return "redirect:/tologin";
        } else {
            User user = new User();
            user.setUsername(username);
            User user1 = userService.getUser(user);
            String md5password = DigestUtils.md5DigestAsHex(password.getBytes());

            //判断用户密码是否正确
            if (user1 != null && user1.getPassword().equals(md5password)) {
                model.addAttribute("user", user1);
                message.add("登录成功");
                model.addAttribute("message", message);
                return "index";
            } else {
                message.add("账号密码错误，请重新输入");
                attributes.addFlashAttribute("message", message);
                return "redirect:tologin";
            }
        }
    }

    /**
     * 注册操作
     */
    @GetMapping(value = "/register")
    public String register(String username, String password, String echopassword, String realname,
                           String phonenumber, String birthday, Model model, RedirectAttributes attributes) {
        ArrayList<String> message = new ArrayList<>();

        //注册验证
        //判断用户输入是否正确
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)
                || StringUtils.isBlank(realname) || StringUtils.isBlank(phonenumber) || StringUtils.isBlank(birthday)) {
            message.add("信息填写不完整，请重新输入");
            attributes.addFlashAttribute("message", message);
            return "redirect:toregister";
        } else if (!password.equals(echopassword)) {
            message.add("密码确认错误，请重新输入");
            attributes.addFlashAttribute("message", message);
            return "redirect:toregister";
        } else {
            User user = new User();
            user.setUsername(username);
            User user1 = userService.getUser(user);

            //判断用户是否已经存在
            if (null != user1) {
                message.add("用户已存在，请重新注册");
                attributes.addFlashAttribute("message", message);
                return "redirect:toregister";
            } else {
                String md5password = DigestUtils.md5DigestAsHex(password.getBytes());
                DateFormat formatter = new SimpleDateFormat("YY-MM-DD");
                Date date = null;
                try {
                    date = formatter.parse(birthday);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //注册用户成功
                User user2 = new User(username, md5password, realname, phonenumber, date);
                userService.add(user2);
                User user3 = userService.getUser(user2);
                model.addAttribute("user", user3);
                message.add("注册成功，你已登录");
                model.addAttribute("message", message);
                return "index";
            }
        }
    }

    /**
     * 登出操作
     */
    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest request, RedirectAttributes attributes) {
        ArrayList<String> message = new ArrayList<>();
        HttpSession session = request.getSession(false);

        //判断session是否过期
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
     * 修改个人信息页面
     */
    @GetMapping(value = "/toupdate")
    public String toupdate(String id, Model model) {
        User user = userService.getUser(Long.valueOf(id));
        model.addAttribute("user", user);
        return "updateuser";
    }

    /**
     * 修改个人信息
     */
    @GetMapping(value = "/updateuser")
    public String updateuser(String id, String username, String realname,
                             String phonenumber, String birthday, Model model) {
        DateFormat formatter = new SimpleDateFormat("YY-MM-DD");
        Date date = null;
        try {
            date = formatter.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User user = new User(Long.valueOf(id), username, realname, phonenumber, date);
        userService.update(user);
        User user1 = userService.getUser(Long.valueOf(id));
        model.addAttribute("user", user1);
        return "index";
    }

    /**
     * 修改密码页面
     */
    @GetMapping(value = "/toupdatepw")
    public String toupdatepw(String id, Model model) {
        User user = new User();
        user.setId(Long.valueOf(id));
        model.addAttribute("user", user);
        return "updatepw";
    }

    /**
     * 修改密码
     */
    @GetMapping(value = "/updatepw")
    public String updatepw(String id, String oldpassword, String newpassword, String echonewpassword, RedirectAttributes attributes) {
        String md5password = DigestUtils.md5DigestAsHex(oldpassword.getBytes());
        ArrayList<String> message = new ArrayList<>();
        User user = userService.getUser(Long.valueOf(id));

        //判断旧密码，确认新密码输入是否正确
        if (!user.getPassword().equals(md5password)) {
            message.add("旧密码输入错误，请重新输入");
            attributes.addFlashAttribute("message", message);
            attributes.addAttribute("id", id);
            return "redirect:toupdatepw";
        } else if (!newpassword.equals(echonewpassword)) {
            message.add("确认新密码输入错误，请重新输入");
            attributes.addFlashAttribute("message", message);
            attributes.addAttribute("id", id);
            return "redirect:toupdatepw";
        } else {
            String md5password1 = DigestUtils.md5DigestAsHex(newpassword.getBytes());
            user.setPassword(md5password1);
            userService.update(user);
            message.add("密码修改成功");
            attributes.addFlashAttribute("message", message);
            return "redirect:tologin";
        }
    }
}
```
* 教程信息管理模块的控制类LearnController
```Java
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
```
* 配置文件pom.xml中引用了Lombok插件，实体类中使用注解@Data，不需要set()、get()
```
<!-- Lombok插件 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.20</version>
            <scope>provided</scope>
        </dependency>
        
/**
 * 教程信息
 */

@Data
@Table(name = "learnresources")
public class LearnResource {}        
```
* 配置文件pom.xml中引用了通用mapper插件，持久层集成Mybatis不需要在mapper.xml配置文件中写一般的CURD语句，通过继承BaseMapper可以直接使用
```
<!--通用mapper插件-->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>1.1.5</version>
        </dependency>
        
@Mapper
public interface UserMapper extends BaseMapper<User> {}

@Mapper
public interface LearnMapper extends BaseMapper<LearnResource> {}
```
* 配置文件pom.xml中引用了分页插件pagehelper,在教程列表页面实现了分页
```
<!-- 分页插件pagehelper -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.5</version>
        </dependency>
        
控制层controller
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

教程管理页面
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>教程管理中心</title>
</head>
<body>
<div style="text-align: center;margin:0 auto;width: 1500px; ">
    <h1>教程管理中心</h1>
    <table width="100%" border="1" cellspacing="0" cellpadding="0">
        <tr>
            <td>作者</td>
            <td>教程名称</td>
            <td>添加人</td>
            <td>添加时间</td>
            <td>修改人</td>
            <td>修改时间</td>
            <td>地址</td>
            <td>编辑</td>
            <td>删除</td>
        </tr>
        <tr th:each="learn : ${learnList}">
            <td th:text="${learn.id}" style="display:none"></td>
            <td th:text="${learn.author}"></td>
            <td th:text="${learn.title}"></td>
            <td th:text="${learn.createdby}"></td>
            <td th:text="${learn.created}"></td>
            <td th:text="${learn.updatedby}"></td>
            <td th:text="${learn.updated}"></td>
            <td><a th:href="${learn.url}" target="_blank">
                <button>点我</button>
            </a></td>
            <td><a th:href="@{/toupdatelearn(id=${learn.id})}">
                <button>编辑</button>
            </a></td>
            <td><a th:href="@{/deletelearn(id=${learn.id})}" onclick="return confirmAct();">
                <button>删除</button>
            </a></td>
        </tr>
    </table>
    <div>
        <p th:text="第+${page.pageNum}+页，共+${page.pages}+页"></p>
        <a href="/tolearn?pageNum=1">[首  页]</a>
        <a th:href="@{/tolearn(pageNum=${page.pageNum-1})}">[上一页]</a>
        <a th:href="@{/tolearn(pageNum=${page.pageNum+1})}">[下一页]</a>
        <a th:href="@{/tolearn(pageNum=${page.pages})}">[末  页]</a>
    </div>
    <br>
    <form action="add">
        作者：<input type="text" name="author" />
        教程名称：<input type="text" name="title" />
        地址：<input type="text" name="url" />
        <input type="submit" value="查询教程"/>
        <input type="submit" value="添加新教程"/>
    </form>
    <br>
    <form action="logout" method="post">
    <input type="submit" value="退出"/>
        <input type="button" value="返回" onclick="javascript:history.back();"/>
    </form>
</div>

<script type="text/javascript" language="javascript">
    function confirmAct()
    {
        if(confirm('确定要删除吗?'))
        {
            return true;
        }
        return false;
    }
</script>
</body>
</html>
```
