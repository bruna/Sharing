package ufrpe.br.sharing.infra.gui;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ufrpe.br.sharing.R;
import ufrpe.br.sharing.dominio.Objeto;

public class ObjetoAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Objeto> objetos;
    private Objeto objeto;

    public ObjetoAdapter(Context context, List<Objeto> objetos){
        this.objetos = objetos;

        mInflater = LayoutInflater.from(context);
    }

    public int getCount(){
        if (objetos != null) {
            return objetos.size();
        } else {
            return 0;
        }
    }

    public Objeto getItem(int posicao)
    {
        return objetos.get(posicao);
    }

    public long getItemId(int posicao)
    {
        return posicao;
    }

    public View getView(int posicao, View view, ViewGroup parent){

        objeto = objetos.get(posicao);

        view = mInflater.inflate(R.layout.content_main, null);

        ((TextView) view.findViewById(R.id.txtitem_nome_objeto)).setText(objeto.getNome());
        ((TextView) view.findViewById(R.id.txtitem_descricao_objeto)).setText(objeto.getDescricao());

        //setFotoObjeto(view);

        return view;
    }

    /*
    public View setFotoObjeto(View view){
        Uri fotoObjeto = objeto.getFoto();


        if (nivelPrioridade == 0 ) {
            ((ImageView) view.findViewById(R.id.img_objeto)).setImageResource(R.drawable.bola_verde);
            return view;
        }else if (nivelPrioridade == 1) {
            ((ImageView) view.findViewById(R.id.img_objeto)).setImageResource(R.drawable.bola_amarela);
            return view;
        }else if (nivelPrioridade == 2) {
            ((ImageView) view.findViewById(R.id.img_objeto)).setImageResource(R.drawable.bola_vermelha);
            return view;
        }return view;*/
    }

