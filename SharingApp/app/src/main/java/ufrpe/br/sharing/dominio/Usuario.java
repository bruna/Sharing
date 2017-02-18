package ufrpe.br.sharing.dominio;

/**
 * Created by bruna on 18/02/17.
 */

public class Usuario {

    private String login;
    private int id;
    private String senha;

    public Usuario() {
        this.login = null;
        this.senha = null;
    }


    public String getLogin(){
        return login;
    }
    public void setLogin(String login){
        this.login = login;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }

}