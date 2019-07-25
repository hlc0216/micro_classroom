package cc.mrbird.gen.domain;

import javax.persistence.*;

@Table(name = "home_word_dept")
public class HomeWordDept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dept_id")
    private String deptId;

    @Column(name = "home_word_id")
    private String homeWordId;

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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getHomeWordId() {
        return homeWordId;
    }

    public void setHomeWordId(String homeWordId) {
        this.homeWordId = homeWordId;
    }
}