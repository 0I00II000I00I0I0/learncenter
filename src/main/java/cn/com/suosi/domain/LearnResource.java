package cn.com.suosi.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 教程信息
 * id，ID
 * author，作者
 * title，教程名称
 * url，教程地址链接
 * createdby，添加人
 * created，添加时间
 * updatedby，修改人
 * updated，修改时间
 * userid，用户ID
 *
 * */

@Data
@Table(name="learnresources")
public class LearnResource {
    @Id
    private Long id;
    private String author;
    private String title;
    private String url;
    private String createdby;
    private Date created;
    private String updatedby;
    private Date updated;
    private Long userid;

    public LearnResource(){};

    public LearnResource(Long id, String authore, String title, String url) {
        this.id = id;
        this.author = authore;
        this.title = title;
        this.url = url;
    }

    public LearnResource(String authore, String title, String url) {
        this.author = authore;
        this.title = title;
        this.url = url;
    }
}
