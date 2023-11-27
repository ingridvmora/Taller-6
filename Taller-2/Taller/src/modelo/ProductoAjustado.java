package modelo;

import java.util.ArrayList;

public class ProductoAjustado implements Producto
{
	// Atributos
	private Producto base;

	private ArrayList<Ingrediente> agregados = new ArrayList<>();
	private ArrayList<Ingrediente> eliminados = new ArrayList<>();

	// Constructor
	public ProductoAjustado(ProductoMenu base)
	{
		super();

		this.base = base;
	}

	// Herencia
	
	public void addAgregado(Ingrediente nuevo)
	{
		agregados.add(nuevo);
	}
	
	public void addEliminado(Ingrediente viejo)
	{
		eliminados.add(viejo);
	}
	

	@Override
	public String getNombre()
	{
		String nombreString = "";

		nombreString += this.base.getNombre();

		if (this.agregados.size() != 0)
		{
			nombreString += " CON ";
			for (Ingrediente i : this.agregados)
			{
				nombreString += " "+ i.getNombre();
			}
		}
		
		if (this.eliminados.size() != 0)
		{
			nombreString += " SIN ";
			for (Ingrediente i: this.eliminados)
			{
				nombreString += " " + i.getNombre();
			}
		}
		
		return nombreString;
	}

	@Override
	public int getPrecio()
	{
		int precioAgregado = 0;

		for (Ingrediente i : agregados)
		{
			precioAgregado += i.getCostoAdicional();
		}

		return (this.base.getPrecio() + precioAgregado);
	}

	@Override
	public String generarTextoFactura()
	{
		return ("$" + this.getPrecio() + " |" + this.getNombre());
	}
	

}
