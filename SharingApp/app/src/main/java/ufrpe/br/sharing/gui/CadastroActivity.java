package ufrpe.br.sharing.gui;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;

import ufrpe.br.sharing.R;
import ufrpe.br.sharing.dominio.Pessoa;
import ufrpe.br.sharing.dominio.Usuario;
import ufrpe.br.sharing.infra.gui.GuiUtil;
import ufrpe.br.sharing.infra.gui.SharingException;
import ufrpe.br.sharing.negocio.UsuarioNegocio;

public class CadastroActivity extends AppCompatActivity {
    private ImageView imgFoto;
    private File caminhoFoto;
    public static final int TIRA_FOTO = 1;
    Uri foto = FOTO_PADRAO;

    private EditText editPessoaNome;
    private EditText editUsuarioLogin;
    private EditText editPessoaEmail;
    private EditText editPessoaCpf;
    private EditText editPessoaEndereco;
    private EditText editUsuarioSenha;
    private EditText editUsuarioSenhaConfirma;

    private Resources resources;

    private String nome;
    private String login;
    private String email;
    private String cpf;
    private String endereco;
    private String senha;
    private String senhaConfirma;

    private Button btnCadastrar;
    private UsuarioNegocio usuarioNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgFoto = (ImageView) findViewById(R.id.userPicture);
        initViews();

        btnCadastrar = (Button) findViewById(R.id.btn_signup);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    private void initViews() {
        resources = getResources();
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        editPessoaNome = (EditText) findViewById(R.id.input_nome);
        editPessoaNome.addTextChangedListener(textWatcher);

        editUsuarioLogin = (EditText) findViewById(R.id.input_login);
        editUsuarioLogin.addTextChangedListener(textWatcher);

        editPessoaEmail = (EditText) findViewById(R.id.input_email);
        editPessoaEmail.addTextChangedListener(textWatcher);

        editPessoaCpf = (EditText) findViewById(R.id.input_cpf);
        editPessoaCpf.addTextChangedListener(textWatcher);

        editPessoaEndereco = (EditText) findViewById(R.id.input_endereco);
        editPessoaEndereco.addTextChangedListener(textWatcher);

        editUsuarioSenha = (EditText) findViewById(R.id.input_senha);
        editUsuarioSenha.addTextChangedListener(textWatcher);

        editUsuarioSenhaConfirma = (EditText) findViewById(R.id.input_senha_confirma);
        editUsuarioSenhaConfirma.addTextChangedListener(textWatcher);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (usuarioNegocio == null) {
            usuarioNegocio = UsuarioNegocio.getInstancia(this);
        }
    }

    private boolean validateFieldsCadastro(){
        nome = editPessoaNome.getText().toString().trim();
        login = editUsuarioLogin.getText().toString().trim();
        email = editPessoaEmail.getText().toString().trim();
        cpf = editPessoaCpf.getText().toString().trim();
        endereco = editPessoaEndereco.getText().toString().trim();
        senha = editUsuarioSenha.getText().toString();
        senhaConfirma = editUsuarioSenhaConfirma.getText().toString();

        return (!isEmptyFieldsCadastro(nome, login, email, cpf, endereco, senha, senhaConfirma)
                && hasSizeValidCadastro(login, email, cpf, endereco, senha, senhaConfirma) &&
                !noHasSpaceCadastro(login, email, cpf, senha) &&
                isValidEmail(email));
    }

    private boolean isEmptyFieldsCadastro(String nome, String login, String email, String cpf, String endereco, String senha, String senhaConfirma) {
        if (TextUtils.isEmpty(nome)) {
            editPessoaNome.requestFocus();
            editPessoaNome.setError(resources.getString(R.string.cadastro_nome_vazio));
            return true;
        } else if (TextUtils.isEmpty(login)) {
            editUsuarioLogin.requestFocus();
            editUsuarioLogin.setError(resources.getString(R.string.login_vazio));
            return true;
        } else if (TextUtils.isEmpty(email)) {
            editPessoaEmail.requestFocus();
            editPessoaEmail.setError(resources.getString(R.string.cadastro_email_vazio));
            return true;
        } else if (TextUtils.isEmpty(cpf)) {
            editPessoaCpf.requestFocus();
            editPessoaCpf.setError(resources.getString(R.string.cadastro_cpf_vazio));
            return true;
        } else if (TextUtils.isEmpty(endereco)) {
            editPessoaEndereco.requestFocus();
            editPessoaEndereco.setError(resources.getString(R.string.cadastro_endereco_vazio));
            return true;
        } else if (TextUtils.isEmpty(senha)) {
            editUsuarioSenha.requestFocus();
            editUsuarioSenha.setError(resources.getString(R.string.login_senha_vazia));
            return true;
        } else if (TextUtils.isEmpty(senhaConfirma)) {
            editUsuarioSenhaConfirma.requestFocus();
            editUsuarioSenhaConfirma.setError(resources.getString(R.string.cadastro_senha_confirm_vazia));
            return true;
        }
        return false;
    }

    private boolean hasSizeValidCadastro(String user, String email,String cpf, String endereco, String pass, String pass2) {
        if (!(user.length() > 4)) {
            editUsuarioLogin.requestFocus();
            editUsuarioLogin.setError(resources.getString(R.string.login_curto));
            return false;
        } else if (!(email.length() > 4)) {
            editPessoaEmail.requestFocus();
            editPessoaEmail.setError(resources.getString(R.string.cadastro_email_curto));
            return false;
        } else if (cpf.length() != 11) {
            editPessoaCpf.requestFocus();
            editPessoaCpf.setError(resources.getString(R.string.cadastro_cpf_curto));
            return false;
        } else if (!(endereco.length() > 6)) {
            editPessoaEndereco.requestFocus();
            editPessoaEndereco.setError(resources.getString(R.string.cadastro_endereco_curto));
            return false;
        }else if (!(pass.length() > 4)) {
            editUsuarioSenha.requestFocus();
            editUsuarioSenha.setError(resources.getString(R.string.login_senha_curta));
            return false;
        }  else if (!(pass2.length() > 4)) {
            editUsuarioSenhaConfirma.requestFocus();
            editUsuarioSenhaConfirma.setError(resources.getString(R.string.login_senha_curta));
            return false;
        } else if (!(pass.equals(pass2))){
            editUsuarioSenhaConfirma.requestFocus();
            editUsuarioSenhaConfirma.setError(resources.getString(R.string.cadastro_senhas_diferentes));
            return false;
        }
        return true;
    }

    private boolean noHasSpaceCadastro(String login, String email, String cpf, String senha) {

        if (login.contains(" ")){
            editUsuarioLogin.requestFocus();
            editUsuarioLogin.setError(resources.getString(R.string.login_invalido));
            return true;
        } else if (email.contains(" ")){
            editPessoaEmail.requestFocus();
            editPessoaEmail.setError(resources.getString(R.string.login_invalido));
            return true;
        } else if (cpf.contains(" ")){
            editPessoaCpf.requestFocus();
            editPessoaCpf.setError(resources.getString(R.string.cadastro_cpf_invalido));
            return true;
        }else if (senha.contains(" ")){
            editUsuarioSenha.requestFocus();
            editUsuarioSenha.setError(resources.getString(R.string.cadastro_senha_invalida));
            return true;
        }return false;
    }

    private boolean isValidEmail(CharSequence email) {
        if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            editPessoaEmail.requestFocus();
            editPessoaEmail.setError(resources.getString(R.string.cadastro_email_invalido));
            return false;
        }
        return true;
    }

    private  void cadastrar(){
        if(validateFieldsCadastro()){

            try {
                Usuario usuario = new Usuario();
                usuario.setLogin(login);
                usuario.setSenha(senha);

                Pessoa pessoa = new Pessoa();
                pessoa.setNome(nome);
                pessoa.setEmail(email);
                pessoa.setCpf(cpf);
                pessoa.setEndereco(endereco);
                pessoa.setFoto(foto);
                pessoa.setUsuario(usuario);

                usuarioNegocio.validarCadastro(pessoa);
                GuiUtil.exibirMsg(this, getString(R.string.cadastro_sucesso));
                startLoginActivity();

            }catch (SharingException e){
                GuiUtil.exibirMsg(CadastroActivity.this, e.getMessage());

            }
        }
    }

    public void tirarFoto(View v){
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
            foto = GuiUtil.getInstancia().getImageUri(CadastroActivity.this , ajustarFoto());
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

    public void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}