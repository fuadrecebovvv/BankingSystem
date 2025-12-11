package banking.model;

public class UserDto {
    private Integer id;
    private String name;
    private String surname;
    private String passwd;

    public UserDto(Integer id, String name, String surname, String passwd) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.passwd = passwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
