
package model;

import java.sql.Time;
import been.RefeicaoBean;

public class RefeicaoHorario {
    private int idRefeicao;
    private Time horaRefeicao;


    public RefeicaoHorario(int idRefeicao, Time horaRefeicao){
        this.idRefeicao = idRefeicao;
        this.horaRefeicao = horaRefeicao;
    }
    
    // Getter para idRefeicao
    public int getIdRefeicao() {
        return idRefeicao;
    }

    // Setter para idRefeicao
    public void setIdRefeicao(int idRefeicao) {
        this.idRefeicao = idRefeicao;
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

