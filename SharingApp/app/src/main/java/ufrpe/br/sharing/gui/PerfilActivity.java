package ufrpe.br.sharing.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import ufrpe.br.sharing.R;
import ufrpe.br.sharing.dominio.Objeto;
import ufrpe.br.sharing.dominio.Pessoa;
import ufrpe.br.sharing.infra.gui.GuiUtil;
import ufrpe.br.sharing.infra.gui.ObjetoAdapter;
import ufrpe.br.sharing.infra.gui.ObjetoListAdapter;
import ufrpe.br.sharing.infra.gui.SharingException;
import ufrpe.br.sharing.negocio.ObjetoNegocio;
import ufrpe.br.sharing.negocio.SessaoUsuario;
import ufrpe.br.sharing.infra.gui.SharingException;

public class PerfilActivity extends AppCompatActivity {
    private ArrayList<Objeto> objetos;
    private ArrayList<Objeto> objetosEncontrados;
    private ArrayList<Objeto> listItems = new ArrayList<>();

    private ListView listView;
    private EditText campoBusca;
    private Button btnCadastrarObjeto;

    private SessaoUsuario sessaoUsuario;
    private Pessoa pessoaLogada;
    private ObjetoNegocio objetoNegocio;
    private ObjetoAdapter objetoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessaoUsuario = SessaoUsuario.getInstancia();
        pessoaLogada = sessaoUsuario.getPessoaLogada();
        objetoNegocio = ObjetoNegocio.getInstancia();

        listView = (ListView) findViewById(R.id.listaObjetos);
        campoBusca = (EditText) findViewById(R.id.edtBusca);
        btnCadastrarObjeto = (Button) findViewById(R.id.btnCadastrarObjeto);

        try {
            initList();
        } catch (SharingException e) {
            GuiUtil.exibirMsg(PerfilActivity.this, e.getMessage());
        }
        objetoAdapter = new ObjetoAdapter(this, listItems);

        campoBusca.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s.toString().equals("")) {
                        initList();
                    } else {
                        searchItem(s.toString().trim());
                    }
                } catch (SharingException e) {
                    GuiUtil.exibirMsg(PerfilActivity.this, e.getMessage());
                }

            }
        });

        btnCadastrarObjeto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cadastrarObjeto(view);
            }
        });
    }

    public void searchItem(String textToSearch) throws SharingException {

        int id = pessoaLogada.getId();
        objetosEncontrados = (ArrayList<Objeto>) objetoNegocio.consultarNomeDescricaoParcial(id, textToSearch);

        objetoAdapter = new ObjetoAdapter(this, objetosEncontrados);
        listView.setAdapter(objetoAdapter);
    }

    public void initList() throws SharingException {
       /* objetos = objetoNegocio.listarObjetos();

        objetoAdapter = new ObjetoAdapter(this, objetos);

        listView.setAdapter(objetoAdapter);*/
    }

    public void cadastrarObjeto(View view){
        Intent i = new Intent(PerfilActivity.this,CadastroObjetoActivity.class);
        startActivity(i);
    }

}


