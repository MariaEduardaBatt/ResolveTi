package com.empresa.chamados.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.empresa.chamados.R;
import com.empresa.chamados.activities.AtendimentoChamadoActivity;
import com.empresa.chamados.models.Chamado;
import java.util.List;

public class ChamadosAdapter extends RecyclerView.Adapter<ChamadosAdapter.ViewHolder> {

    private List<Chamado> lista;

    public ChamadosAdapter(List<Chamado> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chamado, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chamado c = lista.get(position);
        holder.txtTitulo.setText(c.getTitulo());
        holder.txtData.setText(c.getData());
        holder.txtLocal.setText(c.getLocal());
        holder.txtStatus.setText(c.getStatus());

        // Cores do PRD
        int corStatus;
        switch (c.getStatus()) {
            case "Em Atendimento":
                corStatus = ContextCompat.getColor(holder.itemView.getContext(), R.color.laranja_claro);
                break;
            case "Concluído":
                corStatus = ContextCompat.getColor(holder.itemView.getContext(), R.color.marrom);
                break;
            default: // Aberto
                corStatus = ContextCompat.getColor(holder.itemView.getContext(), R.color.laranja_escuro);
                break;
        }
        holder.txtStatus.setBackgroundColor(corStatus);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AtendimentoChamadoActivity.class);
            intent.putExtra("CHAMADO_ID", c.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtData, txtLocal, txtStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.item_titulo);
            txtData = itemView.findViewById(R.id.item_data);
            txtLocal = itemView.findViewById(R.id.item_local);
            txtStatus = itemView.findViewById(R.id.item_status);
        }
    }
}
