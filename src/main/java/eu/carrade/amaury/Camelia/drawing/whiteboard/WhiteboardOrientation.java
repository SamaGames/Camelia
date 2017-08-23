package eu.carrade.amaury.Camelia.drawing.whiteboard;


import org.bukkit.*;

/*
 * This file is part of Camelia.
 *
 * Camelia is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Camelia is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Camelia.  If not, see <http://www.gnu.org/licenses/>.
 */
public enum WhiteboardOrientation {

	/**
	 * The whiteboard plane is on the x0y plane in the Minecraft world.
	 */
	FOLLOWING_X_AXIS,

	/**
	 * The whiteboard plane is on the z0y plane in the Minecraft world.
	 */
	FOLLOWING_Z_AXIS;


	/**
	 * Returns the X whiteboard-coordinate following to this orientation.
	 *
	 * @param board    The whiteboard.
	 * @param location The Bukkit location.
	 *
	 * @return The X-coordinate in the whiteboard.
	 */
	public Integer getWhiteboardX(Whiteboard board, Location location) {
		return getWhiteboardX(board, location.getBlockX(), location.getBlockZ());
	}

	/**
	 * Returns the X whiteboard-coordinate following to this orientation.
	 *
	 * @param board      The whiteboard.
	 * @param minecraftX The X-coordinate in the world.
	 * @param minecraftZ The Z-coordinate in the world.
	 *
	 * @return The X-coordinate in the whiteboard.
	 */
	public Integer getWhiteboardX(Whiteboard board, Integer minecraftX, Integer minecraftZ) {
		switch (this) {
			case FOLLOWING_X_AXIS:
				return minecraftZ - board.getBottomAngle().getBlockZ();

			case FOLLOWING_Z_AXIS:
				return minecraftX - board.getBottomAngle().getBlockX();
		}

		return null; // This cannot be called.
	}

	/**
	 * Converts a whiteboard location to a Bukkit one.
	 *
	 * @param location The {@link WhiteboardLocation}.
	 *
	 * @return The Bukkit {@link Location}.
	 */
	public Location getBukkitLocation(WhiteboardLocation location) {
		int x = 0, z = 0;

		switch (this) {
			case FOLLOWING_X_AXIS:
				x = location.getWhiteboard().getTopAngle().getBlockX();
				z = location.getWhiteboard().getBottomAngle().getBlockZ() + location.getX();
				break;

			case FOLLOWING_Z_AXIS:
				x = location.getWhiteboard().getBottomAngle().getBlockX() + location.getX();
				z = location.getWhiteboard().getTopAngle().getBlockZ();
				break;
		}

		return new Location(
				location.getWhiteboard().getWhiteboardWorld(),
				x,
				location.getWhiteboard().getBottomAngle().getBlockY() + location.getY(),
				z
		);
	}
}
