package ufrpe.br.sharing.dominio;

import android.net.Uri;

import java.util.List;

/**
 * Created by bruna on 18/02/17.
 */

public class Pessoa {
    private int id;
    private Usuario usuario;
    private String nome;
    private String email;
    private String endereco;
    private String cpf;
    private Uri foto;


    public Pessoa() {
        this.nome = null;
        this.email = null;
        this.endereco = null;
        this.cpf = null;
        this.foto = null;
        this.usuario=null;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){ this.id = id; }

    public Usuario getUsuario(){
        return usuario;
    }
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public String getNome(){return this.nome;}
    public void setNome(String nome){ this.nome = nome; }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getEndereco() { return this.endereco; }
    public void setEndereco(String endereco){ this.endereco = endereco; }

    public String getCpf(){return this.cpf;}
    public void setCpf(String cpf){ this.cpf = cpf; }

    public Uri getFoto() {
        return foto;
    }
    public void setFoto(Uri foto) {
        this.foto = foto;
    }




}