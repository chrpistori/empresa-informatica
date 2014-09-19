package kgp.enums;

public enum SituacaoTarefa {
	NOVO(1, "Novo") , A_FAZER(2, "A Fazer"), EM_ANDAMENTO(3, "Em Andamento"), CONCLUIDO(4, "Concluído");
	
	private Integer codigo;
	private String descricao;
	
	private SituacaoTarefa(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	private SituacaoTarefa(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	
	
	
}
