package fr.sio1.boiteoutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListUtilisateursAdap extends BaseAdapter {
    private List<Utilisateur> listUtilisateur;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListUtilisateursAdap (Context context, List<Utilisateur> listUtilisateur) {
        this.context = context;
        this.listUtilisateur = listUtilisateur;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listUtilisateur.size();
    }

    @Override
    public Object getItem(int position) {
        return listUtilisateur.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.detail_utilisateur, null);
            holder = new ViewHolder();
            holder.nomView = (TextView) convertView.findViewById(R.id.detail_util_nom);
            holder.prenomView = (TextView) convertView.findViewById(R.id.detail_util_prenom);
            holder.telView = (TextView) convertView.findViewById(R.id.detail_util_tel);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Utilisateur utilisateur = this.listUtilisateur.get(position);
        holder.nomView.setText(utilisateur.getNom());
        holder.prenomView.setText(utilisateur.getPrenom());
        holder.telView.setText("Téléphone : "+utilisateur.getTelephone());

        return convertView;
    }

    static class ViewHolder {
        TextView nomView;
        TextView prenomView;
        TextView telView;
    }
}
