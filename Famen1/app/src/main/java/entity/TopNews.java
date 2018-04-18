package entity;


import java.io.Serializable;

/**热点资讯*/
public class TopNews implements Serializable{

    private String title;

    private String subtitle;




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
