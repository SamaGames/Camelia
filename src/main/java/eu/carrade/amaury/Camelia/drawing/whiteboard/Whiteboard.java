package eu.carrade.amaury.Camelia.drawing.whiteboard;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorWhite;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorUtils;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import eu.carrade.amaury.Camelia.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;


public class Whiteboard {

	/**
	 * An angle under the whiteboard, with the smallest two coordinates (x and z).
	 */
	private Location bottomAngle = null;

	/**
	 * An angle above the whiteboard, opposed to the first one, so with the biggest
	 * two coordinates (x and z).
	 */
	private Location topAngle = null;
	
	private final int width;
	private final int height;
	
	private final PixelColor[][] board;

	private final WhiteboardOrientation orientation;

	/**
	 * These blocs of the screen are on cooldown: they cannot be changed when in
	 * this list, to avoid fast color mixes when drawing.
	 */
	private final Set<WhiteboardLocation> onCooldownLocations = new CopyOnWriteArraySet<>();

	public Whiteboard() {
		try {

			Location configCorner1 = Utils.stringToLocation(Camelia.getInstance().getArenaConfig().getString("whiteboard.corner1"));
			Location configCorner2 = Utils.stringToLocation(Camelia.getInstance().getArenaConfig().getString("whiteboard.corner2"));

			bottomAngle = new Location(configCorner1.getWorld(),
					Math.min(configCorner1.getBlockX(), configCorner2.getBlockX()),
					Math.min(configCorner1.getBlockY(), configCorner2.getBlockY()),
					Math.min(configCorner1.getBlockZ(), configCorner2.getBlockZ()));

			topAngle = new Location(configCorner1.getWorld(),
					Math.max(configCorner1.getBlockX(), configCorner2.getBlockX()),
					Math.max(configCorner1.getBlockY(), configCorner2.getBlockY()),
					Math.max(configCorner1.getBlockZ(), configCorner2.getBlockZ()));


		} catch (IllegalArgumentException e) {
			Camelia.getInstance().getLogger().log(Level.SEVERE, "Invalid whiteboard configuration!!!", e);
			Camelia.getInstance().getLogger().log(Level.SEVERE, "Disabling the plugin.");
			Camelia.getInstance().disable();
		}


		if(bottomAngle != null && topAngle != null) {

			orientation = bottomAngle.getBlockX() == topAngle.getBlockX() ? WhiteboardOrientation.FOLLOWING_X_AXIS : WhiteboardOrientation.FOLLOWING_Z_AXIS;

			width = orientation == WhiteboardOrientation.FOLLOWING_X_AXIS ? topAngle.getBlockZ() - bottomAngle.getBlockZ() + 1 : topAngle.getBlockX() - bottomAngle.getBlockX() + 1;
			height = topAngle.getBlockY() - bottomAngle.getBlockY() + 1;

			board = new PixelColor[width][height];

			clearBoard(); // To remove all null values
		}
		else {
			orientation = null;
			width = height = 0;
			board = null;
		}
	}


	/**
	 * Returns true if this location is on the whiteboard
	 *
	 * @param location The location
	 *
	 * @return True if on the whiteboard.
	 */
	public boolean isOnTheWhiteboard(Location location) {

		return location != null
				&& location.getWorld().equals(getWhiteboardWorld())
				&& location.getBlockX() >= bottomAngle.getBlockX() && location.getBlockY() >= bottomAngle.getY() && location.getBlockZ() >= bottomAngle.getBlockZ()
				&& location.getBlockX() <= topAngle.getBlockX() && location.getBlockY() <= topAngle.getBlockY() && location.getBlockZ() <= topAngle.getBlockZ();

	}

	/**
	 * Returns true if this location is on the whiteboard
	 *
	 * @param location The location
	 *
	 * @return True if on the whiteboard.
	 */
	public boolean isOnTheWhiteboard(WhiteboardLocation location) {

		return location != null
				&& location.getWhiteboard().equals(this)
				&& location.getX() >= 0 && location.getY() >= 0
				&& location.getX() < width && location.getY() < height;

	}


	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param color The block to set.
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard and not
	 * on a cooldown).
	 */
	public boolean setBlock(final Location location, final PixelColor color) {
		return setBlock(WhiteboardLocation.fromBukkitLocation(location), color, true);
	}

	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param color The block to set.
	 * @param mix If true, mix this color with the old one.
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard and not
	 * on a cooldown).
	 */
	public boolean setBlock(final Location location, final PixelColor color, boolean mix) {
		return setBlock(WhiteboardLocation.fromBukkitLocation(location), color, mix);
	}

	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param color The block to set.
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard and not
	 * on a cooldown).
	 */
	public boolean setBlock(final WhiteboardLocation location, final PixelColor color) {
		return setBlock(location, color, true);
	}

	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param color The block to set.
	 * @param mix If true, mix this color with the old one.
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard and not
	 * on a cooldown).
	 */
	public boolean setBlock(final WhiteboardLocation location, final PixelColor color, boolean mix) {
		if(!isOnTheWhiteboard(location)) {
			return false;
		}

		if(onCooldownLocations.contains(location)) {
			return false;
		}

		PixelColor baseColor = board[location.getX()][location.getY()];
		PixelColor finalColor = mix ? ColorUtils.getMix(color, baseColor) : color;
		
		board[location.getX()][location.getY()] = finalColor;

		Block block = location.toBukkitLocation().getBlock();
		block.setType(finalColor.getBlock().getType());
		block.setData(finalColor.getBlock().getData());


		onCooldownLocations.add(location);

		Bukkit.getScheduler().runTaskLaterAsynchronously(Camelia.getInstance(), new Runnable() {
			@Override
			public void run() {
				onCooldownLocations.remove(location);
			}
		}, 5l);

		return true;
	}


	/**
	 * Returns the block of the whiteboard targeted by the given player.
	 *
	 * @param player The player.
	 *
	 * @return The block. {@code null} if the target is out of the screen, or too far (200 blocks).
	 */
	public Location getTargetBlock(Player player) {
		Block targetBlock = Utils.getTargetBlock(player, 200);

		if(targetBlock == null) return null;

		Location target = targetBlock.getLocation();

		if(isOnTheWhiteboard(target)) {
			return target;
		}

		return null;
	}


	public void clearBoard() {
		PixelColor color = new ColorWhite(ColorType.BASIC);

		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				setBlock(new WhiteboardLocation(this, x, y), color, true);
			}
		}
	}



	public World getWhiteboardWorld() {
		return bottomAngle.getWorld();
	}

	public Location getBottomAngle() {
		return bottomAngle;
	}

	public Location getTopAngle() {
		return topAngle;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public WhiteboardOrientation getOrientation() {
		return orientation;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Whiteboard)) return false;

		Whiteboard that = (Whiteboard) o;

		return !(bottomAngle != null ? !bottomAngle.equals(that.bottomAngle) : that.bottomAngle != null) && !(topAngle != null ? !topAngle.equals(that.topAngle) : that.topAngle != null);

	}

	@Override
	public int hashCode() {
		int result = bottomAngle != null ? bottomAngle.hashCode() : 0;
		result = 31 * result + (topAngle != null ? topAngle.hashCode() : 0);
		return result;
	}
}
