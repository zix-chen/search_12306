package domain;

public class user {
    private String ID_card_num;
    private String username;
    private String phone_number;
    private boolean admin = false;
    private String pass_word;

    public user(String ID_card_num, String username, String phone_number, boolean admin, String pass_word) {
        this.ID_card_num = ID_card_num;
        this.username = username;
        this.phone_number = phone_number;
        this.admin = admin;
        this.pass_word = pass_word;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setID_card_num(String ID_card_num) {
        this.ID_card_num = ID_card_num;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getID_card_num() {
        return ID_card_num;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone_number() {
        return phone_number;
    }

}
