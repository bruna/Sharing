package ufrpe.br.sharing.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ufrpe.br.sharing.dominio.Categoria;
import ufrpe.br.sharing.dominio.Estado;
import ufrpe.br.sharing.dominio.Objeto;
import ufrpe.br.sharing.infra.gui.SharingException;
import ufrpe.br.sharing.negocio.SessaoUsuario;

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
    public void cadastrarObjeto(Objeto objeto) throws SharingException {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.OBJETO_ID, objeto.getId());
        values.put(DatabaseHelper.OBJETO_NOME, objeto.getNome());
        values.put(DatabaseHelper.OBJETO_FOTO, objeto.getFoto().toString());


        values = new ContentValues();
        values.put(DatabaseHelper.OBJETO_NOME, objeto.getNome());
        values.put(DatabaseHelper.OBJETO_CATEGORIA, objeto.getCategoriaEnum().ordinal());
        values.put(DatabaseHelper.OBJETO_ESTADO, objeto.getEstadoEnum().ordinal());
        values.put(DatabaseHelper.OBJETO_DESCRICAO, objeto.getDescricao());

        int idPessoa = SessaoUsuario.getInstancia().getPessoaLogada().getId();
        values.put(DatabaseHelper.OBJETO_DONO_ID, idPessoa);




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
    public Objeto criarObjeto(Cursor cursor) throws SharingException {
        Objeto objeto = new Objeto();
        objeto.setId(cursor.getInt(0));
        objeto.setNome(cursor.getString(1));
        objeto.setDescricao(cursor.getString(4));
        objeto.setFoto(Uri.parse(cursor.getString(5)));

        Categoria categoria = Categoria.values()[cursor.getInt(2)];
        objeto.setCategoriaEnum(categoria);

        Estado estado = Estado.values()[cursor.getInt(3)];
        objeto.setEstadoEnum(estado);



        return objeto;
    }
}
