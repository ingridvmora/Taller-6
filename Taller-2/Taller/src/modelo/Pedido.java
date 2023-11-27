package modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Pedido
{
	// Atributos
	

	private static int numeroPedidos;
	
	private int idPedido;
	
	private String nombreCliente;
	
	private String direccionCliente;
	
	private ArrayList<Producto> itemsPedido = new ArrayList<>();
	
	private static ArrayList<Integer> idsPedidos =  new ArrayList<>();
	
	// Constructor
	
	/**
	 * @param nombreCliente
	 * @param direccionCliente
	 */
	public Pedido(String nombreCliente, String direccionCliente)
	{
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		
		
		Random rand = new Random();
		this.idPedido = rand.nextInt(1000000);
		
		while (Pedido.idsPedidos.contains(this.idPedido))
		{
			this.idPedido = rand.nextInt(1000000);
			Pedido.idsPedidos.add(this.idPedido);
		}
		
		
		
		Pedido.numeroPedidos ++;
	}
	
	
	// Métodos
	
	public int getIdPedido()
	{
		return this.idPedido;
	}
	
	
	public void agregarProducto(Producto nuevoItem)
	{
		this.itemsPedido.add(nuevoItem);
	}
	
	
	private int getPrecioNetoPedido()
	{
		// Sin IVA
		
		int precio = 0;
		
		for (Producto p : itemsPedido)
		{
			precio += p.getPrecio();
		}
		
		return precio;
	}
	
	
	private int getPrecioTotalPedido()
	{
		//Neto + IVA
		return this.getPrecioNetoPedido() + this.getPrecioIVAPedido();
	}
	
	
	private int getPrecioIVAPedido()
	{
		// Cuánto es el IVA
		return (int) (this.getPrecioNetoPedido() * 0.19);
	}
	
	
	public String generarTextoFactura() //TODO Volver PRIVATE
	{
		String facturaString = "";
		facturaString += "\nID: " + this.idPedido + " | Cliente: " + this.nombreCliente + " | Dirección: " + this.direccionCliente; 
		
		for (Producto p: itemsPedido)
		{
			facturaString += "\n- " + p.generarTextoFactura();
		}
		
		facturaString += "\nPrecio Neto: " + this.getPrecioNetoPedido();
		facturaString += "\nPrecio IVA: " + this.getPrecioIVAPedido();
		facturaString += "\nPrecio Total: " + this.getPrecioTotalPedido();
		return facturaString;
	}
	
	
	public void guardarFactura(File archivo) throws IOException
	{
		if (archivo.createNewFile())
		{
			FileWriter myWriter = new FileWriter(archivo);
			myWriter.write(this.generarTextoFactura());
			myWriter.close();
		}
	}

}
