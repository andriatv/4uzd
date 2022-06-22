package lt.viko.vandraityte.saugumas.ketvirtas;

public class Password {
    private String name;
    private String password;
    private String app;
    private String comment;

    public Password() {
    }

    public Password(String name, String password, String app, String comment) {
        this.name = name;
        this.password = password;
        this.app = app;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Password{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", app='" + app + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
