package kgp.enums;

public enum Prioridade {
	SEM_PRIORIDADE(0, "Sem Prioridade"), BAIXA(1, "Baixa"), MEDIA(2, "M�dia"), ALTA(3, "Alta");
	private Integer codigo;
	private String descricao;
	
	private Prioridade(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static Prioridade findByCodigo(Integer i) {
		for (Prioridade prioridade : values()) {
			if(prioridade.codigo == i) {
				return prioridade;
			}
		}
		throw new IllegalArgumentException("N�o foi encontrada prioridade para o c�digo " + i);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
}
