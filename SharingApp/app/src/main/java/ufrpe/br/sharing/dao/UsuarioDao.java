package ufrpe.br.sharing.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by bruna on 18/02/17.
 */

public class UsuarioDao {

    public class UsuarioDao {

        private static DatabaseHelper databaseHelper;
        private static ObjetoDao objetoDao;
        private static Context contexto;

        private static UsuarioDao instanciaUsuarioDao = new UsuarioDao();

        private UsuarioDao() {
        }

        ;

        /* singleton */
        public static UsuarioDao getInstancia(Context contexto) {
            UsuarioDao.contexto = contexto;
            UsuarioDao.databaseHelper = new DatabaseHelper(contexto);
            UsuarioDao.objetoDao = ObjetoDao.getInstancia(contexto);
            return instanciaUsuarioDao;
        }

        private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();

        /**
         * metodo utilizado para registrar o objeto pessoa no sistema
         *
         * @param pessoa pessoa a ser registrada no sistema
         */
        public void cadastrarPessoa(Pessoa pessoa) {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.PESSOA_NOME, pessoa.getNome());
            values.put(DatabaseHelper.PESSOA_EMAIL, pessoa.getEmail());
            values.put(DatabaseHelper.PESSOA_FOTO, pessoa.getFoto().toString());

            long foreing_key_id_pessoa = db.insert(DatabaseHelper.TABELA_PESSOA, null, values);

            values = new ContentValues();
            values.put(DatabaseHelper.USUARIO_LOGIN, pessoa.getUsuario().getLogin());
            values.put(DatabaseHelper.USUARIO_PESSOA_ID, foreing_key_id_pessoa);
            values.put(DatabaseHelper.USUARIO_SENHA, pessoa.getUsuario().getSenha());

            db.insert(DatabaseHelper.TABELA_USUARIO, null, values);
            db.close();
        }
    }
}

