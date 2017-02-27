package ufrpe.br.sharing.negocio;

import android.content.Context;

import ufrpe.br.sharing.R;
import ufrpe.br.sharing.dao.UsuarioDao;
import ufrpe.br.sharing.dominio.Pessoa;
import ufrpe.br.sharing.dominio.Usuario;
import ufrpe.br.sharing.gui.LoginActivity;
import ufrpe.br.sharing.infra.gui.SharingException;

public class UsuarioNegocio {
    private static UsuarioDao usuarioDao;
    private static UsuarioNegocio instanciaUsuarioNegocio = new UsuarioNegocio();

    private UsuarioNegocio() {}

    private Pessoa pessoaEncontrada;

    /* singleton */
    public static UsuarioNegocio getInstancia(Context context) {
        usuarioDao = UsuarioDao.getInstancia(context);
        return instanciaUsuarioNegocio;
    }

    public Usuario logar(String login, String senha) throws SharingException {
        Usuario usuario = usuarioDao.buscarUsuario(login, senha);
        String loginInvalido = "";

        if (usuario == null) {
            loginInvalido = LoginActivity.getContexto().getString(R.string.login_erro);
        }
        if (loginInvalido.length() > 0) {
            throw new SharingException(loginInvalido);
        }

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        sessaoUsuario.setUsuarioLogado(usuario);
        sessaoUsuario.setPessoaLogada(pesquisarPorId(usuario.getId()));

        return usuario;
    }

    public Pessoa pesquisarPorId(int id) {
        Pessoa pessoa = null;
        pessoa = usuarioDao.buscarPessoaId(id);
        return pessoa;
    }

    public void validarCadastro(Pessoa pessoa) throws SharingException {
        Usuario usuario = usuarioDao.buscarUsuarioLogin(pessoa.getUsuario().getLogin());
        if (usuario != null) {
            throw new SharingException("Nome de usuário indisponível");
        }
        pessoaEncontrada = usuarioDao.buscaPessoaPorEmail(pessoa.getEmail());
        if (pessoaEncontrada != null) {
            throw new SharingException("Email já cadastrado");
        }
        pessoaEncontrada = usuarioDao.buscarPessoaCpf(pessoa.getCpf());
        if (pessoaEncontrada!=null){
            throw new SharingException("CPF já cadastrado");
        }
        usuarioDao.cadastrarPessoa(pessoa);
    }



}
