package modelo;

public class Ingrediente
{
	// Atributos
	private String nombre;
	
	private int costoAdicional;
	
	// Constructor
	
	public Ingrediente(String nombre, int costoAdicional)
	{
		this.nombre = nombre;
		this.costoAdicional = costoAdicional;
	}

	
	// Métodos
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public int getCostoAdicional()
	{
		return this.costoAdicional;
	}
	

}
