package consola;

import java.io.File;

import java.io.IOException;

import java.util.HashMap;

import java.util.Map;

import java.io.InputStreamReader;

import java.io.BufferedReader;

import modelo.Combo;
import modelo.Ingrediente;
import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoAjustado;
import modelo.ProductoMenu;
import modelo.Restaurante;

public class Aplicacion
{

	private static Restaurante startRestaurante = new Restaurante();

	public static void main(String[] args) throws IOException
	{

		String nuestroDirectory = System.getProperty("user.dir");

		// Carga de la información.
		startRestaurante.cargarInformacionRestaurante((new File(nuestroDirectory + "/data/ingredientes.txt")), (new File(nuestroDirectory + "/data/menu.txt")),
				(new File(nuestroDirectory + "/data/combos.txt")));

		Aplicacion aplicacion = new Aplicacion();

		aplicacion.ejecutarOpcion();

	}

	public void mostrarMenu()
	{
		System.out.println("\n******************** MENÚ PRINCIPAL ********************\n");
		System.out.println("\nBienvenido al restaurante JJ burgers!");
		System.out.println("\n0. Iniciar un nuevo pedido.");
		System.out.println("\n1. Agregar un elemento a un pedido.");
		System.out.println("\n2. Cerrar un pedido y guardar la factura.");
		System.out.println("\n3. Consultar la información de un pedido dado su ID.");
		System.out.println("\n4. Apagar aplicación.\n");
		System.out.println("*********************************************************\n");
	}

	public void ejecutarOpcion() throws IOException
	{
		System.out.println("Iniciando programa...");

		boolean continuar = true;

		while (continuar)
		{
			try
			{
				mostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 0)
				{
					System.out.println("\n******************** INICIAR UN NUEVO PEDIDO ********************");
					String nombreCliente = new String(input("\nIngrese su nombre"));
					String direccionClient = new String(input("\nIngrese su dirección"));
					startRestaurante.iniciarPedido(nombreCliente, direccionClient);
				} else if (opcion_seleccionada == 1)
				{
					System.out.println("\n******************** AGREGAR UN ELEMENTO ********************");
					System.out.println("\nDesea agregar un (1)PRODUCTO o un (2)COMBO\n");
					int agregar = Integer.parseInt(input("\nSeleccione la opción 1 o 2"));

					// Para agregar un PRODUCTO
					if (agregar == 1)
					{
						System.out.println("\n******************** LISTA PRODUCTOS ********************\n");
						Map<String, Producto> productoHash = new HashMap<>();

						int indice = 0;
						for (Producto p : startRestaurante.getMenuBase())
						{
							System.out.println(indice + " - " + p.getNombre());
							productoHash.put(Integer.toString(indice), p);

							indice++;
						}

						String productoElegido = input("\nIngrese el número del producto que desea agregar"); // Producto elegido
 						String deseaAgregaroQuitar = input("\n¿Desea agregar o quitar un ingrediente? (Y o N)");

						// Para agregar un PRODUCTO AJUSTADO
						if (deseaAgregaroQuitar.equals("Y"))
						{
							System.out.println("\n******************** LISTA INGREDIENTES ********************\n");
							ProductoAjustado ajustado = new ProductoAjustado((ProductoMenu) productoHash.get(productoElegido)); // Producto ajustado
							Map<String, Ingrediente> ingredienteHashMap = new HashMap<>(); // Hash para guardar los ingredientes

							int indiceIngredientes = 0;
							for (Ingrediente i : startRestaurante.getIngredientes())
							{
								System.out.println(indiceIngredientes + " - " + i.getNombre());
								ingredienteHashMap.put(Integer.toString(indiceIngredientes), i);

								indiceIngredientes++;
							}

							System.out.println("\n****Si no desea AGREGAR o ELIMINAR, oprima (ENTER)****");
							String[] agregadosIngredienteStrings = input("\nIngrese el numero de los ingredientes que desea agregar separados POR ESPACIOS").split(" ");
 							String[] eliminadosIngredienteStrings = input("\nIngrese el numero de los ingredientes que desea eliminar separados POR ESPACIOS").split(" ");

							if (!agregadosIngredienteStrings[0].equals("")) // Si no seleccionó nada
							{
								for (String num : agregadosIngredienteStrings)
								{
									ajustado.addAgregado(ingredienteHashMap.get(num));
								}
							}

							if (!eliminadosIngredienteStrings[0].equals("")) // Si no seleccionó nada
							{
								for (String num : eliminadosIngredienteStrings)
								{
									ajustado.addEliminado(ingredienteHashMap.get(num));
								}
							}

							// Se agrega el producto ajustado al pedido
							startRestaurante.getPedidoEnCurso().agregarProducto(ajustado);
						}

						else
						{
							// Se agrega el producto normal al pedido
							startRestaurante.getPedidoEnCurso().agregarProducto(productoHash.get(productoElegido));
						}

					}
					// Para agregar un combo
					if (agregar == 2)
					{
						System.out.println("\n******************** LISTA COMBOS ********************\n");
						Map<String, Combo> combosHashMap = new HashMap<>();
						int indiceCombos = 0;
						for (Combo c : startRestaurante.getCombos())
						{
							System.out.println(indiceCombos + " - " + c.getNombre());
							combosHashMap.put(Integer.toString(indiceCombos), c);
							indiceCombos++;
						}

						String comboSeleccionado = input("\nIngrese el número del combo que desea");

						startRestaurante.getPedidoEnCurso().agregarProducto(combosHashMap.get(comboSeleccionado));
					}

				}

				// OPCION 2 DEL MENÚ
				else if (opcion_seleccionada == 2)
				{
					startRestaurante.cerrarYGuardarPedido();
				}
				
				
				// OPCION 3 DEL MENÚ
				else if (opcion_seleccionada == 3)
				{
					System.out.println("\n******************** CONSULTA POR ID ********************\n");
					int numPedidos = 0;
					Map<String, Pedido> pedidosIDHashMap = new HashMap<>();
					for (Pedido p : Restaurante.getPedidos())
					{
						System.out.println(numPedidos + " - " + p.getIdPedido());
						pedidosIDHashMap.put(Integer.toString(numPedidos), p);

						numPedidos++;
					}

					String idPedidoInputString = input("\nIngrese la opción del pedido que desea ver\n\nEscriba únicamente el índice (Ejemplo: si se muestra 0 - 158531 ingrese 0)\"");

 					System.out.println("\n******************** FACTURA ********************\n");

 					System.out.print("\nPedido: " + pedidosIDHashMap.get(idPedidoInputString).getIdPedido() + "\n");

					System.out.println(pedidosIDHashMap.get(idPedidoInputString).generarTextoFactura());
				}
				
				
				//OPCION 4 DEL MENÚ
				else if (opcion_seleccionada == 4)
				{
					System.out.println("\nSaliendo de la aplicación ...");
					System.out.println("....");
					System.out.println("...");
					System.out.println("..");
					System.out.println(".");
					System.out.println("\nBYE :)");
					continuar = false;
				}

			} catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");

			}
		}
	}

	// Método para poder usar input()
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		} catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
}
