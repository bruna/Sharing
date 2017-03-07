package ufrpe.br.sharing.infra.gui;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ufrpe.br.sharing.R;
import ufrpe.br.sharing.dominio.Objeto;

public class ObjetoListAdapter extends ArrayAdapter<Objeto>{
    private Activity activity;
    private ArrayList<Objeto> listaObjetos;
    private static LayoutInflater inflater = null;
    //private Converter converter = Converter.getInstancia();

    public ObjetoListAdapter(Activity activity, int textViewResourceId, ArrayList<Objeto> _listaObjetos){
        super(activity, textViewResourceId, _listaObjetos);
        try {
            this.activity = activity;
            this.listaObjetos = _listaObjetos;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e){

        }
    }
    public int getCount() {
        return listaObjetos.size();
    }

    public Objeto getItem(Objeto position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView nome_objeto_listagem;
        public TextView descricao_objeto_listagem;
        //public ImageView imagemProjeto;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.padrao_lista_objeto, null);
                holder = new ViewHolder();
                holder.nome_objeto_listagem = (TextView) vi.findViewById(R.id.nome_objeto_listagem);
                holder.descricao_objeto_listagem = (TextView) vi.findViewById(R.id.descricao_objeto_listagem);
                //holder.imagemProjeto = (ImageView) vi.findViewById(R.id.imagemProjeto);
                vi.setTag(holder);

            } else {
                holder = (ViewHolder) vi.getTag();
            }
            //String imagemPrincipal = listaObjetos.get(position).getFoto().toString();
            //Bitmap imagem = converter.StringToBitMap(imagemPrincipal);
            holder.nome_objeto_listagem.setText(listaObjetos.get(position).getNome());
            holder.descricao_objeto_listagem.setText(listaObjetos.get(position).getDescricao());
            //holder.imagemProjeto.setImageBitmap(imagem);

        } catch (Exception e) {

        }
        return vi;
    }

}


