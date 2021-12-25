package tmp;

import java.sql.Timestamp;

public class NotifyBean {
    public Long id;
    public String title;
    public String content;
    public Integer read;
    public Timestamp created;

    public NotifyBean(Long id, String title, String content, Integer read, Timestamp created) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.read = read;
        this.created = created;
    }
}
