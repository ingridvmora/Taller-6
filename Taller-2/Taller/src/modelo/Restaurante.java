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
	public void agregarProductoAlPedido(Producto producto) {
        try {
            if (pedidoEnCurso != null) {
                pedidoEnCurso.agregarProducto(producto);
            } else {
                System.err.println("Error: No hay un pedido en curso. Inicia un pedido primero.");
            }
        } catch (PedidoExcepcion e) {
            System.err.println("Error: " + e.getMessage());
        }
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
	
	
	 private void cargarMenu(File archivoMenu) throws IOException {
		    archivoMenu.createNewFile();
		    Reader targetReader = new FileReader(archivoMenu);

		    try (BufferedReader br = new BufferedReader(targetReader)) {
		        String linea = br.readLine();

		        while (linea != null) {
		            String[] parteStrings = linea.split(";");

		            if (parteStrings.length < 2) {
		                // Imprimir mensaje de error y continuar con la siguiente línea
		                System.err.println("Error: Formato inválido para el menú: " + linea);
		                linea = br.readLine();
		                continue;
		            }

		            String nombreProducto = parteStrings[0];
		            try {
		                int precioProducto = Integer.parseInt(parteStrings[1]);

		                ProductoMenu nuevoProducto = new ProductoMenu(nombreProducto, precioProducto);

		                if (menuBase.contains(nuevoProducto)) {
		                    // Imprimir mensaje de error y continuar con la siguiente línea
		                    System.err.println("Error: Producto repetido en el menú: " + nombreProducto);
		                    linea = br.readLine();
		                    continue;
		                }

		                this.menuBase.add(nuevoProducto);
		            } catch (NumberFormatException e) {
		                // Imprimir mensaje de error y continuar con la siguiente línea
		                System.err.println("Error: Formato inválido para el menú: " + linea);
		            }

		            linea = br.readLine();
		        }
		    }
		}

	
	
	 private void cargarCombos(File archivoCombos) throws IOException {
		    archivoCombos.createNewFile();
		    Reader targetReader = new FileReader(archivoCombos);

		    try (BufferedReader br = new BufferedReader(targetReader)) {
		        String linea = br.readLine();

		        while (linea != null) {
		            String[] parteStrings = linea.split(";");

		            if (parteStrings.length < 2) {
		                // Imprimir mensaje de error y continuar con la siguiente línea
		                System.err.println("Error: Formato inválido para el combo: " + linea);
		                linea = br.readLine();
		                continue;
		            }

		            String nombreComboString = parteStrings[0];

		            try {
		                double descuento = Double.parseDouble((parteStrings[1]).split("%")[0]) / 100;

		                Combo nuevoCombo = new Combo(nombreComboString, descuento);

		                int iteracion = 0; // Para saber cuándo empiezan los productos.
		                for (String indice : parteStrings) {
		                    if (iteracion >= 2) {
		                        for (Producto p : this.menuBase) {
		                            if (p.getNombre().equals(indice)) {
		                                nuevoCombo.agregarItemACombo(p);
		                            }
		                        }
		                    }
		                    iteracion++;
		                }

		                this.combos.add(nuevoCombo);
		            } catch (NumberFormatException e) {
		                // Imprimir mensaje de error y continuar con la siguiente línea
		                System.err.println("Error: Formato inválido para el combo: " + linea);
		            }

		            linea = br.readLine();
		        }
		    }
		}

		
	
}
