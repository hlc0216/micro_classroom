package cc.mrbird.gen.domain;

import javax.persistence.*;

@Table(name = "my_home_word")
public class MyHomeWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Transient
    private String homeWordName;

    public String getHomeWordName() {
        return homeWordName;
    }

    public void setHomeWordName(String homeWordName) {
        this.homeWordName = homeWordName;
    }

    /**
     * 作业id
     */
    @Column(name = "home_word_id")
    private Long homeWordId;

    /**
     * 代码或图片url
     */
    @Column(name = "home_word_url")
    private String homeWordUrl;

    /**
     * 备注
     */
    @Column(name = "home_word_mark")
    private String homeWordMark;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "home_word_img")
    private String homeWordImg;

    /**
     * 状态:0?1
     */
    @Column(name = "home_word_status")
    private String homeWordStatus;

    @Column(name = "home_word_feng")
    private String homeWordFeng;

    @Column(name = "home_word_ping")
    private String homeWordPing;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHomeWordImg() {
        return homeWordImg;
    }

    public void setHomeWordImg(String homeWordImg) {
        this.homeWordImg = homeWordImg;
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

    /**
     * 获取作业id
     *
     * @return home_word_id - 作业id
     */
    public Long getHomeWordId() {
        return homeWordId;
    }

    /**
     * 设置作业id
     *
     * @param homeWordId 作业id
     */
    public void setHomeWordId(Long homeWordId) {
        this.homeWordId = homeWordId;
    }

    /**
     * 获取代码或图片url
     *
     * @return home_word_url - 代码或图片url
     */
    public String getHomeWordUrl() {
        return homeWordUrl;
    }

    /**
     * 设置代码或图片url
     *
     * @param homeWordUrl 代码或图片url
     */
    public void setHomeWordUrl(String homeWordUrl) {
        this.homeWordUrl = homeWordUrl == null ? null : homeWordUrl.trim();
    }

    /**
     * 获取备注
     *
     * @return home_word_mark - 备注
     */
    public String getHomeWordMark() {
        return homeWordMark;
    }

    /**
     * 设置备注
     *
     * @param homeWordMark 备注
     */
    public void setHomeWordMark(String homeWordMark) {
        this.homeWordMark = homeWordMark == null ? null : homeWordMark.trim();
    }

    /**
     * 获取状态:0?1
     *
     * @return home_word_status - 状态:0?1
     */
    public String getHomeWordStatus() {
        return homeWordStatus;
    }

    /**
     * 设置状态:0?1
     *
     * @param homeWordStatus 状态:0?1
     */
    public void setHomeWordStatus(String homeWordStatus) {
        this.homeWordStatus = homeWordStatus == null ? null : homeWordStatus.trim();
    }

    /**
     * @return home_word_feng
     */
    public String getHomeWordFeng() {
        return homeWordFeng;
    }

    /**
     * @param homeWordFeng
     */
    public void setHomeWordFeng(String homeWordFeng) {
        this.homeWordFeng = homeWordFeng == null ? null : homeWordFeng.trim();
    }

    /**
     * @return home_word_ping
     */
    public String getHomeWordPing() {
        return homeWordPing;
    }

    /**
     * @param homeWordPing
     */
    public void setHomeWordPing(String homeWordPing) {
        this.homeWordPing = homeWordPing == null ? null : homeWordPing.trim();
    }
}