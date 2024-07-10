package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountServices {
    private AccountDAO accountDAO;

    public AccountServices(){
        accountDAO = new AccountDAO();
    }

    public AccountServices(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    
    public Account addAccount(Account account) {
        return accountDAO.createAccount(account);
    }

    public Account loginAccount(Account account) {
        return accountDAO.loginAccount(account);
    }

}