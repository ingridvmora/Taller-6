package modelo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.io.BufferedReader;

public class Restaurante
{
	// Atributos
	private ArrayList<Ingrediente> ingredientes = new ArrayList<>();
	private ArrayList<Producto> menuBase = new ArrayList<>();
	
	private ArrayList<Combo> combos = new ArrayList<>();
	
	private static ArrayList<Pedido> pedidos = new ArrayList<>();
	private Pedido pedidoEnCurso;
	
	
	// Constructor
	public Restaurante()
	{
		super();
	}
	
	
	// Métodos
	public void iniciarPedido(String nombreCliente, String direccionCliente)
	{
		this.pedidoEnCurso = new Pedido(nombreCliente, direccionCliente);
	}
	
	
	public void cerrarYGuardarPedido() throws IOException
	{
		String nuestroDirectory = System.getProperty("user.dir") + "/facturas/";
		
		File newFile = new File(nuestroDirectory + this.getPedidoEnCurso().getIdPedido() + ".txt");
		this.getPedidoEnCurso().guardarFactura(newFile);
		
		Restaurante.pedidos.add(pedidoEnCurso);
		this.pedidoEnCurso = null; // Se cierra el pedido.
	}
	
	
	public Pedido getPedidoEnCurso()
	{
		return this.pedidoEnCurso;
	}
	
	
	public ArrayList<Producto> getMenuBase()
	{
		return this.menuBase;
	}
	
	
	public ArrayList<Ingrediente> getIngredientes()
	{
		return this.ingredientes;
	}
	
	
	public ArrayList<Combo> getCombos()
	{
		return this.combos;
	}
	
	
	public static ArrayList<Pedido> getPedidos()
	{
		return Restaurante.pedidos;
	}
	
	
	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) throws IOException, IngredienteRepetidoException, ProductoRepetidoException
	{
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos);
	}
	
	
	 private void cargarIngredientes(File archivoIngredientes) throws IOException, IngredienteRepetidoException {
	        archivoIngredientes.createNewFile();
	        Reader targetReader = new FileReader(archivoIngredientes);

	        try (BufferedReader br = new BufferedReader(targetReader)) {
	            String linea = br.readLine();

	            while (linea != null) {
	                String[] parteStrings = linea.split(";");

	                String nombreIngrediente = parteStrings[0];
	                int precioIngrediente = Integer.parseInt(parteStrings[1]);

	                Ingrediente nuevoIngrediente = new Ingrediente(nombreIngrediente, precioIngrediente);

	                if (ingredientes.contains(nuevoIngrediente)) {
	                    throw new IngredienteRepetidoException(nombreIngrediente);
	                }

	                this.ingredientes.add(nuevoIngrediente);
	                linea = br.readLine();
	            }
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	    }
	
	
	 private void cargarMenu(File archivoMenu) throws IOException, ProductoRepetidoException {
	        archivoMenu.createNewFile();
	        Reader targetReader = new FileReader(archivoMenu);

	        try (BufferedReader br = new BufferedReader(targetReader)) {
	            String linea = br.readLine();

	            while (linea != null) {
	                String[] parteStrings = linea.split(";");

	                String nombreProducto = parteStrings[0];
	                int precioProducto = Integer.parseInt(parteStrings[1]);

	                ProductoMenu nuevoProducto = new ProductoMenu(nombreProducto, precioProducto);

	                if (menuBase.contains(nuevoProducto)) {
	                    throw new ProductoRepetidoException(nombreProducto);
	                }

	                this.menuBase.add(nuevoProducto);
	                linea = br.readLine();
	            }
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	    }
	
	
	private void cargarCombos(File archivoCombos) throws IOException
	{
		//combos.txt
		archivoCombos.createNewFile();
		Reader targetReader = new FileReader(archivoCombos);
		
		try (BufferedReader br = new BufferedReader(targetReader)) {
			String linea = br.readLine();
			
			while (linea != null)
			{
				// Separar los valors que están en linea separados por ;
				String[] parteStrings = linea.split(";");
				
				String nombreComboString = parteStrings[0];
				
				double descuento = Double.parseDouble((parteStrings[1]).split("%")[0])/100;
				
				Combo nuevoCombo = new Combo(nombreComboString, descuento);
				
				int iteracion = 0; // Para saber cuándo empiezan los productos.
				for (String indice: parteStrings)
				{
					if (iteracion >= 2)
					{	
						for (Producto p: this.menuBase)
						{
							
							if (p.getNombre().equals(indice)) // Si el nombre del producto es igual al String indice
							{
								nuevoCombo.agregarItemACombo(p);
							}
						}
					}
					iteracion ++;
				}
				
				
				this.combos.add(nuevoCombo);
				
				linea = br.readLine();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
