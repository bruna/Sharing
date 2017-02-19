package ufrpe.br.sharing.dominio;

import android.net.Uri;

public class Objeto {
    private int id;
    private int idDono;
    private int idAlugador;
    private String nome;
    private Enum<Categoria> categoriaEnum;
    private Enum<Estado> estadoEnum;
    private String descricao;
    private Uri foto;

    public Objeto(){
        this.idAlugador = 0;
        this.nome = null;
        this.categoriaEnum = null;
        this.estadoEnum = Estado.DISPONIVEL;
        this.categoriaEnum = Categoria.OUTROS;
        this.descricao = null;
        this.foto = null;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public int getIdDono() {return idDono;}
    public void setIdDono(int idDono) {this.idDono = idDono;}

    public int getIdAlugador() {return idAlugador;}
    public void setIdAlugador(int idAlugador) {this.idAlugador = idAlugador;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public Enum<Categoria> getCategoriaEnum() {return categoriaEnum;}
    public void setCategoriaEnum(Enum<Categoria> categoriaEnum) {this.categoriaEnum = categoriaEnum;}

    public Enum<Estado> getEstadoEnum() {return estadoEnum;}
    public void setEstadoEnum(Enum<Estado> estadoEnum) {this.estadoEnum = estadoEnum;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public Uri getFoto() {return foto;}
    public void setFoto(Uri foto) {this.foto = foto;}


}
