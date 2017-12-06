package lbevan.github.io.travol.domain;

/**
 * Created by Luke on 06/12/2017.
 */
public class Holiday {

    private String name;
    private int photo;

    public Holiday(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
