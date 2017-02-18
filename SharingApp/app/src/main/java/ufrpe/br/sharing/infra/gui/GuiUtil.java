package ufrpe.br.sharing.infra.gui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import ufrpe.br.sharing.R;
import ufrpe.br.sharing.gui.LoginActivity;
import ufrpe.br.sharing.negocio.SessaoUsuario;

/**
 * Created by bruna on 18/02/17.
 */

public class GuiUtil {
    private static String nomePessoaLogada;

    public static void exibirMsg(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    public static void exibirSaudacao(Activity activity){
        Context context;
        context = LoginActivity.getContexto();
        nomePessoaLogada = SessaoUsuario.getInstancia().getPessoaLogada().getNome();
        Toast.makeText(activity, context.getString(R.string.login_sucesso)+" "+nomePessoaLogada+"!", Toast.LENGTH_LONG).show();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static final Uri IMAGEM_PADRAO = Uri.parse("android.resource://br.com.sharing/"+ R.drawable.user);

    private static GuiUtil instanciaGuiUtil = new GuiUtil();
    private GuiUtil(){}
    public static GuiUtil getInstancia() {
        return instanciaGuiUtil;
    }

}