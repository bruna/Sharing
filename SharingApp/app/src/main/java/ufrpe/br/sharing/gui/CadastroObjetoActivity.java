package ufrpe.br.sharing.gui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;

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
}
