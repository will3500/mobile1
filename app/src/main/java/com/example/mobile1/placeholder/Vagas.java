package com.example.mobile1.placeholder;

public class Vagas {
    private int id;
    private String areaConhecimento;
    private String descricao;
    private float salario;
    private String local;
    private String email;
    private String telefone;
    private String anunciante;

    public Vagas(int id, String areaConhecimento, String descricao, float salario, String local, String email, String telefone, String anunciante) {
        this.id = id;
        this.areaConhecimento = areaConhecimento;
        this.descricao = descricao;
        this.salario = salario;
        this.local = local;
        this.email = email;
        this.telefone = telefone;
        this.anunciante = anunciante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaConhecimento() {
        return areaConhecimento;
    }

    public void setAreaConhecimento(String areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getAnunciante() {
        return anunciante;
    }

    public void setAnunciante(String anunciante) {
        this.anunciante = anunciante;
    }

    @Override
    public String toString() {
        return "Vagas{" +
                "id=" + id +
                ", areaConhecimento='" + areaConhecimento + '\'' +
                ", descricao='" + descricao + '\'' +
                ", salario=" + salario +
                ", local='" + local + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", anunciante='" + anunciante + '\'' +
                '}';
    }
}
