package modelo;

public class ProductoMenu implements Producto
{
	// Atributos
	private String nombre;
	private int precioBase;
	
	
	// Constructor
	public ProductoMenu(String nombre, int precioBase)
	{
		this.nombre = nombre;
		this.precioBase = precioBase;
	}
	
	
	// Métodos
	@Override
	public String getNombre()
	{
		return this.nombre;
	}
	

	@Override
	public int getPrecio()
	{
		return this.precioBase;
	}


	@Override
	public String generarTextoFactura()
	{
		return ("$" + this.getPrecio() + " |" + this.getNombre());
	}

}
