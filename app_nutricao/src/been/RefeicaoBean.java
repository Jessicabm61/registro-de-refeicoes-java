
package been;
import been.AlimentoBeen;
import model.AlimentoQuantidade;
import java.util.List;

public class RefeicaoBean {
    private int idRefeicao;
    private String nomeRefeicao;
    private String descricao;
    private List<AlimentoQuantidade> alimentos; // Uma refeicao possui varios alimentos com quantidade

    public RefeicaoBean(int idRefeicao, String nomeRefeicao, String descricao) {
        this.idRefeicao = idRefeicao;
        this.nomeRefeicao = nomeRefeicao;
        this.descricao = descricao;
    }

    public RefeicaoBean(){
    }
    
 // Getters e setters
    public int getId() {
        return idRefeicao;
    }

    public void setId(int idRefeicao) {
        this.idRefeicao = idRefeicao;
    }

    public String getNome() {
        return nomeRefeicao;
    }

    public void setNome(String nomeRefeicao) {
        this.nomeRefeicao = nomeRefeicao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<AlimentoQuantidade> getAlimentos() {
        return alimentos;
    }

    public void setAlimentos(List<AlimentoQuantidade> alimentos) {
        this.alimentos = alimentos;
    }
    
    @Override
    public String toString() {
    return String.format("ID Refeicao: %-4d Nome Refeicao: %-20s Descricao: %-30s",
                         idRefeicao, nomeRefeicao, descricao);
    }
}

