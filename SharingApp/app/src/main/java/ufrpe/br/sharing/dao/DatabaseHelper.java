package ufrpe.br.sharing.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bruna on 06/02/17.
 */

private static final String NOME_DB = "banco";
private static final int VERSAO_DB = 1;

    /* singleton */
public DatabaseHelper (Context context) {
        super(context.getApplicationContext(),NOME_DB,null,VERSAO_DB);
        }

//TABELA PESSOA
public static final String TABELA_PESSOA= "tabela_pessoa";
public static final String PESSOA_ID = "_id_pessoa";
public static final String PESSOA_NOME = "nome_pessoa";
public static final String PESSOA_EMAIL = "email_pessoa";
public static final String PESSOA_ENDERECO = "endereco_pessoa";
public static final String PESSOA_CPF = "cpf_pessoa";
public static final String PESSOA_FOTO = "foto_pessoa";

//TABELA USUARIO
public static final String TABELA_USUARIO = "tabela_usuario";
public static final String USUARIO_ID = "_id_usuario";
public static final String USUARIO_LOGIN = "login_usuario";
public static final String USUARIO_SENHA = "senha_usuario";
public static final String USUARIO_PESSOA_ID = "id_pessoa_usuario";

//TABELA OBJETO
public static final String TABELA_OBJETO = "tabela_objeto";
public static final String OBJETO ID = "_id_objeto";
public static final String OBJETO_NOME = "nome_objeto";
public static final String OBJETO_CATEGORIA = "categoria_objeto";
public static final String OBJETO_ESTADO = "estado_objeto";
public static final String OBJETO_DESCRICAO = "descricao_objeto";
public static final String OBJETO_FOTO = "foto_objeto";


@Override
public void onCreate(SQLiteDatabase db) {

        db.execSQL(ScriptTableSQL.getTabelaPessoa());
        db.execSQL(ScriptTableSQL.getTabelaUsuario());
        db.execSQL(ScriptTableSQL.getTabelaObjeto());

        }

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABELA_USUARIO);
        db.execSQL("drop table if exists " + TABELA_PESSOA);
        db.execSQL("drop table if exists " + TABELA_OBJETO);

        onCreate(db);
        }

        }