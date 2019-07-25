package cc.mrbird.gen.domain;

import javax.persistence.*;

@Table(name = "home_word")
public class HomeWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "home_word_name")
    private String homeWordName;

    private String re;

    private String ti;

    private String zi;

    @Column(name = "course_id")
    private Long courseId;

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

    /**
     * @return home_word_name
     */
    public String getHomeWordName() {
        return homeWordName;
    }

    /**
     * @param homeWordName
     */
    public void setHomeWordName(String homeWordName) {
        this.homeWordName = homeWordName == null ? null : homeWordName.trim();
    }

    /**
     * @return re
     */
    public String getRe() {
        return re;
    }

    /**
     * @param re
     */
    public void setRe(String re) {
        this.re = re == null ? null : re.trim();
    }

    /**
     * @return ti
     */
    public String getTi() {
        return ti;
    }

    /**
     * @param ti
     */
    public void setTi(String ti) {
        this.ti = ti == null ? null : ti.trim();
    }

    /**
     * @return zi
     */
    public String getZi() {
        return zi;
    }

    /**
     * @param zi
     */
    public void setZi(String zi) {
        this.zi = zi == null ? null : zi.trim();
    }

    /**
     * @return course_id
     */
    public Long getCourseId() {
        return courseId;
    }

    /**
     * @param courseId
     */
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}