package br.com.santander.icheffv1.model;

public enum Tipo {

	CLIENTE(1, "Cliente"), 
	ADMINISTRADOR(2, "Administrador");	

	private int id;
	private String descricao;

	private Tipo(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Tipo toEnum(Integer id) {
		if (id == null) {
			return null;
		}

		for (Tipo x : Tipo.values()) {
			if (id.equals(x.getId())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id invalid:" + id);
	}

}