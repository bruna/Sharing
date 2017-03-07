package ufrpe.br.sharing.gui;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.text.ParseException;
import java.util.Date;

import ufrpe.br.sharing.R;
import ufrpe.br.sharing.dominio.Categoria;
import ufrpe.br.sharing.dominio.Estado;
import ufrpe.br.sharing.dominio.Objeto;
import ufrpe.br.sharing.dominio.Pessoa;
import ufrpe.br.sharing.infra.gui.GuiUtil;
import ufrpe.br.sharing.negocio.ObjetoNegocio;
import ufrpe.br.sharing.negocio.SessaoUsuario;

public class CadastroObjetoActivity extends AppCompatActivity{
    private Resources resources;
    private ObjetoNegocio objetoNegocio;
    private SessaoUsuario sessaoUsuario;
    private Pessoa pessoaLogada;

    private ImageView imgFoto;
    private File caminhoFoto;
    public static final int TIRA_FOTO = 1;
    Uri foto = FOTO_PADRAO;

    private EditText editObjetoNome;
    private EditText editObjetoDescricao;

    private Button btnCadastrar;
    private Spinner spinner;

    private String nomeObjeto;
    private String descricaoObjeto;
    private Categoria categoriaObjeto;
    private Estado estadoObjeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adc_objeto);

        imgFoto = (ImageView)findViewById(R.id.objPicture);
        resources = getResources();

        this.setCamposAdcObjeto();
        objetoNegocio = ObjetoNegocio.getInstancia(this);
        sessaoUsuario = SessaoUsuario.getInstancia();
        pessoaLogada = sessaoUsuario.getPessoaLogada();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaObjeto = ((Categoria) parent.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    cadastrarObjeto();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setCamposAdcObjeto(){

        spinner = (Spinner) findViewById(R.id.spinnerCategoria);
        spinner.setAdapter(new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1, Categoria.values()));
        btnCadastrar = (Button) findViewById(R.id.btnCadastrarObjeto);

        editObjetoNome = (EditText) findViewById(R.id.input_nomeObjeto);
        editObjetoDescricao = (EditText) findViewById(R.id.input_descriObjetoObjeto);
        estadoObjeto = Estado.DISPONIVEL;
    }

    private boolean validateFieldsEvento() throws ParseException {

        nomeObjeto = editObjetoNome.getText().toString().trim();
        descricaoObjeto = editObjetoDescricao.getText().toString().trim();

        return (!isEmptyFields(nomeObjeto, descricaoObjeto) && hasSizeValid(nomeObjeto, descricaoObjeto));
    }

    private boolean isEmptyFields(String nome, String descricao) {

        if (TextUtils.isEmpty(nome)) {
            editObjetoNome.requestFocus();
            editObjetoNome.setError(resources.getString(R.string.cadastro_nome_vazio));
            return true;
        } else if (TextUtils.isEmpty(descricao)) {
            editObjetoDescricao.requestFocus();
            editObjetoDescricao.setError(resources.getString(R.string.adcObjeto_edt_descricao));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String nome, String descricao) {
        if (!(nome.length() > 1)) {
            editObjetoNome.requestFocus();
            editObjetoNome.setError(resources.getString(R.string.adcObjeto_nome_curto));
            return false;
        } else if (!(descricao.length() > 4)) {
            editObjetoDescricao.requestFocus();
            editObjetoDescricao.setError(resources.getString(R.string.adcEvento_descricao_curta));
            return false;
        }
        return true;
    }

    private  void cadastrarObjeto() throws ParseException {
        if (validateFieldsEvento()) {
            try {
                int idPessoaLogada = sessaoUsuario.getPessoaLogada().getId();
                Objeto objeto = new Objeto();
                objeto.setIdDono(idPessoaLogada);
                objeto.setNome(nomeObjeto);
                objeto.setDescricao(descricaoObjeto);
                objeto.setCategoriaEnum(categoriaObjeto);
                objeto.setEstadoEnum(estadoObjeto);
                objeto.setFoto(FOTO_PADRAO);
                objetoNegocio.validarCadastroObjeto(objeto);
                GuiUtil.exibirMsg(this, getString(R.string.adc_objeto_sucesso));
                startPerfilActivity();
            } catch (Exception e) {
                GuiUtil.exibirMsg(CadastroObjetoActivity.this, e.getMessage());

            }
        }
    }

    public void startPerfilActivity() {
        startActivity(new Intent(this, PerfilActivity.class));
        finish();
    }

    public void tirarFotoObj(View v){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String nomeFoto = DateFormat.format("yyyy-MM-dd_hhmmss.png", new Date()).toString();

            caminhoFoto = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nomeFoto);

            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminhoFoto));
            startActivityForResult(i, TIRA_FOTO);
        }else{
            caminhoFoto = null;
        }
    }

    public static final Uri FOTO_PADRAO = Uri.parse("android.resource://ufrpe.br.sharing/"+R.drawable.user);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == TIRA_FOTO && ajustarFoto() != null){
            foto = GuiUtil.getInstancia().getImageUri(CadastroObjetoActivity.this , ajustarFoto());
        }else{
            foto = FOTO_PADRAO;
        }
    }

    private Bitmap ajustarFoto() {
        if(caminhoFoto != null){
            int targetwidth = imgFoto.getWidth();
            int targetHeight = imgFoto.getHeight();

            BitmapFactory.Options bmOption = new BitmapFactory.Options();

            bmOption.inJustDecodeBounds = false;
            BitmapFactory.decodeFile(caminhoFoto.getAbsolutePath(), bmOption);
            int photoW = bmOption.outWidth;
            int photoH = bmOption.outHeight;

            int scaleFactor = Math.min(photoW / targetwidth, photoH / targetHeight);
            bmOption.inSampleSize = scaleFactor;

            Bitmap bmp = BitmapFactory.decodeFile(caminhoFoto.getAbsolutePath(), bmOption);

            imgFoto.setImageBitmap(bmp);
            return bmp;
        } return null;
    }
}
