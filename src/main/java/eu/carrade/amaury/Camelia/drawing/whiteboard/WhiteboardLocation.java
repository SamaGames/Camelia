package eu.carrade.amaury.Camelia.drawing.whiteboard;


import eu.carrade.amaury.Camelia.Camelia;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * A location on the whiteboard, world- and whiteboard-orientation-independent.
 */
public class WhiteboardLocation {

	/**
	 * The related whiteboard.
	 */
	private final Whiteboard whiteboard;

	/**
	 * The X coordinate: horizontal one.
	 */
	private int x;

	/**
	 * The Y coordinate: vertical one (just like Minecraft one).
	 */
	private int y;

	/**
	 * The {@link org.bukkit.Location} object representing the block in the
	 * world.
	 */
	private Location bukkitLocation;


	/**
	 * Constructor, using a custom whiteboard.
	 *
	 * @param whiteboard The {@link Whiteboard} to use.
	 * @param x The x-coordinate (horizontal)
	 * @param y The y-coordinate (vertical)
	 */
	public WhiteboardLocation(Whiteboard whiteboard, int x, int y) {

		Validate.notNull(whiteboard, "The whiteboard cannot be null!");

		this.whiteboard = whiteboard;
		this.x = x;
		this.y = y;

		updateBukkitLocation();
	}

	/**
	 * Constructor, using the main whiteboard.
	 *
	 * @param x The x-coordinate (horizontal)
	 * @param y The y-coordinate (vertical)
	 */
	public WhiteboardLocation(int x, int y) {
		this(Camelia.getInstance().getWhiteboard(), x, y);
	}


	public Whiteboard getWhiteboard() {
		return whiteboard;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}


	public void setX(int x) {
		this.x = x;

		updateBukkitLocation();
	}

	public void setY(int y) {
		this.y = y;

		updateBukkitLocation();
	}


	/**
	 * Converts this WhiteboardLocation to a Bukkit one.
	 *
	 * @return The {@link org.bukkit.Location} object representing the block in the
	 * world.
	 */
	public Location toBukkitLocation() {
		return bukkitLocation.clone();
	}

	/**
	 * Updates the Bukkit internal location.
	 */
	private void updateBukkitLocation() {
		bukkitLocation = whiteboard.getOrientation().getBukkitLocation(this);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof WhiteboardLocation)) return false;

		WhiteboardLocation that = (WhiteboardLocation) o;

		return x == that.x && y == that.y && whiteboard.equals(that.whiteboard);

	}

	@Override
	public int hashCode() {
		int result = whiteboard.hashCode();
		result = 31 * result + x;
		result = 31 * result + y;
		return result;
	}

	@Override
	public String toString() {
		return "WhiteboardLocation{" +
				"whiteboard=" + whiteboard +
				", x=" + x +
				", y=" + y +
				'}';
	}

	/**
	 * Converts a Bukkit Location to a WhiteboardLocation.
	 *
	 * @param bukkitLocation The Bukkit Location.
	 *
	 * @return The WhiteboardLocation object, or {@code null} if this location is not
	 * in the default whiteboard.
	 */
	public static WhiteboardLocation fromBukkitLocation(Location bukkitLocation) {
		return fromBukkitLocation(Camelia.getInstance().getWhiteboard(), bukkitLocation);
	}

	/**
	 * Converts a Bukkit Location to a WhiteboardLocation.
	 *
	 * @param board The {@link Whiteboard} to use.
	 * @param bukkitLocation The Bukkit Location.
	 *
	 * @return The WhiteboardLocation object, or {@code null} if this location is not
	 * in the given whiteboard.
	 */
	public static WhiteboardLocation fromBukkitLocation(Whiteboard board, Location bukkitLocation) {
		if(bukkitLocation == null) return null;

		return new WhiteboardLocation(
				board,
				board.getOrientation().getWhiteboardX(board, bukkitLocation),
				bukkitLocation.getBlockY() - board.getBottomAngle().getBlockY()
		);
	}
}
