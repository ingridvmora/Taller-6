/**
 * 
 */
package modelo;

/**
 * 
 */
public class ProductoRepetidoException extends HamburguesaException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductoRepetidoException(String nombreProducto) {
        super("Producto repetido en el menú: " + nombreProducto);
    }
}

