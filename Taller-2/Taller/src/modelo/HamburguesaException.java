package modelo;

public abstract class HamburguesaException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HamburguesaException(String mesage) {
        super(mesage);
    }
}
