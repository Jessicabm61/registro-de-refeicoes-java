package been;

public class AlimentoBeen {
     
    private int idAlimento;
    private String nomeAlimento;
    private double calorias;
    private double proteinas;
    private double carboidratos;

    // Construtor
    public AlimentoBeen(int idAlimento, String nomeAlimento, double calorias, double proteinas, double carboidratos) {
        this.idAlimento = idAlimento;
        this.nomeAlimento = nomeAlimento;
        this.calorias = calorias;
        this.proteinas = proteinas;
        this.carboidratos = carboidratos;
    }

    public AlimentoBeen(){
    }
    
    // Getters e Setters
    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getNomeAlimento() {
        return nomeAlimento;
    }

    public void setNomeAlimento(String nomeAlimento) {
        this.nomeAlimento = nomeAlimento;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public double getProteinas() {
        return proteinas;
    }

    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }

    public double getCarboidratos() {
        return carboidratos;
    }

    public void setCarboidratos(double carboidratos) {
        this.carboidratos = carboidratos;
    }

    @Override
    public String toString() {
    return String.format(
        "ID: %-4d Nome: %-20s Calorias: %-8.2f Proteínas: %-8.2f Carboidratos: %-8.2f",
        idAlimento, nomeAlimento, calorias, proteinas, carboidratos
        );
    }
}
