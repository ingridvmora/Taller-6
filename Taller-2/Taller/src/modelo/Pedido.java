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
	 private static final int LIMITE_PEDIDO = 150000;
	
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
		
		
		
		Pedido.setNumeroPedidos(Pedido.getNumeroPedidos() + 1);
	}
	
	
	// Métodos
	
	public int getIdPedido()
	{
		return this.idPedido;
	}
	
	
	public void agregarProducto(Producto nuevoItem) throws PedidoExcepcion {
        if (calcularTotal() + nuevoItem.getPrecio() > LIMITE_PEDIDO) {
            throw new PedidoExcepcion("El pedido no puede superar el límite de 150,000 pesos.");
        }

        this.itemsPedido.add(nuevoItem);
    }
	
	private int calcularTotal() {
        int total = 0;
        for (Producto p : itemsPedido) {
            total += p.getPrecio();
        }
        return total;
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


	public static int getNumeroPedidos() {
		return numeroPedidos;
	}


	public static void setNumeroPedidos(int numeroPedidos) {
		Pedido.numeroPedidos = numeroPedidos;
	}

}
