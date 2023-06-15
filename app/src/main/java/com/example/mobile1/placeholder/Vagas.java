package com.example.mobile1.placeholder;

public class Vagas {

    private int id;
    private String areaConhecimento;
    private String descricao;
    private String salario;
    private String local;
    private String email;
    private String telefone;
    private String anunciante;

    private String dataInicio;

    private String dataFim;

    public boolean ativo;

    public Vagas(int id, String areaConhecimento, String descricao, String salario, String local, String email, String telefone,String dataInicio,String dataFim, String anunciante, boolean ativo) {
        this.id = id;
        this.areaConhecimento = areaConhecimento;
        this.descricao = descricao;
        this.salario = salario;
        this.local = local;
        this.email = email;
        this.telefone = telefone;
        this.anunciante = anunciante;
        this.dataFim = dataFim;
        this.dataInicio = dataInicio;
        this.ativo = ativo;
    }
    public Vagas() {
        // Construtor vazio
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
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

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
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
