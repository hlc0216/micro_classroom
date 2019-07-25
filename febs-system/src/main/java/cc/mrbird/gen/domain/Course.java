package cc.mrbird.gen.domain;

import cc.mrbird.common.annotation.ExportConfig;

import javax.persistence.*;

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExportConfig(value = "id")
    private Long id;

    @ExportConfig(value = "课程名称")
    private String courseName;
    @Column(name = "PARENT_ID")
    private Long parentId;
    @ExportConfig(value = "状态")
    @Column(name = "status")
    private String status;

    @Column(name = "x_id")
    private String xId;

    @Column(name = "img_url")
    private String imgUrl;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * @return x_id
     */
    public String getxId() {
        return xId;
    }

    /**
     * @param xId
     */
    public void setxId(String xId) {
        this.xId = xId == null ? null : xId.trim();
    }

    /**
     * @return img_url
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * @param imgUrl
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }
}