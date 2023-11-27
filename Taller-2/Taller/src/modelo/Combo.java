package modelo;

import java.util.ArrayList;

public class Combo implements Producto
{
	// Atributos
	private double descuento; // El porcentaje. Ej: 7% == 0.07;
	private String nombreCombo;
	
	private ArrayList<Producto> itemsCombo = new ArrayList<>();
	
	
	// Constructor
	public Combo(String nombre, double descuento)
	{
		this.descuento = descuento;
		this.nombreCombo = nombre;
	}
	
	
	// Métodos
	public void agregarItemACombo(Producto itemCombo)
	{
		this.itemsCombo.add(itemCombo);
	}
	

	@Override
	public int getPrecio()
	{
		int precioSinDescuento = 0;
		for (Producto p: this.itemsCombo)
		{
			precioSinDescuento += p.getPrecio();
		}
		
		return (int) (precioSinDescuento - (precioSinDescuento * this.descuento));
	}


	@Override
	public String generarTextoFactura()
	{
		return ("$" + this.getPrecio() + " |" + this.getNombre());
	}
	
	
	@Override
	public String getNombre()
	{
		return this.nombreCombo;
	}
	
	
	public ArrayList<Producto> getProductos()
	{
		return this.itemsCombo;
	}

}
