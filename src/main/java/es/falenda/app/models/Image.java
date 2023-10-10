package es.falenda.app.models;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
@Entity
@Table(name = "images")
public class Image implements Serializable {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "mime")
    private String mime;
    @Column(name = "name")
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", columnDefinition = "LONGBLOB")
    private byte[] content;

    public Image() {
    }

    public Image(String mime, String name, byte[] content) {
        this.mime = mime;
        this.name = name;
        this.content = content;
    }

    public String getId() {
        return id;
    }


    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
