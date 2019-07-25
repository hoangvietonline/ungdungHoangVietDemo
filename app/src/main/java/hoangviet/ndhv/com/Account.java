package hoangviet.ndhv.com;

public class Account {
    private String TenAccount;
    private int hinhAccount;

    public Account(String tenAccount, int hinhAccount) {
        TenAccount = tenAccount;
        this.hinhAccount = hinhAccount;
    }

    public String getTenAccount() {
        return TenAccount;
    }

    public void setTenAccount(String tenAccount) {
        TenAccount = tenAccount;
    }

    public int getHinhAccount() {
        return hinhAccount;
    }

    public void setHinhAccount(int hinhAccount) {
        this.hinhAccount = hinhAccount;
    }
}
