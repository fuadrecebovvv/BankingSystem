package banking.model;

public class UserCards {
    private Integer id;
    private String pan;
    private Integer cvv;
    private Integer balance;

    public UserCards(Integer id, String pan, Integer cvv, Integer balance) {
        this.id = id;
        this.pan = pan;
        this.cvv = cvv;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Cards {" +
                "id=" + id +
                ", pan='" + pan + '\'' +
                ", cvv=" + cvv +
                ", balance=" + balance +
                '}';
    }
}
