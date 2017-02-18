package ufrpe.br.sharing.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by bruna on 18/02/17.
 */

public class ObjetoDao {

    private static DatabaseHelper databaseHelper;
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    private static ObjetoDao instanciaObjetoDao = new ObjetoDao();

    private ObjetoDao() {}

    public static ObjetoDao getInstancia(Context contexto) {
        ObjetoDao.databaseHelper = new DatabaseHelper(contexto);
        return instanciaObjetoDao;
    }


    /**
     * metodo utilizado para fazer o cadastro do evento no banco
     *
     * @param objeto                    objeto a ser cadastrado no db
     * @throws SharingException         caso o evento nao consiga ser cadastrado
     */
    public void cadastrarEvento(Objeto objeto) throws SharingException {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.OBJETO_ID, objeto.getId());
        values.put(DatabaseHelper.OBJETO_NOME, objeto.getNome());


        values = new ContentValues();
        values.put(DatabaseHelper.OBJETO_NOME, objeto.getNome());
        values.put(DatabaseHelper.OBJETO_DESCRICAO, objeto.getDescricao().toString());

        int idPessoa = SessaoUsuario.getInstancia().getPessoaLogada().getId();
        values.put(DatabaseHelper.PESSOA_CRIADORA_ID, idPessoa);


        db.insert(DatabaseHelper.TABELA_OBJETO, null, values);
        db.close();
    }

    /**
     * metodo utilizado para fazer a criacao do objeto no banco
     *
     * @param cursor                        cursor a ser usado na criacao do objeto
     * @return                              objeto 'objeto' preenchido
     * @throws SharingException             caso o objeto nao possa ser criado
     */
    public Objeto criarEvento(Cursor cursor) throws SharingException {
        Objeto objeto = new Objeto();
        objeto.setId(cursor.getInt(0));
        objeto.setNome(cursor.getString(1));
        objeto.setCategoria(cursor.getString(2));
        objeto.setEstado(cursor.getString(3));
        objeto.setDescricao(cursor.getString(4));
        objeto.setFoto(cursor.getString(5));

        return objeto;
    }
}
