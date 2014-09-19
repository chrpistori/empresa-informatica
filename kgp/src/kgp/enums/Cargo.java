package kgp.enums;

public enum Cargo {
	ANALISTA(1, "Analista"), PROGRAMADOR(2, "Programador"), GERENTE(3, "Gerente"), DIRETOR(4, "Diretor"), ;
	private Integer codigo;
	private String descricao;
	
	private Cargo(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static Cargo findByCodigo(Integer i) {
		for (Cargo cargo : values()) {
			if(cargo.codigo == i) {
				return cargo;
			}
		}
		throw new IllegalArgumentException("Não foi encontrado cargo para o código " + i);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
}