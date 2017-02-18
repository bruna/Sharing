package ufrpe.br.sharing.dao;

/**
 * Created by bruna on 18/02/17.
 */

public class ScriptSQL {

    public static class ScriptTableSQL {
        public static String getTabelaPessoa() {

            StringBuilder pessoaBuilder = new StringBuilder();
            pessoaBuilder.append("CREATE TABLE  tabela_pessoa  (  ");
            pessoaBuilder.append("_id_pessoa   integer primary key autoincrement,   ");
            pessoaBuilder.append("nome_pessoa  text not null,  ");
            pessoaBuilder.append("email_pessoa text not null unique,   ");
            pessoaBuilder.append("endereco_pessoa text not null);");
            pessoaBuilder.append("cpf_pessoa text not null unique,   ");
            pessoaBuilder.append("foto_pessoa text not null);");
            return pessoaBuilder.toString();
        }

        public static String getTabelaUsuario() {

            StringBuilder usuarioBuilder = new StringBuilder();
            usuarioBuilder.append("CREATE TABLE  tabela_usuario ( ");
            usuarioBuilder.append("_id_usuario   integer primary key autoincrement,   ");
            usuarioBuilder.append("login_usuario  text not null unique,  ");
            usuarioBuilder.append("senha_usuario  text not null, ");
            usuarioBuilder.append("id_pessoa_usuario   integer,  ");
            usuarioBuilder.append("foreign key ( id_pessoa_usuario ) references  tabela_pessoa ( _id_pessoa ) );");
            return usuarioBuilder.toString();
        }

        public static String getTabelaObjeto() {

            StringBuilder objetoBuilder = new StringBuilder();
            objetoBuilder.append("CREATE TABLE  tabela_objeto ( ");
            objetoBuilder.append("_id_objeto   integer primary key autoincrement, ");
            objetoBuilder.append("nome_objeto  text not null unique,  ");
            objetoBuilder.append("categoria_objeto  text not null, ");
            objetoBuilder.append("estado_objeto  text not null, ");
            objetoBuilder.append("descricao_objeto  text not null, ");
            objetoBuilder.append("foto_objeto text not null, ");
            return objetoBuilder.toString();
        }
    }
}
