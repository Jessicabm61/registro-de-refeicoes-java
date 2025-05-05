
package been;

import java.util.List;
import model.RefeicaoHorario;

public class PlanoAlimentarBean {
   private String descricao;
   private List<RefeicaoHorario> refeicoes; //Lista de refeicao com horário
   
   public PlanoAlimentarBean(String descricao){
       this.descricao = descricao;
   }
   
   public List<RefeicaoHorario> getRefeicoes(){
       return refeicoes;
   }
   
   public String getDescricao(){
       return descricao;
   }
   
   public void setRefeicao(List<RefeicaoHorario> refeicoes){
       this.refeicoes = refeicoes;
   }
}
