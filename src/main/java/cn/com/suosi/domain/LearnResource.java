package cn.com.suosi.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
