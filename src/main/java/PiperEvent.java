/**
 * Created by Asra Nizami on 4/1/14.
 */

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.soap.Text;

@Entity
public class PiperEvent {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    private long id;

    private String title;

    private String location;

    @NotNull
    private String description;

    private String time;

//    @Column(columnDefinition="TEXT")
//    private String body;

    @Transient
    private String body;


    public PiperEvent() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body){
        this.body= body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
