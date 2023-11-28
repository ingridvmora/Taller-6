package modelo;

public class IngredienteRepetidoException extends HamburguesaException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IngredienteRepetidoException(String nombreIngrediente) {
        super("Ingrediente repetido: " + nombreIngrediente);
    }
}


