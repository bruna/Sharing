package ufrpe.br.sharing.negocio;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ufrpe.br.sharing.dao.ObjetoDao;
import ufrpe.br.sharing.dominio.Objeto;
import ufrpe.br.sharing.dominio.Pessoa;
import ufrpe.br.sharing.infra.gui.SharingException;

public class ObjetoNegocio {

    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
    private Pessoa pessoaLogada = sessaoUsuario.getPessoaLogada();

    private static ObjetoDao objetoDao;

    private static ObjetoNegocio instancia = new ObjetoNegocio();

    private ObjetoNegocio(){}

    public static ObjetoNegocio getInstancia(Context context){
        objetoDao = ObjetoDao.getInstancia(context);
        return instancia;
    }
    public static ObjetoNegocio getInstancia(){
        return instancia;
    }

    public List<Objeto> consultarNomeDescricaoParcial(int id, String nome) throws SharingException {
        return objetoDao.buscarNomeDescricaoParcial(id, nome);
    }

    public Objeto pesquisarPorNome(String nome) throws SharingException {
        Objeto objeto = objetoDao.buscarObjetoNome(nome);
        return objeto;
    }

    public void validarCadastroObjeto(Objeto objeto) throws SharingException {
        Objeto objetoBusca = objetoDao.buscarObjetoNomeEDono(pessoaLogada.getId(),objeto.getNome());
        if (objetoBusca != null){
            throw new SharingException("Objeto já cadastrado");
        }
        objetoDao.cadastrarObjeto(objeto);
    }

    public ArrayList<Objeto> listarObjetos( ) throws SharingException{
        return objetoDao.listarObjetos();

    }
}
