package kgp.enums;

public enum Perfil {
	ADMINISTRADOR(1, "Administrador"), GERENTE(2, "Gerente de Projetos"), MEMBRO(3, "Membro de Equipe");
	private Integer codigo;
	private String descricao;
	
	private Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static Perfil findByCodigo(Integer i) {
		for (Perfil perfil : values()) {
			if(perfil.codigo == i) {
				return perfil;
			}
		}
		throw new IllegalArgumentException("Não foi encontrado perfil para o código " + i);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
}