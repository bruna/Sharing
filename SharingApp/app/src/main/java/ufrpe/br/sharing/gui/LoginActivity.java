package ufrpe.br.sharing.gui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import ufrpe.br.sharing.R;
import ufrpe.br.sharing.dominio.Usuario;
import ufrpe.br.sharing.infra.gui.GuiUtil;
import ufrpe.br.sharing.infra.gui.SharingException;
import ufrpe.br.sharing.negocio.UsuarioNegocio;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;


    private Resources resources;
    private static Context contexto;
    private Usuario usuario;
    private UsuarioNegocio usuarioNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        contexto = this;

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        initViews();

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, CadastroActivity.class);
                LoginActivity.this.startActivity(registerIntent);


            }
        });

        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    logar(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        usuarioNegocio=UsuarioNegocio.getInstancia(this);
    }

    private void initViews() {
        resources = getResources();
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etUsername = (EditText) findViewById(R.id.etUsername);
        etUsername.addTextChangedListener(textWatcher);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.addTextChangedListener(textWatcher);

    }


    public void logar(View view){
        if (validateFields()){
            try {
                String login = etUsername.getText().toString();
                String senha = etPassword.getText().toString();


                usuario = usuarioNegocio.logar(login, senha);
                GuiUtil.exibirSaudacao(this);
                startNavigationActivity();

            }catch (SharingException e){
                GuiUtil.exibirMsg(LoginActivity.this, e.getMessage());

            }
        }
    }

    public void startNavigationActivity() {
        startActivity(new Intent(this, PerfilActivity.class));
        finish();
    }


    private boolean validateFields(){
        String user = etUsername.getText().toString().trim();
        String pass = etPassword.getText().toString();
        return (!isEmptyFields(user, pass) && hasSizeValid(user, pass) && !noHasSpaceLogin(user));
    }

    private boolean isEmptyFields(String user, String pass) {
        if (TextUtils.isEmpty(user)) {
            etUsername.requestFocus();
            etUsername.setError(resources.getString(R.string.login_vazio));
            return true;
        } else if (TextUtils.isEmpty(pass)) {
            etPassword.requestFocus();
            etPassword.setError(resources.getString(R.string.login_senha_vazia));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String user, String pass) {
        if (!(user.length() > 4)) {
            etUsername.requestFocus();
            etUsername.setError(resources.getString(R.string.login_curto));
            return false;
        } else if (!(pass.length() > 4)) {
            etPassword.requestFocus();
            etPassword.setError(resources.getString(R.string.login_senha_curta));
            return false;
        }
        return true;
    }

    private boolean noHasSpaceLogin(String user) {
        int idx = user.indexOf(" ");
        if (idx != -1){
            etUsername.requestFocus();
            etUsername.setError(resources.getString(R.string.login_invalido));
            return true;
        }return false;
    }

    public static Context getContexto(){ return contexto; }
}
