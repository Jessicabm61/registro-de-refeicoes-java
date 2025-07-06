package model;

import java.sql.Time;

public class RefeicaoHorario {
    private String nomeRefeicao;
    private Time horaRefeicao;


    public RefeicaoHorario(String nomeRefeicao, Time horaRefeicao){
        this.nomeRefeicao = nomeRefeicao;
        this.horaRefeicao = horaRefeicao;
    }
    
    // Getter para idRefeicao
    public String getNomeRefeicao() {
        return nomeRefeicao;
    }

    // Setter para idRefeicao
    public void setNomeRefeicao(String nomeRefeicao) {
        this.nomeRefeicao = nomeRefeicao;
    }

    // Getter para horaRefeicao
    public Time getHoraRefeicao() {
        return horaRefeicao;
    }

    // Setter para horaRefeicao
    public void setHoraRefeicao(Time horaRefeicao) {
        this.horaRefeicao = horaRefeicao;
    }
}

