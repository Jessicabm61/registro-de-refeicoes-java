
package model;

import been.AlimentoBeen;

public class AlimentoQuantidade {
    private AlimentoBeen alimento;
    private double quantidade;
    
    public AlimentoQuantidade (AlimentoBeen alimento, double quantidade){
        this.alimento = alimento;
        this.quantidade = quantidade;
        
    }
public AlimentoBeen getAlimento() {
        return alimento;
    }

    public void setAlimento(AlimentoBeen alimento) {
        this.alimento = alimento;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}